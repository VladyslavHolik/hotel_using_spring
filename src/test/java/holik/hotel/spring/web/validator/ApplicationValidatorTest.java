package holik.hotel.spring.web.validator;

import holik.hotel.spring.persistence.model.Application;
import holik.hotel.spring.persistence.model.ApplicationStatus;
import holik.hotel.spring.persistence.model.User;
import holik.hotel.spring.service.ApplicationService;
import holik.hotel.spring.web.dto.ApplicationDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.validation.BindingResult;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationValidatorTest {
    @Mock
    private BindingResult bindingResult;

    @Mock
    private ApplicationService applicationService;

    @InjectMocks
    private ApplicationValidator applicationValidator;

    @Test
    public void isValidReturnsFalseWhenInvalidArrival() {
        ApplicationDto applicationDto = new ApplicationDto();
        LocalDateTime now = LocalDateTime.now();

        LocalDateTime arrival = now.plusHours(4);
        LocalDateTime leaving = now.plusHours(24);

        applicationDto.setSpace(2);
        applicationDto.setArrival(arrival);
        applicationDto.setLeaving(leaving);

        assertFalse(applicationValidator.isValid(applicationDto, bindingResult));
        verify(bindingResult).rejectValue("arrival", "error.application", "Invalid arrival date");
    }

    @Test
    public void isValidReturnsFalseWhenArrivalIsAfterLeaving() {
        ApplicationDto applicationDto = new ApplicationDto();
        LocalDateTime now = LocalDateTime.now();

        LocalDateTime arrival = now.plusHours(45);
        LocalDateTime leaving = now.plusHours(30);

        applicationDto.setSpace(2);
        applicationDto.setArrival(arrival);
        applicationDto.setLeaving(leaving);

        assertFalse(applicationValidator.isValid(applicationDto, bindingResult));
        verify(bindingResult).rejectValue("arrival", "error.application", "Invalid arrival date");
    }

    @Test
    public void isValidReturnsFalseWhenInvalidDuration() {
        ApplicationDto applicationDto = new ApplicationDto();
        LocalDateTime now = LocalDateTime.now();

        LocalDateTime arrival = now.plusHours(30);
        LocalDateTime leaving = now.plusHours(33);

        applicationDto.setSpace(2);
        applicationDto.setArrival(arrival);
        applicationDto.setLeaving(leaving);

        assertFalse(applicationValidator.isValid(applicationDto, bindingResult));
        verify(bindingResult).rejectValue("leaving", "error.application", "Duration between arrival and leaving should be more than 6 hours");
    }

    @Test
    public void isValidReturnsFalseWhenInvalidSpace() {
        ApplicationDto applicationDto = new ApplicationDto();
        LocalDateTime now = LocalDateTime.now();

        LocalDateTime arrival = now.plusHours(30);
        LocalDateTime leaving = now.plusHours(38);

        applicationDto.setSpace(0);
        applicationDto.setArrival(arrival);
        applicationDto.setLeaving(leaving);

        assertFalse(applicationValidator.isValid(applicationDto, bindingResult));
        verify(bindingResult).rejectValue("space", "error.application", "Invalid space");
    }

    @Test
    public void isValidReturnsFalseWhenAllFieldsInvalid() {
        ApplicationDto applicationDto = new ApplicationDto();
        LocalDateTime now = LocalDateTime.now();

        LocalDateTime arrival = now.plusHours(4);
        LocalDateTime leaving = now.plusHours(6);

        applicationDto.setSpace(7);
        applicationDto.setArrival(arrival);
        applicationDto.setLeaving(leaving);

        assertFalse(applicationValidator.isValid(applicationDto, bindingResult));
        verify(bindingResult).rejectValue("arrival", "error.application", "Invalid arrival date");
        verify(bindingResult).rejectValue("leaving", "error.application", "Duration between arrival and leaving should be more than 6 hours");
        verify(bindingResult).rejectValue("space", "error.application", "Invalid space");
    }

    @Test
    public void isValidReturnsTrue() {
        ApplicationDto applicationDto = new ApplicationDto();
        LocalDateTime now = LocalDateTime.now();

        LocalDateTime arrival = now.plusHours(26);
        LocalDateTime leaving = now.plusHours(36);

        applicationDto.setSpace(4);
        applicationDto.setArrival(arrival);
        applicationDto.setLeaving(leaving);

        assertTrue(applicationValidator.isValid(applicationDto, bindingResult));
        verify(bindingResult, times(0)).rejectValue("arrival", "error.application", "Invalid arrival date");
        verify(bindingResult, times(0)).rejectValue("leaving", "error.application", "Duration between arrival and leaving should be more than 6 hours");
        verify(bindingResult, times(0)).rejectValue("space", "error.application", "Invalid space");
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateForBookingWhenInvalidLeaving() {
        LocalDateTime arrival = LocalDateTime.now().minusDays(2);
        LocalDateTime leaving = LocalDateTime.now().minusDays(1);

        Application application = new Application();
        application.setArrival(arrival);
        application.setLeaving(leaving);

        when(applicationService.getApplicationById(3)).thenReturn(Optional.of(application));
        applicationValidator.validateForBooking(3, new User());
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateForBookingWhenUnauthorizedUser() {
        LocalDateTime arrival = LocalDateTime.now().plusDays(2);
        LocalDateTime leaving = LocalDateTime.now().plusDays(3);

        Application application = new Application();
        application.setArrival(arrival);
        application.setLeaving(leaving);

        User originUser = new User();
        originUser.setId(3);

        application.setUser(originUser);

        when(applicationService.getApplicationById(5)).thenReturn(Optional.of(application));
        User testUser = new User();
        testUser.setId(4);

        applicationValidator.validateForBooking(5, testUser);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateForBookingWhenApplicationCantBeBooked() {
        LocalDateTime arrival = LocalDateTime.now().plusDays(2);
        LocalDateTime leaving = LocalDateTime.now().plusDays(3);

        Application application = new Application();
        application.setArrival(arrival);
        application.setLeaving(leaving);

        User originUser = new User();
        originUser.setId(4);

        application.setUser(originUser);

        when(applicationService.getApplicationById(1)).thenReturn(Optional.of(application));
        when(applicationService.canBeBooked(application)).thenReturn(false);

        User testUser = new User();
        testUser.setId(4);

        applicationValidator.validateForBooking(1, testUser);
    }

    @Test
    public void validateForBookingWhenValid() {
        LocalDateTime arrival = LocalDateTime.now().plusDays(2);
        LocalDateTime leaving = LocalDateTime.now().plusDays(3);

        Application application = new Application();
        application.setArrival(arrival);
        application.setLeaving(leaving);

        User originUser = new User();
        originUser.setId(4);
        application.setUser(originUser);

        when(applicationService.getApplicationById(6)).thenReturn(Optional.of(application));
        when(applicationService.canBeBooked(application)).thenReturn(true);

        User testUser = new User();
        testUser.setId(4);

        applicationValidator.validateForBooking(6, testUser);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateForPayingWhenUnauthorizedUser() {
        Application application = new Application();

        User originUser = new User();
        originUser.setId(4);
        application.setUser(originUser);

        when(applicationService.getApplicationById(4)).thenReturn(Optional.of(application));

        User testUser = new User();
        testUser.setId(3);
        applicationValidator.validateForPaying(4, testUser);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateForPayingWhenInvalidApplication() {
        Application application = new Application();
        application.setStatus(ApplicationStatus.getStatusPaid());
        User originUser = new User();
        originUser.setId(5);
        application.setUser(originUser);

        when(applicationService.getApplicationById(6)).thenReturn(Optional.of(application));

        User testUser = new User();
        testUser.setId(5);
        applicationValidator.validateForPaying(6, testUser);
    }

    @Test
    public void validateForPaying() {
        Application application = new Application();
        application.setStatus(ApplicationStatus.getStatusBooked());
        User originUser = new User();
        originUser.setId(5);
        application.setUser(originUser);

        when(applicationService.getApplicationById(8)).thenReturn(Optional.of(application));

        User testUser = new User();
        testUser.setId(5);
        applicationValidator.validateForPaying(8, testUser);
    }
}