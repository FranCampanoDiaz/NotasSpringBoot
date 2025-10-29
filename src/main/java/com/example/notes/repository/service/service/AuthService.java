package com.example.notes.repository.service.service;

import com.example.notes.controller.AuthController;
import com.example.notes.model.AppUser;
import com.example.notes.repository.service.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;

@Service
public class AuthService {
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

//    public String register(@ModelAttribute("userDto") @Validated AuthController.RegisterDto registerDto, Model model) {
//        //validacion si el usuario existe
//        if (appUserRepository.existsByUsername(registerDto.username)) {
//            model.addAttribute("error", "El usuario ya existe");
//            return "register";
//        }
//        AppUser appUser = new AppUser();
//        appUser.setUsername(registerDto.username);
//        appUser.setPassword(passwordEncoder.encode(registerDto.password));
//        appUser.setRole("ROLE_USER");
//        appUserRepository.save(appUser);
//        return "redirect:/login?registered";
//    }

}
