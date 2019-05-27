package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Record;

@Repository
public interface RecordRepository extends JpaRepository<Record, Integer> {

	@Query("select r from Record r where r.couple.id = ?1")
	Collection<Record> findByCoupleId(int coupleId);

	@Query("select r from Record r where r.category.id = ?1")
	Collection<Record> findByCategoryId(int categoryId);
}
