package services;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.CategoryRepository;
import repositories.ExperienceRepository;
import repositories.RecordRepository;
import security.Authority;

import domain.Actor;
import domain.Category;
import domain.Experience;
import domain.Record;

@Service
@Transactional
public class CategoryService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private RecordRepository recordRepository;

	@Autowired
	private ExperienceRepository experienceRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private ActorService actorService;

	@Autowired
	private Validator validator;

	// Simple CRUD Methods

	public Category create() {
		Category result;
		new Category();

		result = new Category();
		return result;
	}

	public Category findOne(final int categoryId) {
		Category result;

		result = this.categoryRepository.findOne(categoryId);
		Assert.notNull(result);
		return result;
	}

	public Collection<Category> findAll() {
		Collection<Category> result;

		result = this.categoryRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Category save(final Category category) {
		Category result;

		result = this.categoryRepository.save(category);
		Assert.notNull(result);

		return result;
	}

	public void delete(final Category category) {

		Assert.notNull(category);

		Assert.isTrue(!this.isInUse(category), "category.isInUse");
		this.categoryRepository.delete(category);
		
	}

	public Category mostUsedCategory() {
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		Assert.isTrue(actor.getUserAccount().getAuthorities()
				.contains(authority));
		Category result = null;

		if (this.categoryRepository.mostUsedCategory().size() > 0)
			result = this.categoryRepository.mostUsedCategory().iterator()
					.next();

		return result;
	}

	public Category leastUsedCategory() {
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		Assert.isTrue(actor.getUserAccount().getAuthorities()
				.contains(authority));
		Category result = null;

		if (this.categoryRepository.leastUsedCategory().size() > 0)
			result = this.categoryRepository.leastUsedCategory().iterator()
					.next();

		return result;
	}

	public Category reconstruct(final Category category,
			final BindingResult binding) {
		Category result;

		if (category.getId() == 0) {
			result = category;
		} else {
			result = this.categoryRepository.findOne(category.getId());

			if (!category.getTitle().equals("")) {
				result.setTitle(category.getTitle());
			}

			if (!category.getPicture().equals("")) {
				result.setPicture(category.getPicture());
			}

		}
		this.validator.validate(result, binding);

		return result;
	}

	public boolean isInUse(Category category) {
		boolean result = true;

		Collection<Record> records = this.recordRepository
				.findByCategoryId(category.getId());

		Collection<Experience> experiences = this.experienceRepository
				.findByCategoryId(category.getId());

		if (records.isEmpty() && experiences.isEmpty()) {
			result = false;
		}
		return result;
	}

}
