package holik.hotel.spring.job;

import holik.hotel.spring.persistence.model.Application;
import holik.hotel.spring.persistence.model.ApplicationStatus;
import holik.hotel.spring.service.ApplicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Class that is responsible for declining unpaid orders.
 * The application will be declined if more than 48 hours have passed since the time of booking.
 */
@Component
@EnableScheduling
public class BookingRemover {
    private final Logger LOG = LoggerFactory.getLogger(BookingRemover.class);
    private final ApplicationService applicationService;

    public BookingRemover(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @Scheduled(fixedDelay=300000)
    public void removeBooking() {
        LOG.info("Booking removing job starts");

        ApplicationStatus statusBooked = new ApplicationStatus();
        statusBooked.setId(4);
        List<Application> bookedApplications = applicationService.getApplicationsByStatus(statusBooked);
        LocalDateTime now = LocalDateTime.now();

        ApplicationStatus statusDeclined = new ApplicationStatus();
        statusBooked.setId(3);

        for (Application application : bookedApplications) {
            LocalDateTime booked = application.getBooked();
            Duration duration = Duration.between(booked, now);
            if (duration.toHours() > 48) {
                LOG.info("Setting application to declined, id " + application.getId());
                application.setStatus(statusDeclined);
                applicationService.updateApplication(application);
            }
        }
    }
}
