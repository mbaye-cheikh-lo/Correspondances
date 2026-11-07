package Projet.Correspondances.Service.Impl;

import Projet.Correspondances.Entity.FarePeriod;
import Projet.Correspondances.Repository.FarePeriodRepository;
import Projet.Correspondances.Service.FarePeriodService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class FarePeriodServiceImpl implements FarePeriodService {

    private final FarePeriodRepository farePeriodRepository;

    public FarePeriodServiceImpl(FarePeriodRepository farePeriodRepository) {
        this.farePeriodRepository = farePeriodRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<FarePeriod> findAll() {
        return farePeriodRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FarePeriod> findById(Long id) {
        return farePeriodRepository.findById(id);
    }

    @Override
    public FarePeriod save(FarePeriod farePeriod) {
        return farePeriodRepository.save(farePeriod);
    }

    @Override
    public FarePeriod update(Long id, FarePeriod farePeriod) {
        if (!farePeriodRepository.existsById(id)) {
            throw new NoSuchElementException("FarePeriod introuvable : " + id);
        }
        farePeriod.setPeriodId(id);
        return farePeriodRepository.save(farePeriod);
    }

    @Override
    public void deleteById(Long id) {
        if (!farePeriodRepository.existsById(id)) {
            throw new NoSuchElementException("FarePeriod introuvable : " + id);
        }
        farePeriodRepository.deleteById(id);
    }
}
