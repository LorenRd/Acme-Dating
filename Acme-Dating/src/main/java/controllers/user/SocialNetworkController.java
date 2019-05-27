
package controllers.user;

import java.util.Arrays;

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

import services.SocialNetworkService;
import controllers.AbstractController;
import domain.SocialNetwork;

@Controller
@RequestMapping("/socialNetwork")
public class SocialNetworkController extends AbstractController {

	//Services

	@Autowired
	private SocialNetworkService	socialNetworkService;


	//Edit
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int socialNetworkId) {
		ModelAndView result;
		SocialNetwork socialNetwork;
		socialNetwork = this.socialNetworkService.findOne(socialNetworkId);
		Assert.notNull(socialNetwork);
		result = this.createEditModelAndView(socialNetwork);

		return result;
	}

	//Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		SocialNetwork socialNetwork;
		socialNetwork = this.socialNetworkService.create();
		result = this.createModelAndView(socialNetwork);
		return result;
	}

	//Save de create
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "create")
	public ModelAndView create(@ModelAttribute("socialNetwork") SocialNetwork socialNetwork, final BindingResult binding) {
		ModelAndView result;

		try {
			socialNetwork = this.socialNetworkService.reconstructPruned(socialNetwork, binding);
			if (binding.hasErrors()) {
				result = this.createEditModelAndView(socialNetwork);
				for (final ObjectError e : binding.getAllErrors())
					System.out.println(e.getObjectName() + " error [" + e.getDefaultMessage() + "] " + Arrays.toString(e.getCodes()));
			} else {
				socialNetwork = this.socialNetworkService.save(socialNetwork);
				result = new ModelAndView("redirect:/welcome/index.do");
			}
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(socialNetwork, "socialNetwork.commit.error");
		}
		return result;
	}

	//Save de edit
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("socialNetwork") SocialNetwork socialNetwork, final BindingResult binding) {
		ModelAndView result;

		try {
			socialNetwork = this.socialNetworkService.reconstructPruned(socialNetwork, binding);
			if (binding.hasErrors()) {
				result = this.createEditModelAndView(socialNetwork);
				for (final ObjectError e : binding.getAllErrors())
					System.out.println(e.getObjectName() + " error [" + e.getDefaultMessage() + "] " + Arrays.toString(e.getCodes()));
			} else {
				socialNetwork = this.socialNetworkService.save(socialNetwork);
				result = new ModelAndView("redirect:/welcome/index.do");
			}
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(socialNetwork, "socialNetwork.commit.error");
		}
		return result;
	}

	private ModelAndView createEditModelAndView(final SocialNetwork socialNetwork) {
		ModelAndView result;
		result = this.createEditModelAndView(socialNetwork, null);
		return result;
	}

	private ModelAndView createEditModelAndView(final SocialNetwork socialNetwork, final String messageCode) {
		ModelAndView result;
		result = new ModelAndView("socialNetwork/edit");
		result.addObject("socialNetwork", socialNetwork);
		result.addObject("message", messageCode);
		return result;
	}

	private ModelAndView createModelAndView(final SocialNetwork socialNetwork) {
		ModelAndView result;
		result = this.createModelAndView(socialNetwork, null);
		return result;
	}

	private ModelAndView createModelAndView(final SocialNetwork socialNetwork, final String messageCode) {
		ModelAndView result;
		result = new ModelAndView("socialNetwork/create");
		result.addObject("socialNetwork", socialNetwork);
		result.addObject("message", messageCode);
		return result;
	}
}
