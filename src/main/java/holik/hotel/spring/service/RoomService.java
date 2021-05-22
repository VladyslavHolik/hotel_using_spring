package holik.hotel.spring.service;

import holik.hotel.spring.persistence.model.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface RoomService {
    Page<Room> findPaginated(Pageable pageable);
    Optional<Room> getRoomById(int id);
}
