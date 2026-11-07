package Projet.Correspondances.Service;

import Projet.Correspondances.Entity.ServiceException;
import java.util.List;
import java.util.Optional;

public interface ServiceExceptionService {

    List<ServiceException> findAll();

    Optional<ServiceException> findById(Long id);

    ServiceException save(ServiceException serviceException);

    ServiceException update(Long id, ServiceException serviceException);

    void deleteById(Long id);

    List<ServiceException> findByCalendarId(Long calendarId);
}
