
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.CoupleRequest;

@Repository
public interface CoupleRequestRepository extends JpaRepository<CoupleRequest, Integer> {

	@Query("select cR from CoupleRequest cR where cR.sender.id = ?1 and cR.recipient.id = ?2")
	CoupleRequest findBySenderIdAndRecipientId(int senderId, int recipientId);

	@Query("select cR from CoupleRequest cR where cR.recipient.id = ?1")
	Collection<CoupleRequest> findCoupleRequestsByRecipientId(int userId);

	@Query("select cR from CoupleRequest cR where cR.sender.id = ?1")
	CoupleRequest findCoupleRequestsBySenderId(int userId);
}
