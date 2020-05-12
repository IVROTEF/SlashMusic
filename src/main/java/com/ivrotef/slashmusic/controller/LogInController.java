package com.ivrotef.slashmusic.controller;

import com.ivrotef.slashmusic.model.Persona;
import com.ivrotef.slashmusic.config.PersonaWrapper;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class LogInController {

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (error != null) {
          model.addAttribute("error", "El correo o contraseña son incorrectos");
        }
        return "login";
    }
    /*
    @GetMapping("/")
    public String index(Model model, @AuthenticationPrincipal PersonaWrapper persona) {
        String correo = persona.getPersona().getCorreo();
        model.addAttribute("currentUsername", correo);
        return "index";
    }
    */
}
