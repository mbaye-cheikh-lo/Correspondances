package Projet.Correspondances.Controller;

import Projet.Correspondances.Dto.BookingDto;
import Projet.Correspondances.Entity.Booking;
import Projet.Correspondances.Entity.Circulation;
import Projet.Correspondances.Entity.Fare;
import Projet.Correspondances.Entity.Seat;
import Projet.Correspondances.Entity.Traveler;
import Projet.Correspondances.Service.BookingService;
import Projet.Correspondances.Service.CirculationService;
import Projet.Correspondances.Service.FareService;
import Projet.Correspondances.Service.SeatService;
import Projet.Correspondances.Service.TravelerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(path = "booking")
public class BookingController {

    // permet d'avoir une connexion entre les trois Controller, Services, Repository
    private BookingService bookingService;
    private TravelerService travelerService;
    private CirculationService circulationService;
    private SeatService seatService;
    private FareService fareService;
    //Constructor
    @Autowired
    public BookingController(BookingService bookingService, TravelerService travelerService, CirculationService circulationService, SeatService seatService, FareService fareService) {
        this.bookingService = bookingService;
        this.travelerService = travelerService;
        this.circulationService = circulationService;
        this.seatService = seatService;
        this.fareService = fareService;
    }

    @GetMapping
    public List<Booking> all() {
        return bookingService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> get(@PathVariable Long id) {
        return bookingService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Booking> create(@RequestBody BookingDto dto) {
        // Traveler : on le cherche grâce à son id
        Traveler traveler = null;
        if (dto.travelerId != null) {
            traveler = travelerService.findById(dto.travelerId).orElse(null);
        }
        if (traveler == null) {
            return ResponseEntity.badRequest().build();   // id absent ou inconnu
        }

        // Circulation : on le cherche grâce à son id
        Circulation circulation = null;
        if (dto.circulationId != null) {
            circulation = circulationService.findById(dto.circulationId).orElse(null);
        }
        if (circulation == null) {
            return ResponseEntity.badRequest().build();   // id absent ou inconnu
        }

        // Seat : on le cherche grâce à son id
        Seat seat = null;
        if (dto.seatId != null) {
            seat = seatService.findById(dto.seatId).orElse(null);
        }
        if (seat == null) {
            return ResponseEntity.badRequest().build();   // id absent ou inconnu
        }

        // Fare : on le cherche grâce à son id
        Fare fare = null;
        if (dto.fareId != null) {
            fare = fareService.findById(dto.fareId).orElse(null);
        }
        if (fare == null) {
            return ResponseEntity.badRequest().build();   // id absent ou inconnu
        }

        Booking booking = new Booking();
        booking.setBookingDate(dto.bookingDate);
        booking.setStatus(dto.status);
        booking.setPricePaid(dto.pricePaid);
        booking.setTraveler(traveler);
        booking.setCirculation(circulation);
        booking.setSeat(seat);
        booking.setFare(fare);

        Booking create = bookingService.save(booking);
        return ResponseEntity.created(URI.create("/booking/" + create.getBookingId())).body(create);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Booking> update(@PathVariable Long id, @RequestBody BookingDto dto) {
        // on cherche l'élément à modifier
        Optional<Booking> found = bookingService.findById(id);
        if (found.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Booking existing = found.get();

        // Traveler : on le cherche grâce à son id
        Traveler traveler = null;
        if (dto.travelerId != null) {
            traveler = travelerService.findById(dto.travelerId).orElse(null);
        }
        if (traveler == null) {
            return ResponseEntity.badRequest().build();   // id absent ou inconnu
        }

        // Circulation : on le cherche grâce à son id
        Circulation circulation = null;
        if (dto.circulationId != null) {
            circulation = circulationService.findById(dto.circulationId).orElse(null);
        }
        if (circulation == null) {
            return ResponseEntity.badRequest().build();   // id absent ou inconnu
        }

        // Seat : on le cherche grâce à son id
        Seat seat = null;
        if (dto.seatId != null) {
            seat = seatService.findById(dto.seatId).orElse(null);
        }
        if (seat == null) {
            return ResponseEntity.badRequest().build();   // id absent ou inconnu
        }

        // Fare : on le cherche grâce à son id
        Fare fare = null;
        if (dto.fareId != null) {
            fare = fareService.findById(dto.fareId).orElse(null);
        }
        if (fare == null) {
            return ResponseEntity.badRequest().build();   // id absent ou inconnu
        }

        existing.setBookingDate(dto.bookingDate);
        existing.setStatus(dto.status);
        existing.setPricePaid(dto.pricePaid);
        existing.setTraveler(traveler);
        existing.setCirculation(circulation);
        existing.setSeat(seat);
        existing.setFare(fare);

        Booking updated = bookingService.update(id, existing);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (bookingService.findById(id).isEmpty())
            return ResponseEntity.notFound().build();
        bookingService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Toutes les réservations d'un voyageur
    @GetMapping("/traveler/{travelerId}")
    public List<Booking> byTraveler(@PathVariable Long travelerId) {
        return bookingService.findByTravelerId(travelerId);
    }
}
