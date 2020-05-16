package com.ivrotef.slashmusic.controller;

import com.ivrotef.slashmusic.model.Cancion;
import com.ivrotef.slashmusic.model.Artista;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import java.util.ArrayList;

@Controller
public class BusquedaController {

  @Autowired
  CancionService cancionService;


  @RequestMapping(value = "/ver", method = RequestMethod.GET)
  public ModelAndView verListas(@RequestParam(value="item", required=true) String param1) {
    ModelAndView modelAndView = new ModelAndView ("Busqueda");
    ArrayList<Cancion> resultados = cancionService.getCancionesSimilares(param1);
    modelAndView.addObject("listas", resultados);
    return modelAndView;
  }

}