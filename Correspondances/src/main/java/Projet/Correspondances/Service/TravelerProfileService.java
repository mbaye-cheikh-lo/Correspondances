package Projet.Correspondances.Service;

import Projet.Correspondances.Entity.TravelerProfile;
import java.util.List;
import java.util.Optional;

public interface TravelerProfileService {

    List<TravelerProfile> findAll();

    Optional<TravelerProfile> findById(Long id);

    TravelerProfile save(TravelerProfile travelerProfile);

    TravelerProfile update(Long id, TravelerProfile travelerProfile);

    void deleteById(Long id);
}
