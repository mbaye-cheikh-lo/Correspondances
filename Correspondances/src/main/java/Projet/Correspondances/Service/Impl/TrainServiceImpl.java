package Projet.Correspondances.Service.Impl;

import Projet.Correspondances.Entity.Train;
import Projet.Correspondances.Repository.TrainRepository;
import Projet.Correspondances.Service.TrainService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class TrainServiceImpl implements TrainService {

    private final TrainRepository trainRepository;

    public TrainServiceImpl(TrainRepository trainRepository) {
        this.trainRepository = trainRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Train> findAll() {
        return trainRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Train> findById(Long id) {
        return trainRepository.findById(id);
    }

    @Override
    public Train save(Train train) {
        return trainRepository.save(train);
    }

    @Override
    public Train update(Long id, Train train) {
        if (!trainRepository.existsById(id)) {
            throw new NoSuchElementException("Train introuvable : " + id);
        }
        train.setTrainId(id);
        return trainRepository.save(train);
    }

    @Override
    public void deleteById(Long id) {
        if (!trainRepository.existsById(id)) {
            throw new NoSuchElementException("Train introuvable : " + id);
        }
        trainRepository.deleteById(id);
    }
}
