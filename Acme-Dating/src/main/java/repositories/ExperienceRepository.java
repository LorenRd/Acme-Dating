package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Experience;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Integer> {


}
