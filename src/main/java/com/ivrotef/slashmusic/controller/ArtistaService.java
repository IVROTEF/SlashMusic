package com.ivrotef.slashmusic.controller;

import com.ivrotef.slashmusic.controller.ArtistaRepository;
import com.ivrotef.slashmusic.model.Artista;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.ArrayList;

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
import com.ivrotef.slashmusic.model.Artista;


@Service
public class ArtistaService {

    @Autowired
    ArtistaRepository repository;
    @PersistenceContext
    EntityManager entityManager;


    public Artista obtenerArtistaNombre (String nombre) {
        Optional<Artista> artista =  repository.findById(nombre);
        if (artista.isPresent()) {
          return artista.get();
        }
        return null;
    }

    public ArrayList<Artista> getArtistas () {
      ArrayList<Artista> artistas = (ArrayList<Artista>) repository.findAll();
      return artistas;
    }

     public ArrayList<Artista> getArtSimilares (String id) {
      Query query = entityManager.createQuery("FROM Artista c WHERE c.nombre LIKE :nombre", Artista.class);
      query.setParameter("nombre", "%"+id+"%");
      ArrayList<Artista> canciones = (ArrayList<Artista>) query.getResultList();
      if (canciones.size() == 0) {
        return null;
      }
      return canciones;
    }

    public void eliminar (Artista d) {
      repository.deleteById(d.getNombre());
    }

    public void guardar (Artista artista) {
      repository.save(artista);
    }

}
