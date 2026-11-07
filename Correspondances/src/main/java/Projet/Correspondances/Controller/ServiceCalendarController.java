package Projet.Correspondances.Controller;

import Projet.Correspondances.Dto.ServiceCalendarDto;
import Projet.Correspondances.Entity.ServiceCalendar;
import Projet.Correspondances.Service.ServiceCalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping(path = "service-calendar")
public class ServiceCalendarController {

    // permet d'avoir une connexion entre les trois Controller, Services, Repository
    private ServiceCalendarService serviceCalendarService;
    //Constructor
    @Autowired
    public ServiceCalendarController(ServiceCalendarService serviceCalendarService) {
        this.serviceCalendarService = serviceCalendarService;
    }

    @GetMapping
    public List<ServiceCalendar> all() {
        return serviceCalendarService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceCalendar> get(@PathVariable Long id) {
        return serviceCalendarService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ServiceCalendar> create(@RequestBody ServiceCalendarDto dto) {
        ServiceCalendar serviceCalendar = new ServiceCalendar();
        serviceCalendar.setCalendarType(dto.calendarType);
        serviceCalendar.setStartDate(dto.startDate);
        serviceCalendar.setEndDate(dto.endDate);

        ServiceCalendar create = serviceCalendarService.save(serviceCalendar);
        return ResponseEntity.created(URI.create("/service-calendar/" + create.getCalendarId())).body(create);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceCalendar> update(@PathVariable Long id, @RequestBody ServiceCalendarDto dto) {
        return serviceCalendarService.findById(id)
                .map(existing -> {
                    existing.setCalendarType(dto.calendarType);
                    existing.setStartDate(dto.startDate);
                    existing.setEndDate(dto.endDate);

                    return ResponseEntity.ok(serviceCalendarService.update(id, existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (serviceCalendarService.findById(id).isEmpty())
            return ResponseEntity.notFound().build();
        serviceCalendarService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
