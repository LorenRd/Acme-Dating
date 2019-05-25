/*
 * AdministratorController.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.administrator;

import java.util.Arrays;

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

import services.AdministratorService;
import services.CustomisationService;
import controllers.AbstractController;
import domain.Administrator;
import forms.AdministratorForm;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private CustomisationService	customisationService;


	// Constructors -----------------------------------------------------------

	public AdministratorController() {
		super();
	}

	//Display
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(required = false) final Integer administratorId) {
		final ModelAndView result;
		Administrator administrator = new Administrator();

		if (administratorId == null)
			administrator = this.administratorService.findByPrincipal();
		else
			administrator = this.administratorService.findOne(administratorId);
		result = new ModelAndView("administrator/display");
		result.addObject("administrator", administrator);
		return result;
	}

	//Edit
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Administrator administrator;
		administrator = this.administratorService.findByPrincipal();
		Assert.notNull(administrator);
		result = this.editModelAndView(administrator);

		return result;
	}

	//Save de edit
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("administrator") Administrator administrator, final BindingResult binding) {
		ModelAndView result;

		try {
			administrator = this.administratorService.reconstructPruned(administrator, binding);
			if (binding.hasErrors()) {
				result = this.editModelAndView(administrator);
				for (final ObjectError e : binding.getAllErrors())
					System.out.println(e.getObjectName() + " error [" + e.getDefaultMessage() + "] " + Arrays.toString(e.getCodes()));
			} else {
				administrator = this.administratorService.save(administrator);
				result = new ModelAndView("redirect:/welcome/index.do");
			}
		} catch (final Throwable oops) {
			result = this.editModelAndView(administrator, "administrator.commit.error");
		}
		return result;
	}

	//Create
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register() {
		ModelAndView res;
		Administrator administrator;
		AdministratorForm administratorForm;
		administrator = this.administratorService.create();
		administratorForm = this.administratorService.construct(administrator);
		res = this.createRegisterModelAndView(administratorForm);
		return res;
	}

	//Save de register
	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "register")
	public ModelAndView register(@ModelAttribute("administratorForm") @Valid final AdministratorForm administratorForm, final BindingResult binding) {
		ModelAndView res;
		Administrator admin;

		try {
			admin = this.administratorService.reconstruct(administratorForm, binding);
			if (binding.hasErrors()) {
				for (final ObjectError e : binding.getAllErrors())
					System.out.println(e.getObjectName() + " error [" + e.getDefaultMessage() + "] " + Arrays.toString(e.getCodes()));
				res = this.createRegisterModelAndView(administratorForm);
			} else {
				admin = this.administratorService.save(admin);
				res = new ModelAndView("welcome/index");
			}
		} catch (final Throwable oops) {
			res = this.createRegisterModelAndView(administratorForm, "administrator.commit.error");
		}
		return res;
	}

	private ModelAndView createRegisterModelAndView(final AdministratorForm administratorForm) {
		ModelAndView result;
		result = this.createRegisterModelAndView(administratorForm, null);
		return result;
	}

	private ModelAndView createRegisterModelAndView(final AdministratorForm administratorForm, final String messageCode) {
		ModelAndView res;
		String countryCode;

		countryCode = this.customisationService.find().getCountryCode();

		res = new ModelAndView("administrator/register");
		res.addObject("administratorForm", administratorForm);
		res.addObject("countryCode", countryCode);
		res.addObject("message", messageCode);

		return res;
	}

	private ModelAndView editModelAndView(final Administrator administrator) {
		ModelAndView result;
		result = this.editModelAndView(administrator, null);
		return result;
	}

	private ModelAndView editModelAndView(final Administrator administrator, final String messageCode) {
		ModelAndView result;
		String countryCode;

		countryCode = this.customisationService.find().getCountryCode();

		result = new ModelAndView("administrator/edit");
		result.addObject("administrator", administrator);
		result.addObject("countryCode", countryCode);
		result.addObject("message", messageCode);

		return result;
	}
}
