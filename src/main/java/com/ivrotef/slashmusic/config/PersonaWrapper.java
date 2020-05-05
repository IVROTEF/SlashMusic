package com.ivrotef.slashmusic.config;

import com.ivrotef.slashmusic.model.Persona;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class PersonaWrapper extends User {
  private static final long serialVersionUID = 1L;

  private Persona persona;

  public PersonaWrapper(Persona persona, String nombre,
  String password, Collection<? extends GrantedAuthority> authorities) {
    super(nombre, password, authorities);
    this.persona = persona;
  }

  public Persona getPersona (){
    return this.persona;
  }

  public void setPersona (Persona persona) {
    this.persona = persona;
  }

}
