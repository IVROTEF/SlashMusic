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

import com.ivrotef.slashmusic.controller.PublicacionCompartidaRepository;
import com.ivrotef.slashmusic.model.PublicacionCompartidaID;
import com.ivrotef.slashmusic.model.PublicacionCompartida;

@Service
public class PublicacionCompartidaService {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    private PublicacionCompartidaRepository repository;

    /* Obtiene todas las publicaciones compartidas por usuario identificado por el correo. */
    public ArrayList<PublicacionCompartida> obtenerPCCorreo (String correo_usuario) {
        Query query = entityManager.createQuery("FROM Publicacion_Compartida p WHERE p.usuarioPC.correo =: correo", PublicacionCompartida.class);
        query.setParameter("correo", correo_usuario);
        ArrayList<PublicacionCompartida> publicaciones = (ArrayList<PublicacionCompartida>) query.getResultList();
        if (publicaciones.size() == 0) {
          return null;
        }
        return publicaciones;
    }

    /* Obtiene todas las publicaciones compartidas de una publicacion identificada por el id_publicación. */
    public ArrayList<PublicacionCompartida> obtenerPCPublicacion (int id_publicacion) {
        Query query = entityManager.createQuery("FROM Publicacion_Compartida p WHERE p.publicacionCompartidaID.idPublicacion =: id", PublicacionCompartida.class);
        query.setParameter("id", id_publicacion);
        ArrayList<PublicacionCompartida> publicaciones = (ArrayList<PublicacionCompartida>) query.getResultList();
        if (publicaciones.size() == 0) {
          return null;
        }
        return publicaciones;
    }

    /* Obtiene todas las publicaciones de la base de datos */
    public ArrayList<PublicacionCompartida> obtenerPublicacionesCompartidas () {
        ArrayList<PublicacionCompartida> publicacionesCompartidas = (ArrayList<PublicacionCompartida>) repository.findAll();
        return publicacionesCompartidas;
    }


    /* Obtener publicacion compartida por id. */
    public PublicacionCompartida obtenerPCId (PublicacionCompartidaID id) {
        Optional<PublicacionCompartida> publicacion = repository.findById(id);
        if (publicacion.isPresent()) {
          return publicacion.get();
        }
        return null;
    }

     /* Guarda la publicacion compartida en la base de datos. */
    public PublicacionCompartida guardar(PublicacionCompartida publicacionCompartida) {
        PublicacionCompartida p = repository.save(publicacionCompartida);
        return p;
    }

      /* Elimina la publicacion compartida si se encuentra en la base de datos. */
    public void eliminar (PublicacionCompartida publicacionCompartida) {
        Optional<PublicacionCompartida> p = repository.findById(publicacionCompartida.getPublicacionCompartidaID());
        if (p.isPresent()) {
          repository.deleteById(publicacionCompartida.getPublicacionCompartidaID());
        }
    }

      /* Si la publicacion compartida se encuentra en la base de datos lo actualiza. */
    public PublicacionCompartida actualizar (PublicacionCompartida publicacionCompartida) throws SQLIntegrityConstraintViolationException {
        Optional<PublicacionCompartida> p1 = repository.findById(publicacionCompartida.getPublicacionCompartidaID());
        PublicacionCompartida p2 = null;
        if (p1.isPresent()) {
          p2 = p1.get();
          p2.setPublicacionCompartidaID(publicacionCompartida.getPublicacionCompartidaID());
          p2.setDescripcion(publicacionCompartida.getDescripcion());
          p2.setUsuarioPC(publicacionCompartida.getUsuarioPC());
          try {
            p2 = repository.save(p2);
          } catch (Exception e) { e.printStackTrace(); }
        }
        return p2;
    }

    /* Actualiza las publicaciones compartidas de una publicación por las publicaciones compartidas recibidas
     * Borra todos las publicaciones compartidas y guarda las que el usuario no elimino.
     */
    public void actualizarPublicacionComp(ArrayList<PublicacionCompartida> publicacionComp, int id_publicacion) {
      Query query = entityManager.createQuery("FROM Publicacion_Compartida p WHERE p.publicacionCompartidaID.idPublicacion =: id", PublicacionCompartida.class);
      query.setParameter("id", id_publicacion);
      ArrayList<PublicacionCompartida> publicacionesAnt = (ArrayList<PublicacionCompartida>) query.getResultList();
      repository.deleteAll(publicacionesAnt);
      repository.saveAll(publicacionComp);
    }
}
