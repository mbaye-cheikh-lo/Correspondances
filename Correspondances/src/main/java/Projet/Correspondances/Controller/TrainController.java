package Projet.Correspondances.Controller;

import Projet.Correspondances.Dto.TrainDto;
import Projet.Correspondances.Entity.Train;
import Projet.Correspondances.Service.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping(path = "train")
public class TrainController {

    // permet d'avoir une connexion entre les trois Controller, Services, Repository
    private TrainService trainService;
    //Constructor
    @Autowired
    public TrainController(TrainService trainService) {
        this.trainService = trainService;
    }

    @GetMapping
    public List<Train> all() {
        return trainService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Train> get(@PathVariable Long id) {
        return trainService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Train> create(@RequestBody TrainDto dto) {
        Train train = new Train();

        Train create = trainService.save(train);
        return ResponseEntity.created(URI.create("/train/" + create.getTrainId())).body(create);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Train> update(@PathVariable Long id, @RequestBody TrainDto dto) {
        return trainService.findById(id)
                .map(existing -> {
                    return ResponseEntity.ok(trainService.update(id, existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (trainService.findById(id).isEmpty())
            return ResponseEntity.notFound().build();
        trainService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
