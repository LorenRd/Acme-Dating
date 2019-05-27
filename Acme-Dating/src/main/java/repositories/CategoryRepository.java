package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

	@Query("select e.category from Experience e group by e.category order by count(e.category) desc")
	Collection<Category> mostUsedCategory();
	
	@Query("select e.category from Experience e group by e.category order by count(e.category) asc")
	Collection<Category> leastUsedCategory();
	
}