package controllers.administrator;

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

import services.TrophyService;

import controllers.AbstractController;
import domain.Trophy;

@Controller
@RequestMapping("/trophy/administrator")
public class TrophyAdministratorController extends AbstractController {

	@Autowired
	private TrophyService trophyService;

	// List

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Collection<Trophy> trophies = new ArrayList<Trophy>();

		trophies = this.trophyService.findAll();

		result = new ModelAndView("trophy/list");
		result.addObject("trophies", trophies);
		result.addObject("requestURI", "trophy/administrator/list.do");
		return result;
	}

	// Create

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Trophy trophy;

		trophy = this.trophyService.create();
		result = this.createModelAndView(trophy);

		return result;
	}

	// Edit

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int trophyId) {
		ModelAndView result;
		Trophy trophy;

		trophy = this.trophyService.findOne(trophyId);
		Assert.notNull(trophy);

		result = this.createEditModelAndView(trophy);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("trophy") Trophy trophy,
			final BindingResult binding) {
		ModelAndView result;

		try {
			trophy = this.trophyService.reconstruct(trophy, binding);
			if (binding.hasErrors()) {
				result = this.createEditModelAndView(trophy);
				for (final ObjectError e : binding.getAllErrors())
					System.out.println(e.getObjectName() + " error ["
							+ e.getDefaultMessage() + "] "
							+ Arrays.toString(e.getCodes()));
			} else {
				trophy = this.trophyService.save(trophy);
				result = new ModelAndView("redirect:/welcome/index.do");
			}

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(trophy, "trophy.commit.error");
		}
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView create(@ModelAttribute("trophy") Trophy trophy,
			final BindingResult binding) {
		ModelAndView result;

		try {
			trophy = this.trophyService.reconstruct(trophy, binding);
			if (binding.hasErrors()) {
				result = this.createModelAndView(trophy);
				for (final ObjectError e : binding.getAllErrors())
					System.out.println(e.getObjectName() + " error ["
							+ e.getDefaultMessage() + "] "
							+ Arrays.toString(e.getCodes()));
			} else {
				trophy = this.trophyService.save(trophy);
				result = new ModelAndView("redirect:/welcome/index.do");
			}

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(trophy, "trophy.commit.error");
		}
		return result;
	}

	// Delete

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(final int trophyId) {
		ModelAndView result;
		Trophy trophy;

		trophy = this.trophyService.findOne(trophyId);

		try {
			this.trophyService.delete(trophy);
			result = new ModelAndView("redirect:/welcome/index.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(trophy, "trophy.commit.error");
		}
		return result;
	}

	// ------------------- Ancillary Methods

	protected ModelAndView createEditModelAndView(final Trophy trophy) {
		ModelAndView result;

		result = this.createEditModelAndView(trophy, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Trophy trophy,
			final String messageCode) {
		ModelAndView result;
		result = new ModelAndView("trophy/edit");

		result.addObject("trophy", trophy);
		result.addObject("message", messageCode);
		return result;
	}

	private ModelAndView createModelAndView(final Trophy trophy) {
		ModelAndView result;

		result = this.createModelAndView(trophy, null);
		return result;
	}

	private ModelAndView createModelAndView(final Trophy trophy,
			final String messageCode) {
		ModelAndView result;
		result = new ModelAndView("trophy/create");

		result.addObject("trophy", trophy);
		result.addObject("message", messageCode);
		return result;
	}

}
