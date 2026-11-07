package Projet.Correspondances.Controller;

import Projet.Correspondances.Dto.TravelerProfileDto;
import Projet.Correspondances.Entity.TravelerProfile;
import Projet.Correspondances.Service.TravelerProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping(path = "traveler-profile")
public class TravelerProfileController {

    // permet d'avoir une connexion entre les trois Controller, Services, Repository
    private TravelerProfileService travelerProfileService;
    //Constructor
    @Autowired
    public TravelerProfileController(TravelerProfileService travelerProfileService) {
        this.travelerProfileService = travelerProfileService;
    }

    @GetMapping
    public List<TravelerProfile> all() {
        return travelerProfileService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TravelerProfile> get(@PathVariable Long id) {
        return travelerProfileService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TravelerProfile> create(@RequestBody TravelerProfileDto dto) {
        TravelerProfile travelerProfile = new TravelerProfile();
        travelerProfile.setProfileName(dto.profileName);

        TravelerProfile create = travelerProfileService.save(travelerProfile);
        return ResponseEntity.created(URI.create("/traveler-profile/" + create.getProfileId())).body(create);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TravelerProfile> update(@PathVariable Long id, @RequestBody TravelerProfileDto dto) {
        return travelerProfileService.findById(id)
                .map(existing -> {
                    existing.setProfileName(dto.profileName);

                    return ResponseEntity.ok(travelerProfileService.update(id, existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (travelerProfileService.findById(id).isEmpty())
            return ResponseEntity.notFound().build();
        travelerProfileService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
