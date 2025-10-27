package com.example.notes.controller;


import com.example.notes.model.AppUser;
import com.example.notes.repository.service.AppUserRepository;
import com.example.notes.repository.service.service.CustomUserDetailsService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Validated
public class AuthController {
    //private CustomUserDetailsService customUserDetailsService;
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    //muestra el formulario registro
    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("userDto", new RegisterDto("", ""));
        return "register";
    }

    //Procesar el registro
    @PostMapping("/register")
    public String register(@ModelAttribute("userDto") @Validated RegisterDto registerDto, Model model) {
        //validacion si el usuario existe
        if (appUserRepository.existsByUsername(registerDto.username)) {
            model.addAttribute("error", "El usuario ya existe");
            return "register";
        }
        AppUser appUser = new AppUser();
        appUser.setUsername(registerDto.username);
        appUser.setPassword(passwordEncoder.encode(registerDto.password));
        appUser.setRole("ROLE_USER");
        appUserRepository.save(appUser);
        return "redirect:/login?registered";
    }

    public record RegisterDto(@NotBlank String username, @NotBlank String password) {

    }
}
