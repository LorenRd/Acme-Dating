package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Experience;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Integer> {
	
	@Query("select e from Experience e where e.company.id = ?1")
	Collection<Experience> findByCompanyId(int companyId);
	
	@Query("select distinct e from Experience e where (e.title like %?1% or e.body like %?1% or e.ubication like %?1%)")
	Collection<Experience> findByKeyword(String keyword);

}
