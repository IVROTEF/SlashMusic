package com.ivrotef.slashmusic.controller;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ivrotef.slashmusic.model.Administrador;

@Repository
public interface AdministradorRepository extends CrudRepository<Administrador, String>{}
