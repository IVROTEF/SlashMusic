package com.ivrotef.slashmusic.controller;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ivrotef.slashmusic.model.Artista;

@Repository
public interface ArtistaRepository extends CrudRepository<Artista, String>{}
