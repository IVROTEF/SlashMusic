package com.ivrotef.slashmusic.controller;

import com.ivrotef.slashmusic.model.Artista;
import com.ivrotef.slashmusic.model.Persona;
import com.ivrotef.slashmusic.config.PersonaWrapper;
import com.ivrotef.slashmusic.controller.ArtistaService;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import java.util.ArrayList;

@Controller
@RequestMapping(value = "/artistas")
public class ArtistaController {

  @Autowired
  ArtistaService artistaService;
  
  ArrayList<Artista> artistas;

  @RequestMapping(value = "/ver", method = RequestMethod.GET)
  public ModelAndView verArtistas(@AuthenticationPrincipal PersonaWrapper persona){
      ModelAndView modelAndView = new ModelAndView ("VerArtistas");
      artistas = artistaService.obtenerArtistas();
      if (artistas == null){
          artistas = new ArrayList<Artista>();
      }
      boolean hayArtistas = (artistas.size() == 0) ? false : true;
      modelAndView.addObject("artistas", artistas);
      modelAndView.addObject("hayArtistas", hayArtistas);
      return modelAndView;
  }
}