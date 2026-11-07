package Projet.Correspondances.Controller;

import Projet.Correspondances.Dto.SeatDto;
import Projet.Correspondances.Entity.Coach;
import Projet.Correspondances.Entity.Seat;
import Projet.Correspondances.Entity.TravelClass;
import Projet.Correspondances.Service.CoachService;
import Projet.Correspondances.Service.SeatService;
import Projet.Correspondances.Service.TravelClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(path = "seat")
public class SeatController {

    // permet d'avoir une connexion entre les trois Controller, Services, Repository
    private SeatService seatService;
    private CoachService coachService;
    private TravelClassService travelClassService;
    //Constructor
    @Autowired
    public SeatController(SeatService seatService, CoachService coachService, TravelClassService travelClassService) {
        this.seatService = seatService;
        this.coachService = coachService;
        this.travelClassService = travelClassService;
    }

    @GetMapping
    public List<Seat> all() {
        return seatService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Seat> get(@PathVariable Long id) {
        return seatService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Seat> create(@RequestBody SeatDto dto) {
        // Coach : on le cherche grâce à son id
        Coach coach = null;
        if (dto.coachId != null) {
            coach = coachService.findById(dto.coachId).orElse(null);
        }
        if (coach == null) {
            return ResponseEntity.badRequest().build();   // id absent ou inconnu
        }

        // TravelClass : on le cherche grâce à son id
        TravelClass travelClass = null;
        if (dto.classId != null) {
            travelClass = travelClassService.findById(dto.classId).orElse(null);
        }
        if (travelClass == null) {
            return ResponseEntity.badRequest().build();   // id absent ou inconnu
        }

        Seat seat = new Seat();
        seat.setSeatNumber(dto.seatNumber);
        seat.setCoach(coach);
        seat.setTravelClass(travelClass);

        Seat create = seatService.save(seat);
        return ResponseEntity.created(URI.create("/seat/" + create.getSeatId())).body(create);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Seat> update(@PathVariable Long id, @RequestBody SeatDto dto) {
        // on cherche l'élément à modifier
        Optional<Seat> found = seatService.findById(id);
        if (found.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Seat existing = found.get();

        // Coach : on le cherche grâce à son id
        Coach coach = null;
        if (dto.coachId != null) {
            coach = coachService.findById(dto.coachId).orElse(null);
        }
        if (coach == null) {
            return ResponseEntity.badRequest().build();   // id absent ou inconnu
        }

        // TravelClass : on le cherche grâce à son id
        TravelClass travelClass = null;
        if (dto.classId != null) {
            travelClass = travelClassService.findById(dto.classId).orElse(null);
        }
        if (travelClass == null) {
            return ResponseEntity.badRequest().build();   // id absent ou inconnu
        }

        existing.setSeatNumber(dto.seatNumber);
        existing.setCoach(coach);
        existing.setTravelClass(travelClass);

        Seat updated = seatService.update(id, existing);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (seatService.findById(id).isEmpty())
            return ResponseEntity.notFound().build();
        seatService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Tous les sièges d'une voiture
    @GetMapping("/coach/{coachId}")
    public List<Seat> byCoach(@PathVariable Long coachId) {
        return seatService.findByCoachId(coachId);
    }
}
