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

import services.CategoryService;

import controllers.AbstractController;
import domain.Category;

@Controller
@RequestMapping("/category/administrator")
public class CategoryAdministratorController extends AbstractController {

	@Autowired
	private CategoryService categoryService;

	// List

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Collection<Category> categories = new ArrayList<Category>();

		categories = this.categoryService.findAll();

		result = new ModelAndView("category/list");
		result.addObject("categories", categories);
		result.addObject("requestURI", "category/administrator/list.do");
		return result;
	}

	// Create

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Category category;

		category = this.categoryService.create();
		result = this.createModelAndView(category);

		return result;
	}

	// Edit

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int categoryId) {
		ModelAndView result;
		Category category;

		category = this.categoryService.findOne(categoryId);
		Assert.notNull(category);
		if (this.categoryService.isInUse(category)) {
			result = new ModelAndView("redirect:/welcome/index.do");
		} else {
			result = this.createEditModelAndView(category);
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("category") Category category,
			final BindingResult binding) {
		ModelAndView result;

		try {
			category = this.categoryService.reconstruct(category, binding);
			if (binding.hasErrors()) {
				result = this.createModelAndView(category);
				for (final ObjectError e : binding.getAllErrors())
					System.out.println(e.getObjectName() + " error ["
							+ e.getDefaultMessage() + "] "
							+ Arrays.toString(e.getCodes()));
			} else {
				category = this.categoryService.save(category);
				result = new ModelAndView("redirect:/welcome/index.do");
			}

		} catch (final Throwable oops) {
			result = this.createModelAndView(category, "category.commit.error");
		}
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView create(@ModelAttribute("category") Category category,
			final BindingResult binding) {
		ModelAndView result;

		try {
			category = this.categoryService.reconstruct(category, binding);
			if (binding.hasErrors()) {
				result = this.createModelAndView(category);
				for (final ObjectError e : binding.getAllErrors())
					System.out.println(e.getObjectName() + " error ["
							+ e.getDefaultMessage() + "] "
							+ Arrays.toString(e.getCodes()));
			} else {
				category = this.categoryService.save(category);
				result = new ModelAndView("redirect:/welcome/index.do");
			}

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(category,
					"category.commit.error");
		}
		return result;
	}

	// Delete

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(final int categoryId) {
		ModelAndView result;
		Category category;

		category = this.categoryService.findOne(categoryId);

		try {
			this.categoryService.delete(category);
			result = new ModelAndView("redirect:/welcome/index.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(category,
					"category.commit.error");
		}
		return result;
	}

	// ------------------- Ancillary Methods

	protected ModelAndView createEditModelAndView(final Category category) {
		ModelAndView result;

		result = this.createEditModelAndView(category, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Category category,
			final String messageCode) {
		ModelAndView result;
		result = new ModelAndView("category/edit");

		result.addObject("category", category);
		result.addObject("message", messageCode);
		return result;
	}

	private ModelAndView createModelAndView(final Category category) {
		ModelAndView result;

		result = this.createModelAndView(category, null);
		return result;
	}

	private ModelAndView createModelAndView(final Category category,
			final String messageCode) {
		ModelAndView result;
		result = new ModelAndView("category/create");

		result.addObject("category", category);
		result.addObject("message", messageCode);
		return result;
	}
}
