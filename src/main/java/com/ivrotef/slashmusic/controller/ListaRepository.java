package com.ivrotef.slashmusic.controller;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ivrotef.slashmusic.model.Lista;
import com.ivrotef.slashmusic.model.ListaID;

@Repository
public interface ListaRepository extends CrudRepository<Lista, ListaID>{}
