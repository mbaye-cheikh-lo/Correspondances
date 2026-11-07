package Projet.Correspondances.Service.Impl;

import Projet.Correspondances.Entity.Traveler;
import Projet.Correspondances.Repository.TravelerRepository;
import Projet.Correspondances.Service.TravelerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class TravelerServiceImpl implements TravelerService {

    private final TravelerRepository travelerRepository;

    public TravelerServiceImpl(TravelerRepository travelerRepository) {
        this.travelerRepository = travelerRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Traveler> findAll() {
        return travelerRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Traveler> findById(Long id) {
        return travelerRepository.findById(id);
    }

    @Override
    public Traveler save(Traveler traveler) {
        return travelerRepository.save(traveler);
    }

    @Override
    public Traveler update(Long id, Traveler traveler) {
        if (!travelerRepository.existsById(id)) {
            throw new NoSuchElementException("Traveler introuvable : " + id);
        }
        traveler.setTravelerId(id);
        return travelerRepository.save(traveler);
    }

    @Override
    public void deleteById(Long id) {
        if (!travelerRepository.existsById(id)) {
            throw new NoSuchElementException("Traveler introuvable : " + id);
        }
        travelerRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Traveler> findByEmail(String email) {
        return travelerRepository.findByEmail(email);
    }
}
