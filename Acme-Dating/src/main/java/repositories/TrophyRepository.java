package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Trophy;

@Repository
public interface TrophyRepository extends JpaRepository<Trophy, Integer> {

}