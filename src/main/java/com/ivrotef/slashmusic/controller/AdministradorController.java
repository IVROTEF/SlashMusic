package com.ivrotef.slashmusic.controller;

import com.ivrotef.slashmusic.model.Persona;
import com.ivrotef.slashmusic.model.Artista;
import com.ivrotef.slashmusic.model.Administrador;
import com.ivrotef.slashmusic.controller.ArtistaService;
import com.ivrotef.slashmusic.config.PersonaWrapper;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/admin")
public class AdministradorController {

  @Autowired
  ArtistaService artistaService;

  /* El admin se loggea y ve su pantalla de inicio */
  @PreAuthorize("hasRole('ADMIN')")
  @RequestMapping(value = "/inicio", method = RequestMethod.GET)
  public ModelAndView verInicio (@AuthenticationPrincipal PersonaWrapper persona) {
    ModelAndView modelAndView = new ModelAndView ("AdminInicio");
    Persona actual = persona.getPersona();
    Administrador admin = actual.getAdministrador();
    modelAndView.addObject("admin", admin.getCorreo());
    return modelAndView;
  }

  @PreAuthorize("hasRole('ADMIN')")
  @RequestMapping(value = "/artistas/ver", method = RequestMethod.GET)
  public ModelAndView verArtistas (@AuthenticationPrincipal PersonaWrapper persona) {
    ModelAndView modelAndView = new ModelAndView ("VerArtistasAdmin");
    Persona actual = persona.getPersona();
    List<Artista> artistas = artistaService.getArtistas();
    boolean hayArtistas = false;
    if (artistas != null) {
      hayArtistas = (artistas.size() == 0)? false : true;
    }
    modelAndView.addObject("hayArtistas", hayArtistas);
    modelAndView.addObject("artistas", artistas);
    return modelAndView;
  }

  @PreAuthorize("hasRole('ADMIN')")
  @RequestMapping(value = "/artistas/agregar/{nombreArtista}", method = RequestMethod.GET)
  public String agregarArtista (@PathVariable("nombreArtista") String nombreArtista,
                                   @AuthenticationPrincipal PersonaWrapper persona) {
    Artista n = new Artista (nombreArtista);
    artistaService.guardar(n);
    return "redirect:/admin/artistas/ver";
  }

  @PreAuthorize("hasRole('ADMIN')")
  @RequestMapping(value = "/artistas/eliminar/{nombreArtista}", method = RequestMethod.GET)
  public String eliminarArtista (@PathVariable("nombreArtista") String nombreArtista,
                                   @AuthenticationPrincipal PersonaWrapper persona) {
    Artista d = artistaService.obtenerArtistaNombre(nombreArtista);
    artistaService.eliminar (d);
    return "redirect:/admin/artistas/ver";
  }

}
