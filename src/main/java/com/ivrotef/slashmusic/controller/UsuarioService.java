package com.ivrotef.slashmusic.controller;

import com.ivrotef.slashmusic.model.Usuario;
import com.ivrotef.slashmusic.controller.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UsuarioService {

  @Autowired
  private UsuarioRepository repository;

  public void guardar (Usuario usuario) {
    repository.save(usuario);
  }

}
