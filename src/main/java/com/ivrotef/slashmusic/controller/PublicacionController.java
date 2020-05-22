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
import java.util.List;

@Controller
@RequestMapping(value = "/inicio")
public class PublicacionController {
    
    @Autowired
    PublicacionService publicacionService;

    @Autowired 
    ComentarioService comentarioService;

    @Autowired
    PublicacionCompartidaService pcService;

    @Autowired
    CancionService cancionService;

    @Autowired
    UsuarioService usuarioService;

    ArrayList<Publicacion> publicaciones;

    ArrayList<Publicacion> publicacionesUsuario;

    ArrayList<PublicacionCompartida> publicacionCompartidas;

    @RequestMapping(value = "/ver", method = RequestMethod.GET)
    public ModelAndView verPublicaciones(){
        ModelAndView modelAndView = new ModelAndView("VerPublicaciones");
        obtPublicaciones();
        asignarComentarios();
        asignarPCompartidas();
        boolean hayPublicaciones = (publicaciones.size() == 0) ? false : true;
        modelAndView.addObject("publicaciones", publicaciones);
        modelAndView.addObject("hayPublicaciones", hayPublicaciones);
        return modelAndView;
    }

    @RequestMapping(value = "/publicaciones", method = RequestMethod.GET)
    public ModelAndView verPublicacionesPropias () {

    }

    @RequestMapping(value = "/publicaciones_compartidas", method = RequestMethod.GET)
    public ModelAndView verPublicacionesCompartidas () {

    }

    @RequestMapping(value = "/crear", method = RequestMethod.GET)
    public String crearPublicacion () {

    }

    @RequestMapping(value = "/crear/canciones", method = RequestMethod.GET)
    public ModelAndView verCanciones () {

    }

    @RequestMapping(value = "/crear/canciones/{nombreCancion}", method = RequestMethod.GET)
    public String seleccionarCancion () {

    }

    @RequestMapping(value = "/eliminar/{}", method = RequestMethod.GET)
    public String eliminarPublicacion () {

    }

    @RequestMapping(value = "/actualizar/{}", method = RequestMethod.GET)
    public String actualizarPublicacionPropia () {

    }

    @RequestMapping(value = "/actualizar_compartida/{}", method = RequestMethod.GET)
    public String actualizarPublicacionCompartida () {

    }

    @RequestMapping(value = "/compartir/{}", method = RequestMethod.GET)
    public String compartirPublicacion () {

    }

    @RequestMapping(value = "/comentar/{}", method = RequestMethod.GET)
    public String comentarPublicacion () {

    }

    private void obtPublicaciones() {
        publicaciones = publicacionService.obtenerPublicaciones();
        if (publicaciones == null) {
            publicaciones = new ArrayList<Publicacion>();
        }
    }

    private void asignarComentarios() {
        if (publicaciones.size() != 0) {
            for (Publicacion p : publicaciones) {
                List<Comentario> comentarios = new ArrayList<Comentario>();
                comentarios = comentarioService.obtenerComentariosPublicacion(p.getIdPublicacion());
                p.setComentarios(comentarios);
            }
        }
    }

    private void asignarPCompartidas() {
        if (publicaciones.size() != 0) {
            for (Publicacion p : publicaciones) {
                List<PublicacionCompartida> publicacionesC = new ArrayList<PublicacionCompartida>();
                publicacionesC = pcService.obtenerPCPublicacion(p.getIdPublicacion());
                p.setPublicacionCompartidas(publicacionesC);
            }
        }
    }
}