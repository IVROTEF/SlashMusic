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
<<<<<<< HEAD
=======

>>>>>>> origin
import java.util.ArrayList;
import java.util.List;

@Controller
<<<<<<< HEAD
@RequestMapping(value = "/usuario")
=======
>>>>>>> origin
public class UsuarioController {

  @Autowired
  UsuarioService usuarioService;
<<<<<<< HEAD
  
  @Autowired
  ArtistaService artistaService; 

  List<Artista> artistasFavoritos;

  List<Usuario> usuariosFavoritos;

  List<Artista> artistas;

  List<Usuario> usuarios;
  
  @RequestMapping(value = "/artistasFav", method = RequestMethod.GET)
  public ModelAndView verArtistasFav(@AuthenticationPrincipal PersonaWrapper persona) {
      ModelAndView modelAndView = new ModelAndView("VerArtistasFav");
      Persona actual = persona.getPersona();
      artistasFav(actual);
      boolean hayArtistasFav = (artistasFavoritos.size() == 0) ? false : true;
      modelAndView.addObject("artistasFavoritos", artistasFavoritos);
      modelAndView.addObject("hayArtistasFav", hayArtistasFav);
      return modelAndView;
  }

  @RequestMapping(value = "/artistasFav/editar", method = RequestMethod.GET)
  public ModelAndView editarArtistasFav(){
      ModelAndView modelAndView = new ModelAndView("EditarArtistasFav");
      modelAndView.addObject("artistasFavoritos", artistasFavoritos);
      modelAndView.addObject("aceptar", "/usuario/artistasFav");
      return modelAndView;
  }

  @RequestMapping(value = "/artistasFav/agregar_art_fav", method = RequestMethod.GET)
  public ModelAndView agregarArtistaFav(){
      ModelAndView modelAndView = new ModelAndView("AgregarArtistasFav");
      artistas = artistaService.obtenerArtistas();
      if(artistas == null){
        artistas = new ArrayList<Artista>();
      }
      boolean hayArtistas = (artistas.size() == 0) ? false : true;
      modelAndView.addObject("artistas", artistas);
      modelAndView.addObject("hayArtistas", hayArtistas);
      return modelAndView;
  }

  @RequestMapping(value = "/artistasFav/agregar_art_fav/seleccionar/{nombreArtista}")
  public String seleccionarArtistaFav(@PathVariable("nombreArtista") String nombreArtista, @AuthenticationPrincipal PersonaWrapper persona){
    Persona actual = persona.getPersona();
    for(Artista a : artistasFavoritos) {
        if(a.getNombre().equals(nombreArtista)){
            return "redirect:/usuario/artistasFav";
        }
    }
    Artista artista = new Artista(nombreArtista);
    usuarioService.guardarArtistaFav(artista, actual.getCorreo());
    return "redirect:/usuario/artistasFav";
  }

  @RequestMapping(value = "/artistasFav/eliminar/{nombreArtista}")
  public String eliminarArtFav(@PathVariable("nombreArtista") String nombreArtista, @AuthenticationPrincipal PersonaWrapper persona){
      Persona actual = persona.getPersona();
      Artista artista = new Artista(nombreArtista);
      usuarioService.eliminarArtistaFav(artista, actual.getCorreo());
      artistasFavoritos.remove(artista);
      return "redirect:/usuario/artistasFav/editar";
  }

  @RequestMapping(value = "/usuariosFav", method = RequestMethod.GET)
  public ModelAndView verUsuariosFav(@AuthenticationPrincipal PersonaWrapper persona) {
      ModelAndView modelAndView = new ModelAndView("VerUsuariosFav");
      Persona actual = persona.getPersona();
      usuariosFav(actual);
      boolean hayUsuariosFav = (usuariosFavoritos.size() == 0) ? false : true;
      modelAndView.addObject("usuariosFavoritos", usuariosFavoritos);
      modelAndView.addObject("hayUsuariosFav", hayUsuariosFav);
      return modelAndView;
  }

  @RequestMapping(value = "/usuariosFav/editar", method = RequestMethod.GET)
  public ModelAndView editarUsuariosFav(){
      ModelAndView modelAndView = new ModelAndView("EditarUsuariosFav");
      modelAndView.addObject("usuariosFavoritos", usuariosFavoritos);
      modelAndView.addObject("aceptar", "/usuario/usuariosFav");
      return modelAndView;
  }

  @RequestMapping(value = "/usuariosFav/agregar_us_fav", method = RequestMethod.GET)
  public ModelAndView agregarUsuarioFav(){
      ModelAndView modelAndView = new ModelAndView("AgregarUsuarioFav");
      usuarios = usuarioService.obtenerUsuarios();
      if(usuarios == null){
        usuarios = new ArrayList<Usuario>();
      }
      boolean hayUsuarios = (usuarios.size() == 0) ? false : true;
      modelAndView.addObject("usuarios", usuarios);
      modelAndView.addObject("hayUsuarios", hayUsuarios);
      return modelAndView;
  }

  @RequestMapping(value = "/usuariosFav/agregar_us_fav/seleccionar/{correoUsuario}")
  public String seleccionarUsuarioFav(@PathVariable("correoUsuario") String correoUsuario, @AuthenticationPrincipal PersonaWrapper persona){
    Persona actual = persona.getPersona();
    for(Usuario u : usuariosFavoritos) {
        if(u.getCorreo().equals(correoUsuario)){
            return "redirect:/usuario/usuariosFav";
        }
    }
    Usuario usuario = usuarioService.obtenerUsuario(correoUsuario);
    usuarioService.guardarUsuarioFav(usuario, actual.getCorreo());
    return "redirect:/usuario/usuariosFav";
  }

  @RequestMapping(value = "/usuariosFav/eliminar/{correoUsuario}")
  public String eliminarUsFav(@PathVariable("correoUsuario") String correoUsuario, @AuthenticationPrincipal PersonaWrapper persona){
      Persona actual = persona.getPersona();
      Usuario usuario = usuarioService.obtenerUsuario(correoUsuario);
      usuarioService.eliminarUsuarioFav(usuario, actual.getCorreo());
      usuariosFavoritos.remove(usuario);
      return "redirect:/usuario/usuariosFav/editar";
  }

  private void artistasFav(Persona persona){
      artistasFavoritos = usuarioService.obtenerArtistasFavoritos(persona.getCorreo());
      if (artistasFavoritos == null){
          artistasFavoritos = new ArrayList<Artista>();
      }
  }

  private void usuariosFav(Persona persona){
    usuariosFavoritos = usuarioService.obtenerUsuariosFavoritos(persona.getCorreo());
    if (usuariosFavoritos == null){
        usuariosFavoritos = new ArrayList<Usuario>();
    }
}
}
=======

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
>>>>>>> origin
