package holik.hotel.spring.job;

import holik.hotel.spring.persistence.model.Application;
import holik.hotel.spring.persistence.model.ApplicationStatus;
import holik.hotel.spring.persistence.model.Room;
import holik.hotel.spring.persistence.model.RoomStatus;
import holik.hotel.spring.service.ApplicationService;
import holik.hotel.spring.service.RoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Class that is responsible for setting current room statuses.
 */
@Component
@EnableScheduling
public class RoomStatusSetter {
    private final Logger LOG = LoggerFactory.getLogger(RoomStatusSetter.class);
    private final ApplicationService applicationService;
    private final RoomService roomService;

    public RoomStatusSetter(ApplicationService applicationService, RoomService roomService) {
        this.applicationService = applicationService;
        this.roomService = roomService;
    }

    @Scheduled(fixedDelay = 300000, initialDelay = 10000)
    public void setRoomStatuses() {
        LOG.info("Room status setting job starts");
        RoomStatus statusFree = RoomStatus.getStatusFree();
        RoomStatus statusBooked = RoomStatus.getStatusBooked();
        RoomStatus statusBusy = RoomStatus.getStatusBusy();

        List<Room> availableRooms = roomService.getAvailableRooms();
        for (Room room : availableRooms) {
            room.setRoomStatus(statusFree);
        }

        ApplicationStatus applicationStatusBooked = ApplicationStatus.getStatusBooked();
        ApplicationStatus applicationStatusPaid = ApplicationStatus.getStatusPaid();

        LocalDateTime now = LocalDateTime.now();
        List<Application> bookedApplications = applicationService.getApplicationsByStatus(applicationStatusBooked);
        for (Application application : bookedApplications) {
            if (isNotFreeNow(application, now)) {
                setRoomStatusById(statusBooked, application.getRoom().getId(), availableRooms);
            }
        }

        List<Application> paidApplications = applicationService.getApplicationsByStatus(applicationStatusPaid);
        for (Application application : paidApplications) {
            if (isNotFreeNow(application, now)) {
                setRoomStatusById(statusBusy, application.getRoom().getId(), availableRooms);
            }
        }

        for (Room room : availableRooms) {
            roomService.updateRoom(room);
        }
    }

    private void setRoomStatusById(RoomStatus roomStatus, int roomId, List<Room> rooms) {
        for (Room room : rooms) {
            if (room.getId() == roomId) {
                room.setRoomStatus(roomStatus);
            }
        }
    }

    private boolean isNotFreeNow(Application application, LocalDateTime point) {
        LocalDateTime arrival = application.getArrival();
        LocalDateTime leaving = application.getLeaving();
        return point.isAfter(arrival) && point.isBefore(leaving);
    }
}
