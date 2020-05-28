package com.ivrotef.slashmusic.controller;

import com.ivrotef.slashmusic.model.Artista;
import com.ivrotef.slashmusic.model.Cancion;
import com.ivrotef.slashmusic.model.Persona;
import com.ivrotef.slashmusic.config.PersonaWrapper;
import com.ivrotef.slashmusic.controller.UsuarioService;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


import java.util.ArrayList;
import java.util.List;

@Controller
public class CancionController {

      @Autowired
      UsuarioService usService;
    
      @RequestMapping(value = "/canciones_favoritas", method = RequestMethod.GET)
      public ModelAndView verListas(@AuthenticationPrincipal PersonaWrapper persona) {
        ModelAndView modelAndView = new ModelAndView ("Canc_fav");
        Persona actual = persona.getPersona();
        String correo = actual.getCorreo();
        List<Cancion> cancionesFavoritas = usService.obtenerCancionesFavoritas(correo);
        boolean hayListas = false;
        if (cancionesFavoritas != null){
          hayListas = (cancionesFavoritas.size() == 0) ? false : true;
        }
        modelAndView.addObject("list", cancionesFavoritas);
        modelAndView.addObject("hayListas", hayListas);
        return modelAndView;
      }
    
}
