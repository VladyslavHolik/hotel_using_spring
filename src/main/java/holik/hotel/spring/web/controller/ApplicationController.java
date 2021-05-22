package holik.hotel.spring.web.controller;

import holik.hotel.spring.web.dto.ApplicationDto;
import holik.hotel.spring.web.validator.ApplicationValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.time.Duration;
import java.time.LocalDateTime;

@Controller
public class ApplicationController {
    private static final long DAYS_PERIOD = 1;
    private static final int TIME_OFFSET = 16;
    private final ApplicationValidator applicationValidator;

    public ApplicationController(ApplicationValidator applicationValidator) {
        this.applicationValidator = applicationValidator;
    }

    @GetMapping("/application")
    public String getApplication(Model model) {
        String minTime = LocalDateTime.now().plusDays(DAYS_PERIOD).toString().substring(0, TIME_OFFSET);
        model.addAttribute("minTime", minTime);
        model.addAttribute("application", new ApplicationDto());
        return "application";
    }

    @PostMapping("/application")
    public String createApplication(@Valid @ModelAttribute("application") ApplicationDto applicationDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors() || !applicationValidator.isValid(applicationDto, bindingResult)) {
            return "application";
        }
        return "redirect:/";
    }


}
