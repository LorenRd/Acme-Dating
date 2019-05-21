
package controllers.any;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CompanyService;
import services.ExperienceService;
import controllers.AbstractController;
import domain.Company;
import domain.Experience;

@Controller
@RequestMapping("/company")
public class CompanyController extends AbstractController {

	// Services

	@Autowired
	private CompanyService			companyService;

	@Autowired
	private ExperienceService			experienceService;

	


	// Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(required = false) final Integer companyId) {
		final ModelAndView result;
		Company company = new Company();
		if (companyId == null)
			company = this.companyService.findByPrincipal();
		else
			company = this.companyService.findOne(companyId);

		Collection<Experience> experiences;
		

		Company principal;
		principal = this.companyService.findByPrincipal();
		experiences = this.experienceService.findByCompany(principal.getId());
	
		result = new ModelAndView("company/display");
		result.addObject("company", company);
		result.addObject("experiences", experiences);

		return result;

	}


}
