
package controllers.user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.validation.Valid;

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

import services.CustomisationService;
import services.SocialNetworkService;
import services.UserService;
import controllers.AbstractController;
import domain.SocialNetwork;
import domain.User;
import forms.UserForm;

@Controller
@RequestMapping("/user")
public class UserController extends AbstractController {

	// Services

	@Autowired
	private UserService				userService;

	@Autowired
	private SocialNetworkService	socialNetworkService;

	@Autowired
	private CustomisationService	customisationService;


	//Display
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(required = false) final Integer userId) {
		final ModelAndView result;
		Collection<SocialNetwork> socialNetworks = new ArrayList<SocialNetwork>();
		User user = new User();

		if (userId == null)
			user = this.userService.findByPrincipal();
		else
			user = this.userService.findOne(userId);
		socialNetworks = this.socialNetworkService.findByUserId(user.getId());
		result = new ModelAndView("user/display");
		result.addObject("user", user);
		result.addObject("socialNetworks", socialNetworks);

		return result;

	}

	//Edit
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		User user;
		user = this.userService.findByPrincipal();
		Assert.notNull(user);
		result = this.editModelAndView(user);

		return result;
	}

	// Create
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		User user;
		UserForm userForm;

		user = this.userService.create();
		userForm = this.userService.construct(user);
		result = this.createEditModelAndView(userForm);

		return result;
	}

	//Save de edit
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("user") User user, final BindingResult binding) {
		ModelAndView result;

		try {
			user = this.userService.reconstructPruned(user, binding);
			if (binding.hasErrors()) {
				result = this.editModelAndView(user);
				for (final ObjectError e : binding.getAllErrors())
					System.out.println(e.getObjectName() + " error [" + e.getDefaultMessage() + "] " + Arrays.toString(e.getCodes()));
			} else {
				user = this.userService.save(user);
				result = new ModelAndView("redirect:/welcome/index.do");
			}
		} catch (final Throwable oops) {
			result = this.editModelAndView(user, "user.commit.error");
		}
		return result;
	}

	// Save de Register
	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "register")
	public ModelAndView register(@ModelAttribute("userForm") @Valid final UserForm userForm, final BindingResult binding) {
		ModelAndView result;
		User user;

		try {
			user = this.userService.reconstruct(userForm, binding);
			if (binding.hasErrors()) {
				for (final ObjectError e : binding.getAllErrors())
					System.out.println(e.getObjectName() + " error [" + e.getDefaultMessage() + "] " + Arrays.toString(e.getCodes()));
				result = this.createEditModelAndView(userForm);
			} else {
				user = this.userService.save(user);
				result = new ModelAndView("welcome/index");
			}
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(userForm, "user.commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/delete")
	public ModelAndView delete() {
		ModelAndView result;

		try {
			this.userService.delete();

			result = new ModelAndView("redirect:/j_spring_security_logout");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/user/display.do");
		}

		return result;
	}
	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final UserForm userForm) {
		ModelAndView result;
		result = this.createEditModelAndView(userForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final UserForm userForm, final String messageCode) {
		ModelAndView result;
		String countryCode;

		countryCode = this.customisationService.find().getCountryCode();

		result = new ModelAndView("user/register");

		result.addObject("userForm", userForm);
		result.addObject("countryCode", countryCode);

		result.addObject("message", messageCode);

		return result;
	}

	private ModelAndView editModelAndView(final User user) {
		ModelAndView result;
		result = this.editModelAndView(user, null);
		return result;
	}

	private ModelAndView editModelAndView(final User user, final String messageCode) {
		ModelAndView result;
		String countryCode;

		countryCode = this.customisationService.find().getCountryCode();

		result = new ModelAndView("user/edit");
		result.addObject("user", user);
		result.addObject("countryCode", countryCode);
		result.addObject("message", messageCode);
		return result;
	}
}
