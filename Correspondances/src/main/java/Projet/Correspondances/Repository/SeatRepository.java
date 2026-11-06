package Projet.Correspondances.Repository;

import Projet.Correspondances.Entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {

    List<Seat> findByCoachCoachId(Long coachId);
}
