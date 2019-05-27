
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.FeatureRepository;
import domain.Book;
import domain.Company;
import domain.Experience;
import domain.Feature;

@Service
@Transactional
public class FeatureService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private FeatureRepository	featureRepository;
	// Supporting services ----------------------------------------------------
	@Autowired
	private CompanyService		companyService;

	@Autowired
	private ExperienceService	experienceService;

	@Autowired
	private BookService			bookService;

	@Autowired
	private Validator			validator;


	// Simple CRUD Methods

	public Feature create() {
		Feature result;
		final Company principal;

		principal = this.companyService.findByPrincipal();
		Assert.notNull(principal);

		result = new Feature();
		result.setCompany(principal);
		return result;
	}

	public Feature save(final Feature feature) {
		Feature result;
		Company principal;

		principal = this.companyService.findByPrincipal();
		Assert.notNull(principal);

		Assert.notNull(feature);
		Assert.isTrue(feature.getCompany() == principal);

		result = this.featureRepository.save(feature);
		Assert.notNull(result);
		return result;
	}

	public void delete(final Feature feature) {
		Collection<Experience> experiences;
		Collection<Book> books;

		experiences = this.experienceService.findByFeatureId(feature.getId());
		books = this.bookService.findByFeatureId(feature.getId());
		for (final Experience e : experiences) {
			Collection<Feature> features = new ArrayList<Feature>();
			features = e.getFeatures();
			features.remove(feature);

			e.setFeatures(features);
			this.experienceService.save(e);
		}
		for (final Book b : books) {
			Collection<Feature> features = new ArrayList<Feature>();
			features = b.getFeatures();
			features.remove(feature);

			b.setFeatures(features);
			this.bookService.save(b);
		}

		this.featureRepository.delete(feature);

	}

	public Feature findOne(final int featureId) {
		Feature result;

		result = this.featureRepository.findOne(featureId);
		Assert.notNull(result);
		return result;
	}

	public Collection<Feature> findAll() {
		Collection<Feature> result;

		result = this.featureRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	// Business Methods

	public Collection<Feature> findAllByCompanyId(final int companyId) {
		Collection<Feature> result;

		result = this.featureRepository.findAllByCompanyId(companyId);

		return result;
	}

	public Feature reconstruct(final Feature feature, final BindingResult binding) {
		Feature result;
		if (feature.getId() == 0)
			result = feature;
		else
			result = this.featureRepository.findOne(feature.getId());

		result.setTitle(feature.getTitle());
		result.setDescription(feature.getDescription());
		result.setPhoto(feature.getPhoto());
		result.setSupplement(feature.getSupplement());
		result.setCompany(this.companyService.findByPrincipal());

		this.validator.validate(result, binding);
		return result;
	}

}
