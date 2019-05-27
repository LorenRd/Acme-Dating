package services;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ExperienceRepository;
import security.Authority;
import domain.Actor;
import domain.Book;
import domain.Company;
import domain.Experience;
import domain.Feature;

@Service
@Transactional
public class ExperienceService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private ExperienceRepository experienceRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private CompanyService companyService;

	@Autowired
	private BookService bookService;

	@Autowired
	private FeatureService featureService;

	@Autowired
	private Validator validator;

	@Autowired
	private ActorService actorService;

	// Simple CRUD Methods

	public boolean exist(final Integer experienceId) {
		return this.experienceRepository.exists(experienceId);
	}

	public Experience findOne(final int experienceId) {
		Experience result;

		result = this.experienceRepository.findOne(experienceId);
		Assert.notNull(result);
		return result;
	}

	public Collection<Experience> findAll() {
		Collection<Experience> result;

		result = this.experienceRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Experience create() {
		Experience result;
		final Company principal;

		principal = this.companyService.findByPrincipal();
		Assert.notNull(principal);

		result = new Experience();
		result.setCompany(principal);
		return result;
	}

	public Experience save(final Experience experience) {
		Company principal;
		Experience result;

		principal = this.companyService.findByPrincipal();
		Assert.notNull(principal);

		Assert.notNull(experience);
		Assert.isTrue(experience.getCompany() == principal);

		result = this.experienceRepository.save(experience);
		Assert.notNull(result);

		return result;
	}

	public void delete(final Experience experience) {
		Company principal;
		Collection<Book> books;
		Collection<Feature> features;
		Assert.notNull(experience);

		principal = this.companyService.findByPrincipal();
		Assert.notNull(principal);

		Assert.isTrue(experience.getCompany().getId() == principal.getId());

		books = this.bookService.findAllByExperienceId(experience.getId());

		for (final Book b : books)
			this.bookService.delete(b);

		features = experience.getFeatures();

		for (Feature f : features) {
			this.featureService.delete(f);
		}

		this.experienceRepository.delete(experience);
	}

	// Business Methods

	public Collection<Experience> findByCompany(final int companyId) {
		Collection<Experience> result;

		result = this.experienceRepository.findByCompanyId(companyId);
		return result;
	}

	public Collection<Experience> findByKeywordAll(final String keyword) {
		final Collection<Experience> result = this.experienceRepository
				.findByKeyword(keyword);

		return result;
	}

	public Collection<Experience> findByKeywordCompany(final String keyword,
			final int companyId) {
		final Collection<Experience> result = this.experienceRepository
				.findByKeywordCompany(keyword, companyId);

		return result;
	}

	public Experience reconstruct(final Experience experience,
			final BindingResult binding) {
		Experience original;
		if (experience.getId() == 0) {
			original = experience;
			original.setCompany(this.companyService.findByPrincipal());
		} else {
			original = this.experienceRepository.findOne(experience.getId());
			experience.setCompany(this.companyService.findByPrincipal());

		}

		this.validator.validate(experience, binding);

		return experience;
	}

	public void flush() {
		this.experienceRepository.flush();
	}

	public void subtractPlaces(int experienceId) {
		Experience experience;
		int places;
		experience = this.findOne(experienceId);
		places = experience.getCoupleLimit();
		places = places - 1;
		experience.setCoupleLimit(places);
	}

	public Double avgExperiencesPerCompany() {
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		Assert.isTrue(actor.getUserAccount().getAuthorities()
				.contains(authority));
		Double result;

		result = this.experienceRepository.avgExperiencesPerCompany();

		return result;
	}

	public Double minExperiencesPerCompany() {
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		Assert.isTrue(actor.getUserAccount().getAuthorities()
				.contains(authority));
		Double result;

		result = this.experienceRepository.minExperiencesPerCompany();

		return result;
	}

	public Double maxExperiencesPerCompany() {
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		Assert.isTrue(actor.getUserAccount().getAuthorities()
				.contains(authority));
		Double result;

		result = this.experienceRepository.maxExperiencesPerCompany();

		return result;
	}

	public Double stddevExperiencesPerCompany() {
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		Assert.isTrue(actor.getUserAccount().getAuthorities()
				.contains(authority));
		Double result;

		result = this.experienceRepository.stddevExperiencesPerCompany();

		return result;
	}

	public Double avgPriceOfExperiencesPerCompany() {
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		Assert.isTrue(actor.getUserAccount().getAuthorities()
				.contains(authority));
		Double result;

		result = this.experienceRepository.avgPriceOfExperiencesPerCompany();

		return result;
	}

}
