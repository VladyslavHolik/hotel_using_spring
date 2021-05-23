package holik.hotel.spring.service;

import holik.hotel.spring.persistence.model.Bill;
import holik.hotel.spring.persistence.model.User;

import java.util.List;

public interface BillService {
    List<Bill> getUserBills(User user);
}
