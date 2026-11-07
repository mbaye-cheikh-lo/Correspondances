package Projet.Correspondances.Service.Impl;

import Projet.Correspondances.Entity.ServiceCalendar;
import Projet.Correspondances.Repository.ServiceCalendarRepository;
import Projet.Correspondances.Service.ServiceCalendarService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class ServiceCalendarServiceImpl implements ServiceCalendarService {

    private final ServiceCalendarRepository serviceCalendarRepository;

    public ServiceCalendarServiceImpl(ServiceCalendarRepository serviceCalendarRepository) {
        this.serviceCalendarRepository = serviceCalendarRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServiceCalendar> findAll() {
        return serviceCalendarRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ServiceCalendar> findById(Long id) {
        return serviceCalendarRepository.findById(id);
    }

    @Override
    public ServiceCalendar save(ServiceCalendar serviceCalendar) {
        return serviceCalendarRepository.save(serviceCalendar);
    }

    @Override
    public ServiceCalendar update(Long id, ServiceCalendar serviceCalendar) {
        if (!serviceCalendarRepository.existsById(id)) {
            throw new NoSuchElementException("ServiceCalendar introuvable : " + id);
        }
        serviceCalendar.setCalendarId(id);
        return serviceCalendarRepository.save(serviceCalendar);
    }

    @Override
    public void deleteById(Long id) {
        if (!serviceCalendarRepository.existsById(id)) {
            throw new NoSuchElementException("ServiceCalendar introuvable : " + id);
        }
        serviceCalendarRepository.deleteById(id);
    }
}
