package Projet.Correspondances.Service.Impl;

import Projet.Correspondances.Entity.ServiceException;
import Projet.Correspondances.Repository.ServiceExceptionRepository;
import Projet.Correspondances.Service.ServiceExceptionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class ServiceExceptionServiceImpl implements ServiceExceptionService {

    private final ServiceExceptionRepository serviceExceptionRepository;

    public ServiceExceptionServiceImpl(ServiceExceptionRepository serviceExceptionRepository) {
        this.serviceExceptionRepository = serviceExceptionRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServiceException> findAll() {
        return serviceExceptionRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ServiceException> findById(Long id) {
        return serviceExceptionRepository.findById(id);
    }

    @Override
    public ServiceException save(ServiceException serviceException) {
        return serviceExceptionRepository.save(serviceException);
    }

    @Override
    public ServiceException update(Long id, ServiceException serviceException) {
        if (!serviceExceptionRepository.existsById(id)) {
            throw new NoSuchElementException("ServiceException introuvable : " + id);
        }
        serviceException.setExceptionId(id);
        return serviceExceptionRepository.save(serviceException);
    }

    @Override
    public void deleteById(Long id) {
        if (!serviceExceptionRepository.existsById(id)) {
            throw new NoSuchElementException("ServiceException introuvable : " + id);
        }
        serviceExceptionRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServiceException> findByCalendarId(Long calendarId) {
        return serviceExceptionRepository.findByCalendarCalendarId(calendarId);
    }
}
