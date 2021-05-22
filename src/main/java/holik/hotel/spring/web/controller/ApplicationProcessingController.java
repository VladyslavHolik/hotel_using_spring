package holik.hotel.spring.web.controller;

import holik.hotel.spring.persistence.model.Application;
import holik.hotel.spring.persistence.model.Room;
import holik.hotel.spring.service.ApplicationService;
import holik.hotel.spring.web.validator.ChoiceValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ApplicationProcessingController {
    private final ApplicationService applicationService;
    private final ChoiceValidator choiceValidator;

    public ApplicationProcessingController(ApplicationService applicationService, ChoiceValidator choiceValidator) {
        this.applicationService = applicationService;
        this.choiceValidator = choiceValidator;
    }

    @GetMapping("/applications")
    public String getApplications(Model model) {
        List<Application> requestedApplications = applicationService.getRequestedApplications();
        model.addAttribute("applications", requestedApplications);
        return "applications";
    }

    @GetMapping("/form")
    public String getApplicationForm(@RequestParam("id") Integer id, Model model) {
        Application application = applicationService.getApplicationById(id).orElseThrow();
        List<Room> freeRooms = applicationService.getFreeRooms(application);
        model.addAttribute("applicationData", application);
        model.addAttribute("rooms", freeRooms);
        return "applicationForm";
    }

    @PostMapping("/form")
    public String processApplication(@RequestParam("id") Integer applicationId, @RequestParam("choice") String choice) {
        Application application = applicationService.getApplicationById(applicationId).orElseThrow();
        if (!"requested".equals(application.getStatus().getStatus())) {
            throw new IllegalArgumentException("Invalid application");
        }

        choiceValidator.validateChoice(choice);
        applicationService.processApplication(application, choice);
        return "redirect:/applications";
    }
}
