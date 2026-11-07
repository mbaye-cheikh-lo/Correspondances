package Projet.Correspondances.Service;

import Projet.Correspondances.Entity.ServiceCalendar;
import java.util.List;
import java.util.Optional;

public interface ServiceCalendarService {

    List<ServiceCalendar> findAll();

    Optional<ServiceCalendar> findById(Long id);

    ServiceCalendar save(ServiceCalendar serviceCalendar);

    ServiceCalendar update(Long id, ServiceCalendar serviceCalendar);

    void deleteById(Long id);
}
