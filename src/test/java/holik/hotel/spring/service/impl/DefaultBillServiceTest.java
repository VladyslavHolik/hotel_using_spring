package holik.hotel.spring.service.impl;

import holik.hotel.spring.persistence.model.Application;
import holik.hotel.spring.persistence.model.Bill;
import holik.hotel.spring.persistence.model.Room;
import holik.hotel.spring.persistence.model.User;
import holik.hotel.spring.service.ApplicationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DefaultBillServiceTest {
    @Mock
    private ApplicationService applicationService;

    @InjectMocks
    private DefaultBillService billService;

    @Test
    public void getUserBills() {
        Application application = new Application();
        Room room = new Room();
        room.setPrice(4);
        application.setRoom(room);

        LocalDateTime now = LocalDateTime.now();
        application.setArrival(now.plusDays(1));
        application.setLeaving(now.plusDays(2));

        List<Application> bookedApplications = new ArrayList<>();
        bookedApplications.add(application);

        User user = new User();
        when(applicationService.getBookedApplicationsByUser(user)).thenReturn(bookedApplications);

        List<Bill> bills = billService.getUserBills(user);
        Bill bill = bills.get(0);

        verify(applicationService).getBookedApplicationsByUser(user);
        assertEquals(1, bills.size());
        assertEquals(application, bill.getApplication());
        assertEquals(room, bill.getRoom());
        assertEquals(24 * 4, bill.getPrice(), 0.01);
    }
}