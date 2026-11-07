package Projet.Correspondances.Service.Impl;

import Projet.Correspondances.Entity.Seat;
import Projet.Correspondances.Repository.SeatRepository;
import Projet.Correspondances.Service.SeatService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;

    public SeatServiceImpl(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Seat> findAll() {
        return seatRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Seat> findById(Long id) {
        return seatRepository.findById(id);
    }

    @Override
    public Seat save(Seat seat) {
        return seatRepository.save(seat);
    }

    @Override
    public Seat update(Long id, Seat seat) {
        if (!seatRepository.existsById(id)) {
            throw new NoSuchElementException("Seat introuvable : " + id);
        }
        seat.setSeatId(id);
        return seatRepository.save(seat);
    }

    @Override
    public void deleteById(Long id) {
        if (!seatRepository.existsById(id)) {
            throw new NoSuchElementException("Seat introuvable : " + id);
        }
        seatRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Seat> findByCoachId(Long coachId) {
        return seatRepository.findByCoachCoachId(coachId);
    }
}
