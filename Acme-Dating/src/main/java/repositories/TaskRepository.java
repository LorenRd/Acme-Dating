package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

	@Query("select t from Task t where t.couple.id = ?1")
	Collection<Task> findByCoupleId(int coupleId);

}