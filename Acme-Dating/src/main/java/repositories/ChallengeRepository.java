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

	@Query("select c from Challenge c where c.sender.id = ?1 AND c.status = 'COMPLETED'")
	Collection<Challenge> findAllCompletedBySenderId(int userId);

	@Query("select avg(1.0*(select count(c) from Challenge c where c.sender.id = sender.id AND c.status = 'COMPLETED')) from Challenge c")
	Double avgCompletedChallengesPerSender();

	@Query("select min(1.0*(select count(c) from Challenge c where c.sender.id = sender.id AND c.status = 'COMPLETED')) from Challenge c")
	Double minCompletedChallengesPerSender();

	@Query("select max(1.0*(select count(c) from Challenge c where c.sender.id = sender.id AND c.status = 'COMPLETED')) from Challenge c")
	Double maxCompletedChallengesPerSender();

	@Query("select stddev(1.0*(select count(c) from Challenge c where c.sender.id = sender.id AND c.status = 'COMPLETED')) from Challenge c")
	Double stddevCompletedChallengesPerSender();

	@Query("select c from Challenge c where c.recipient.id = ?1 or c.sender.id = ?1")
	Collection<Challenge> findAllChallengesByUserId(int userId);
}
