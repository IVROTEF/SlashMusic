package com.ivrotef.slashmusic.controller;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ivrotef.slashmusic.model.Publicacion;

@Repository
public interface PublicacionRepository extends CrudRepository<Publicacion, Integer>{}
