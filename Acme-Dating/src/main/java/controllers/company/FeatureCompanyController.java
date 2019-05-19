
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

import services.ActorService;
import services.FeatureService;
import domain.Actor;
import domain.Feature;

@Controller
@RequestMapping("/feature/company")
public class FeatureCompanyController {

	@Autowired
	private ActorService	actorService;

	@Autowired
	private FeatureService	featureService;


	//List

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Collection<Feature> features;
		features = new ArrayList<Feature>();

		final Actor principal = this.actorService.findByPrincipal();

		features = this.featureService.findAllByCompanyId(principal.getId());

		result = new ModelAndView("feauture/list");
		result.addObject("features", features);
		result.addObject("requestURI", "feauture/company/list.do");

		return result;
	}

	// Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int featureId) {
		// Inicializa resultado
		ModelAndView result;
		Feature feature;

		// Busca en el repositorio
		feature = this.featureService.findOne(featureId);
		Assert.notNull(feature);

		// Crea y anade objetos a la vista
		result = new ModelAndView("feature/display");
		result.addObject("requestURI", "feature/display.do");
		result.addObject("feature", feature);

		// Envia la vista
		return result;
	}

	//Create

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Feature feature;

		feature = this.featureService.create();
		result = this.createModelAndView(feature);

		return result;
	}

	// Delete

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(final int featureId) {
		Feature feature;
		ModelAndView result;

		feature = this.featureService.findOne(featureId);
		try {
			this.featureService.delete(feature);
			result = new ModelAndView("redirect:/welcome/index.do");
		} catch (final Throwable oops) {
			result = this.displayModelAndView(feature, "feature.commit.error");
		}
		return result;
	}

	//Edit

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int featureId) {
		ModelAndView result;
		Feature feature;

		feature = this.featureService.findOne(featureId);
		Assert.notNull(feature);
		result = this.createEditModelAndView(feature);

		return result;
	}

	//Save Final --- CREATE

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "saveFinal")
	public ModelAndView createFinal(@ModelAttribute("feature") Feature feature, final BindingResult binding) {
		ModelAndView result;

		try {
			feature = this.featureService.reconstruct(feature, binding);
			if (binding.hasErrors()) {
				result = this.createModelAndView(feature);
				for (final ObjectError e : binding.getAllErrors())
					System.out.println(e.getObjectName() + " error [" + e.getDefaultMessage() + "] " + Arrays.toString(e.getCodes()));
			} else {
				feature = this.featureService.save(feature);
				result = new ModelAndView("redirect:/welcome/index.do");
			}

		} catch (final Throwable oops) {
			result = this.createModelAndView(feature, "feature.commit.error");
		}
		return result;
	}

	// Save Final --- Edit

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveFinal")
	public ModelAndView saveFinal(@ModelAttribute("feature") Feature feature, final BindingResult binding) {
		ModelAndView result;

		try {
			feature = this.featureService.reconstruct(feature, binding);
			if (binding.hasErrors()) {
				System.out.println(binding.getAllErrors());
				result = this.editModelAndView(feature);
			} else {
				feature = this.featureService.save(feature);
				result = new ModelAndView("redirect:/welcome/index.do");
			}

		} catch (final Throwable oops) {
			oops.printStackTrace();
			result = this.editModelAndView(feature, "feature.commit.error");
		}
		return result;
	}

	// ------------------- Ancillary Methods
	protected ModelAndView createEditModelAndView(final Feature feature) {
		ModelAndView result;

		result = this.createEditModelAndView(feature, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Feature feature, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("feature/edit");
		result.addObject("feature", feature);

		result.addObject("message", messageCode);

		return result;
	}

	protected ModelAndView displayModelAndView(final Feature feature, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("feature/display");
		result.addObject("feature", feature);
		result.addObject("message", messageCode);

		return result;
	}

	private ModelAndView createModelAndView(final Feature feature) {
		ModelAndView result;

		result = this.createModelAndView(feature, null);
		return result;
	}

	private ModelAndView createModelAndView(final Feature feature, final String messageCode) {
		ModelAndView result;
		result = new ModelAndView("feature/create");
		result.addObject("feature", feature);
		result.addObject("message", messageCode);
		return result;
	}

	private ModelAndView editModelAndView(final Feature feature) {
		ModelAndView result;

		result = this.editModelAndView(feature, null);
		return result;
	}

	private ModelAndView editModelAndView(final Feature feature, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("feature/edit");
		result.addObject("feature", feature);
		result.addObject("message", messageCode);
		return result;
	}

}
