package holik.hotel.spring.persistence.repository;

import holik.hotel.spring.persistence.model.Room;
import holik.hotel.spring.persistence.model.RoomClass;
import holik.hotel.spring.persistence.model.RoomStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends CrudRepository<Room, Integer>, PagingAndSortingRepository<Room, Integer> {
    List<Room> getRoomsByRoomClassAndSpaceAndRoomStatusIsNot(RoomClass roomClass, int space, RoomStatus roomStatus);
}
