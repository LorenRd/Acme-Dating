package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.BookRepository;
import domain.Book;
import domain.Couple;
import forms.BookForm;

@Service
@Transactional
public class BookService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private CoupleService coupleService;

	@Autowired
	private Validator validator;

	// Simple CRUD Methods
	public void delete(final Book book) {
		this.bookRepository.delete(book);

	}

	public Book findOne(final int bookId) {
		Book result;

		result = this.bookRepository.findOne(bookId);
		Assert.notNull(result);
		return result;

	}

	public Book save(final Book book) {
		Book result;
		result = this.bookRepository.save(book);
		Assert.notNull(result);
		return result;
	}

	// Additional functions

	public Collection<Book> findAllByExperienceId(final int experienceId) {
		Collection<Book> result;

		result = this.bookRepository.findAllByExperienceId(experienceId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Book> findAllByCompanyId(final int companyId) {
		Collection<Book> result;
		result = new ArrayList<Book>();
		result = this.bookRepository.findAllByCompanyId(companyId);

		return result;
	}

	public Collection<Book> findAllByCoupleId(final int coupleId) {
		Collection<Book> result;
		result = new ArrayList<Book>();
		result = this.bookRepository.findAllByCoupleId(coupleId);

		return result;
	}

	public Book create() {
		Book result;
		final Couple principal;

		principal = this.coupleService.findByUser();
		Assert.notNull(principal);

		result = new Book();
		result.setCouple(principal);
		return result;
	}

	public BookForm construct(final Book book) {
		final BookForm bookForm = new BookForm();
		bookForm.setId(book.getId());
		bookForm.setExperience(book.getExperience());
		return bookForm;
	}

	public Book reconstruct(final BookForm bookForm, final BindingResult binding) {
		Book result;
		if (bookForm.getId() == 0) {
			result = this.create();
			result.setMoment(new Date(System.currentTimeMillis() - 1));
			result.setCouple(this.coupleService.findByUser());
			result.setExperience(bookForm.getExperience());

		} else {
			result = this.bookRepository.findOne(bookForm.getId());
		}

		this.validator.validate(result, binding);
		this.bookRepository.flush();
		return result;
	}

}
