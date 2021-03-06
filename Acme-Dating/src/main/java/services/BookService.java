
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.BookRepository;
import security.Authority;
import domain.Actor;
import domain.Book;
import domain.Couple;
import domain.Feature;
import forms.BookForm;

@Service
@Transactional
public class BookService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private BookRepository		bookRepository;

	@Autowired
	private CoupleService coupleService;

	@Autowired
	private ExperienceService experienceService;

	@Autowired
	private Validator			validator;

	@Autowired
	private ActorService actorService;

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
		this.experienceService.subtractPlaces(book.getExperience().getId());

		result = this.bookRepository.save(book);
		Assert.notNull(result);
		return result;
	}

	public Book saveScore(final Book book) {
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
		result.setFeatures(new ArrayList<Feature>());
		result.setMoment(new Date(System.currentTimeMillis() - 1));

		return result;
	}

	public BookForm construct(final Book book) {
		final BookForm bookForm = new BookForm();
		bookForm.setId(book.getId());
		bookForm.setExperience(book.getExperience());
		bookForm.setCouple(book.getCouple());
		bookForm.setMoment(book.getMoment());
		bookForm.setDate(book.getDate());
		bookForm.setScore(book.getScore());
		bookForm.setFeatures(book.getFeatures());

		return bookForm;
	}


	public Book reconstruct(final BookForm bookForm, final BindingResult binding) {
		Book result;
		
		result = this.create();
		result.setMoment(new Date(System.currentTimeMillis() - 1));
		result.setCouple(this.coupleService.findByUser());
		result.setExperience(bookForm.getExperience());
		result.setDate(bookForm.getDate());
		if(bookForm.getFeatures()==null)
			result.setFeatures(new ArrayList<Feature>());
		else
			result.setFeatures(bookForm.getFeatures());
		
		if(result.getDate()!=null)
			if (result.getDate().before(Calendar.getInstance().getTime()))
				binding.rejectValue("date", "book.validation.date","Date must be future");


		this.validator.validate(result, binding);
		return result;
	}

	public Double avgExperiencesPerCouple() {
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		Assert.isTrue(actor.getUserAccount().getAuthorities()
				.contains(authority));
		Double result;

		result = this.bookRepository.avgExperiencesPerCouple();

		return result;
	}

	public Double minExperiencesPerCouple() {
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		Assert.isTrue(actor.getUserAccount().getAuthorities()
				.contains(authority));
		Double result;

		result = this.bookRepository.minExperiencesPerCouple();

		return result;
	}

	public Double maxExperiencesPerCouple() {
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		Assert.isTrue(actor.getUserAccount().getAuthorities()
				.contains(authority));
		Double result;

		result = this.bookRepository.maxExperiencesPerCouple();

		return result;
	}

	public Double stddevExperiencesPerCouple() {
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		Assert.isTrue(actor.getUserAccount().getAuthorities()
				.contains(authority));
		Double result;

		result = this.bookRepository.stddevExperiencesPerCouple();

		return result;
	}
	
	public Collection<Book> findByFeatureId(final int featureId) {
		Collection<Book> books;
		books = this.bookRepository.findByFeatureId(featureId);
		return books;
	}

	public void flush() {
		this.bookRepository.flush();
	}

}
