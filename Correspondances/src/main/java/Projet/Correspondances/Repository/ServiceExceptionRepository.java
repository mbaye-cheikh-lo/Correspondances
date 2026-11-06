package Projet.Correspondances.Repository;

import Projet.Correspondances.Entity.ServiceException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ServiceExceptionRepository extends JpaRepository<ServiceException, Long> {

    List<ServiceException> findByCalendarCalendarId(Long calendarId);
}
