package Projet.Correspondances.Service;

import Projet.Correspondances.Entity.FarePeriod;
import java.util.List;
import java.util.Optional;

public interface FarePeriodService {

    List<FarePeriod> findAll();

    Optional<FarePeriod> findById(Long id);

    FarePeriod save(FarePeriod farePeriod);

    FarePeriod update(Long id, FarePeriod farePeriod);

    void deleteById(Long id);
}
