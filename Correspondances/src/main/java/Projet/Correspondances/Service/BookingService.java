package Projet.Correspondances.Service;

import Projet.Correspondances.Entity.Booking;
import java.util.List;
import java.util.Optional;

public interface BookingService {

    List<Booking> findAll();

    Optional<Booking> findById(Long id);

    Booking save(Booking booking);

    Booking update(Long id, Booking booking);

    void deleteById(Long id);

    List<Booking> findByTravelerId(Long travelerId);
}
