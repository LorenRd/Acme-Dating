
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Book;

public interface BookRepository extends JpaRepository<Book, Integer> {

	@Query("select b from Book b where b.experience.id = ?1")
	Collection<Book> findAllByExperienceId(int experienceId);

	@Query("select b from Book b where b.couple.id = ?1")
	Collection<Book> findAllByCoupleId(int problemId);

	@Query("select b from Book b join b.experience e where e.company.id = ?1")
	Collection<Book> findAllByCompanyId(int companyId);

	@Query("select distinct b from Book b join b.features f where f.id = ?1")
	Collection<Book> findByFeatureId(int featureId);
}
