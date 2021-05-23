package holik.hotel.spring.web.validator;

import holik.hotel.spring.persistence.model.Application;
import holik.hotel.spring.persistence.model.User;
import holik.hotel.spring.service.ApplicationService;
import holik.hotel.spring.web.dto.ApplicationDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ApplicationValidator {
    private final ApplicationService applicationService;

    public ApplicationValidator(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

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

    public void validateForBooking(int applicationId, User user) {
        Application application = applicationService.getApplicationById(applicationId).orElseThrow();

        LocalDateTime datetimeOfLeaving = application.getLeaving();
        LocalDateTime now = LocalDateTime.now();

        if (datetimeOfLeaving.isBefore(now)) {
            // Application should not be expired
            throw new IllegalArgumentException("Application is expired");
        }

        User originUser = application.getUser();

        if (user.getId() != originUser.getId()) {
            // Only authorized user can book room
            throw new IllegalArgumentException("User is not authorized");
        }
        if (!applicationService.canBeBooked(application)) {
            // Can't book room when it is already booked or paid
            throw new IllegalArgumentException("Room is already booked");
        }
    }

    public void validateForPaying(int applicationId, User user) {
        Application application = applicationService.getApplicationById(applicationId).orElseThrow();
        if (!(user.getId() == application.getUser().getId() && application.getStatus().getId() == 4)) {
            throw new IllegalArgumentException("Application can't be paid");
        }
    }
}
