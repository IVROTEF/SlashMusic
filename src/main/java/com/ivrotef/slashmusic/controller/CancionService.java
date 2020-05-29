package com.ivrotef.slashmusic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

import java.sql.SQLIntegrityConstraintViolationException;

import com.ivrotef.slashmusic.controller.CancionRepository;
import com.ivrotef.slashmusic.model.Cancion;

@Service
public class CancionService {

  @PersistenceContext
  EntityManager entityManager;

  @Autowired
  private CancionRepository repository;

  public ArrayList<Cancion> getCanciones() {
    ArrayList<Cancion> canciones = (ArrayList<Cancion>) repository.findAll();
    return canciones;
  }

  public Cancion obtenerCancion(String id) {
    Optional<Cancion> cancion =  repository.findById(id);
    if (cancion.isPresent()) {
      return cancion.get();
    }
    return null;
  }

  public Cancion obtenerCancionArchivo (String ruta) {
    Cancion cancion =  repository.findByArchivo(ruta);
    return cancion;
  }

  public ArrayList<Cancion> getCancionesSimilares (String id) {
    Query query = entityManager.createQuery("FROM Cancion c WHERE c.nombre LIKE :nombre", Cancion.class);
    query.setParameter("nombre", "%"+id+"%");
    ArrayList<Cancion> canciones = (ArrayList<Cancion>) query.getResultList();
    if (canciones.size() == 0) {
      return null;
    }
    return canciones;
  }

  public Cancion actualizar (Cancion cancion) {
    Optional<Cancion> l = repository.findById(cancion.getNombre());
    Cancion t = null;
    if (l.isPresent()) {
      t = l.get();
      t.setNombre(cancion.getNombre());
      t.setArchivo(cancion.getArchivo());
      t.setAutor(cancion.getAutor());
      t.setListas(cancion.getListas());
      //t.setComentarios(cancion.getComentarios());
      t.setSeguidores(cancion.getSeguidores());
      t.setPropietarios(cancion.getPropietarios());
     // t.setDistribuidores(cancion.getDistribuidores());
      t.setAutores(cancion.getAutores());
      try {
        t = repository.save(t);
      } catch (Exception e) { e.printStackTrace();}
    }
    return t;
  }

  public void guardar (Cancion cancion) {
    repository.save(cancion);
  }

  public void eliminar (Cancion cancion) {
    

    repository.deleteById (cancion.getNombre());
  }

}
