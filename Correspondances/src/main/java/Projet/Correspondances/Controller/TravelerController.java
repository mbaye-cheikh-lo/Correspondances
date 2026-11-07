package Projet.Correspondances.Controller;

import Projet.Correspondances.Dto.TravelerDto;
import Projet.Correspondances.Entity.Traveler;
import Projet.Correspondances.Entity.TravelerProfile;
import Projet.Correspondances.Service.TravelerService;
import Projet.Correspondances.Service.TravelerProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(path = "traveler")
public class TravelerController {

    // permet d'avoir une connexion entre les trois Controller, Services, Repository
    private TravelerService travelerService;
    private TravelerProfileService travelerProfileService;
    //Constructor
    @Autowired
    public TravelerController(TravelerService travelerService, TravelerProfileService travelerProfileService) {
        this.travelerService = travelerService;
        this.travelerProfileService = travelerProfileService;
    }

    @GetMapping
    public List<Traveler> all() {
        return travelerService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Traveler> get(@PathVariable Long id) {
        return travelerService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Traveler> create(@RequestBody TravelerDto dto) {
        // TravelerProfile : on le cherche grâce à son id
        TravelerProfile profile = null;
        if (dto.profileId != null) {
            profile = travelerProfileService.findById(dto.profileId).orElse(null);
            if (profile == null) {
                return ResponseEntity.badRequest().build();   // l'id envoyé n'existe pas
            }
        }

        Traveler traveler = new Traveler();
        traveler.setFirstName(dto.firstName);
        traveler.setLastName(dto.lastName);
        traveler.setEmail(dto.email);
        traveler.setPhone(dto.phone);
        traveler.setProfile(profile);

        Traveler create = travelerService.save(traveler);
        return ResponseEntity.created(URI.create("/traveler/" + create.getTravelerId())).body(create);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Traveler> update(@PathVariable Long id, @RequestBody TravelerDto dto) {
        // on cherche l'élément à modifier
        Optional<Traveler> found = travelerService.findById(id);
        if (found.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Traveler existing = found.get();

        // TravelerProfile : on le cherche grâce à son id
        TravelerProfile profile = null;
        if (dto.profileId != null) {
            profile = travelerProfileService.findById(dto.profileId).orElse(null);
            if (profile == null) {
                return ResponseEntity.badRequest().build();   // l'id envoyé n'existe pas
            }
        }

        existing.setFirstName(dto.firstName);
        existing.setLastName(dto.lastName);
        existing.setEmail(dto.email);
        existing.setPhone(dto.phone);
        existing.setProfile(profile);

        Traveler updated = travelerService.update(id, existing);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (travelerService.findById(id).isEmpty())
            return ResponseEntity.notFound().build();
        travelerService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Un voyageur grâce à son email
    @GetMapping("/email/{email}")
    public ResponseEntity<Traveler> byEmail(@PathVariable String email) {
        return travelerService.findByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
