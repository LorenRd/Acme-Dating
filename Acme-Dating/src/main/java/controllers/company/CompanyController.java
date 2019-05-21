
package controllers.company;

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

import services.CompanyService;
import services.CustomisationService;
import controllers.AbstractController;
import domain.Company;
import forms.CompanyForm;

@Controller
@RequestMapping("/company")
public class CompanyController extends AbstractController {

	// Services

	@Autowired
	private CompanyService			companyService;

	@Autowired
	private CustomisationService	customisationService;


	// Create
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Company company;
		CompanyForm companyForm;

		company = this.companyService.create();
		companyForm = this.companyService.construct(company);
		result = this.createEditModelAndView(companyForm);

		return result;
	}

	// Save de Register
	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "register")
	public ModelAndView register(@ModelAttribute("companyForm") @Valid final CompanyForm companyForm, final BindingResult binding) {
		ModelAndView result;
		Company company;

		try {
			company = this.companyService.reconstruct(companyForm, binding);
			if (binding.hasErrors()) {
				for (final ObjectError e : binding.getAllErrors())
					System.out.println(e.getObjectName() + " error [" + e.getDefaultMessage() + "] " + Arrays.toString(e.getCodes()));
				result = this.createEditModelAndView(companyForm);
			} else {
				company = this.companyService.save(company);
				result = new ModelAndView("welcome/index");
			}
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(companyForm, "company.commit.error");
		}

		return result;
	}

	protected ModelAndView createEditModelAndView(final CompanyForm companyForm) {
		ModelAndView result;
		result = this.createEditModelAndView(companyForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final CompanyForm companyForm, final String message) {
		ModelAndView result;
		String countryCode;

		countryCode = this.customisationService.find().getCountryCode();
		if (companyForm.getId() != 0)
			result = new ModelAndView("company/edit");
		else
			result = new ModelAndView("company/register");

		result.addObject("companyForm", companyForm);
		result.addObject("countryCode", countryCode);

		result.addObject("message", message);

		return result;
	}
}
