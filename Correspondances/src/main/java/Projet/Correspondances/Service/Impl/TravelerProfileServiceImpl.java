package Projet.Correspondances.Service.Impl;

import Projet.Correspondances.Entity.TravelerProfile;
import Projet.Correspondances.Repository.TravelerProfileRepository;
import Projet.Correspondances.Service.TravelerProfileService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class TravelerProfileServiceImpl implements TravelerProfileService {

    private final TravelerProfileRepository travelerProfileRepository;

    public TravelerProfileServiceImpl(TravelerProfileRepository travelerProfileRepository) {
        this.travelerProfileRepository = travelerProfileRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TravelerProfile> findAll() {
        return travelerProfileRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TravelerProfile> findById(Long id) {
        return travelerProfileRepository.findById(id);
    }

    @Override
    public TravelerProfile save(TravelerProfile travelerProfile) {
        return travelerProfileRepository.save(travelerProfile);
    }

    @Override
    public TravelerProfile update(Long id, TravelerProfile travelerProfile) {
        if (!travelerProfileRepository.existsById(id)) {
            throw new NoSuchElementException("TravelerProfile introuvable : " + id);
        }
        travelerProfile.setProfileId(id);
        return travelerProfileRepository.save(travelerProfile);
    }

    @Override
    public void deleteById(Long id) {
        if (!travelerProfileRepository.existsById(id)) {
            throw new NoSuchElementException("TravelerProfile introuvable : " + id);
        }
        travelerProfileRepository.deleteById(id);
    }
}
