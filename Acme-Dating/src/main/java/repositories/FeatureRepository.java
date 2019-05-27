package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Feature;

@Repository
public interface FeatureRepository extends JpaRepository<Feature, Integer> {

	
	@Query("select f from Feature f where f.company.id = ?1")
	Collection<Feature> findAllByCompanyId(int companyId);


}
