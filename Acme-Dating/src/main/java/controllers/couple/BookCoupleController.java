
package controllers.couple;

import java.util.Arrays;
import java.util.Collection;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Book;
import domain.Couple;
import forms.BookForm;

import services.BookService;
import services.CoupleService;
import services.ExperienceService;

@Controller
@RequestMapping("/book/couple")
public class BookCoupleController extends AbstractController {

	//Services
	@Autowired
	private CoupleService		coupleService;

	@Autowired
	private BookService			bookService;

	@Autowired
	private ExperienceService	experienceService;


	//Repositories

	//List
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Book> books;
		Couple principal;
		principal = this.coupleService.findByUser();

		books = this.bookService.findAllByCoupleId(principal.getId());

		result = new ModelAndView("books/list");
		result.addObject("books", books);
		result.addObject("requestURI", "book/couple/list.do");


		return result;
	}


	//Display
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int bookId) {
		final ModelAndView result;
		Book book;

		book = this.bookService.findOne(bookId);

		result = new ModelAndView("book/display");
		result.addObject("book", book);
		return result;
	}

	//Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Book book;
		BookForm bookForm;

		book = this.bookService.create();
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
		result.addObject("experiences", this.experienceService.findAll());
		result.addObject("message", messageCode);
		return result;
	}
}
