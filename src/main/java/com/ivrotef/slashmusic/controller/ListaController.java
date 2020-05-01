package com.ivrotef.slashmusic.controller;

import com.ivrotef.slashmusic.model.Lista;

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

  String user = "m@ciencias.unam.mx";

  @RequestMapping(value = "/ver", method = RequestMethod.GET)
  public ModelAndView verListas() {
    ModelAndView modelAndView = new ModelAndView ("VerListas");
    check();
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
  public String editarLista(@PathVariable("nombreLista") String nombreLista){
    Lista descartado = listaService.obtenerListaNombre(user, nombreLista);
    listas.remove(descartado);
    return "redirect:/listas/editar";
  }

  @RequestMapping(value = "/eliminar", method = RequestMethod.GET)
  public String eliminar(){
    try {
      listaService.actualizar(listas, user);
    } catch (Exception e) {}
    return "redirect:/listas/ver";
  }


  /* Asigna la variable listas de la base de datos o la crea
    si aún no tiene listas el usuario */
  private void check () {
    // Revisa si existen listas del usuario
    listas = listaService.obtenerListasCorreo(user);
    // El usuario apenas se registro
    if (listas == null) {
      // Inicializamos la lista
      listas = new ArrayList<Lista>();
    }
  }

}
