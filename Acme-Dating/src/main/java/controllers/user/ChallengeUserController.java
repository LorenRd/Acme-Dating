
package controllers.user;

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

import services.ChallengeService;
import services.CoupleService;
import services.UserService;
import controllers.AbstractController;
import domain.Challenge;
import domain.Couple;
import domain.User;

@Controller
@RequestMapping("/challenge/user")
public class ChallengeUserController extends AbstractController {

	//Services
	@Autowired
	private UserService			userService;

	@Autowired
	private ChallengeService	challengeService;

	@Autowired
	private CoupleService		coupleService;


	//List
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Collection<Challenge> myChallenges;
		Collection<Challenge> hisHerChallenges;
		User principal;

		principal = this.userService.findByPrincipal();

		result = new ModelAndView("challenge/list");
		result.addObject("requestURI", "challenge/user/list.do");

		if (principal.getCouple() == null) {
			result.addObject("couple", null);

			return result;
		} else {

			final Couple couple = this.coupleService.findByUser();
			myChallenges = this.challengeService.findByRecipientId(principal.getId());
			hisHerChallenges = this.challengeService.findBySenderId(principal.getId());

			result.addObject("myChallenges", myChallenges);
			result.addObject("hisHerChallenges", hisHerChallenges);
			result.addObject("couple", couple);

			return result;
		}
	}

	//Display
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int challengeId) {
		final ModelAndView result;
		Challenge challenge;

		challenge = this.challengeService.findOne(challengeId);

		result = new ModelAndView("challenge/display");
		result.addObject("challenge", challenge);
		return result;
	}

	//Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Challenge challenge;

		challenge = this.challengeService.create();
		result = this.createModelAndView(challenge);

		return result;
	}

	//Save de create
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView create(@ModelAttribute("challenge") Challenge challenge, final BindingResult binding) {
		ModelAndView result;

		try {
			challenge = this.challengeService.reconstruct(challenge, binding);
			if (binding.hasErrors()) {
				result = this.createModelAndView(challenge);
				for (final ObjectError e : binding.getAllErrors())
					System.out.println(e.getObjectName() + " error [" + e.getDefaultMessage() + "] " + Arrays.toString(e.getCodes()));
			} else {
				challenge = this.challengeService.save(challenge);
				result = new ModelAndView("redirect:display.do?challengeId=" + challenge.getId());
			}

		} catch (final Throwable oops) {
			result = this.createModelAndView(challenge, "challenge.commit.error");
		}
		return result;
	}

	// Reject
	@RequestMapping(value = "/reject", method = RequestMethod.GET)
	public ModelAndView reject(@RequestParam final int challengeId) {
		ModelAndView result;
		Challenge challenge;
		User principal;
		principal = this.userService.findByPrincipal();

		challenge = this.challengeService.findOne(challengeId);
		Assert.notNull(challenge);
		Assert.isTrue(challenge.getRecipient().getId() == principal.getId() && challenge.getStatus().equals("PENDING"));

		try {
			this.challengeService.reject(challenge);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:display.do?challengeId=" + challengeId);
		}
		return result;

	}

	// Accept
	@RequestMapping(value = "/accept", method = RequestMethod.GET)
	public ModelAndView accept(@RequestParam final int challengeId) {
		ModelAndView result;
		Challenge challenge;
		User principal;
		principal = this.userService.findByPrincipal();

		challenge = this.challengeService.findOne(challengeId);
		Assert.notNull(challenge);
		Assert.isTrue(challenge.getRecipient().getId() == principal.getId() && challenge.getStatus().equals("PENDING"));

		try {
			this.challengeService.accept(challenge);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:display.do?challengeId=" + challengeId);
		}
		return result;
	}

	// Complete
	@RequestMapping(value = "/complete", method = RequestMethod.GET)
	public ModelAndView complete(@RequestParam final int challengeId) {
		ModelAndView result;
		Challenge challenge;
		User principal;
		principal = this.userService.findByPrincipal();

		challenge = this.challengeService.findOne(challengeId);
		Assert.notNull(challenge);
		Assert.isTrue(challenge.getSender().getId() == principal.getId() && challenge.getStatus().equals("ACCEPTED"));

		try {
			this.challengeService.complete(challenge);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:display.do?challengeId=" + challengeId);
		}
		return result;
	}

	private ModelAndView createModelAndView(final Challenge challenge) {
		ModelAndView result;

		result = this.createModelAndView(challenge, null);
		return result;
	}

	private ModelAndView createModelAndView(final Challenge challenge, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("challenge/create");
		result.addObject("challenge", challenge);
		result.addObject("message", messageCode);
		return result;
	}
}
