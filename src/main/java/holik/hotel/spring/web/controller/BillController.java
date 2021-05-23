package holik.hotel.spring.web.controller;

import holik.hotel.spring.persistence.model.Application;
import holik.hotel.spring.persistence.model.ApplicationStatus;
import holik.hotel.spring.persistence.model.Bill;
import holik.hotel.spring.persistence.model.User;
import holik.hotel.spring.service.ApplicationService;
import holik.hotel.spring.service.BillService;
import holik.hotel.spring.service.UserService;
import holik.hotel.spring.web.validator.ApplicationValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class BillController {
    private final BillService billService;
    private final UserService userService;
    private final ApplicationService applicationService;
    private final ApplicationValidator applicationValidator;

    public BillController(BillService billService, UserService userService, ApplicationService applicationService, ApplicationValidator applicationValidator) {
        this.billService = billService;
        this.userService = userService;
        this.applicationService = applicationService;
        this.applicationValidator = applicationValidator;
    }

    @GetMapping("/bills")
    public String getBills(Model model, Principal principal) {
        User user = userService.getUserByEmail(principal.getName()).orElseThrow();
        List<Bill> bills = billService.getUserBills(user);
        model.addAttribute("bills", bills);
        return "bills";
    }

    @PostMapping("/bills")
    public String payBill(@RequestParam("id") int applicationId, Principal principal) {
        User user = userService.getUserByEmail(principal.getName()).orElseThrow();
        applicationValidator.validateForPaying(applicationId, user);
        Application application = applicationService.getApplicationById(applicationId).orElseThrow();
        ApplicationStatus statusPaid = new ApplicationStatus();
        statusPaid.setId(5);
        application.setStatus(statusPaid);
        applicationService.updateApplication(application);
        return "redirect:/";
    }
}
