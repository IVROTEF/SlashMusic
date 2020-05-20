package com.ivrotef.slashmusic.config;

import javax.annotation.PostConstruct;

import com.ivrotef.slashmusic.controller.AdministradorRepository;
import com.ivrotef.slashmusic.controller.UsuarioRepository;
import com.ivrotef.slashmusic.controller.PersonaService;
import com.ivrotef.slashmusic.model.Administrador;
import com.ivrotef.slashmusic.model.Persona;
import com.ivrotef.slashmusic.model.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Init {

  @Autowired
  private PasswordEncoder encoder;

  @Autowired
  private AdministradorRepository administradorRepository;

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Autowired
  private PersonaService personaService;

  @PostConstruct
  public void init() {

    // Administrador
    Persona persona = new Persona("admin@ivrotef.io", encoder.encode("admin123"), "administrador");
    Administrador admin = new Administrador (persona);
    persona.setAdministrador(admin);
    administradorRepository.save(admin);

    // Usuarios
    Persona persona1 = new Persona("example@email", encoder.encode("12345678"), "exampleuser");
    Usuario usuario1 = new Usuario (persona1);
    persona1.setUsuario(usuario1);
    usuarioRepository.save(usuario1);
  }
}
