package com.ivrotef.slashmusic.config;

import java.util.ArrayList;

import com.ivrotef.slashmusic.controller.PersonaRepository;
import com.ivrotef.slashmusic.model.Persona;
import com.ivrotef.slashmusic.config.PersonaWrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Service implementation to use custom users with the default Spring Security
 * configuration.
 */
@Service
public class UserConfiguration implements UserDetailsService{

  @Autowired
  private PersonaRepository dao;

  public UserDetails loadUserByUsername(String email) {
    Persona persona = dao.findByCorreo(email);
    if (persona == null) {
      throw new UsernameNotFoundException(email);
    }
    return buildPersona(persona);
  }

  private PersonaWrapper buildPersona(Persona persona) {
    String username = persona.getCorreo();
    String password = persona.getPassword();
    ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();

    if(persona.getAdministrador() != null){
      authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    if(persona.getUsuario() != null){
      authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
    }

    return new PersonaWrapper(persona, username, password, authorities);
  }
}
