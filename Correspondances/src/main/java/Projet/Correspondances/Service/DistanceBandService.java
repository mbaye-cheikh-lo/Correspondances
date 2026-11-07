package Projet.Correspondances.Service;

import Projet.Correspondances.Entity.DistanceBand;
import java.util.List;
import java.util.Optional;

public interface DistanceBandService {

    List<DistanceBand> findAll();

    Optional<DistanceBand> findById(Long id);

    DistanceBand save(DistanceBand distanceBand);

    DistanceBand update(Long id, DistanceBand distanceBand);

    void deleteById(Long id);
}
