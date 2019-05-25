
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Challenge;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, Integer> {

	@Query("select c from Challenge c where c.recipient.id = ?1")
	Collection<Challenge> findByRecipientId(int userId);

	@Query("select c from Challenge c where c.sender.id = ?1")
	Collection<Challenge> findBySenderId(int userId);
}
