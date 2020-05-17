package com.ivrotef.slashmusic.config;

import javax.annotation.PostConstruct;

import com.ivrotef.slashmusic.controller.AdministradorRepository;
import com.ivrotef.slashmusic.controller.PersonaService;
import com.ivrotef.slashmusic.model.Administrador;
import com.ivrotef.slashmusic.model.Persona;

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
  private PersonaService personaService;

  @PostConstruct
  public void init() {

    // Administrador
    Persona persona = new Persona("admin@ivrotef.io", encoder.encode("admin123"), "administrador");
    Administrador admin = new Administrador (persona);
    persona.setAdministrador(admin);
    administradorRepository.save(admin);

  }
}
