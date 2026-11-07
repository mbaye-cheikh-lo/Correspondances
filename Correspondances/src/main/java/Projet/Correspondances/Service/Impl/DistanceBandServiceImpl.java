package Projet.Correspondances.Service.Impl;

import Projet.Correspondances.Entity.DistanceBand;
import Projet.Correspondances.Repository.DistanceBandRepository;
import Projet.Correspondances.Service.DistanceBandService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class DistanceBandServiceImpl implements DistanceBandService {

    private final DistanceBandRepository distanceBandRepository;

    public DistanceBandServiceImpl(DistanceBandRepository distanceBandRepository) {
        this.distanceBandRepository = distanceBandRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DistanceBand> findAll() {
        return distanceBandRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DistanceBand> findById(Long id) {
        return distanceBandRepository.findById(id);
    }

    @Override
    public DistanceBand save(DistanceBand distanceBand) {
        return distanceBandRepository.save(distanceBand);
    }

    @Override
    public DistanceBand update(Long id, DistanceBand distanceBand) {
        if (!distanceBandRepository.existsById(id)) {
            throw new NoSuchElementException("DistanceBand introuvable : " + id);
        }
        distanceBand.setDistanceBandId(id);
        return distanceBandRepository.save(distanceBand);
    }

    @Override
    public void deleteById(Long id) {
        if (!distanceBandRepository.existsById(id)) {
            throw new NoSuchElementException("DistanceBand introuvable : " + id);
        }
        distanceBandRepository.deleteById(id);
    }
}
