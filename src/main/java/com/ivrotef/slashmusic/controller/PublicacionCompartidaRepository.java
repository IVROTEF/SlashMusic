package com.ivrotef.slashmusic.controller;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ivrotef.slashmusic.model.PublicacionCompartida;
import com.ivrotef.slashmusic.model.PublicacionCompartidaID;

@Repository
public interface PublicacionCompartidaRepository extends CrudRepository<PublicacionCompartida, PublicacionCompartidaID>{}