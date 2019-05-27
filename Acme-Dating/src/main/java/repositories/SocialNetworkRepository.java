
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.SocialNetwork;

@Repository
public interface SocialNetworkRepository extends JpaRepository<SocialNetwork, Integer> {

	@Query("select s from SocialNetwork s where s.user.id = ?1")
	Collection<SocialNetwork> findByUserId(int id);

}
