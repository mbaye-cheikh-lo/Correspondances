package Projet.Correspondances.Controller;

import Projet.Correspondances.Dto.TravelClassDto;
import Projet.Correspondances.Entity.TravelClass;
import Projet.Correspondances.Service.TravelClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping(path = "travel-class")
public class TravelClassController {

    // permet d'avoir une connexion entre les trois Controller, Services, Repository
    private TravelClassService travelClassService;
    //Constructor
    @Autowired
    public TravelClassController(TravelClassService travelClassService) {
        this.travelClassService = travelClassService;
    }

    @GetMapping
    public List<TravelClass> all() {
        return travelClassService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TravelClass> get(@PathVariable Long id) {
        return travelClassService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TravelClass> create(@RequestBody TravelClassDto dto) {
        TravelClass travelClass = new TravelClass();
        travelClass.setClassName(dto.className);
        travelClass.setDescription(dto.description);

        TravelClass create = travelClassService.save(travelClass);
        return ResponseEntity.created(URI.create("/travel-class/" + create.getClassId())).body(create);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TravelClass> update(@PathVariable Long id, @RequestBody TravelClassDto dto) {
        return travelClassService.findById(id)
                .map(existing -> {
                    existing.setClassName(dto.className);
                    existing.setDescription(dto.description);

                    return ResponseEntity.ok(travelClassService.update(id, existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (travelClassService.findById(id).isEmpty())
            return ResponseEntity.notFound().build();
        travelClassService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
