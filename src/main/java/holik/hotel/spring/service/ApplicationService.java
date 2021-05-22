package holik.hotel.spring.service;

import holik.hotel.spring.persistence.model.Application;

import java.util.List;

public interface ApplicationService {
    void createApplication(Application application);
    List<Application> getRequestedApplications();
}
