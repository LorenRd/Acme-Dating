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

import repositories.CategoryRepository;
import repositories.UserRepository;
import security.LoginService;
import security.UserAccount;
import services.CategoryService;
import services.CoupleService;
import services.RecordCommentService;
import services.RecordService;
import services.UserService;

import controllers.AbstractController;
import domain.Category;
import domain.Couple;
import domain.Record;
import domain.RecordComment;
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

	@Autowired
	private RecordCommentService recordCommentService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	// Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int recordId) {
		// Inicializa resultado
		final ModelAndView result;
		Record record;
		Collection<RecordComment> comments;
		Collection<RecordComment> commentsChild = new ArrayList<RecordComment>();

		User user;
		boolean hasCouple = false;

		// Busca en el repositorio
		record = this.recordService.findOne(recordId);
		Assert.notNull(record);

		comments = this.recordCommentService.findByRecordId(recordId);
		for (RecordComment rC : comments) {
			commentsChild.addAll(this.recordCommentService.findChilds(rC
					.getId()));
		}
		try {
			user = this.userService.findByPrincipal();
			if (user.getCouple() != null) {
				hasCouple = true;
			}
		} catch (Exception e) {
		}

		// Crea y añade objetos a la vista
		result = new ModelAndView("record/display");
		result.addObject("requestURI", "record/couple/display.do");
		result.addObject("record", record);
		result.addObject("hasCouple", hasCouple);
		result.addObject("comments", comments);
		result.addObject("commentsChild", commentsChild);

		// Envía la vista
		return result;
	}

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
			result = new ModelAndView("record/create");
			result.addObject("requestURI", "record/couple/create.do");
			result.addObject("couple", null);
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
		result = this.editModelAndView(record);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("record") Record record,
			final BindingResult binding) {
		ModelAndView result;

		try {
			record = this.recordService.reconstruct(record, binding);
			if (binding.hasErrors()) {
				result = this.editModelAndView(record);
				for (final ObjectError e : binding.getAllErrors())
					System.out.println(e.getObjectName() + " error ["
							+ e.getDefaultMessage() + "] "
							+ Arrays.toString(e.getCodes()));
			} else {
				record = this.recordService.save(record);
				result = new ModelAndView("redirect:/welcome/index.do");
			}

		} catch (final Throwable oops) {
			result = this.editModelAndView(record, "record.commit.error");
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
			result = this.createModelAndView(record, "record.commit.error");
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
			final Couple couple = this.coupleService.findByUser();

			Collection<Category> categories = this.categoryService.findAll();

			result.addObject("record", record);
			result.addObject("couple", couple);
			result.addObject("categories", categories);
			result.addObject("message", messageCode);
			return result;

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

	protected ModelAndView editModelAndView(final Record record) {
		ModelAndView result;

		result = this.editModelAndView(record, null);

		return result;
	}

	protected ModelAndView editModelAndView(final Record record,
			final String messageCode) {
		ModelAndView result;
		result = new ModelAndView("record/edit");

		final UserAccount userAccountPrincipal = LoginService.getPrincipal();
		User principal = this.userRepository
				.findByUserAccountId(userAccountPrincipal.getId());

		if (principal.getCouple() == null) {
			result.addObject("couple", null);
			return result;
		} else {

			Couple couple = principal.getCouple();
			Collection<Category> categories = this.categoryRepository.findAll();

			result.addObject("record", record);
			result.addObject("couple", couple);
			result.addObject("categories", categories);
			result.addObject("message", messageCode);
			return result;

		}
	}

}
