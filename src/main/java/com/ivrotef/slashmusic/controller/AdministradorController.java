package com.ivrotef.slashmusic.controller;

import com.ivrotef.slashmusic.model.Persona;
import com.ivrotef.slashmusic.model.Administrador;
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

@Controller
@RequestMapping(value = "/admin")
public class AdministradorController {

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

}
