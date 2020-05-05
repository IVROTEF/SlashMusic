package com.ivrotef.slashmusic.controller;

import com.ivrotef.slashmusic.model.Lista;
import com.ivrotef.slashmusic.model.Persona;
import com.ivrotef.slashmusic.config.PersonaWrapper;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import java.util.ArrayList;

@Controller
@RequestMapping(value = "/listas")
public class ListaController {

  @Autowired
  ListaService listaService;

  ArrayList<Lista> listas;


  @RequestMapping(value = "/ver", method = RequestMethod.GET)
  public ModelAndView verListas(@AuthenticationPrincipal PersonaWrapper persona) {
    ModelAndView modelAndView = new ModelAndView ("VerListas");
    Persona actual = persona.getPersona();
    check(actual);
    modelAndView.addObject("listas", listas);
    return modelAndView;
  }

  @RequestMapping(value = "/editar", method = RequestMethod.GET)
  public ModelAndView editar(){
    ModelAndView modelAndView = new ModelAndView ("EditarLista");
    modelAndView.addObject("listas", listas);
    // el url para eliminar las listas descartadas
    modelAndView.addObject("aceptar", "/listas/eliminar");
    return modelAndView;
  }

  @RequestMapping(value = "/editar/{nombreLista}" )
  public String editarLista(@PathVariable("nombreLista") String nombreLista, @AuthenticationPrincipal PersonaWrapper persona){
    Persona actual = persona.getPersona();
    Lista descartado = listaService.obtenerListaNombre(actual.getCorreo(), nombreLista);
    listas.remove(descartado);
    return "redirect:/listas/editar";
  }

  @RequestMapping(value = "/eliminar", method = RequestMethod.GET)
  public String eliminar(@AuthenticationPrincipal PersonaWrapper persona){
    Persona actual = persona.getPersona();
    try {
      listaService.actualizar(listas, actual.getCorreo());
    } catch (Exception e) {}
    return "redirect:/listas/ver";
  }


  /* Asigna la variable listas de la base de datos o la crea
    si aún no tiene listas el usuario */
  private void check (Persona persona) {
    // Revisa si existen listas del usuario
    listas = listaService.obtenerListasCorreo(persona.getCorreo());
    // El usuario apenas se registro
    if (listas == null) {
      // Inicializamos la lista
      listas = new ArrayList<Lista>();
    }
  }

}
