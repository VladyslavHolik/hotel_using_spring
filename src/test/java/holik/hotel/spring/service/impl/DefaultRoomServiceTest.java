package holik.hotel.spring.service.impl;

import holik.hotel.spring.persistence.model.Room;
import holik.hotel.spring.persistence.model.RoomClass;
import holik.hotel.spring.persistence.model.RoomStatus;
import holik.hotel.spring.persistence.repository.RoomRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DefaultRoomServiceTest {
    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private DefaultRoomService roomService;

    @Test
    public void findPaginated() {
        Pageable pageable = PageRequest.of(1,4, Sort.by("price"));
        roomService.findPaginated(pageable);
        verify(roomRepository).findAll(pageable);
    }

    @Test
    public void getRoomById() {
        roomService.getRoomById(4);
        verify(roomRepository).findById(4);
    }

    @Test
    public void getAvailableRooms() {
        roomService.getAvailableRooms();
        verify(roomRepository).getRoomsByRoomStatusIsNot(RoomStatus.getStatusUnavailable());
    }

    @Test
    public void getAvailableRoomsByRoomClassAndSpace() {
        RoomClass apartment = new RoomClass();
        apartment.setId(1);
        roomService.getAvailableRooms(apartment, 2);
        verify(roomRepository)
                .getRoomsByRoomClassAndSpaceAndRoomStatusIsNot(apartment, 2,RoomStatus.getStatusUnavailable());
    }

    @Test
    public void updateRoom() {
        Room room = new Room();
        roomService.updateRoom(room);
        verify(roomRepository).save(room);
    }
}