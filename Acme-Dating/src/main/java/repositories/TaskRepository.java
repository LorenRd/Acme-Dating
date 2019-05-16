package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Task;

@Repository
public interface TaskRepository extends
		JpaRepository<Task, Integer> {

}