package com.ivrotef.slashmusic.controller;

import com.ivrotef.slashmusic.model.Artista;
import com.ivrotef.slashmusic.model.Persona;
import com.ivrotef.slashmusic.config.PersonaWrapper;
import com.ivrotef.slashmusic.controller.ArtistaService;
<<<<<<< HEAD

=======
import com.ivrotef.slashmusic.controller.UsuarioService;

import org.springframework.web.bind.annotation.PathVariable;
>>>>>>> origin
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import java.util.ArrayList;
<<<<<<< HEAD

@Controller
@RequestMapping(value = "/artistas")
public class ArtistaController {

}
=======
import java.util.List;

@Controller
@RequestMapping(value = "/artistasFavoritos")
public class ArtistaController {

  @Autowired
  ArtistaService artistaService;

  @Autowired
  UsuarioService usuarioService;

  @RequestMapping(value = "/ver", method = RequestMethod.GET)
  public ModelAndView verArtistas(@AuthenticationPrincipal PersonaWrapper persona){
      Persona actual = persona.getPersona ();
      ModelAndView modelAndView = new ModelAndView ("VerArtistas");
      List<Artista> artistas = usuarioService.getArtistasFavoritosUsuario(actual.getCorreo());
      if (artistas == null){
          artistas = new ArrayList<Artista>();
      }
      boolean hayArtistas = (artistas.size() == 0) ? false : true;
      modelAndView.addObject("artistas", artistas);
      modelAndView.addObject("hayArtistas", hayArtistas);
      return modelAndView;
  }

  @RequestMapping(value = "/agregar/{nombreArtista}", method = RequestMethod.GET)
  public String agregarArtista (@PathVariable("nombreArtista") String nombreArtista,
                                   @AuthenticationPrincipal PersonaWrapper persona) {
    Persona actual = persona.getPersona();
    Artista a = artistaService.obtenerArtistaNombre(nombreArtista);
    usuarioService.agregarArtista(a, actual.getCorreo());
    return "redirect:/artistasFavoritos/ver";
  }

  @RequestMapping(value = "/eliminar/{nombreArtista}", method = RequestMethod.GET)
  public String eliminarArtista (@PathVariable("nombreArtista") String nombreArtista,
                                   @AuthenticationPrincipal PersonaWrapper persona) {
    Persona actual = persona.getPersona();
    Artista d = artistaService.obtenerArtistaNombre(nombreArtista);
    usuarioService.eliminarArtista(d, actual.getCorreo());
    return "redirect:/artistasFavoritos/ver";
  }

}
>>>>>>> origin
