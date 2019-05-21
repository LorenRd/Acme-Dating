
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.ExperienceComment;

@Repository
public interface ExperienceCommentRepository extends JpaRepository<ExperienceComment, Integer> {

	@Query("select eC from ExperienceComment eC where eC.experience.id = ?1")
	Collection<ExperienceComment> findByExperienceId(int experienceId);

}
