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

}
