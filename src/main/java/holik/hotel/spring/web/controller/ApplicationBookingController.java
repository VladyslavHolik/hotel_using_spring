package holik.hotel.spring.web.controller;

import holik.hotel.spring.persistence.model.Application;
import holik.hotel.spring.persistence.model.User;
import holik.hotel.spring.service.ApplicationService;
import holik.hotel.spring.service.UserService;
import holik.hotel.spring.web.validator.ApplicationValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class ApplicationBookingController {
    private final ApplicationService applicationService;
    private final ApplicationValidator applicationValidator;
    private final UserService userService;

    public ApplicationBookingController(ApplicationService applicationService,
                                        ApplicationValidator applicationValidator,
                                        UserService userService) {
        this.applicationService = applicationService;
        this.applicationValidator = applicationValidator;
        this.userService = userService;
    }

    @GetMapping("/myapplications")
    public String getApprovedApplications(Model model, Principal principal) {
        User user = userService.getUserByEmail(principal.getName()).orElseThrow();
        List<Application> readyToBookApplications = applicationService.getReadyToBookApplications(user);
        model.addAttribute("applications", readyToBookApplications);
        return "myapplications";
    }
}
