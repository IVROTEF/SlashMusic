package com.ivrotef.slashmusic.controller;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ivrotef.slashmusic.model.Cancion;

@Repository
public interface CancionRepository extends CrudRepository<Cancion, String>{
  Cancion findByArchivo (String ruta);
}
