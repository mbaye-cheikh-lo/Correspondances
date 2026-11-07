package Projet.Correspondances.Service;

import Projet.Correspondances.Entity.Train;
import java.util.List;
import java.util.Optional;

public interface TrainService {

    List<Train> findAll();

    Optional<Train> findById(Long id);

    Train save(Train train);

    Train update(Long id, Train train);

    void deleteById(Long id);
}
