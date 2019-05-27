
package controllers.couple;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;

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

import services.BookService;
import services.CoupleService;
import services.CustomisationService;
import services.ExperienceCommentService;
import services.ExperienceService;
import services.UserService;
import controllers.AbstractController;
import domain.Book;
import domain.Couple;
import domain.Customisation;
import domain.Experience;
import domain.ExperienceComment;
import domain.Feature;
import domain.User;
import forms.BookForm;

@Controller
@RequestMapping("/book/couple")
public class BookCoupleController extends AbstractController {

	//Services
	@Autowired
	private CoupleService				coupleService;

	@Autowired
	private BookService					bookService;

	@Autowired
	private ExperienceService			experienceService;

	@Autowired
	private CustomisationService		customisationService;

	@Autowired
	private ExperienceCommentService	experienceCommentService;

	@Autowired
	private UserService					userService;


	//List
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Book> books;
		Couple principal;

		principal = this.coupleService.findByUser();

		result = new ModelAndView("book/list");
		result.addObject("requestURI", "book/couple/list.do");

		if (principal == null) {
			result.addObject("couple", null);

			return result;
		} else {

			books = this.bookService.findAllByCoupleId(principal.getId());

			result.addObject("books", books);
			result.addObject("couple", principal);

			return result;
		}
	}

	//Display
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int bookId) {
		final ModelAndView result;
		Book book;
		Collection<Feature> features;
		Customisation customisation;
		Double vat;
		boolean scored = false;

		customisation = this.customisationService.find();
		vat = (customisation.getVatNumber()) / 100 + 1.0;

		Double price = 0.0;

		book = this.bookService.findOne(bookId);
		features = book.getFeatures();

		price += book.getExperience().getPrice();
		for (final Feature f : features)
			price += f.getSupplement();

		if (book.getScore() != null)
			scored = true;

		result = new ModelAndView("book/display");
		result.addObject("book", book);
		result.addObject("scored", scored);
		result.addObject("totalPrice", price * vat);
		result.addObject("features", book.getFeatures());

		return result;
	}

	//Edit
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int bookId) {
		ModelAndView result;
		Book book;
		BookForm bookForm;

		book = this.bookService.findOne(bookId);
		bookForm = this.bookService.construct(book);
		Assert.notNull(book);
		result = this.createEditModelAndView(bookForm);

		return result;
	}

	//Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int experienceId) {
		ModelAndView result;
		Experience experience;
		Book book;
		BookForm bookForm;

		experience = this.experienceService.findOne(experienceId);
		try {
			Assert.isTrue(experience.getCoupleLimit() > 0);
		} catch (final Throwable oops) {
			result = this.createModelAndView(experience, "book.no.places");
			return result;
		}

		book = this.bookService.create();
		book.setExperience(experience);

		bookForm = this.bookService.construct(book);
		result = this.createEditModelAndView(bookForm);

		return result;
	}

	//Save de create
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("bookForm") @Valid final BookForm bookForm, final BindingResult binding) {
		ModelAndView result;
		Book book;

		try {
			book = this.bookService.reconstruct(bookForm, binding);
			if (binding.hasErrors()) {
				for (final ObjectError e : binding.getAllErrors())
					System.out.println(e.getObjectName() + " error [" + e.getDefaultMessage() + "] " + Arrays.toString(e.getCodes()));
				result = this.createEditModelAndView(bookForm);
			} else {
				book = this.bookService.save(book);
				result = new ModelAndView("redirect:/welcome/index.do");
			}
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(bookForm, "book.commit.error");
		}
		return result;
	}
	//Save de score
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "score")
	public ModelAndView edit(@ModelAttribute("bookForm") @Valid final BookForm bookForm, final BindingResult binding) {
		ModelAndView result;
		Book book;

		try {
			Assert.isTrue(bookForm.getDate().before(Calendar.getInstance().getTime()));

			book = this.bookService.reconstruct(bookForm, binding);

			if (binding.hasErrors()) {
				for (final ObjectError e : binding.getAllErrors())
					System.out.println(e.getObjectName() + " error [" + e.getDefaultMessage() + "] " + Arrays.toString(e.getCodes()));
				result = this.createEditModelAndView(bookForm);
			} else {
				book = this.bookService.saveScore(book);
				result = new ModelAndView("redirect:/welcome/index.do");
			}
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(bookForm, "book.score.before");
		}
		return result;
	}

	protected ModelAndView createEditModelAndView(final BookForm bookForm) {
		ModelAndView result;
		result = this.createEditModelAndView(bookForm, null);
		return result;
	}

	private ModelAndView createEditModelAndView(final BookForm bookForm, final String messageCode) {
		ModelAndView result;

		if (bookForm.getId() != 0)
			result = new ModelAndView("book/edit");
		else
			result = new ModelAndView("book/create");

		result.addObject("bookForm", bookForm);
		result.addObject("features", bookForm.getExperience().getFeatures());
		result.addObject("message", messageCode);
		return result;
	}

	protected ModelAndView createModelAndView(final Experience experience) {
		ModelAndView result;
		result = this.createModelAndView(experience, null);
		return result;
	}

	private ModelAndView createModelAndView(final Experience experience, final String messageCode) {
		ModelAndView result;
		Collection<ExperienceComment> comments;
		User user;
		boolean hasCouple = false;

		comments = this.experienceCommentService.findByExperienceId(experience.getId());

		try {
			user = this.userService.findByPrincipal();
			if (user.getCouple() != null)
				hasCouple = true;
		} catch (final Exception e) {
		}

		result = new ModelAndView("experience/display");

		result.addObject("experience", experience);
		result.addObject("features", experience.getFeatures());
		result.addObject("message", messageCode);
		result.addObject("hasCouple", hasCouple);
		result.addObject("comments", comments);
		return result;
	}
}
