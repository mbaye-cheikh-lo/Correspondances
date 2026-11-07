package Projet.Correspondances.Service.Impl;

import Projet.Correspondances.Entity.Circulation;
import Projet.Correspondances.Repository.CirculationRepository;
import Projet.Correspondances.Service.CirculationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class CirculationServiceImpl implements CirculationService {

    private final CirculationRepository circulationRepository;

    public CirculationServiceImpl(CirculationRepository circulationRepository) {
        this.circulationRepository = circulationRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Circulation> findAll() {
        return circulationRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Circulation> findById(Long id) {
        return circulationRepository.findById(id);
    }

    @Override
    public Circulation save(Circulation circulation) {
        return circulationRepository.save(circulation);
    }

    @Override
    public Circulation update(Long id, Circulation circulation) {
        if (!circulationRepository.existsById(id)) {
            throw new NoSuchElementException("Circulation introuvable : " + id);
        }
        circulation.setCirculationId(id);
        return circulationRepository.save(circulation);
    }

    @Override
    public void deleteById(Long id) {
        if (!circulationRepository.existsById(id)) {
            throw new NoSuchElementException("Circulation introuvable : " + id);
        }
        circulationRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Circulation> findByDate(LocalDate date) {
        return circulationRepository.findByCirculationDate(date);
    }
}
