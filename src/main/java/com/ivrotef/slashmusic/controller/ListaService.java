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

import com.ivrotef.slashmusic.controller.ListaRepository;
import com.ivrotef.slashmusic.model.Lista;
import com.ivrotef.slashmusic.model.ListaID;

@Service
public class ListaService {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    private ListaRepository repository;

    /* Obtiene todas las listas de la base de datos */
    public ArrayList<Lista> obtenerListas () {
      ArrayList<Lista> listas = (ArrayList<Lista>) repository.findAll();
      return listas;
    }

    /* Obtiene la lista por el id*/
    public Lista obtenerListaId (ListaID id) {
      Optional<Lista> lista = repository.findById(id);
      if (lista.isPresent()) {
        return lista.get();
      }
      return null;
    }

    /* Obtiene todas las listas del usuario identificado por el correo */
    public ArrayList<Lista> obtenerListasCorreo (String correo_usuario) {
      Query query = entityManager.createQuery("FROM Lista l WHERE l.listaID.usuario =: correo", Lista.class);
      query.setParameter("correo", correo_usuario);
      ArrayList<Lista> listas = (ArrayList<Lista>) query.getResultList();
      if (listas.size() == 0) {
        return null;
      }
      return listas;
    }

    /* Obtiene la lista del usuario identificado por el correo */
    public Lista obtenerListaNombre (String correo_usuario, String nombreLista){
      Query query = entityManager.createQuery("FROM Lista l WHERE l.listaID.usuario =: correo AND l.listaID.nombreLista =: lista", Lista.class);
      query.setParameter("correo", correo_usuario);
      query.setParameter("lista", nombreLista);
      ArrayList<Lista> listas = (ArrayList<Lista>) query.getResultList();
      if (listas.size() == 0) {
        return null;
      }
      return listas.get(0);
    }


    /* Guarda la lista en la base de datos */
    public Lista guardar (Lista lista) {
      Lista l = repository.save(lista);
      return l;
    }

    /* Elimina la lista si se encuentra en la base de datos */
    public void eliminar (Lista lista) {
      Optional<Lista> l = repository.findById(lista.getListaID());
      if (l.isPresent()) {
        repository.deleteById(lista.getListaID());
      }
    }


    /* Si la lista se encuentra en la base de datos la actualiza*/
    public Lista actualizar (Lista lista) throws SQLIntegrityConstraintViolationException {
      Optional<Lista> l = repository.findById(lista.getListaID());
      Lista t = null;
      if (l.isPresent()) {
        t = l.get();
        t.setListaID(lista.getListaID());
        t.setCanciones(lista.getCanciones());
        t = repository.save(t);
      }
      return t;
    }


    /* Actualiza las listas del usuario a las listas recibidas
    * Borra todas las listas y guarda las que el usuario no elimino
    */
    public void actualizar (ArrayList<Lista> listas) {
      repository.deleteAll();
      repository.saveAll(listas);
    }
}
