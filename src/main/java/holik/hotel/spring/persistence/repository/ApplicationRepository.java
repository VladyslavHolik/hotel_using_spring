package holik.hotel.spring.persistence.repository;

import holik.hotel.spring.persistence.model.Application;
import holik.hotel.spring.persistence.model.ApplicationStatus;
import holik.hotel.spring.persistence.model.Room;
import holik.hotel.spring.persistence.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ApplicationRepository extends CrudRepository<Application, Integer> {
    List<Application> getApplicationsByStatus(ApplicationStatus status);
    List<Application> getApplicationsByStatusAndUser(ApplicationStatus status, User user);
    List<Application> getApplicationsByStatusAndRoom(ApplicationStatus status, Room room);
}
