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

import java.lang.ProcessBuilder.Redirect;
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

    ArrayList<PublicacionCompartida> publicacionesCompartidas;

    /* Muestra todas las publicaciones de la base de datos. */
    @RequestMapping(value = "/ver", method = RequestMethod.GET)
    public ModelAndView verPublicaciones(){
        ModelAndView modelAndView = new ModelAndView("Publicaciones");
        obtPublicaciones();
        asignarComentarios();
        asignarPCompartidas();
        boolean hayPublicaciones = (publicaciones.size() == 0) ? false : true;
        modelAndView.addObject("publicaciones", publicaciones);
        modelAndView.addObject("hayPublicaciones", hayPublicaciones);
        return modelAndView;
    }

    /* Muestra los comentarios de una publicacion. */
    @RequestMapping(value = "/ver/{idPublicacion}", method = RequestMethod.GET)
    public ModelAndView verComentariosP(@PathVariable("idPublicacion") String idPublicacion){
        ModelAndView modelAndView = new ModelAndView("VerPublicacion");
        int id = Integer.parseInt(idPublicacion);
        Publicacion publicacion = publicacionService.obtenerPublicacionId(id);
        ArrayList<Comentario> comentarios = comentarioService.obtenerComentariosPublicacion(id);
        if (comentarios == null) {
            comentarios = new ArrayList<Comentario>();
        }
        boolean hayComentarios = (comentarios.size() == 0) ? false : true;
        modelAndView.addObject("publicacion", publicacion);
        modelAndView.addObject("comentarios", comentarios);
        modelAndView.addObject("hayComentarios", hayComentarios);
        return modelAndView;
    }

    /* Muestra todas las publicaciones realizadas por el usuario actual. */
    @RequestMapping(value = "/publicaciones", method = RequestMethod.GET)
    public ModelAndView verPublicacionesPropias (@AuthenticationPrincipal PersonaWrapper persona) {
        ModelAndView modelAndView = new ModelAndView("VerPublicaciones");
        Persona actual = persona.getPersona();
        obtienePU(actual);
        boolean hayPU = (publicacionesUsuario.size() == 0) ? false : true;
        modelAndView.addObject("publicacionesUsuario", publicacionesUsuario);
        modelAndView.addObject("hayPU", hayPU);
        return modelAndView;
    }

    /* Muestra los comentarios de una publicacion realizada por el usuario. */
    @RequestMapping(value = "/publicaciones/{idPublicacion}", method = RequestMethod.GET)
    public ModelAndView verComentariosPU(@PathVariable("idPublicacion") String idPublicacion) {
        ModelAndView modelAndView = new ModelAndView("ComentariosPublicacionU");
        int id = Integer.parseInt(idPublicacion);
        Publicacion publicacion = publicacionService.obtenerPublicacionId(id);
        ArrayList<Comentario> comentarios = comentarioService.obtenerComentariosPublicacion(id);
        if (comentarios == null) {
            comentarios = new ArrayList<Comentario>();
        }
        boolean hayComentarios = (comentarios.size() == 0) ? false : true;
        modelAndView.addObject("publicacion", publicacion);
        modelAndView.addObject("comentarios", comentarios);
        modelAndView.addObject("hayComentarios", hayComentarios);
        return modelAndView;
    }

    /* Muestra todas las publicaciones compartidas por el usuario actual. */
    @RequestMapping(value = "/publicaciones_compartidas", method = RequestMethod.GET)
    public ModelAndView verPublicacionesCompartidas () {
        ModelAndView modelAndView = new ModelAndView("VerPublicacionesComp");
        Persona actual = persona.getPersona();
        obtienePC(actual);
        boolean hayPC = (publicacionesCompartidas.size() == 0) ? false : true;
        modelAndView.addObject("publicacionesCompartidas", publicacionesCompartidas);
        modelAndView.addObject("hayPC", hayPC);
        return modelAndView;
    }

    /* Muestra los comentarios de una publicacion compartida por el usuario. */
    @RequestMapping(value = "/publicaciones_compartidas/{idPublicacion}", method = RequestMethod.GET)
    public ModelAndView verComentariosPC(@PathVariable("idPublicacion") String idPublicacion){
        ModelAndView modelAndView = new ModelAndView("ComentariosPublicacionU");
        int id = Integer.parseInt(idPublicacion);
        PublicacionCompartida pc = pcService.obtenerPCPublicacion(id);
        Publicacion publicacion = pc.getPublicacionPC();
        /* Publicacion publicacion = publicacionService.obtenerPublicacionId(pc.getPublicacionCompartidaID().getIdPublicacion()); */
        ArrayList<Comentario> comentarios = comentarioService.obtenerComentariosPublicacion(publicacion.getIdPublicacion());
        if (comentarios == null) {
            comentarios = new ArrayList<Comentario>();
        }
        boolean hayComentarios = (comentarios.size() == 0) ? false : true;
        modelAndView.addObject("publicacionComp", pc);
        modelAndView.addObject("publicacion", publicacion);
        modelAndView.addObject("comentarios", comentarios);
        modelAndView.addObject("hayComentarios", hayComentarios);
        return modelAndView;
    }

    /* El usuario hace una publicación. */
    @RequestMapping(value = "/hacer_publicacion", method = RequestMethod.GET)
    public String hacerPublicacion () {

    }

    /* El usuario publica la publicación que acaba de hacer. */
    @RequestMapping(value = "/hacer_publicacion/{idPublicacion}", method = RequestMethod.GET)
    public String publicar () {

    }

    /* El usuario actual ve todas las canciones del sistema*/
    @RequestMapping(value = "/hacer_publicacion/{idPublicacion}/canciones", method = RequestMethod.GET)
    public ModelAndView verCanciones (@PathVariable("idPublicacion") String idPublicacion) {
        ModelAndView modelAndView = new ModelAndView("SeleccionarCancion");
        ArrayList<Cancion> canciones = cancionService.getCanciones();
        
    }

    /* El usuario actual selecciona la canción que quiere publicar. */
    @RequestMapping(value = "/hacer_publicacion/{idPublicacion}/canciones/{nombreCancion}", method = RequestMethod.GET)
    public String seleccionarCancion () {

    }

    /* Se elimina una publicación del usuario actual. */
    @RequestMapping(value = "/publicaciones/eliminar/{idPublicacion}", method = RequestMethod.GET)
    public String eliminarPublicacion (@PathVariable("idPublicacion") String idPublicacion) {
        int id = Integer.parseInt(idPublicacion);
        Publicacion eliminada = publicacionService.obtenerPublicacionId(id);
        publicacionesUsuario.remove(eliminada);
        publicaciones.remove(eliminada);
        try{
            publicacionService.actualizarPublicaciones(publicaciones);
        } catch (Exception e) {}
        return "redirect:/inicio/publicaciones";
    }

    /* Vista para actualizar una publicacion realizada por el usuario. */
    @RequestMapping(value = "/publicaciones/actualizar/{idPublicacion}", method = RequestMethod.GET)
    public ModelAndView actualizarPropia(){
        ModelAndView modelAndView = new ModelAndView ("ActualizarPropia");
        int id = Integer.parseInt(idPublicacion);
        Publicacion publicacion = publicacionService.obtenerPublicacionId(id);
        modelAndView.addObject("publicacion", publicacion);
        modelAndView.addObject("aceptar", "/publicaciones/actualizar/{idPublicacion}/editar_datos");
    return modelAndView;
    }

    /* Se actualiza una publicación del usuario actual. */
    @RequestMapping(value = "/publicaciones/actualizar/{idPublicacion}/editar_datos", method = RequestMethod.GET)
    public String actualizarPublicacionPropia (@PathVariable("idPublicacion") String idPublicacion,
                                               @RequestParam("decripcion") String decripcion,
                                               @RequestParam("cancion") String cancion,  
                                               @AuthenticationPrincipal PersonaWrapper persona) {
        Persona actual = persona.getPersona();
        int id = Integer.parseInt(idPublicacion);
        Publicacion publicacion = new Publicacion(id, descripcion, actual.getUsuario());
        Cancion cancionNueva = cancionService.obtenerCancion(cancion);
        publicacion.setCancionPublicacion(cancionNueva);
        publicacionService.actualizar(publicacion);
        return "redirect:/inicio/publicaciones/{idPublicacion}";               
    }

    /* Se elimina una publicación del usuario actual. */
    @RequestMapping(value = "/publicaciones_compartidas/eliminar/{idPublicacion}/{idPublicacionC}", method = RequestMethod.GET)
    public String eliminarPublicacionCompartida (@PathVariable("idPublicacion") String idPublicacion,
                                                 @PathVariable("idPublicacionC") String idPublicacionC,  
                                                 @AuthenticationPrincipal PersonaWrapper persona) {
        Persona actual = persona.getPersona();
        int id = Integer.parseInt(idPublicacion);
        int idComp = Integer.parseInt(idPublicacionC);
        PublicacionCompartidaID id_publicacionComp = new PublicacionCompartidaID(id, idComp);
        PublicacionCompartida eliminada = publicacionService.obtenerPCId(id_publicacionComp);
        ArrayList<PublicacionCompartida> pc = pcService.obtenerPCPublicacion(id);    
        pc.remove(eliminada);
        try{
            publicacionService.actualizarPC(pc, id);
        } catch (Exception e) {}
        return "redirect:/inicio/publicaciones_compartidas";
    }

    /* Vista para actualizar una publicacion compartida por el usuario. */
    @RequestMapping(value = "/publicaciones_compartidas/actualizar/{idPublicacion}/{idPublicacionC}", method = RequestMethod.GET)
    public ModelAndView actualizarCompartida(){
        ModelAndView modelAndView = new ModelAndView ("ActualizarCompartida");
        int id = Integer.parseInt(idPublicacion);
        int idComp = Integer.parseInt(idPublicacionC);
        PublicacionCompartidaID id_publicacionComp = new PublicacionCompartidaID(id, idComp);
        PublicacionCompartida publicacionComp = pcService.obtenerPCId(id_publicacionComp);
        modelAndView.addObject("publicacionComp", publicacionComp);
        modelAndView.addObject("aceptar", "/publicaciones_compartidas/actualizar/{idPublicacion}/{idPublicacionC}/editar_datos");
    return modelAndView;
    }

     /* Se actualiza una publicación compartida del usuario actual. */
    @RequestMapping(value = "/publicaciones_compartidas/actualizar/{idPublicacion}/{idPublicacionC}/editar_datos", method = RequestMethod.GET)
    public String actualizarPublicacionCompartida (@PathVariable("idPublicacion") String idPublicacion,
                                                   @PathVariable("idPublicacionC") String idPublicacionC,  
                                                   @RequestParam("descripcion") String descripcion,
                                                   @AuthenticationPrincipal PersonaWrapper persona) {
        Persona actual = persona.getPersona();
        int id = Integer.parseInt(idPublicacion);
        int idComp = Integer.parseInt(idPublicacionC);
        PublicacionCompartidaID id_publicacionComp = new PublicacionCompartidaID(id, idComp);
        PublicacionCompartida pc = new PublicacionCompartida(id_publicacionComp);
        pc.setUsuarioPC(actual.getUsuario());
        pc.setDescripcion(descripcion);
        pcService.actualizar(pc);
        return "redirect:/inicio/publicaciones_compartidas";
    }

    /* Vista para compartir una publicacion. */
    @RequestMapping(value = "/compartir/{idPublicacion}", method = RequestMethod.GET)
    public  ModelAndView compartirPub (@PathVariable("idPublicacion") String idPublicacion, @AuthenticationPrincipal PersonaWrapper persona) {
        ModelAndView modelAndView = new ModelAndView ("CompartirPublicacion");
        int id = Integer.parseInt(idPublicacion);
        Publicacion publicacion = publicacionService.obtenerPublicacionId(id);
        modelAndView.addObject("publicacion", publicacion);
        modelAndView.addObject("aceptar", "/compartir/{idPublicacion}/hacer_publicacion");
        return modelAndView;
    }

    /* El usuario actual comparte una publicacion. */
    @RequestMapping(value = "/compartir/{idPublicacion}/hacer_publicacion", method = RequestMethod.GET)
    public String compartirPublicacion (@PathVariable("idPublicacion") String idPublicacion, 
                                        @RequestParam("descripcion") String descripcion, 
                                        @AuthenticationPrincipal PersonaWrapper persona) {
        Persona actual = persona.getPersona();
        int id = Integer.parseInt(idPublicacion);
        ArrayList<PublicacionCompartida> publicacionesCom = pcService.obtenerPCPublicacion(idPublicacion);
        int longitud = publicacionesCom.size() + 1;
        PublicacionCompartidaID id_publicacion = new PublicacionCompartidaID(id, longitud);
        PublicacionCompartida publicacionComp = new PublicacionCompartida(id_publicacion);
        pc.setUsuarioPC(actual.getUsuario());
        publicacionComp.setDescripcion(descripcion);
        pcService.guardar(publicacionComp);
        return "redirect:/inicio/publicaciones_compartidas";
    }

    /* El usuario actual comenta en una publicación. */
    @RequestMapping(value = "/comentar/{idPublicacion}", method = RequestMethod.GET)
    public String comentarPublicacion (@PathVariable("idPublicacion") String idPublicacion, 
                                       @RequestParam("comentario") String comentario, 
                                       @AuthenticationPrincipal PersonaWrapper persona) {
        Persona actual = persona.getPersona();
        int id = Integer.parseInt(idPublicacion);
        Publicacion publicacion = publicacionService.obtenerPublicacionId(id);
        ArrayList<Comentario> comentarios = comentarioService.obtenerComentariosPublicacion(id);
        int longitud = comentarios.size() + 1; 
        ComentarioID idComentario = new ComentarioID(id, longitud);
        Comentario c = new Comentario(idComentario);
        c.setComentario(comentario);
        c.setPublicacionC(publicacion);
        c.setUsuarioComentario(actual.getUsuario());
        comentarioService.guardar(c);
        return "redirect:/inicio/ver/{idPublicacion}";                        
    }
 
    /* El usuario actual comenta en una publicación propia. */
    @RequestMapping(value = "/publicaciones/comentar/{idPublicacion}", method = RequestMethod.GET)
    public String comentarPublicacionU (@PathVariable("idPublicacion") String idPublicacion, 
                                        @RequestParam("comentario") String comentario, 
                                        @AuthenticationPrincipal PersonaWrapper persona) {
        Persona actual = persona.getPersona();
        int id = Integer.parseInt(idPublicacion);
        Publicacion publicacion = publicacionService.obtenerPublicacionId(id);
        ArrayList<Comentario> comentarios = comentarioService.obtenerComentariosPublicacion(id);
        int longitud = comentarios.size() + 1; 
        ComentarioID idComentario = new ComentarioID(id, longitud);
        Comentario c = new Comentario(idComentario);
        c.setComentario(comentario);
        c.setPublicacionC(publicacion);
        c.setUsuarioComentario(actual.getUsuario());
        comentarioService.guardar(c);
        return "redirect:/inicio/publicaciones/{idPublicacion}";                                      
    }
 
    /* El usuario actual comenta en una publicación compartida. */
    @RequestMapping(value = "/publicaciones_compartidas/comentar/{idPublicacion}", method = RequestMethod.GET)
    public String comentarPublicacionComp (@PathVariable("idPublicacion") String idPublicacion, 
                                           @RequestParam("descripcion") String descripcion, 
                                           @AuthenticationPrincipal PersonaWrapper persona) {
        Persona actual = persona.getPersona();
        int id = Integer.parseInt(idPublicacion);
        Publicacion publicacion = publicacionService.obtenerPublicacionId(id);
        ArrayList<Comentario> comentarios = comentarioService.obtenerComentariosPublicacion(id);
        int longitud = comentarios.size() + 1; 
        ComentarioID idComentario = new ComentarioID(id, longitud);
        Comentario c = new Comentario(idComentario);
        c.setComentario(comentario);
        c.setPublicacionC(publicacion);
        c.setUsuarioComentario(actual.getUsuario());
        comentarioService.guardar(c);
        return "redirect:/inicio/publicaciones_compartidas/{idPublicacion}"; 
    }

    /* Eliminar comentario en una publicación. */
    @RequestMapping(value = "/ver/{idPublicacion}/{idComentario}", method = RequestMethod.GET)
    public String eliminarComentario (@PathVariable("idPublicacion") String idPublicacion, 
                                      @RequestParam("idComentario") String idComentario, 
                                      @AuthenticationPrincipal PersonaWrapper persona) {
        Persona actual = persona.getPersona();
        int id_publicacion = Integer.parseInt(idPublicacion); 
        int id_comentario = Integer.parseInt(idComentario);
        ComentarioID idCom = new ComentarioID(id_publicacion, id_comentario);
        Comentario comentario = comentarioService.obtenerComentarioId(idCom);
        ArrayList<Comentario> comentarios = comentarioService.obtenerComentariosPublicacion(id_publicacion);
        comentarios.remove(comentario);
        try{
            comentarioService.actualizarComentarios(comentarios, id_publicacion);
        } catch (Exception e) {}
        return "redirect:/inicio/ver/{idPublicacion}";
    }

    /* Eliminar comentario en una publicación propia. */
    @RequestMapping(value = "/publicaciones/{idPublicacion}/{idComentario}", method = RequestMethod.GET)
    public String eliminarComentarioPropio (@PathVariable("idPublicacion") String idPublicacion, 
                                            @RequestParam("idComentario") String idComentario, 
                                            @AuthenticationPrincipal PersonaWrapper persona) {
        Persona actual = persona.getPersona();
        int id_publicacion = Integer.parseInt(idPublicacion); 
        int id_comentario = Integer.parseInt(idComentario); 
        ComentarioID idCom = new ComentarioID(id_publicacion, id_comentario);
        Comentario comentario = comentarioService.obtenerComentarioId(idCom);
        ArrayList<Comentario> comentarios = comentarioService.obtenerComentariosPublicacion(id_publicacion);
        comentarios.remove(comentario); 
        try{
            comentarioService.actualizarComentarios(comentarios, id_publicacion);
        } catch (Exception e) {}
        return "redirect:/inicio/publicaciones/{idPublicacion}";                                     
    }

    /* Eliminar comentario en una publicación comentario. */
    @RequestMapping(value = "/publicaciones_compartidas/{idPublicacion}/{idComentario}", method = RequestMethod.GET)
    public String eliminarComentarioCompartida (@PathVariable("idPublicacion") String idPublicacion, 
                                                @RequestParam("idComentario") String idComentario, 
                                                @AuthenticationPrincipal PersonaWrapper persona) {
        Persona actual = persona.getPersona();
        int id_publicacion = Integer.parseInt(idPublicacion); 
        int id_comentario = Integer.parseInt(idComentario);
        ComentarioID idCom = new ComentarioID(id_publicacion, id_comentario);
        Comentario comentario = comentarioService.obtenerComentarioId(idCom);
        ArrayList<Comentario> comentarios = comentarioService.obtenerComentariosPublicacion(id_publicacion);
        comentarios.remove(comentario);
        try{
            comentarioService.actualizarComentarios(comentarios, id_publicacion);
        } catch (Exception e) {}
        return "redirect:/inicio/publicaciones_compartidas/{idPublicacion}";
    }

    /* Obtiene todas las publicaciones. */
    private void obtPublicaciones() {
        publicaciones = publicacionService.obtenerPublicaciones();
        if (publicaciones == null) {
            publicaciones = new ArrayList<Publicacion>();
        }
    }

    /* Obtiene todas las publicaciones realizadas por el usuario. */
    private void obtienePU(Persona persona){
        publicacionesUsuario = publicacionService.obtenerPublicacionesCorreo(persona.getPersona());
        if (publicacionesUsuario == null){
            publicacionesUsuario = new ArrayList<Publicacion>();
        }
    }

     /* Obtiene todas las publicaciones compartidas por el usuario. */
    private void obtienePC(Persona persona){
        publicacionesCompartidas = pcService.obtenerPCCorreo(persona.getPersona());
        if (publicacionesCompartidas == null){
            publicacionesCompartidas = new ArrayList<PublicacionCompartida>();
        }
    }

    /* A cada publicación le asigna sus comentarios. */
    private void asignarComentarios() {
        if (publicaciones.size() != 0) {
            for (Publicacion p : publicaciones) {
                List<Comentario> comentarios = new ArrayList<Comentario>();
                comentarios = comentarioService.obtenerComentariosPublicacion(p.getIdPublicacion());
                p.setComentarios(comentarios);
            }
        }
    }

    /* A cada publicación le asigna sus publicaciones compartidas. */
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