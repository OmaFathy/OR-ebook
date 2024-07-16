package com.example.ebook_edition1.service.impl;

import com.example.ebook_edition1.DTO.UserRegistrationDto;
import com.example.ebook_edition1.exception.UserAlreadyExistsException;
import com.example.ebook_edition1.model.User;
import com.example.ebook_edition1.model.Role;
import com.example.ebook_edition1.repository.UserRepository;
import com.example.ebook_edition1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User registerUser(UserRegistrationDto registrationDto) {
        // Check if the username or email already exists
        if (userRepository.findByUsername(registrationDto.getUsername()).isPresent() ||
                userRepository.findByEmail(registrationDto.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("Username or email already exists.");
        }

        User user = new User(registrationDto.getUsername(), registrationDto.getEmail(),
                passwordEncoder.encode(registrationDto.getPassword()), Arrays.asList(new Role("ROLE_USER")));

        // Save the user to the database
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.get().getEmail(), user.get().getPassword(), mapRolesToAuthorities(user.get().getRoles()));
    }
    @Override
    public String getUsernameByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.map(User::getUsername).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
  @Override
  public User getUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        return user.get();
  }
  @Override
  public User updateUser(User user) {
        return userRepository.save(user);


  }
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}
