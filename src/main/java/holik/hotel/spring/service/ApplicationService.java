package holik.hotel.spring.service;

import holik.hotel.spring.persistence.model.Application;
import holik.hotel.spring.persistence.model.ApplicationStatus;
import holik.hotel.spring.persistence.model.Room;
import holik.hotel.spring.persistence.model.User;

import java.util.List;
import java.util.Optional;

public interface ApplicationService {
    void createApplication(Application application);
    List<Application> getRequestedApplications();
    Optional<Application> getApplicationById(int id);
    List<Room> getFreeRooms(Application application);
    List<Application> getApplicationsByStatus(ApplicationStatus status);
    void processApplication(Application application, String choice);
    void updateApplication(Application application);
    List<Application> getReadyToBookApplications(User user);
    boolean canBeBooked(Application application);
    void bookRoom(Application application);
    List<Application> getBookedApplicationsByUser(User user);
}
