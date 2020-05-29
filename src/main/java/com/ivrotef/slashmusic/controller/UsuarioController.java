package com.ivrotef.slashmusic.controller;

import com.ivrotef.slashmusic.model.Usuario;
import com.ivrotef.slashmusic.model.Artista;
import com.ivrotef.slashmusic.model.Persona;
import com.ivrotef.slashmusic.config.PersonaWrapper;
import com.ivrotef.slashmusic.controller.UsuarioService;
import com.ivrotef.slashmusic.controller.ArtistaService;

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
public class UsuarioController {

  @Autowired
  UsuarioService usuarioService;

  @RequestMapping(value= "/siguiendo/agregar/{usuario}" )
  public String seguirUsuario (@PathVariable("usuario") String usuario, @AuthenticationPrincipal PersonaWrapper persona){
    Persona actual = persona.getPersona();
    Usuario f = usuarioService.obtenerUsuarioCorreo(usuario);
    usuarioService.agregarSeguidor(f, actual.getCorreo());
    return "redirect:/siguiendo/ver";
  }

  @RequestMapping(value= "/siguiendo/ver" )
  public ModelAndView verSeguidos (@AuthenticationPrincipal PersonaWrapper persona){
    ModelAndView modelAndView = new ModelAndView ("Siguiendo");
    Persona actual = persona.getPersona();
    List<Usuario> amigos = usuarioService.obtenerAmigos(actual.getCorreo());
    boolean hayAmigos = false;
    if (amigos != null){
      hayAmigos = (amigos.size() == 0)? false :true;
    }
    modelAndView.addObject("hayAmigos", hayAmigos);
    modelAndView.addObject("amigos", amigos);
    return modelAndView;
  }

  @RequestMapping(value = "/siguiendo/eliminar/{usuario}")
  public String eliminarAmigo (@PathVariable("usuario") String usuario, @AuthenticationPrincipal PersonaWrapper persona){
    Persona actual = persona.getPersona ();
    Usuario d = usuarioService.obtenerUsuarioCorreo(usuario);
    usuarioService.eliminarAmigo (d, actual.getCorreo());
    return "redirect:/siguiendo/ver";
  }
}
