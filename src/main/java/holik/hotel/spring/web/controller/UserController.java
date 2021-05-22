package holik.hotel.spring.web.controller;

import holik.hotel.spring.persistence.model.User;
import holik.hotel.spring.service.UserService;
import holik.hotel.spring.web.converter.Converter;
import holik.hotel.spring.web.dto.UserDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/")
public class UserController {
    private final UserService userService;
    private final Converter<UserDto, User> userConverter;

    public UserController(UserService userService, Converter<UserDto, User> userConverter) {
        this.userService = userService;
        this.userConverter = userConverter;
    }

    @GetMapping("/signup")
    public String registerForm(Model model) {
        model.addAttribute("user", new UserDto());

        return "signup";
    }

    @PostMapping("/signup")
    public String registerUser(@Valid @ModelAttribute("user") UserDto userDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup";
        } else if (userService.getUserByEmail(userDto.getEmail()).isPresent()) {
            bindingResult.addError(new ObjectError("global", "User with this email exists"));
            return "signup";
        }

        userService.createUser(userConverter.covertToEntity(userDto));
        return "redirect:/";
    }

    @GetMapping("/signin")
    public String viewSignInPage() {
        return "signin";
    }

    @PostMapping("/signin")
    public String signIn() {
        return "redirect:/";
    }
}
