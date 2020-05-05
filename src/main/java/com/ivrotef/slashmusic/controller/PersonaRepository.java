package com.ivrotef.slashmusic.controller;

import com.ivrotef.slashmusic.model.Persona;

import org.springframework.data.repository.CrudRepository;

public interface PersonaRepository extends CrudRepository<Persona, String>{
  public Persona findByNombre(String nombre);
  public Persona findByCorreo(String correo);  
}
