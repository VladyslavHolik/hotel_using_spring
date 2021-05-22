package holik.hotel.spring.persistence.repository;

import holik.hotel.spring.persistence.model.Room;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends CrudRepository<Room, Integer>, PagingAndSortingRepository<Room, Integer> {
}
