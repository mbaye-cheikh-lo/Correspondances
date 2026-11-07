package Projet.Correspondances.Service;

import Projet.Correspondances.Entity.TravelClass;
import java.util.List;
import java.util.Optional;

public interface TravelClassService {

    List<TravelClass> findAll();

    Optional<TravelClass> findById(Long id);

    TravelClass save(TravelClass travelClass);

    TravelClass update(Long id, TravelClass travelClass);

    void deleteById(Long id);
}
