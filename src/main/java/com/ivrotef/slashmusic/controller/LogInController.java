package com.ivrotef.slashmusic.controller;

import com.ivrotef.slashmusic.model.Persona;
import com.ivrotef.slashmusic.model.Lista;
import com.ivrotef.slashmusic.config.PersonaWrapper;
import com.ivrotef.slashmusic.controller.ListaService;
import com.ivrotef.slashmusic.controller.CancionService;

import java.util.ArrayList;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class LogInController {

    @Autowired
    ListaService listaService;

    @Autowired
    CancionService cancionService;

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (error != null) {
          model.addAttribute("error", "El correo o contraseña son incorrectos");
        }
        return "login";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView verInicio(@AuthenticationPrincipal PersonaWrapper persona) {
      Persona actual = persona.getPersona();
      ModelAndView modelAndView = new ModelAndView("index");
      String correo = persona.getPersona().getCorreo();
      ArrayList<Lista> listas = listaService.obtenerListasCorreo(actual.getCorreo());
      boolean hayListas = false;
      if (listas != null){
        hayListas = (listas.size() == 0) ? false : true;
      }
      modelAndView.addObject("currentUsername", correo);
      modelAndView.addObject("canciones", cancionService.getCanciones());
      modelAndView.addObject("listas", listas);
      modelAndView.addObject("hayListas", hayListas);
      return modelAndView;
    }
}
