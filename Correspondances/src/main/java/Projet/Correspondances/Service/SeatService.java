package Projet.Correspondances.Service;

import Projet.Correspondances.Entity.Seat;
import java.util.List;
import java.util.Optional;

public interface SeatService {

    List<Seat> findAll();

    Optional<Seat> findById(Long id);

    Seat save(Seat seat);

    Seat update(Long id, Seat seat);

    void deleteById(Long id);

    List<Seat> findByCoachId(Long coachId);
}
