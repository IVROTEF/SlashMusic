package com.ivrotef.slashmusic.controller;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ivrotef.slashmusic.model.Comentario;
import com.ivrotef.slashmusic.model.ComentarioID;

@Repository
public interface ComentarioRepository extends CrudRepository<Comentario, ComentarioID>{}