package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.SocialNetwork;

@Repository
public interface SocialNetworkRepository extends
		JpaRepository<SocialNetwork, Integer> {

}