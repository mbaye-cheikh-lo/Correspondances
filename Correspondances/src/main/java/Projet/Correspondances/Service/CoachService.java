package Projet.Correspondances.Service;

import Projet.Correspondances.Entity.Coach;
import java.util.List;
import java.util.Optional;

public interface CoachService {

    List<Coach> findAll();

    Optional<Coach> findById(Long id);

    Coach save(Coach coach);

    Coach update(Long id, Coach coach);

    void deleteById(Long id);
}
