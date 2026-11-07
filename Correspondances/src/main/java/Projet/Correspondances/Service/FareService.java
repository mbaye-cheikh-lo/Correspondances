package Projet.Correspondances.Service;

import Projet.Correspondances.Entity.Fare;
import java.util.List;
import java.util.Optional;

public interface FareService {

    List<Fare> findAll();

    Optional<Fare> findById(Long id);

    Fare save(Fare fare);

    Fare update(Long id, Fare fare);

    void deleteById(Long id);
}
