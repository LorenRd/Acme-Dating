package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CategoryRepository;
import security.Authority;

import domain.Actor;
import domain.Category;

@Service
@Transactional
public class CategoryService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private CategoryRepository categoryRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private ActorService actorService;

	public Collection<Category> findAll() {
		Collection<Category> result;

		result = this.categoryRepository.findAll();
		Assert.notNull(result);
		return result;
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

}
