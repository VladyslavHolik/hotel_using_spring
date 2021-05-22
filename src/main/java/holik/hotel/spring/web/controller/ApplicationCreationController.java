package holik.hotel.spring.web.controller;

import holik.hotel.spring.persistence.model.Application;
import holik.hotel.spring.persistence.model.User;
import holik.hotel.spring.service.ApplicationService;
import holik.hotel.spring.service.UserService;
import holik.hotel.spring.web.converter.ApplicationConverter;
import holik.hotel.spring.web.dto.ApplicationDto;
import holik.hotel.spring.web.validator.ApplicationValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;

@Controller
public class ApplicationCreationController {
    private static final long DAYS_PERIOD = 1;
    private static final int TIME_OFFSET = 16;
    private final ApplicationValidator applicationValidator;
    private final ApplicationConverter applicationConverter;
    private final UserService userService;
    private final ApplicationService applicationService;

    public ApplicationCreationController(ApplicationValidator applicationValidator,
                                         ApplicationConverter applicationConverter,
                                         UserService userService,
                                         ApplicationService applicationService) {
        this.applicationValidator = applicationValidator;
        this.applicationConverter = applicationConverter;
        this.userService = userService;
        this.applicationService = applicationService;
    }

    @GetMapping("/application")
    public String getApplication(Model model) {
        String minTime = LocalDateTime.now().plusDays(DAYS_PERIOD).toString().substring(0, TIME_OFFSET);
        model.addAttribute("minTime", minTime);
        model.addAttribute("application", new ApplicationDto());
        return "application";
    }

    @PostMapping("/application")
    public String createApplication(@Valid @ModelAttribute("application") ApplicationDto applicationDto,
                                    BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors() || !applicationValidator.isValid(applicationDto, bindingResult)) {
            return "application";
        }

        String userEmail = principal.getName();
        User user = userService.getUserByEmail(userEmail).orElseThrow();
        applicationDto.setUser(user);
        Application application = applicationConverter.convertToEntity(applicationDto);
        applicationService.createApplication(application);
        return "redirect:/";
    }
}
