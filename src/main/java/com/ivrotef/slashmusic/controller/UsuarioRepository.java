package com.ivrotef.slashmusic.controller;

import com.ivrotef.slashmusic.model.Usuario;

import org.springframework.data.repository.CrudRepository;

public interface UsuarioRepository extends CrudRepository<Usuario, String>{
}
