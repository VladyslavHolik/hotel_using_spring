package holik.hotel.spring.service.impl;

import holik.hotel.spring.persistence.model.Application;
import holik.hotel.spring.persistence.model.Bill;
import holik.hotel.spring.persistence.model.Room;
import holik.hotel.spring.persistence.model.User;
import holik.hotel.spring.service.ApplicationService;
import holik.hotel.spring.service.BillService;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
public class DefaultBillService implements BillService {
    private final ApplicationService applicationService;

    public DefaultBillService(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @Override
    public List<Bill> getUserBills(User user) {
        List<Bill> bills = new ArrayList<>();
        List<Application> bookedApplications = applicationService.getBookedApplicationsByUser(user);
        for (Application application : bookedApplications) {
            Duration duration = Duration.between(application.getArrival(),
                    application.getLeaving());
            long hours = duration.toHours();
            Room room = application.getRoom();
            long price = hours * room.getPrice();

            Bill bill = new Bill();
            bill.setApplication(application);
            bill.setRoom(room);
            bill.setPrice(price);
            bills.add(bill);
        }
        return bills;
    }
}
