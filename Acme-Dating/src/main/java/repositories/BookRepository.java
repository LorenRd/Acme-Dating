
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

	@Query("select avg(1.0*(select count(b) from Book b where b.couple.id = c.id)) from Couple c")
	Double avgExperiencesPerCouple();

	@Query("select min(1.0*(select count(b) from Book b where b.couple.id = c.id)) from Couple c")
	Double minExperiencesPerCouple();

	@Query("select max(1.0*(select count(b) from Book b where b.couple.id = c.id)) from Couple c")
	Double maxExperiencesPerCouple();

	@Query("select stddev(1.0*(select count(b) from Book b where b.couple.id = c.id)) from Couple c")
	Double stddevExperiencesPerCouple();


	@Query("select b from Book b join b.features bf where bf.id = ?1")
	Collection<Book> findByFeatureId(int featureId);
}
