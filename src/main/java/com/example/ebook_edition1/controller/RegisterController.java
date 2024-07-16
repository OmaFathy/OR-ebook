package com.example.ebook_edition1.controller;

import com.example.ebook_edition1.DTO.UserRegistrationDto;
import com.example.ebook_edition1.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }

    @GetMapping
    public String showRegistrationForm() {
        return "pages/register";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") @Valid UserRegistrationDto registrationDto,
                                     Model model) {

        try {
            userService.registerUser(registrationDto);
            return "redirect:/pages/login";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "pages/register";
        }
    }
}
