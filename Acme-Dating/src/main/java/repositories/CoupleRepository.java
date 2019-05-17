package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Couple;

@Repository
public interface CoupleRepository extends JpaRepository<Couple, Integer> {


}
