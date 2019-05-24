package controllers.couple;

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
import services.CoupleService;
import services.RecordService;
import services.UserService;

import controllers.AbstractController;
import domain.Category;
import domain.Couple;
import domain.Record;
import domain.User;

@Controller
@RequestMapping("/record/couple")
public class RecordCoupleController extends AbstractController {

	@Autowired
	private RecordService recordService;

	@Autowired
	private CoupleService coupleService;

	@Autowired
	private UserService userService;

	@Autowired
	private CategoryService categoryService;

	// List

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Collection<Record> records;
		records = new ArrayList<Record>();
		result = new ModelAndView("record/list");
		result.addObject("requestURI", "record/couple/list.do");

		final User principal = this.userService.findByPrincipal();

		if (principal.getCouple() == null) {
			result.addObject("couple", null);
			return result;
		} else {

			final Couple couple = this.coupleService.findByUser();
			records = this.recordService.findByCoupleId(couple.getId());

			result.addObject("records", records);
			result.addObject("couple", couple);
			return result;
		}
	}

	// Create

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Record record;

		final User principal = this.userService.findByPrincipal();

		if (principal.getCouple() == null) {
			result = new ModelAndView("redirect:/welcome/index.do");
			return result;
		} else {
			record = this.recordService.create();
			result = this.createModelAndView(record);

			return result;
		}
	}

	// Edit

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int recordId) {
		ModelAndView result;
		Record record;

		record = this.recordService.findOne(recordId);
		Assert.notNull(record);
		result = this.createEditModelAndView(record);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("record") Record record,
			final BindingResult binding) {
		ModelAndView result;

		try {
			record = this.recordService.reconstruct(record, binding);
			if (binding.hasErrors()) {
				result = this.createModelAndView(record);
				for (final ObjectError e : binding.getAllErrors())
					System.out.println(e.getObjectName() + " error ["
							+ e.getDefaultMessage() + "] "
							+ Arrays.toString(e.getCodes()));
			} else {
				record = this.recordService.save(record);
				result = new ModelAndView("redirect:/welcome/index.do");
			}

		} catch (final Throwable oops) {
			result = this.createModelAndView(record, "record.commit.error");
		}
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView create(@ModelAttribute("record") Record record,
			final BindingResult binding) {
		ModelAndView result;

		try {
			record = this.recordService.reconstruct(record, binding);
			if (binding.hasErrors()) {
				result = this.createModelAndView(record);
				for (final ObjectError e : binding.getAllErrors())
					System.out.println(e.getObjectName() + " error ["
							+ e.getDefaultMessage() + "] "
							+ Arrays.toString(e.getCodes()));
			} else {
				record = this.recordService.save(record);
				result = new ModelAndView("redirect:/welcome/index.do");
			}

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(record, "record.commit.error");
		}
		return result;
	}

	// Delete

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(final int recordId) {
		ModelAndView result;
		Record record;

		record = this.recordService.findOne(recordId);

		try {
			this.recordService.delete(record);
			result = new ModelAndView("redirect:/welcome/index.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(record, "record.commit.error");
		}
		return result;
	}

	// ------------------- Ancillary Methods

	protected ModelAndView createEditModelAndView(final Record record) {
		ModelAndView result;

		result = this.createEditModelAndView(record, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Record record,
			final String messageCode) {
		ModelAndView result;
		result = new ModelAndView("record/edit");

		final User principal = this.userService.findByPrincipal();

		if (principal.getCouple() == null) {
			result.addObject("couple", null);
			return result;
		} else {
			Collection<Record> records;
			records = new ArrayList<Record>();

			final Couple couple = this.coupleService.findByUser();
			records = this.recordService.findByCoupleId(couple.getId());

			if (!records.contains(record)) {
				result = new ModelAndView("redirect:/welcome/index.do");
				return result;
			} else {

				Collection<Category> categories = this.categoryService.findAll();
				
				result.addObject("record", record);
				result.addObject("couple", couple);
				result.addObject("categories", categories);
				result.addObject("message", messageCode);
				return result;
			}
		}
	}

	private ModelAndView createModelAndView(final Record record) {
		ModelAndView result;

		result = this.createModelAndView(record, null);
		return result;
	}

	private ModelAndView createModelAndView(final Record record,
			final String messageCode) {
		ModelAndView result;
		result = new ModelAndView("record/create");

		final User principal = this.userService.findByPrincipal();

		if (principal.getCouple() == null) {
			result.addObject("couple", null);
			return result;
		} else {

			final Couple couple = this.coupleService.findByUser();
			Collection<Category> categories = this.categoryService.findAll();

			result.addObject("record", record);
			result.addObject("couple", couple);
			result.addObject("categories", categories);
			result.addObject("message", messageCode);
			return result;
		}
	}
}
