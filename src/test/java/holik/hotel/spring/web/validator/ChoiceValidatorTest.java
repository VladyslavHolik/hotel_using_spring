package holik.hotel.spring.web.validator;

import holik.hotel.spring.persistence.model.Room;
import holik.hotel.spring.service.RoomService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class ChoiceValidatorTest {
    @Mock
    private RoomService roomService;

    @InjectMocks
    private ChoiceValidator choiceValidator;

    @Test(expected = IllegalArgumentException.class)
    public void validateChoiceIfNull() {
        choiceValidator.validateChoice(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateChoiceIfEmpty() {
        choiceValidator.validateChoice("");
    }

    @Test(expected = NumberFormatException.class)
    public void validateChoiceIfInvalid() {
        choiceValidator.validateChoice("this is not room id");
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateChoiceIfInvalidRoomId() {
        choiceValidator.validateChoice("-2");
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateChoiceIfRoomDoesNotExist() {
        when(roomService.getRoomById(5)).thenReturn(Optional.empty());
        choiceValidator.validateChoice("5");
    }

    public void validateChoiceIfValid() {
        when(roomService.getRoomById(2)).thenReturn(Optional.of(new Room()));
        choiceValidator.validateChoice("2");
    }

    public void validateChoiceIfValidDeclined() {
        choiceValidator.validateChoice("decline");
    }
}