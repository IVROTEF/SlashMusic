package com.ivrotef.slashmusic.controller;

import com.ivrotef.slashmusic.config.SecurityService;
import com.ivrotef.slashmusic.controller.PersonaService;
import com.ivrotef.slashmusic.validator.PersonaValidator;
import com.ivrotef.slashmusic.config.PersonaWrapper;
import com.ivrotef.slashmusic.model.Persona;

import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class SignUpController {

      @Autowired
      private PersonaService personaService;

      @Autowired
      private SecurityService securityService;

      @Autowired
      private PersonaValidator personaValidator;

      @GetMapping("/signup")
      public String registration(Model model) {
          model.addAttribute("persona", new Persona());
          return "signup";
      }

      @PostMapping("/signup")
      public String registration(@ModelAttribute @Valid Persona persona, BindingResult bindingResult) {
          personaValidator.validate(persona, bindingResult);

          if (bindingResult.hasErrors()) {
            return "signup";
          }

          personaService.guardar(persona);

          personaService.nuevoUsuario(persona);

          securityService.autoLogin(persona.getNombre(), persona.getPassword());

          return "redirect:/";
      }
}
