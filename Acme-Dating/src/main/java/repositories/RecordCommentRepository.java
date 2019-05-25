package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.RecordComment;

@Repository
public interface RecordCommentRepository extends
		JpaRepository<RecordComment, Integer> {

	@Query("select rC from RecordComment rC where rC.record.id = ?1")
	Collection<RecordComment> findByRecordId(int recordId);

	@Query("select rC from RecordComment rC where rC.recordComment.id = ?1")
	Collection<RecordComment> findChilds(int recordCommentFatherId);

}
