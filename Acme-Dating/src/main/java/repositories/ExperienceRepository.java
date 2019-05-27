
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Experience;

@Repository
public interface ExperienceRepository extends
		JpaRepository<Experience, Integer> {

	@Query("select e from Experience e where e.company.id = ?1")
	Collection<Experience> findByCompanyId(int companyId);

	@Query("select e from Experience e where e.category.id = ?1")
	Collection<Experience> findByCategoryId(int categoryId);

	@Query("select distinct e from Experience e where (e.title like %?1% or e.body like %?1% or e.ubication like %?1%)")
	Collection<Experience> findByKeyword(String keyword);

	@Query("select distinct e from Experience e join e.company c where c.id = ?2 and (e.title like %?1% or e.body like %?1% or e.ubication like %?1%)")
	Collection<Experience> findByKeywordCompany(String keyword, int companyId);

	@Query("select avg(1.0*(select count(e) from Experience e where e.company.id = c.id)) from Company c")
	Double avgExperiencesPerCompany();

	@Query("select min(1.0*(select count(e) from Experience e where e.company.id = c.id)) from Company c")
	Double minExperiencesPerCompany();

	@Query("select max(1.0*(select count(e) from Experience e where e.company.id = c.id)) from Company c")
	Double maxExperiencesPerCompany();

	@Query("select stddev(1.0*(select count(e) from Experience e where e.company.id = c.id)) from Company c")
	Double stddevExperiencesPerCompany();

	@Query("select avg(1.0*(select e.price from Experience e where e.company.id = c.id)) from Company c")
	Double avgPriceOfExperiencesPerCompany();


	@Query("select e from Experience e join e.features ef where ef.id = ?1")
	Collection<Experience> findByFeatureId(int featureId);
}
