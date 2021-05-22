package holik.hotel.spring.web.validator;

import holik.hotel.spring.persistence.model.Room;
import holik.hotel.spring.service.RoomService;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ChoiceValidator {
    private final RoomService roomService;

    public ChoiceValidator(RoomService roomService) {
        this.roomService = roomService;
    }

    public void validateChoice(String choice) {
        if (choice == null || choice.isEmpty()) {
            throw new IllegalArgumentException("Choice cannot be empty");
        }

        if (!"decline".equals(choice)) {
            int roomId = Integer.parseInt(choice);

            if (roomId < 1) {
                // non existing room
                throw new IllegalArgumentException("Invalid room id");
            }

            Optional<Room> optionalRoom = roomService.getRoomById(roomId);
            if (optionalRoom.isEmpty()) {
                // non existing room
                throw new IllegalArgumentException("Invalid room id");
            }
        }
    }
}
