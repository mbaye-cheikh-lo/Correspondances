package Projet.Correspondances.Controller;

import Projet.Correspondances.Dto.FareDto;
import Projet.Correspondances.Entity.DistanceBand;
import Projet.Correspondances.Entity.Fare;
import Projet.Correspondances.Entity.FarePeriod;
import Projet.Correspondances.Entity.TravelClass;
import Projet.Correspondances.Entity.TravelerProfile;
import Projet.Correspondances.Service.DistanceBandService;
import Projet.Correspondances.Service.FareService;
import Projet.Correspondances.Service.FarePeriodService;
import Projet.Correspondances.Service.TravelClassService;
import Projet.Correspondances.Service.TravelerProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(path = "fare")
public class FareController {

    // permet d'avoir une connexion entre les trois Controller, Services, Repository
    private FareService fareService;
    private FarePeriodService farePeriodService;
    private DistanceBandService distanceBandService;
    private TravelClassService travelClassService;
    private TravelerProfileService travelerProfileService;
    //Constructor
    @Autowired
    public FareController(FareService fareService, FarePeriodService farePeriodService, DistanceBandService distanceBandService, TravelClassService travelClassService, TravelerProfileService travelerProfileService) {
        this.fareService = fareService;
        this.farePeriodService = farePeriodService;
        this.distanceBandService = distanceBandService;
        this.travelClassService = travelClassService;
        this.travelerProfileService = travelerProfileService;
    }

    @GetMapping
    public List<Fare> all() {
        return fareService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fare> get(@PathVariable Long id) {
        return fareService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Fare> create(@RequestBody FareDto dto) {
        // FarePeriod : on le cherche grâce à son id
        FarePeriod period = null;
        if (dto.periodId != null) {
            period = farePeriodService.findById(dto.periodId).orElse(null);
        }
        if (period == null) {
            return ResponseEntity.badRequest().build();   // id absent ou inconnu
        }

        // DistanceBand : on le cherche grâce à son id
        DistanceBand distanceBand = null;
        if (dto.distanceBandId != null) {
            distanceBand = distanceBandService.findById(dto.distanceBandId).orElse(null);
        }
        if (distanceBand == null) {
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

        // TravelerProfile : on le cherche grâce à son id
        TravelerProfile profile = null;
        if (dto.profileId != null) {
            profile = travelerProfileService.findById(dto.profileId).orElse(null);
        }
        if (profile == null) {
            return ResponseEntity.badRequest().build();   // id absent ou inconnu
        }

        Fare fare = new Fare();
        fare.setPrice(dto.price);
        fare.setPeriod(period);
        fare.setDistanceBand(distanceBand);
        fare.setTravelClass(travelClass);
        fare.setProfile(profile);

        Fare create = fareService.save(fare);
        return ResponseEntity.created(URI.create("/fare/" + create.getFareId())).body(create);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Fare> update(@PathVariable Long id, @RequestBody FareDto dto) {
        // on cherche l'élément à modifier
        Optional<Fare> found = fareService.findById(id);
        if (found.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Fare existing = found.get();

        // FarePeriod : on le cherche grâce à son id
        FarePeriod period = null;
        if (dto.periodId != null) {
            period = farePeriodService.findById(dto.periodId).orElse(null);
        }
        if (period == null) {
            return ResponseEntity.badRequest().build();   // id absent ou inconnu
        }

        // DistanceBand : on le cherche grâce à son id
        DistanceBand distanceBand = null;
        if (dto.distanceBandId != null) {
            distanceBand = distanceBandService.findById(dto.distanceBandId).orElse(null);
        }
        if (distanceBand == null) {
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

        // TravelerProfile : on le cherche grâce à son id
        TravelerProfile profile = null;
        if (dto.profileId != null) {
            profile = travelerProfileService.findById(dto.profileId).orElse(null);
        }
        if (profile == null) {
            return ResponseEntity.badRequest().build();   // id absent ou inconnu
        }

        existing.setPrice(dto.price);
        existing.setPeriod(period);
        existing.setDistanceBand(distanceBand);
        existing.setTravelClass(travelClass);
        existing.setProfile(profile);

        Fare updated = fareService.update(id, existing);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (fareService.findById(id).isEmpty())
            return ResponseEntity.notFound().build();
        fareService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
