package holik.hotel.spring.service.impl;

import holik.hotel.spring.persistence.model.Room;
import holik.hotel.spring.persistence.model.RoomClass;
import holik.hotel.spring.persistence.model.RoomStatus;
import holik.hotel.spring.persistence.repository.RoomRepository;
import holik.hotel.spring.service.RoomService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
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

    @Override
    public List<Room> getAvailableRooms(RoomClass roomClass, int space) {
        RoomStatus statusUnavailable = RoomStatus.getStatusUnavailable();
        return roomRepository.getRoomsByRoomClassAndSpaceAndRoomStatusIsNot(roomClass, space, statusUnavailable);
    }

    @Override
    public List<Room> getAvailableRooms() {
        RoomStatus statusUnavailable = RoomStatus.getStatusUnavailable();
        return roomRepository.getRoomsByRoomStatusIsNot(statusUnavailable);
    }

    @Override
    public void updateRoom(Room room) {
        roomRepository.save(room);
    }
}
