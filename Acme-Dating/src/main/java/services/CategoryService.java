package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CategoryRepository;

import domain.Category;

@Service
@Transactional
public class CategoryService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private CategoryRepository categoryRepository;

	public Collection<Category> findAll() {
		Collection<Category> result;

		result = this.categoryRepository.findAll();
		Assert.notNull(result);
		return result;
	}
}
