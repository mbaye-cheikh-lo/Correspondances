package Projet.Correspondances.Controller;

import Projet.Correspondances.Dto.CirculationDto;
import Projet.Correspondances.Entity.Circulation;
import Projet.Correspondances.Entity.ServiceCalendar;
import Projet.Correspondances.Entity.Train;
import Projet.Correspondances.Service.CirculationService;
import Projet.Correspondances.Service.ServiceCalendarService;
import Projet.Correspondances.Service.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(path = "circulation")
public class CirculationController {

    // permet d'avoir une connexion entre les trois Controller, Services, Repository
    private CirculationService circulationService;
    private TrainService trainService;
    private ServiceCalendarService serviceCalendarService;
    //Constructor
    @Autowired
    public CirculationController(CirculationService circulationService, TrainService trainService, ServiceCalendarService serviceCalendarService) {
        this.circulationService = circulationService;
        this.trainService = trainService;
        this.serviceCalendarService = serviceCalendarService;
    }

    @GetMapping
    public List<Circulation> all() {
        return circulationService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Circulation> get(@PathVariable Long id) {
        return circulationService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Circulation> create(@RequestBody CirculationDto dto) {
        // Train : on le cherche grâce à son id
        Train train = null;
        if (dto.trainId != null) {
            train = trainService.findById(dto.trainId).orElse(null);
        }
        if (train == null) {
            return ResponseEntity.badRequest().build();   // id absent ou inconnu
        }

        // ServiceCalendar : on le cherche grâce à son id
        ServiceCalendar calendar = null;
        if (dto.calendarId != null) {
            calendar = serviceCalendarService.findById(dto.calendarId).orElse(null);
        }
        if (calendar == null) {
            return ResponseEntity.badRequest().build();   // id absent ou inconnu
        }

        Circulation circulation = new Circulation();
        circulation.setCirculationDate(dto.circulationDate);
        circulation.setTrain(train);
        circulation.setCalendar(calendar);

        Circulation create = circulationService.save(circulation);
        return ResponseEntity.created(URI.create("/circulation/" + create.getCirculationId())).body(create);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Circulation> update(@PathVariable Long id, @RequestBody CirculationDto dto) {
        // on cherche l'élément à modifier
        Optional<Circulation> found = circulationService.findById(id);
        if (found.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Circulation existing = found.get();

        // Train : on le cherche grâce à son id
        Train train = null;
        if (dto.trainId != null) {
            train = trainService.findById(dto.trainId).orElse(null);
        }
        if (train == null) {
            return ResponseEntity.badRequest().build();   // id absent ou inconnu
        }

        // ServiceCalendar : on le cherche grâce à son id
        ServiceCalendar calendar = null;
        if (dto.calendarId != null) {
            calendar = serviceCalendarService.findById(dto.calendarId).orElse(null);
        }
        if (calendar == null) {
            return ResponseEntity.badRequest().build();   // id absent ou inconnu
        }

        existing.setCirculationDate(dto.circulationDate);
        existing.setTrain(train);
        existing.setCalendar(calendar);

        Circulation updated = circulationService.update(id, existing);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (circulationService.findById(id).isEmpty())
            return ResponseEntity.notFound().build();
        circulationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Toutes les circulations d'un jour, exemple : /circulation/date/2026-11-07
    @GetMapping("/date/{date}")
    public List<Circulation> byDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return circulationService.findByDate(date);
    }
}
