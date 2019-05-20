
package controllers.user;

import java.util.Arrays;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CustomisationService;
import services.UserService;
import controllers.AbstractController;
import domain.User;
import forms.UserForm;

@Controller
@RequestMapping("/user")
public class UserController extends AbstractController {

	// Services

	@Autowired
	private UserService				userService;

	@Autowired
	private CustomisationService	customisationService;


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

	//	@RequestMapping(value = "/delete")
	//	public ModelAndView delete() {
	//		ModelAndView result;
	//
	//		try {
	//			this.userService.delete();
	//
	//			result = new ModelAndView("redirect:/j_spring_security_logout");
	//		} catch (final Throwable oops) {
	//			result = new ModelAndView("redirect:/user/display.do");
	//		}
	//
	//		return result;
	//	}
	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final UserForm userForm) {
		ModelAndView result;
		result = this.createEditModelAndView(userForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final UserForm userForm, final String message) {
		ModelAndView result;
		String countryCode;

		countryCode = this.customisationService.find().getCountryCode();
		if (userForm.getId() != 0)
			result = new ModelAndView("user/edit");
		else
			result = new ModelAndView("user/register");

		result.addObject("userForm", userForm);
		result.addObject("countryCode", countryCode);

		result.addObject("message", message);

		return result;
	}
}
