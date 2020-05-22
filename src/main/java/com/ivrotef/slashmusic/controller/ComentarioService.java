package com.ivrotef.slashmusic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.Optional;
import java.util.ArrayList;

import java.sql.SQLIntegrityConstraintViolationException;

import com.ivrotef.slashmusic.controller.ComentarioRepository;
import com.ivrotef.slashmusic.model.Comentario;
import com.ivrotef.slashmusic.model.ComentarioID;

public class ComentarioService {
    
    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    private ComentarioRepository repository;

    /* Obtiene todos los comentarios por usuario identificado por el correo. */
    public ArrayList<Comentario> obtenerComentariosCorreo (String correo_usuario) {
        Query query = entityManager.createQuery("FROM Comentario c WHERE c.usuario =: correo", Comentario.class);
        query.setParameter("correo", correo_usuario);
        ArrayList<Comentario> comentarios = (ArrayList<Comentario>) query.getResultList();
        if (comentarios.size() == 0) {
          return null;
        }
        return comentarios;
    }

    /* Obtiene todos los comentarios por publicación indentificada por el id_publicacion. */
    public ArrayList<Comentario> obtenerComentariosPublicacion (int id_publicacion) {
        Query query = entityManager.createQuery("FROM Comentario c WHERE c.id_publicacion =: id", Comentario.class);
        query.setParameter("id", id_publicacion);
        ArrayList<Comentario> comentarios = (ArrayList<Comentario>) query.getResultList();
        if (comentarios.size() == 0) {
          return null;
        }
        return comentarios;
    }

    /* Obtener comentario por id. */
    public Comentario obtenerComentarioId (ComentarioID id) {
        Optional<Comentario> comentario = repository.findById(id);
        if (comentario.isPresent()) {
          return comentario.get();
        }
        return null;
    }

     /* Guarda el comentario en la base de datos. */
    public Comentario guardar (Comentario comentario) {
        Comentario c = repository.save(comentario);
        return c;
    }
  
      /* Elimina el comentario si se encuentra en la base de datos. */
    public void eliminar (Comentario comentario) {  
        Optional<Comentario> c = repository.findById(comentario.getComentarioID());
        if (c.isPresent()) {
          repository.deleteById(comentario.getComentarioID());
        }
    }
  
      /* Si el comentario se encuentra en la base de datos lo actualiza. */
    public Comentario actualizar (Comentario comentario) throws SQLIntegrityConstraintViolationException {
        Optional<Comentario> c1 = repository.findById(comentario.getComentarioID());
        Comentario c2 = null;
        if (c1.isPresent()) {
          c2 = c1.get();
          c2.setComentarioID(comentario.getComentarioID());
          c2.setComentario(comentario.getComentario());
          c2.setUsuarioComentario(comentario.getUsuarioComentario());
          try {
            c2 = repository.save(c2);
          } catch (Exception e) { e.printStackTrace(); }
        }
        return c2;
    }
}