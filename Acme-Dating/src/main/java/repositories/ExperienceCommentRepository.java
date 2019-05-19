package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.ExperienceComment;

@Repository
public interface ExperienceCommentRepository extends
		JpaRepository<ExperienceComment, Integer> {

}
