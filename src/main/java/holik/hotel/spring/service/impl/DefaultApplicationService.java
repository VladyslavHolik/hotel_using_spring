package holik.hotel.spring.service.impl;

import holik.hotel.spring.persistence.model.Application;
import holik.hotel.spring.persistence.model.ApplicationStatus;
import holik.hotel.spring.persistence.model.Room;
import holik.hotel.spring.persistence.model.User;
import holik.hotel.spring.persistence.repository.ApplicationRepository;
import holik.hotel.spring.service.ApplicationService;
import holik.hotel.spring.service.RoomService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DefaultApplicationService implements ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final RoomService roomService;

    public DefaultApplicationService(ApplicationRepository applicationRepository, RoomService roomService) {
        this.applicationRepository = applicationRepository;
        this.roomService = roomService;
    }

    @Override
    public void createApplication(Application application) {
        applicationRepository.save(application);
    }

    @Override
    public List<Application> getRequestedApplications() {
        ApplicationStatus status = new ApplicationStatus();
        status.setId(1);
        return applicationRepository.getApplicationsByStatus(status);
    }

    @Override
    public Optional<Application> getApplicationById(int id) {
        return applicationRepository.findById(id);
    }

    @Override
    public List<Room> getFreeRooms(Application application) {
        List<Room> availableRooms = roomService.getAvailableRooms(application.getRoomClass(), application.getSpace());
        return availableRooms.stream()
                .filter((room) -> isAvailable(room, application))
                .collect(Collectors.toList());
    }

    @Override
    public List<Application> getApplicationsByStatus(ApplicationStatus status) {
       return applicationRepository.getApplicationsByStatus(status);
    }

    @Override
    public void processApplication(Application application, String choice) {
        if ("decline".equals(choice)) {
            ApplicationStatus status = new ApplicationStatus();
            status.setId(3);
            // status declined;
            application.setStatus(status);
        } else {
            int roomId = Integer.parseInt(choice);
            Room room = roomService.getRoomById(roomId).orElseThrow();
            application.setRoom(room);
            ApplicationStatus status = new ApplicationStatus();
            status.setId(2);
            // status approved;
            application.setStatus(status);
        }
        updateApplication(application);
    }

    @Override
    public void updateApplication(Application application) {
        applicationRepository.save(application);
    }

    @Override
    public List<Application> getReadyToBookApplications(User user) {
        ApplicationStatus statusApproved = new ApplicationStatus();
        statusApproved.setId(2);

        List<Application> readyToBookApplications = new ArrayList<>();
        List<Application> approvedApplications = applicationRepository.getApplicationsByStatusAndUser(statusApproved, user);
        for (Application application : approvedApplications) {
            if (canBeBooked(application)) {
                readyToBookApplications.add(application);
            }
        }
        return readyToBookApplications;
    }

    @Override
    public boolean canBeBooked(Application application) {
        boolean result = true;
        ApplicationStatus statusBooked = new ApplicationStatus();
        statusBooked.setId(4);

        ApplicationStatus statusPaid = new ApplicationStatus();
        statusPaid.setId(5);

        LocalDateTime arrival = application.getArrival();
        LocalDateTime leaving = application.getLeaving();

        List<Application> applicationsOnRoom = applicationRepository.getApplicationsByStatusAndRoom(statusBooked, application.getRoom());
        List<Application> paidApplicationsOnRoom = applicationRepository.getApplicationsByStatusAndRoom(statusPaid, application.getRoom());
        applicationsOnRoom.addAll(paidApplicationsOnRoom);

        for (Application originApplication : applicationsOnRoom) {
            LocalDateTime originArrival = originApplication.getArrival();
            LocalDateTime originLeaving = originApplication.getLeaving();
            if (isBetween(arrival, originArrival, originLeaving) ||
            isBetween(leaving, originArrival, originLeaving)) {
                result = false;
                break;
            }
        }
        return result;
    }

    @Override
    public void bookRoom(Application application) {
        ApplicationStatus statusBooked = new ApplicationStatus();
        statusBooked.setId(4);
        application.setStatus(statusBooked);
        application.setBooked(LocalDateTime.now());
        updateApplication(application);
    }

    @Override
    public List<Application> getBookedApplicationsByUser(User user) {
        ApplicationStatus statusBooked = new ApplicationStatus();
        statusBooked.setId(4);
        return applicationRepository.getApplicationsByStatusAndUser(statusBooked, user);
    }

    private boolean isAvailable(Room room, Application application) {
        boolean result = true;
        LocalDateTime datetimeOfArrival = application.getArrival();
        LocalDateTime datetimeOfLeaving = application.getLeaving();

        ApplicationStatus booked = new ApplicationStatus();
        booked.setId(4);
        ApplicationStatus paid = new ApplicationStatus();
        paid.setId(5);

        List<Application> bookedApplications = getApplicationsByStatus(booked);
        List<Application> paidApplications = getApplicationsByStatus(paid);

        List<Application> paidAndBookedApplications = new ArrayList<>();
        paidAndBookedApplications.addAll(bookedApplications);
        paidAndBookedApplications.addAll(paidApplications);

        for (Application originApplication : paidAndBookedApplications) {
            if (originApplication.getRoom().getId() == room.getId()
                    && (isBetween(datetimeOfArrival, originApplication.getArrival(),
                    originApplication.getLeaving())
                    || isBetween(datetimeOfLeaving, originApplication.getArrival(),
                    originApplication.getLeaving()))) {
                result = false;
                break;
            }
        }
        return result;
    }

    private boolean isBetween(LocalDateTime origin, LocalDateTime start, LocalDateTime end) {
        return origin.isAfter(start) && origin.isBefore(end);
    }
}
