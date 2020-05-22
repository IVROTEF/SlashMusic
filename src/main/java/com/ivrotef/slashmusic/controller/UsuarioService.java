package com.ivrotef.slashmusic.controller;

import com.ivrotef.slashmusic.model.Usuario;
import com.ivrotef.slashmusic.model.Cancion;
import com.ivrotef.slashmusic.model.Persona;
import com.ivrotef.slashmusic.controller.UsuarioRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.Optional;
import java.util.ArrayList;

@Service
public class UsuarioService {

  @Autowired
  private UsuarioRepository repository;

  @PersistenceContext
  EntityManager entityManager;

  public Usuario obtenerUsuarioCorreo(String correo) {
    Optional<Usuario> usuario =  repository.findById(correo);
    if (usuario.isPresent()) {
      return usuario.get();
    }
    return null;
  }

  public void guardar (Usuario usuario) {
    repository.save(usuario);
  }

  public ArrayList<Usuario> getUsSimilares (String id) {
    Query query = entityManager.createQuery("FROM Usuario c WHERE c.persona.nombre LIKE :nombre", Usuario.class);
    query.setParameter("nombre", "%"+id+"%");
    ArrayList<Usuario> us = (ArrayList<Usuario>) query.getResultList();
    if (us.size() == 0) {
      return null;
    }
    return us;
  }


}
