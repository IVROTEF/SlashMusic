package com.ivrotef.slashmusic.controller;

import com.ivrotef.slashmusic.model.Cancion;
import com.ivrotef.slashmusic.model.Usuario;
import com.ivrotef.slashmusic.model.Persona;
import com.ivrotef.slashmusic.model.Artista;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import java.util.ArrayList;
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
@RequestMapping(value = "/search")
public class BusquedaController {

  @Autowired
  CancionService cancionService;
  @Autowired
  ArtistaService artistaService;
  @Autowired
  PersonaService personaService;
  @Autowired
  ListaService listaService;
  @Autowired
  UsuarioService usService;

  String valor;

  // Cuando se reproduce una cancion dentro de la busqueda
  @RequestMapping(value = "/{cancion:\\w+\\W(?:mp3$)}", method = RequestMethod.GET )
  public String reproducirCancion (@PathVariable("cancion") String song) {
    return "redirect:/" + song;
  }

  @RequestMapping(value = "/ver", method = RequestMethod.GET)
  public ModelAndView verListas(@RequestParam(value="item", required=true) String param1,@AuthenticationPrincipal PersonaWrapper persona) {
    ModelAndView modelAndView = new ModelAndView ("Busqueda");
    valor = param1;
    ArrayList<Cancion> resultados = cancionService.getCancionesSimilares(param1);
    ArrayList<Artista> art = artistaService.getArtSimilares(param1);
    ArrayList<Persona> us = personaService.getUsSimilares(param1);
    if (us == null) {
      us = new ArrayList<Persona>();
    } else {
      /* No agrega en la lista de busqueda al usuario actual */
      us.remove(persona.getPersona());
    }
    Persona actual = persona.getPersona();
    ArrayList<Lista> listas = listaService.obtenerListasCorreo(actual.getCorreo());
    boolean hayListas = false;
    if (listas != null){
      hayListas = (listas.size() == 0) ? false : true;
    }
    modelAndView.addObject("list", listas);
    modelAndView.addObject("hayListas", hayListas);
    modelAndView.addObject("listasUs", us);
    modelAndView.addObject("listas", resultados);
    modelAndView.addObject("listasArt", art);

    return modelAndView;
  }

  @RequestMapping(value = "/agregaC/{cancion}" )
  public String agregarCancion(@PathVariable("cancion") String can, @AuthenticationPrincipal PersonaWrapper persona){
    Persona actual = persona.getPersona();
    String correo = actual.getCorreo();
    Cancion c = cancionService.obtenerCancion(can);
    usService.guardarCancionFav(c, correo);
    return "redirect:/search/ver/?item="+valor;
  }

  @RequestMapping(value= "/eliminar/{cancion}" )
  public String eliminarCancion (@PathVariable("cancion") String cancion, @AuthenticationPrincipal PersonaWrapper persona){
    Persona actual = persona.getPersona();
    String correo = actual.getCorreo();
    Cancion r = cancionService.obtenerCancion(cancion);
    usService.eliminarCancionFav(r, correo);
    return "redirect:/canciones_favoritas";
  }

}
