package Projet.Correspondances.Service.Impl;

import Projet.Correspondances.Entity.Fare;
import Projet.Correspondances.Repository.FareRepository;
import Projet.Correspondances.Service.FareService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class FareServiceImpl implements FareService {

    private final FareRepository fareRepository;

    public FareServiceImpl(FareRepository fareRepository) {
        this.fareRepository = fareRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Fare> findAll() {
        return fareRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Fare> findById(Long id) {
        return fareRepository.findById(id);
    }

    @Override
    public Fare save(Fare fare) {
        return fareRepository.save(fare);
    }

    @Override
    public Fare update(Long id, Fare fare) {
        if (!fareRepository.existsById(id)) {
            throw new NoSuchElementException("Fare introuvable : " + id);
        }
        fare.setFareId(id);
        return fareRepository.save(fare);
    }

    @Override
    public void deleteById(Long id) {
        if (!fareRepository.existsById(id)) {
            throw new NoSuchElementException("Fare introuvable : " + id);
        }
        fareRepository.deleteById(id);
    }
}
