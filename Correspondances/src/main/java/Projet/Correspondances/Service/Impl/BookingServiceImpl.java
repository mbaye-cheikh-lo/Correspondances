package Projet.Correspondances.Service.Impl;

import Projet.Correspondances.Entity.Booking;
import Projet.Correspondances.Repository.BookingRepository;
import Projet.Correspondances.Service.BookingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    public BookingServiceImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Booking> findById(Long id) {
        return bookingRepository.findById(id);
    }

    @Override
    public Booking save(Booking booking) {
        return bookingRepository.save(booking);
    }

    @Override
    public Booking update(Long id, Booking booking) {
        if (!bookingRepository.existsById(id)) {
            throw new NoSuchElementException("Booking introuvable : " + id);
        }
        booking.setBookingId(id);
        return bookingRepository.save(booking);
    }

    @Override
    public void deleteById(Long id) {
        if (!bookingRepository.existsById(id)) {
            throw new NoSuchElementException("Booking introuvable : " + id);
        }
        bookingRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Booking> findByTravelerId(Long travelerId) {
        return bookingRepository.findByTravelerTravelerId(travelerId);
    }
}
