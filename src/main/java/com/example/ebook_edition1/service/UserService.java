package com.example.ebook_edition1.service;

import com.example.ebook_edition1.DTO.UserRegistrationDto;
import com.example.ebook_edition1.model.User;

import java.util.Optional;

public interface UserService {
    User registerUser(UserRegistrationDto registrationDto);
    public String getUsernameByEmail(String email);
    User getUserByEmail(String email);
    User  updateUser(User user);
}