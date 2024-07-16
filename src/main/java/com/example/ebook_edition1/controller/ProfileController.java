package com.example.ebook_edition1.controller;

import org.springframework.ui.Model;
import com.example.ebook_edition1.model.User;
import com.example.ebook_edition1.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProfileController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/profile")
    public String profile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User currentUser = userService.getUserByEmail(email);

        model.addAttribute("user", currentUser);
        return "pages/profile";
    }

    @PostMapping("/profile")
    public String updateProfile(@RequestParam("username") String username,
                                @RequestParam("email") String email,
                                @RequestParam("password") String password,
                                Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String emailOfCurrentUser = authentication.getName();
        User currentUser = userService.getUserByEmail(emailOfCurrentUser);

        model.addAttribute("user", currentUser);
        model.addAttribute("message", "Profile updated successfully");

        currentUser.setUsername(username);
        currentUser.setEmail(email);
        if (!password.isEmpty()) {
            currentUser.setPassword(bCryptPasswordEncoder.encode(password));
        }
        // No need to set password if it is empty, keep the old one

        userService.updateUser(currentUser);
        return "pages/profile";
    }
}
