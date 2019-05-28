package controllers.company;

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

import services.CategoryService;
import services.CompanyService;
import services.CustomisationService;
import services.ExperienceService;
import services.FeatureService;
import controllers.AbstractController;
import domain.Category;
import domain.Company;
import domain.Customisation;
import domain.Experience;
import domain.Feature;

@Controller
@RequestMapping("/experience/company")
public class ExperienceCompanyController extends AbstractController {

	@Autowired
	private ExperienceService experienceService;

	@Autowired
	private FeatureService featureService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private CustomisationService customisationService;

	// List

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(
			@RequestParam(required = false) final String keyword,
			@RequestParam(required = false, defaultValue = "false") final Boolean keywordBool) {
		final ModelAndView result;
		Collection<Experience> experiences;
		experiences = new ArrayList<Experience>();
		Company principal;
		principal = this.companyService.findByPrincipal();

		if (keywordBool && keyword != null)
			experiences = this.experienceService.findByKeywordCompany(keyword,
					principal.getId());
		else
			experiences = this.experienceService.findByCompany(principal
					.getId());

		result = new ModelAndView("experience/list");
		result.addObject("experiences", experiences);
		result.addObject("requestURI", "experience/company/list.do");

		return result;
	}

	// Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int experienceId) {
		// Inicializa resultado
		ModelAndView result;
		Experience experience;
		Customisation customisation;
		Double vat;
		customisation = this.customisationService.find();
		vat = (customisation.getVatNumber()) / 100 + 1.0;

		// Busca en el repositorio
		experience = this.experienceService.findOne(experienceId);
		Assert.notNull(experience);

		// Crea y añade objetos a la vista
		result = new ModelAndView("experience/display");
		result.addObject("requestURI", "experience/display.do");
		result.addObject("vat", vat);
		result.addObject("experience", experience);

		// Envía la vista
		return result;
	}

	// Create

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Experience experience;

		experience = this.experienceService.create();
		result = this.createModelAndView(experience);

		return result;
	}

	// Edit

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int experienceId) {
		ModelAndView result;
		Experience experience;

		experience = this.experienceService.findOne(experienceId);
		Assert.notNull(experience);
		result = this.createEditModelAndView(experience);

		return result;
	}

	// --- CREATION ---

	// Save Final

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "saveFinal")
	public ModelAndView createFinal(
			@ModelAttribute("experience") Experience experience,
			final BindingResult binding) {
		ModelAndView result;

		try {
			experience = this.experienceService
					.reconstruct(experience, binding);
			if (binding.hasErrors()) {
				result = this.createModelAndView(experience);
				for (final ObjectError e : binding.getAllErrors())
					System.out.println(e.getObjectName() + " error ["
							+ e.getDefaultMessage() + "] "
							+ Arrays.toString(e.getCodes()));
			} else {
				experience = this.experienceService.save(experience);
				result = new ModelAndView("redirect:/welcome/index.do");
			}

		} catch (final Throwable oops) {
			result = this.createModelAndView(experience,
					"experience.commit.error");
		}
		return result;
	}

	// --- EDIT ---

	// Save Final

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveFinal")
	public ModelAndView saveFinal(
			@ModelAttribute("experience") Experience experience,
			final BindingResult binding) {
		ModelAndView result;

		try {
			experience = this.experienceService
					.reconstruct(experience, binding);
			if (binding.hasErrors()) {
				result = this.createEditModelAndView(experience);
				for (final ObjectError e : binding.getAllErrors())
					System.out.println(e.getObjectName() + " error ["
							+ e.getDefaultMessage() + "] "
							+ Arrays.toString(e.getCodes()));
			} else {
				experience = this.experienceService.save(experience);
				result = new ModelAndView("redirect:/welcome/index.do");
			}

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(experience,
					"experience.commit.error");
		}

		return result;
	}

	// Delete

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(final int experienceId) {
		ModelAndView result;
		Experience experience;

		experience = this.experienceService.findOne(experienceId);

		try {
			this.experienceService.delete(experience);
			result = new ModelAndView("redirect:/welcome/index.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(experience,
					"experience.commit.error");
		}
		return result;
	}

	// -------------------

	protected ModelAndView createEditModelAndView(final Experience experience) {
		ModelAndView result;

		result = this.createEditModelAndView(experience, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Experience experience,
			final String messageCode) {
		ModelAndView result;
		Collection<Feature> features;
		Collection<Category> categories;
		Company company;
		categories = this.categoryService.findAll();
		company = this.companyService.findByPrincipal();
		features = this.featureService.findAllByCompanyId(company.getId());

		result = new ModelAndView("experience/edit");
		result.addObject("experience", experience);
		result.addObject("features", features);
		result.addObject("categories", categories);
		result.addObject("message", messageCode);

		return result;
	}

	private ModelAndView createModelAndView(final Experience experience) {
		ModelAndView result;

		result = this.createModelAndView(experience, null);
		return result;
	}

	private ModelAndView createModelAndView(final Experience experience,
			final String messageCode) {
		ModelAndView result;
		Collection<Feature> features;
		Collection<Category> categories;
		Company company;

		categories = this.categoryService.findAll();
		company = this.companyService.findByPrincipal();
		features = this.featureService.findAllByCompanyId(company.getId());

		result = new ModelAndView("experience/create");
		result.addObject("experience", experience);
		result.addObject("features", features);
		result.addObject("categories", categories);
		result.addObject("message", messageCode);
		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET, params = "computeScore")
	public ModelAndView computeScore() {
		final ModelAndView result;

		this.companyService.computeScore();

		result = new ModelAndView("redirect:list.do");

		return result;
	}

}
