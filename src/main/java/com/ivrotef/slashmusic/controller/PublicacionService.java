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

import com.ivrotef.slashmusic.controller.PublicacionRepository;
import com.ivrotef.slashmusic.model.Publicacion;

public class PublicacionService {
    
    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    private PublicacionRepository repository;

    /* Obtiene todas las publicaciones de la base de datos. */
    public ArrayList<Publicacion> obtenerPublicaciones () {
      ArrayList<Publicacion> publicaciones = (ArrayList<Publicacion>) repository.findAll();
      return publicaciones;
    }

    /* Obtener publicación por id. */
    public Publicacion obtenerPublicacionId (int id_publicacion) {
        Integer id = new Integer(id_publicacion);
        Optional<Publicacion> publicacion = repository.findById(id);
        if (publicacion.isPresent()) {
          return publicacion.get();
        }
        return null;
    }

    /* Obtiene todas las publicaciones del usuario identificado por el correo. */
    public ArrayList<Publicacion> obtenerPublicacionesCorreo (String correo_usuario) {
        Query query = entityManager.createQuery("FROM Publicacion p WHERE p.usuario =: correo", Publicacion.class);
        query.setParameter("correo", correo_usuario);
        ArrayList<Publicacion> publicaciones = (ArrayList<Publicacion>) query.getResultList();
        if (publicaciones.size() == 0) {
          return null;
        }
        return publicaciones;
    }

     /* Guarda la publicación en la base de datos. */
    public Publicacion guardar (Publicacion publicacion) {
        Publicacion p = repository.save(publicacion);
        return p;
    }
  
      /* Elimina la publicación si se encuentra en la base de datos. */
    public void eliminar (Publicacion publicacion) {
        Integer id = new Integer(publicacion.getIdPublicacion());  
        Optional<Publicacion> p = repository.findById(id);
        if (p.isPresent()) {
          repository.deleteById(id);
        }
    }
  
      /* Si la publicacion se encuentra en la base de datos la actualiza*/
    public Publicacion actualizar (Publicacion publicacion) throws SQLIntegrityConstraintViolationException {
        Integer id = new Integer(publicacion.getIdPublicacion()); 
        Optional<Publicacion> p1 = repository.findById(id);
        Publicacion p2 = null;
        if (p1.isPresent()) {
          p2 = p1.get();
          p2.setIdPublicacion(publicacion.getIdPublicacion());
          p2.setDescripcion(publicacion.getDescripcion());
          p2.setUsuarioPublicacion(publicacion.getUsuarioPublicacion());
          p2.setCancionPublicacion(publicacion.getCancionPublicacion());
          try {
            p2 = repository.save(p2);
          } catch (Exception e) { e.printStackTrace(); }
        }
        return p2;
    }

    /* Actualiza las publicaciones de la base de datos por las publicaciones recibidas
     * Borra todas las publicaciones y guarda las que el usuario no elimino.
     */
    public void actualizarPublicaciones(ArrayList<Publicacion> publicaciones) {
      ArrayList<Publicacion> publicacionesAnt = (ArrayList<Publicacion>) repository.findAll();
      repository.deleteAll(publicacionesAnt);
      repository.saveAll(publicaciones);
    }
}