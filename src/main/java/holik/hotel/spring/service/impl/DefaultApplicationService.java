package holik.hotel.spring.service.impl;

import holik.hotel.spring.persistence.model.Application;
import holik.hotel.spring.persistence.model.ApplicationStatus;
import holik.hotel.spring.persistence.repository.ApplicationRepository;
import holik.hotel.spring.service.ApplicationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultApplicationService implements ApplicationService {
    private final ApplicationRepository applicationRepository;

    public DefaultApplicationService(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    @Override
    public void createApplication(Application application) {
        applicationRepository.save(application);
    }

    @Override
    public List<Application> getRequestedApplications() {
        ApplicationStatus status = new ApplicationStatus();
        status.setId(1);
        return applicationRepository.getApplicationsByStatus(status);
    }
}
