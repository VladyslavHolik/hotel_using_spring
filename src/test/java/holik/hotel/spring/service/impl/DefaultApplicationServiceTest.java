package holik.hotel.spring.service.impl;

import holik.hotel.spring.persistence.model.*;
import holik.hotel.spring.persistence.repository.ApplicationRepository;
import holik.hotel.spring.service.RoomService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DefaultApplicationServiceTest {
    @Mock
    private ApplicationRepository applicationRepository;

    @Mock
    private RoomService roomService;

    @InjectMocks
    private DefaultApplicationService applicationService;

    @Test
    public void createApplication() {
        Application application = new Application();
        applicationService.createApplication(application);
        verify(applicationRepository).save(application);
    }

    @Test
    public void getRequestedApplications() {
        applicationService.getRequestedApplications();
        verify(applicationRepository).getApplicationsByStatus(any());
    }

    @Test
    public void getApplicationById() {
        applicationService.getApplicationById(5);
        verify(applicationRepository).findById(5);
    }

    @Test
    public void getFreeRooms() {
        List<Room> availableRooms = new ArrayList<>();
        Room firstRoom = new Room();
        Room secondRoom = new Room();
        Room thirdRoom = new Room();
        Room fourthRoom = new Room();

        firstRoom.setId(2);
        secondRoom.setId(3);
        thirdRoom.setId(4);
        fourthRoom.setId(5);

        availableRooms.add(firstRoom);
        availableRooms.add(secondRoom);
        availableRooms.add(thirdRoom);
        availableRooms.add(fourthRoom);

        RoomClass apartment = new RoomClass();
        apartment.setId(1);

        when(roomService.getAvailableRooms(apartment, 5)).thenReturn(availableRooms);

        List<Application> bookedApplications = new ArrayList<>();
        Application bookedApplication = new Application();
        bookedApplication.setRoom(firstRoom);
        bookedApplication.setArrival(LocalDateTime.now().minusDays(1));
        bookedApplication.setLeaving(LocalDateTime.now().plusDays(1));
        bookedApplications.add(bookedApplication);

        List<Application> paidApplications = new ArrayList<>();
        Application paidApplication = new Application();
        paidApplication.setRoom(secondRoom);
        paidApplication.setArrival(LocalDateTime.now().minusDays(1));
        paidApplication.setLeaving(LocalDateTime.now().plusDays(1));
        paidApplications.add(paidApplication);

        Application paidApplicationButOnAnotherTime = new Application();
        paidApplicationButOnAnotherTime.setRoom(secondRoom);
        paidApplicationButOnAnotherTime.setArrival(LocalDateTime.now().plusDays(3));
        paidApplicationButOnAnotherTime.setLeaving(LocalDateTime.now().plusDays(5));
        paidApplications.add(paidApplicationButOnAnotherTime);

        when(applicationRepository.getApplicationsByStatus(ApplicationStatus.getStatusBooked()))
                .thenReturn(bookedApplications);
        when(applicationRepository.getApplicationsByStatus(ApplicationStatus.getStatusPaid()))
                .thenReturn(paidApplications);

        Application application = new Application();
        application.setSpace(5);
        application.setRoomClass(apartment);
        application.setArrival(LocalDateTime.now());
        application.setLeaving(LocalDateTime.now().plusHours(7));

        List<Room> freeRooms = applicationService.getFreeRooms(application);

        verify(roomService).getAvailableRooms(apartment, 5);
        verify(applicationRepository, times(4))
                .getApplicationsByStatus(ApplicationStatus.getStatusBooked());
        verify(applicationRepository, times(4))
                .getApplicationsByStatus(ApplicationStatus.getStatusPaid());

        assertEquals(2, freeRooms.size());
        assertTrue(freeRooms.contains(thirdRoom));
        assertTrue(freeRooms.contains(fourthRoom));
    }

    @Test
    public void getFreeRoomsIfAllUnavailable() {
        List<Room> availableRooms = new ArrayList<>();

        RoomClass bedroom = new RoomClass();
        bedroom.setId(5);

        when(roomService.getAvailableRooms(bedroom, 1)).thenReturn(availableRooms);

        Application application = new Application();
        application.setRoomClass(bedroom);
        application.setSpace(1);

        List<Room> freeRooms = applicationService.getFreeRooms(application);
        assertTrue(freeRooms.isEmpty());
    }


    @Test
    public void getFreeRoomsIfAllFree() {
        List<Room> availableRooms = new ArrayList<>();
        Room firstRoom = new Room();
        Room secondRoom = new Room();
        Room thirdRoom = new Room();
        Room fourthRoom = new Room();

        firstRoom.setId(2);
        secondRoom.setId(3);
        thirdRoom.setId(4);
        fourthRoom.setId(5);

        availableRooms.add(firstRoom);
        availableRooms.add(secondRoom);
        availableRooms.add(thirdRoom);
        availableRooms.add(fourthRoom);

        RoomClass apartment = new RoomClass();
        apartment.setId(1);

        when(roomService.getAvailableRooms(apartment, 5)).thenReturn(availableRooms);

        List<Application> bookedApplications = new ArrayList<>();
        List<Application> paidApplications = new ArrayList<>();

        when(applicationRepository.getApplicationsByStatus(ApplicationStatus.getStatusBooked()))
                .thenReturn(bookedApplications);
        when(applicationRepository.getApplicationsByStatus(ApplicationStatus.getStatusPaid()))
                .thenReturn(paidApplications);

        Application application = new Application();
        application.setSpace(5);
        application.setRoomClass(apartment);
        application.setArrival(LocalDateTime.now());
        application.setLeaving(LocalDateTime.now().plusHours(7));

        List<Room> freeRooms = applicationService.getFreeRooms(application);

        verify(roomService).getAvailableRooms(apartment, 5);
        verify(applicationRepository, times(4))
                .getApplicationsByStatus(ApplicationStatus.getStatusBooked());
        verify(applicationRepository, times(4))
                .getApplicationsByStatus(ApplicationStatus.getStatusPaid());

        assertEquals(availableRooms, freeRooms);
    }

    @Test
    public void getApplicationsByStatus() {
        applicationService.getApplicationsByStatus(ApplicationStatus.getStatusPaid());
        verify(applicationRepository).getApplicationsByStatus(ApplicationStatus.getStatusPaid());
    }

    @Test
    public void processApplicationIfDeclined() {
        Application application = new Application();
        applicationService.processApplication(application, "decline");
        verify(applicationRepository).save(application);
        assertEquals(ApplicationStatus.getStatusDeclined(), application.getStatus());
    }

    @Test(expected = NoSuchElementException.class)
    public void processApplicationIfInvalidRoomId() {
        Application application = new Application();
        when(roomService.getRoomById(2)).thenReturn(Optional.empty());
        applicationService.processApplication(application, "2");
    }

    @Test
    public void processApplicationIfValid() {
        Application application = new Application();
        when(roomService.getRoomById(3)).thenReturn(Optional.of(new Room()));
        applicationService.processApplication(application, "3");

        verify(applicationRepository).save(application);
        assertEquals(ApplicationStatus.getStatusApproved(), application.getStatus());
    }

    @Test
    public void updateApplication() {
        Application application = new Application();
        applicationService.updateApplication(application);
        verify(applicationRepository).save(application);
    }

    @Test
    public void getReadyToBookApplicationsIfAllFree() {
        List<Application> approvedApplications = new ArrayList<>();
        Application firstApplication = new Application();
        Application secondApplication = new Application();

        Room firstRoom = new Room();
        Room secondRoom = new Room();

        firstApplication.setRoom(firstRoom);
        secondApplication.setRoom(secondRoom);

        approvedApplications.add(firstApplication);
        approvedApplications.add(secondApplication);

        List<Application> applicationsOnRoom = new ArrayList<>();
        List<Application> paidApplicationsOnRoom = new ArrayList<>();

        User user = new User();
        when(applicationRepository.getApplicationsByStatusAndUser(ApplicationStatus.getStatusApproved(), user))
                .thenReturn(approvedApplications);
        when(applicationRepository.getApplicationsByStatusAndRoom(ApplicationStatus.getStatusBooked(), firstRoom))
                .thenReturn(applicationsOnRoom);
        when(applicationRepository.getApplicationsByStatusAndRoom(ApplicationStatus.getStatusPaid(), secondRoom))
                .thenReturn(paidApplicationsOnRoom);

        List<Application> readyToBookApplications = applicationService.getReadyToBookApplications(user);
        assertEquals(approvedApplications, readyToBookApplications);
    }

    @Test
    public void getReadyToBookApplicationsIfSomeAreBooked() {
        List<Application> approvedApplications = new ArrayList<>();
        Application firstApplication = new Application();
        Application secondApplication = new Application();

        LocalDateTime now = LocalDateTime.now();
        firstApplication.setArrival(now.plusDays(3));
        firstApplication.setLeaving(now.plusDays(5));

        secondApplication.setArrival(now.plusDays(3));
        secondApplication.setLeaving(now.plusDays(5));

        Room firstRoom = new Room();
        Room secondRoom = new Room();

        firstApplication.setRoom(firstRoom);
        secondApplication.setRoom(secondRoom);

        approvedApplications.add(firstApplication);
        approvedApplications.add(secondApplication);

        List<Application> applicationsOnRoom = new ArrayList<>();
        List<Application> paidApplicationsOnRoom = new ArrayList<>();

        Application bookedApplication = new Application();
        bookedApplication.setArrival(now.plusDays(4));
        bookedApplication.setLeaving(now.plusDays(6));
        applicationsOnRoom.add(bookedApplication);

        User user = new User();
        when(applicationRepository.getApplicationsByStatusAndUser(ApplicationStatus.getStatusApproved(), user))
                .thenReturn(approvedApplications);
        when(applicationRepository.getApplicationsByStatusAndRoom(ApplicationStatus.getStatusBooked(), firstRoom))
                .thenReturn(applicationsOnRoom);
        when(applicationRepository.getApplicationsByStatusAndRoom(ApplicationStatus.getStatusPaid(), secondRoom))
                .thenReturn(paidApplicationsOnRoom);

        List<Application> readyToBookApplications = applicationService.getReadyToBookApplications(user);
        assertEquals(1, readyToBookApplications.size());
        assertTrue(readyToBookApplications.contains(secondApplication));
    }

    @Test
    public void canBeBookedIfPaid() {
        Application application = new Application();
        LocalDateTime now = LocalDateTime.now();
        application.setArrival(now.plusDays(3));
        application.setLeaving(now.plusDays(7));

        Room room = new Room();
        application.setRoom(room);

        List<Application> applicationsOnRoom = new ArrayList<>();
        List<Application> paidApplicationsOnRoom = new ArrayList<>();

        Application paidApplication = new Application();
        paidApplication.setArrival(now.plusDays(4));
        paidApplication.setLeaving(now.plusDays(6));
        paidApplicationsOnRoom.add(paidApplication);

        when(applicationRepository.getApplicationsByStatusAndRoom(ApplicationStatus.getStatusBooked(), room))
                .thenReturn(applicationsOnRoom);
        when(applicationRepository.getApplicationsByStatusAndRoom(ApplicationStatus.getStatusPaid(), room))
                .thenReturn(paidApplicationsOnRoom);


        assertFalse(applicationService.canBeBooked(application));
        verify(applicationRepository).getApplicationsByStatusAndRoom(ApplicationStatus.getStatusBooked(), room);
        verify(applicationRepository).getApplicationsByStatusAndRoom(ApplicationStatus.getStatusPaid(), room);
    }

    @Test
    public void bookRoom() {
        Application application = new Application();
        applicationService.bookRoom(application);
        verify(applicationRepository).save(application);
        assertEquals(ApplicationStatus.getStatusBooked(), application.getStatus());
        assertTrue(LocalDateTime.now().isAfter(application.getBooked()));
        assertTrue(LocalDateTime.now().minusMinutes(1).isBefore(application.getBooked()));
    }

    @Test
    public void getBookedApplicationsByUser() {
        User user = new User();
        applicationService.getBookedApplicationsByUser(user);
        verify(applicationRepository).getApplicationsByStatusAndUser(ApplicationStatus.getStatusBooked(), user);
    }
}