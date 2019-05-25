package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Couple;
import domain.User;

@Repository
public interface CoupleRepository extends JpaRepository<Couple, Integer> {

	@Query("select u from User u where u.couple.id = ?1")
	Collection<User> findUsersOfACouple(int coupleId);

}
