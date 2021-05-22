package holik.hotel.spring.persistence.repository;

import holik.hotel.spring.persistence.model.Application;
import org.springframework.data.repository.CrudRepository;

public interface ApplicationRepository extends CrudRepository<Application, Integer> {
}
