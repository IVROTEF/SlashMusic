package com.ivrotef.slashmusic.controller;

import com.ivrotef.slashmusic.model.Persona;
import com.ivrotef.slashmusic.model.Usuario;
import com.ivrotef.slashmusic.controller.PersonaRepository;
import com.ivrotef.slashmusic.controller.UsuarioService;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Service
public class PersonaService {

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PersistenceContext
    EntityManager entityManager;


    public Persona findByCorreo (String email){
      return personaRepository.findByCorreo(email);
    }

    public Persona findByNombre (String nombre) {
      return personaRepository.findByNombre(nombre);
    }

    public void nuevoUsuario (Persona persona) {
      Usuario usuario = new Usuario();
      usuario.setCorreo(persona.getCorreo());
      usuario.setPersona(persona);
      persona.setUsuario(usuario);
      usuarioService.guardar(usuario);
    }

    public void guardar (Persona persona){
      persona.setPassword (bCryptPasswordEncoder.encode(persona.getPassword()));
      personaRepository.save(persona);
    }

 public ArrayList<Persona> getUsSimilares (String id) {
      Query query = entityManager.createQuery("FROM Persona c WHERE c.nombre LIKE :nombre", Persona.class);
      query.setParameter("nombre", "%"+id+"%");
      ArrayList<Persona> us = (ArrayList<Persona>) query.getResultList();
      /* Quita al administrador */
      for (int i = 0; i < us.size() ; i++) {
        if (us.get(i).getAdministrador() != null) {
          us.remove(i);
          break;
        }
      }
      if (us.size() == 0) {
        return null;
      }
      return us;
    }
}
