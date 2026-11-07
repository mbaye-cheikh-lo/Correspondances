package Projet.Correspondances.Controller;

import Projet.Correspondances.Dto.ServiceExceptionDto;
import Projet.Correspondances.Entity.ServiceCalendar;
import Projet.Correspondances.Entity.ServiceException;
import Projet.Correspondances.Service.ServiceCalendarService;
import Projet.Correspondances.Service.ServiceExceptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(path = "service-exception")
public class ServiceExceptionController {

    // permet d'avoir une connexion entre les trois Controller, Services, Repository
    private ServiceExceptionService serviceExceptionService;
    private ServiceCalendarService serviceCalendarService;
    //Constructor
    @Autowired
    public ServiceExceptionController(ServiceExceptionService serviceExceptionService, ServiceCalendarService serviceCalendarService) {
        this.serviceExceptionService = serviceExceptionService;
        this.serviceCalendarService = serviceCalendarService;
    }

    @GetMapping
    public List<ServiceException> all() {
        return serviceExceptionService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceException> get(@PathVariable Long id) {
        return serviceExceptionService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ServiceException> create(@RequestBody ServiceExceptionDto dto) {
        // ServiceCalendar : on le cherche grâce à son id
        ServiceCalendar calendar = null;
        if (dto.calendarId != null) {
            calendar = serviceCalendarService.findById(dto.calendarId).orElse(null);
        }
        if (calendar == null) {
            return ResponseEntity.badRequest().build();   // id absent ou inconnu
        }

        ServiceException serviceException = new ServiceException();
        serviceException.setExceptionDate(dto.exceptionDate);
        serviceException.setExceptionType(dto.exceptionType);
        serviceException.setCalendar(calendar);

        ServiceException create = serviceExceptionService.save(serviceException);
        return ResponseEntity.created(URI.create("/service-exception/" + create.getExceptionId())).body(create);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceException> update(@PathVariable Long id, @RequestBody ServiceExceptionDto dto) {
        // on cherche l'élément à modifier
        Optional<ServiceException> found = serviceExceptionService.findById(id);
        if (found.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        ServiceException existing = found.get();

        // ServiceCalendar : on le cherche grâce à son id
        ServiceCalendar calendar = null;
        if (dto.calendarId != null) {
            calendar = serviceCalendarService.findById(dto.calendarId).orElse(null);
        }
        if (calendar == null) {
            return ResponseEntity.badRequest().build();   // id absent ou inconnu
        }

        existing.setExceptionDate(dto.exceptionDate);
        existing.setExceptionType(dto.exceptionType);
        existing.setCalendar(calendar);

        ServiceException updated = serviceExceptionService.update(id, existing);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (serviceExceptionService.findById(id).isEmpty())
            return ResponseEntity.notFound().build();
        serviceExceptionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Toutes les exceptions d'un calendrier
    @GetMapping("/calendar/{calendarId}")
    public List<ServiceException> byCalendar(@PathVariable Long calendarId) {
        return serviceExceptionService.findByCalendarId(calendarId);
    }
}
