package Projet.Correspondances.Service.Impl;

import Projet.Correspondances.Entity.Coach;
import Projet.Correspondances.Repository.CoachRepository;
import Projet.Correspondances.Service.CoachService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class CoachServiceImpl implements CoachService {

    private final CoachRepository coachRepository;

    public CoachServiceImpl(CoachRepository coachRepository) {
        this.coachRepository = coachRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Coach> findAll() {
        return coachRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Coach> findById(Long id) {
        return coachRepository.findById(id);
    }

    @Override
    public Coach save(Coach coach) {
        return coachRepository.save(coach);
    }

    @Override
    public Coach update(Long id, Coach coach) {
        if (!coachRepository.existsById(id)) {
            throw new NoSuchElementException("Coach introuvable : " + id);
        }
        coach.setCoachId(id);
        return coachRepository.save(coach);
    }

    @Override
    public void deleteById(Long id) {
        if (!coachRepository.existsById(id)) {
            throw new NoSuchElementException("Coach introuvable : " + id);
        }
        coachRepository.deleteById(id);
    }
}
