package holik.hotel.spring.web.validator;

import holik.hotel.spring.web.dto.ApplicationDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ApplicationValidator {

    public boolean isValid(ApplicationDto applicationDto, BindingResult bindingResult) {
        LocalDateTime arrival = applicationDto.getArrival();
        LocalDateTime leaving = applicationDto.getLeaving();
        LocalDateTime now = LocalDateTime.now();

        boolean isValid = true;
        if (arrival.isBefore(now.plusHours(23)) || arrival.isAfter(leaving)) {
            isValid = false;
            bindingResult.rejectValue("arrival", "error.application", "Invalid arrival date");
        }

        Duration duration = Duration.between(arrival, leaving);
        if (duration.toHours() < 6) {
            isValid = false;
            bindingResult.rejectValue("leaving", "error.application", "Duration between arrival and leaving should be more than 6 hours");
        }

        int space = applicationDto.getSpace();
        if (space > 6 || space < 1) {
            isValid = false;
            bindingResult.rejectValue("space", "error.application", "Invalid space");
        }
        return isValid;
    }
}
