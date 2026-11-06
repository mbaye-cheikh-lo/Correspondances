package Projet.Correspondances.Repository;

import Projet.Correspondances.Entity.Circulation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.time.LocalDate;

@Repository
public interface CirculationRepository extends JpaRepository<Circulation, Long> {

    List<Circulation> findByCirculationDate(LocalDate circulationDate);
}
