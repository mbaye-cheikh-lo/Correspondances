package Projet.Correspondances.Controller;

import Projet.Correspondances.Dto.FarePeriodDto;
import Projet.Correspondances.Entity.FarePeriod;
import Projet.Correspondances.Service.FarePeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping(path = "fare-period")
public class FarePeriodController {

    // permet d'avoir une connexion entre les trois Controller, Services, Repository
    private FarePeriodService farePeriodService;
    //Constructor
    @Autowired
    public FarePeriodController(FarePeriodService farePeriodService) {
        this.farePeriodService = farePeriodService;
    }

    @GetMapping
    public List<FarePeriod> all() {
        return farePeriodService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FarePeriod> get(@PathVariable Long id) {
        return farePeriodService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<FarePeriod> create(@RequestBody FarePeriodDto dto) {
        FarePeriod farePeriod = new FarePeriod();
        farePeriod.setPeriodName(dto.periodName);

        FarePeriod create = farePeriodService.save(farePeriod);
        return ResponseEntity.created(URI.create("/fare-period/" + create.getPeriodId())).body(create);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FarePeriod> update(@PathVariable Long id, @RequestBody FarePeriodDto dto) {
        return farePeriodService.findById(id)
                .map(existing -> {
                    existing.setPeriodName(dto.periodName);

                    return ResponseEntity.ok(farePeriodService.update(id, existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (farePeriodService.findById(id).isEmpty())
            return ResponseEntity.notFound().build();
        farePeriodService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
