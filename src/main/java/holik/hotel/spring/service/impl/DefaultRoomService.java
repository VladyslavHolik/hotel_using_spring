package holik.hotel.spring.service.impl;

import holik.hotel.spring.persistence.model.Room;
import holik.hotel.spring.persistence.repository.RoomRepository;
import holik.hotel.spring.service.RoomService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DefaultRoomService implements RoomService {
    private final RoomRepository roomRepository;

    public DefaultRoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public Page<Room> findPaginated(Pageable pageable) {
        return roomRepository.findAll(pageable);
    }

    @Override
    public Optional<Room> getRoomById(int id) {
        return roomRepository.findById(id);
    }
}
