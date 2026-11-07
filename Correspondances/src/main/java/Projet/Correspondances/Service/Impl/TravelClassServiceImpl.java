package Projet.Correspondances.Service.Impl;

import Projet.Correspondances.Entity.TravelClass;
import Projet.Correspondances.Repository.TravelClassRepository;
import Projet.Correspondances.Service.TravelClassService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class TravelClassServiceImpl implements TravelClassService {

    private final TravelClassRepository travelClassRepository;

    public TravelClassServiceImpl(TravelClassRepository travelClassRepository) {
        this.travelClassRepository = travelClassRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TravelClass> findAll() {
        return travelClassRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TravelClass> findById(Long id) {
        return travelClassRepository.findById(id);
    }

    @Override
    public TravelClass save(TravelClass travelClass) {
        return travelClassRepository.save(travelClass);
    }

    @Override
    public TravelClass update(Long id, TravelClass travelClass) {
        if (!travelClassRepository.existsById(id)) {
            throw new NoSuchElementException("TravelClass introuvable : " + id);
        }
        travelClass.setClassId(id);
        return travelClassRepository.save(travelClass);
    }

    @Override
    public void deleteById(Long id) {
        if (!travelClassRepository.existsById(id)) {
            throw new NoSuchElementException("TravelClass introuvable : " + id);
        }
        travelClassRepository.deleteById(id);
    }
}
