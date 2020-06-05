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
    boolean hayListas = (listas.size() == 0) ? false : true;
    modelAndView.addObject("listas", listas);
    modelAndView.addObject("hayListas", hayListas);
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
    ModelAndView modelAndView = new ModelAndView ("EditarListas");
    modelAndView.addObject("listas", listas);
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

  // Cuando se reproduce la cancion dentro de una lista
  @RequestMapping(value = "/ver/{cancion:\\w+\\W(?:mp3$)}", method = RequestMethod.GET )
  public String reproducirCancion (@PathVariable("cancion") String song) {
    return "redirect:/" + song;
  }

  @RequestMapping(value= "/ver/{nombreLista:\\w+(?!:mp3$)}", method = RequestMethod.GET)
  public ModelAndView verLista(@PathVariable("nombreLista") String nombreLista,  @AuthenticationPrincipal PersonaWrapper persona){
    Persona actual = persona.getPersona();
    check(actual);
    ModelAndView modelAndView = new ModelAndView("VerLista");
    boolean hayCanciones = false;
    for (Lista lista : listas) {
      if (lista.getListaID().getNombreLista().equals(nombreLista)) {
        lista = listaService.obtenerListaId(new ListaID(actual.getCorreo(), nombreLista));
        hayCanciones = (lista.getCanciones().size() == 0) ? false : true;
        modelAndView.addObject("lista_actual", lista);
        modelAndView.addObject("canciones", lista.getCanciones());
        break;
      }
    }
    modelAndView.addObject("hayCanciones", hayCanciones);
    return modelAndView;
  }

  @RequestMapping(value="/ver/{nombreLista}/agregar/{cancion}", method = RequestMethod.GET)
  public String agregarCancion(@PathVariable("nombreLista") String nombreLista,
                               @PathVariable("cancion") String cancion,
                               @AuthenticationPrincipal PersonaWrapper persona) {
    Persona actual = persona.getPersona();
    check(actual);
    Cancion nueva = cancionService.obtenerCancion(cancion);
    if (listas.size() == 0) {

    }
    for (Lista lista : listas) {
      if (lista.getListaID().getNombreLista().equals(nombreLista)){
        if (!lista.contiene(nueva)) {
          lista.agregarCancion(nueva);
          try {
            listaService.actualizar(listas, actual.getCorreo());
          } catch (Exception e) {}
        }
        break;
      }
    }
    return "redirect:/listas/ver/" + nombreLista;
  }

  @RequestMapping(value="/ver/{nombreLista}/cambiar_nombre/{nuevoNombre}", method = RequestMethod.GET)
  public String editarNombre(@PathVariable("nombreLista") String nombreLista,
                             @PathVariable("nuevoNombre") String nuevoNombre,
                             @AuthenticationPrincipal PersonaWrapper persona) {
    Persona actual = persona.getPersona();
    for (Lista lista : listas) {
      if (lista.getListaID().getNombreLista().equals(nombreLista)) {
        lista.getListaID().setNombreLista(nuevoNombre);
        break;
      }
    }
    listaService.actualizar(listas, actual.getCorreo());
    return "redirect:/listas/ver/" + nuevoNombre;
  }

  @RequestMapping(value="/ver/{nombreLista}/editar", method = RequestMethod.GET)
  public ModelAndView editarListaCanciones(@PathVariable("nombreLista") String nombreLista,
                             @AuthenticationPrincipal PersonaWrapper persona) {
    Persona actual = persona.getPersona();
    ModelAndView modelAndView = new ModelAndView("EditarLista");
    for (Lista lista : listas) {
      if (lista.getListaID().getNombreLista().equals(nombreLista)) {
        modelAndView.addObject("lista_actual", lista);
        break;
      }
    }
    modelAndView.addObject("aceptar", "/listas/ver/" + nombreLista + "/eliminar" );
    return modelAndView;
  }


  @RequestMapping(value="/ver/{nombreLista}/editar/{nombreCancion}", method = RequestMethod.GET)
  public String editarCancion(@PathVariable("nombreLista") String nombreLista,
                             @PathVariable("nombreCancion") String nombreCancion,
                             @AuthenticationPrincipal PersonaWrapper persona) {
    Persona actual = persona.getPersona();
    Cancion descartado = cancionService.obtenerCancion(nombreCancion);
    for (Lista lista : listas) {
      if (lista.getListaID().getNombreLista().equals(nombreLista)) {
        lista.eliminarCancion(descartado);
        break;
      }
    }
    return "redirect:/listas/ver/" + nombreLista +"/editar";
  }

  @RequestMapping(value="/ver/{nombreLista}/eliminar")
  public String eliminarLista(@PathVariable("nombreLista") String nombreLista,
                              @AuthenticationPrincipal PersonaWrapper persona) {
    Persona actual = persona.getPersona();
    listaService.actualizar(listas, actual.getCorreo());
    return "redirect:/listas/ver/" + nombreLista;
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
