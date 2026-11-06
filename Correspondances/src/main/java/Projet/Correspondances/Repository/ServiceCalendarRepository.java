package Projet.Correspondances.Repository;

import Projet.Correspondances.Entity.ServiceCalendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceCalendarRepository extends JpaRepository<ServiceCalendar, Long> {
}
