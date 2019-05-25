
package controllers.user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CoupleRequestService;
import services.UserService;
import controllers.AbstractController;
import domain.CoupleRequest;
import domain.User;

@Controller
@RequestMapping("/coupleRequest/user")
public class CoupleRequestUserController extends AbstractController {

	//Services
	@Autowired
	private UserService				userService;

	@Autowired
	private CoupleRequestService	coupleRequestService;


	//List
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Collection<CoupleRequest> coupleRequests;
		User principal;
		boolean requestSended = false;
		boolean haveCouple = false;

		principal = this.userService.findByPrincipal();

		coupleRequests = this.coupleRequestService.findCoupleRequestsByRecipientId(principal.getId());

		for (final CoupleRequest cR : coupleRequests)
			if (cR.getStatus().equals("ACCEPTED")) {
				coupleRequests = new ArrayList<CoupleRequest>();
				coupleRequests.add(cR);
				break;
			}

		if (this.coupleRequestService.findCoupleRequestsBySenderId(principal.getId()) != null)
			requestSended = true;

		if (principal.getCouple() != null)
			haveCouple = true;

		result = new ModelAndView("coupleRequest/list");
		result.addObject("coupleRequests", coupleRequests);
		result.addObject("requestSended", requestSended);
		result.addObject("haveCouple", haveCouple);
		result.addObject("requestURI", "coupleRequest/user/list.do");

		return result;
	}

	//Display
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int coupleRequestId) {
		final ModelAndView result;
		CoupleRequest coupleRequest;

		coupleRequest = this.coupleRequestService.findOne(coupleRequestId);

		result = new ModelAndView("coupleRequest/display");
		result.addObject("coupleRequest", coupleRequest);
		return result;
	}

	//Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		CoupleRequest coupleRequest;

		coupleRequest = this.coupleRequestService.create();
		result = this.createModelAndView(coupleRequest);

		return result;
	}

	//Save de create
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView create(@ModelAttribute("coupleRequest") CoupleRequest coupleRequest, final BindingResult binding) {
		ModelAndView result;

		try {
			coupleRequest = this.coupleRequestService.reconstruct(coupleRequest, binding);
			if (binding.hasErrors()) {
				result = this.createModelAndView(coupleRequest);
				for (final ObjectError e : binding.getAllErrors())
					System.out.println(e.getObjectName() + " error [" + e.getDefaultMessage() + "] " + Arrays.toString(e.getCodes()));
			} else {
				coupleRequest = this.coupleRequestService.save(coupleRequest);
				result = new ModelAndView("redirect:/welcome/index.do");
			}

		} catch (final Throwable oops) {
			result = this.createModelAndView(coupleRequest, "coupleRequest.commit.error");
		}
		return result;
	}

	// Reject
	@RequestMapping(value = "/reject", method = RequestMethod.GET)
	public ModelAndView reject(@RequestParam final int coupleRequestId) {
		ModelAndView result;
		CoupleRequest coupleRequest;
		User principal;
		principal = this.userService.findByPrincipal();

		coupleRequest = this.coupleRequestService.findOne(coupleRequestId);
		Assert.notNull(coupleRequest);
		Assert.isTrue(coupleRequest.getRecipient().getId() == principal.getId() && coupleRequest.getStatus().equals("PENDING"));

		try {
			this.coupleRequestService.reject(coupleRequest);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:display.do");
		}
		return result;

	}

	// Accept
	@RequestMapping(value = "/accept", method = RequestMethod.GET)
	public ModelAndView approve(@RequestParam final int coupleRequestId) {
		ModelAndView result;
		CoupleRequest coupleRequest;
		User principal;
		principal = this.userService.findByPrincipal();

		coupleRequest = this.coupleRequestService.findOne(coupleRequestId);
		Assert.notNull(coupleRequest);
		Assert.isTrue(coupleRequest.getRecipient().getId() == principal.getId() && coupleRequest.getStatus().equals("PENDING"));

		try {
			this.coupleRequestService.accept(coupleRequest);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:display.do?coupleRequestId=" + coupleRequestId);
		}
		return result;
	}

	private ModelAndView createModelAndView(final CoupleRequest coupleRequest) {
		ModelAndView result;

		result = this.createModelAndView(coupleRequest, null);
		return result;
	}

	private ModelAndView createModelAndView(final CoupleRequest coupleRequest, final String messageCode) {
		ModelAndView result;
		Collection<User> recipients;
		Collection<CoupleRequest> hadRequest;
		User principal;

		recipients = new ArrayList<User>();
		for (final User u : this.userService.findAll())
			if (u.getCouple() == null)
				recipients.add(u);

		principal = this.userService.findByPrincipal();
		hadRequest = this.coupleRequestService.findCoupleRequestsByRecipientId(principal.getId());
		for (final CoupleRequest cR : hadRequest)
			recipients.remove(cR.getSender());

		recipients.remove(this.userService.findByPrincipal());

		result = new ModelAndView("coupleRequest/create");
		result.addObject("coupleRequest", coupleRequest);
		result.addObject("recipients", recipients);
		result.addObject("message", messageCode);
		return result;
	}
}
