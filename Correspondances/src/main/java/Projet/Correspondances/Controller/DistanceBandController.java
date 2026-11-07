package Projet.Correspondances.Controller;

import Projet.Correspondances.Dto.DistanceBandDto;
import Projet.Correspondances.Entity.DistanceBand;
import Projet.Correspondances.Service.DistanceBandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping(path = "distance-band")
public class DistanceBandController {

    // permet d'avoir une connexion entre les trois Controller, Services, Repository
    private DistanceBandService distanceBandService;
    //Constructor
    @Autowired
    public DistanceBandController(DistanceBandService distanceBandService) {
        this.distanceBandService = distanceBandService;
    }

    @GetMapping
    public List<DistanceBand> all() {
        return distanceBandService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DistanceBand> get(@PathVariable Long id) {
        return distanceBandService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<DistanceBand> create(@RequestBody DistanceBandDto dto) {
        DistanceBand distanceBand = new DistanceBand();
        distanceBand.setMinDistanceKm(dto.minDistanceKm);
        distanceBand.setMaxDistanceKm(dto.maxDistanceKm);

        DistanceBand create = distanceBandService.save(distanceBand);
        return ResponseEntity.created(URI.create("/distance-band/" + create.getDistanceBandId())).body(create);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DistanceBand> update(@PathVariable Long id, @RequestBody DistanceBandDto dto) {
        return distanceBandService.findById(id)
                .map(existing -> {
                    existing.setMinDistanceKm(dto.minDistanceKm);
                    existing.setMaxDistanceKm(dto.maxDistanceKm);

                    return ResponseEntity.ok(distanceBandService.update(id, existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (distanceBandService.findById(id).isEmpty())
            return ResponseEntity.notFound().build();
        distanceBandService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
