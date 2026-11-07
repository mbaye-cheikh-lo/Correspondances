package Projet.Correspondances.Controller;

import Projet.Correspondances.Dto.CoachDto;
import Projet.Correspondances.Entity.Coach;
import Projet.Correspondances.Entity.Train;
import Projet.Correspondances.Service.CoachService;
import Projet.Correspondances.Service.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(path = "coach")
public class CoachController {

    // permet d'avoir une connexion entre les trois Controller, Services, Repository
    private CoachService coachService;
    private TrainService trainService;
    //Constructor
    @Autowired
    public CoachController(CoachService coachService, TrainService trainService) {
        this.coachService = coachService;
        this.trainService = trainService;
    }

    @GetMapping
    public List<Coach> all() {
        return coachService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Coach> get(@PathVariable Long id) {
        return coachService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Coach> create(@RequestBody CoachDto dto) {
        // Train : on le cherche grâce à son id
        Train train = null;
        if (dto.trainId != null) {
            train = trainService.findById(dto.trainId).orElse(null);
        }
        if (train == null) {
            return ResponseEntity.badRequest().build();   // id absent ou inconnu
        }

        Coach coach = new Coach();
        coach.setCoachNumber(dto.coachNumber);
        coach.setTrain(train);

        Coach create = coachService.save(coach);
        return ResponseEntity.created(URI.create("/coach/" + create.getCoachId())).body(create);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Coach> update(@PathVariable Long id, @RequestBody CoachDto dto) {
        // on cherche l'élément à modifier
        Optional<Coach> found = coachService.findById(id);
        if (found.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Coach existing = found.get();

        // Train : on le cherche grâce à son id
        Train train = null;
        if (dto.trainId != null) {
            train = trainService.findById(dto.trainId).orElse(null);
        }
        if (train == null) {
            return ResponseEntity.badRequest().build();   // id absent ou inconnu
        }

        existing.setCoachNumber(dto.coachNumber);
        existing.setTrain(train);

        Coach updated = coachService.update(id, existing);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (coachService.findById(id).isEmpty())
            return ResponseEntity.notFound().build();
        coachService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
