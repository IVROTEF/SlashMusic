package com.ivrotef.slashmusic.controller;

import com.ivrotef.slashmusic.model.Usuario;
import com.ivrotef.slashmusic.model.Cancion;
import com.ivrotef.slashmusic.model.Persona;
import com.ivrotef.slashmusic.controller.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.Optional;

@Service
public class UsuarioService {

  @Autowired
  private UsuarioRepository repository;


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


}
