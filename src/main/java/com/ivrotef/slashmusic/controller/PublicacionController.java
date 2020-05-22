package com.ivrotef.slashmusic.controller;

import com.ivrotef.slashmusic.model.Publicacion;
import com.ivrotef.slashmusic.model.Comentario;
import com.ivrotef.slashmusic.model.ComentarioID;
import com.ivrotef.slashmusic.model.PublicacionCompartida;
import com.ivrotef.slashmusic.model.PublicacionCompartidaID;
import com.ivrotef.slashmusic.model.Persona;
import com.ivrotef.slashmusic.config.PersonaWrapper;
import com.ivrotef.slashmusic.controller.PublicacionService;
import com.ivrotef.slashmusic.controller.ComentarioService;
import com.ivrotef.slashmusic.controller.PublicacionCompartidaService;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import java.util.ArrayList;

@Controller
@RequestMapping(value = "/inicio")
public class PublicacionController {
    
}