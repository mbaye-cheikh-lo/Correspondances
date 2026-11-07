package Projet.Correspondances.Service;

import Projet.Correspondances.Entity.Circulation;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CirculationService {

    List<Circulation> findAll();

    Optional<Circulation> findById(Long id);

    Circulation save(Circulation circulation);

    Circulation update(Long id, Circulation circulation);

    void deleteById(Long id);

    List<Circulation> findByDate(LocalDate date);
}
