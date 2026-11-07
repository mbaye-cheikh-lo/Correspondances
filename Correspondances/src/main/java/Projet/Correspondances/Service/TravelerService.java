package Projet.Correspondances.Service;

import Projet.Correspondances.Entity.Traveler;
import java.util.List;
import java.util.Optional;

public interface TravelerService {

    List<Traveler> findAll();

    Optional<Traveler> findById(Long id);

    Traveler save(Traveler traveler);

    Traveler update(Long id, Traveler traveler);

    void deleteById(Long id);

    Optional<Traveler> findByEmail(String email);
}
