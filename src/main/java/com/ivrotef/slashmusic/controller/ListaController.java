package com.ivrotef.slashmusic.controller;

import com.ivrotef.slashmusic.model.Lista;
import com.ivrotef.slashmusic.model.ListaID;
import com.ivrotef.slashmusic.model.Persona;
import com.ivrotef.slashmusic.config.PersonaWrapper;
import com.ivrotef.slashmusic.model.Cancion;
import com.ivrotef.slashmusic.controller.ListaService;
import com.ivrotef.slashmusic.controller.CancionService;

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

  @Autowired
  CancionService cancionService;


  @RequestMapping(value = "/ver", method = RequestMethod.GET)
  public ModelAndView verListas(@AuthenticationPrincipal PersonaWrapper persona) {
    ModelAndView modelAndView = new ModelAndView ("VerListas");
    Persona actual = persona.getPersona();
    check(actual);
    modelAndView.addObject("listas", listas);
    return modelAndView;
  }

  @RequestMapping(value = "/crear/{nombreLista}",  method = RequestMethod.GET)
  public String crearLista(@PathVariable("nombreLista") String nombreLista, @AuthenticationPrincipal PersonaWrapper persona){
    Persona actual = persona.getPersona();
    for (Lista l : listas) {
      if (l.getListaID().getNombreLista().equals(nombreLista)) {
        return "redirect:/listas/ver";
      }
    }
    ListaID listaId = new ListaID(actual.getCorreo(), nombreLista);
    Lista nueva = new Lista (listaId);
    listaService.guardar(nueva);
    return "redirect:/listas/ver";
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


  /* Operaciones sobre una lista */


  @RequestMapping(value= "/ver/{nombreLista}", method = RequestMethod.GET)
  public ModelAndView verLista(@PathVariable("nombreLista") String nombreLista,  @AuthenticationPrincipal PersonaWrapper persona){
    Persona actual = persona.getPersona();
    check(actual);
    ModelAndView modelAndView = new ModelAndView("VerLista");
    for (Lista lista : listas) {
      if (lista.getListaID().getNombreLista().equals(nombreLista)) {
        lista = listaService.obtenerListaId(new ListaID(actual.getCorreo(), nombreLista));
        modelAndView.addObject("lista_actual", lista);
        modelAndView.addObject("canciones", lista.getCanciones());
        break;
      }
    }
    return modelAndView;
  }

  @RequestMapping(value="/ver/{nombreLista}/agregar/{cancion}", method = RequestMethod.GET)
  public String agregarCancion(@PathVariable("nombreLista") String nombreLista,
                               @PathVariable("cancion") String cancion,
                               @AuthenticationPrincipal PersonaWrapper persona) {
    Persona actual = persona.getPersona();
    check(actual);
    Cancion nueva = cancionService.obtenerCancion(cancion);
    for (Lista lista : listas) {
      if (lista.getListaID().getNombreLista().equals(nombreLista)){
        if (!lista.contiene(nueva)) {
          lista.agregarCancion(nueva);
          listaService.actualizar(listas, actual.getCorreo());
        }
      }
      break;
    }
    return "redirect:/listas/ver/" + nombreLista;
  }

  @RequestMapping(value="/ver/{nombreLista}/cambiar_nombre/{nuevoNombre}", method = RequestMethod.GET)
  public String editarNombre(@PathVariable("nombreLista") String nombreLista,
                             @PathVariable("nuevoNombre") String nuevoNombre,
                             @AuthenticationPrincipal PersonaWrapper persona) {
    for (Lista lista : listas) {
      if (lista.getListaID().getNombreLista().equals(nombreLista)) {
        lista.getListaID().setNombreLista(nuevoNombre);
        try {
          listaService.actualizar(lista);
        } catch (Exception e) {}
        break;
      }
    }
    return "redirect:/listas/ver/" + nuevoNombre;
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
