package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.CoupleRequest;

@Repository
public interface CoupleRequestRepository extends
		JpaRepository<CoupleRequest, Integer> {

}