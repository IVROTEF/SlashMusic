package com.ivrotef.slashmusic.controller;

import com.ivrotef.slashmusic.model.Usuario;
import com.ivrotef.slashmusic.model.Artista;
import com.ivrotef.slashmusic.controller.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

import java.sql.SQLIntegrityConstraintViolationException;

import java.util.Set;

@Service
public class UsuarioService {
  
  @PersistenceContext
    EntityManager entityManager;

  @Autowired
  private UsuarioRepository repository;

  public void guardar (Usuario usuario) {
    repository.save(usuario);
  }

  /* Obtiener usuario por correo*/
  public Usuario obtenerUsuario (String correo) {
    Optional<Usuario> usuario = repository.findById(correo);
    if (usuario.isPresent()) {
      return usuario.get();
    }
    return null;
  }

  /* Obtener todos los usuarios de la base de datos. */
  public ArrayList<Usuario> obtenerUsuarios () {
    ArrayList<Usuario> usuarios = (ArrayList<Usuario>) repository.findAll();
    return usuarios;
  }

  /* Obtener los artistas favoritos por usuario identificado por el correo. */
  public List<Artista> obtenerArtistasFavoritos (String correo_usuario) {
    Usuario usuario = obtenerUsuario(correo_usuario);
    List<Artista> artistasFavoritos = usuario.getArtistasFavoritos();
    return artistasFavoritos;
  }

  /* Guardar artista favorito por usurio identificado por el correo. */
  public void guardarArtistaFav (Artista artista, String correo_usuario) {
    Usuario usuario = obtenerUsuario(correo_usuario);
    List<Artista> artistasFavoritos = usuario.getArtistasFavoritos();
    artistasFavoritos.add(artista);
    usuario.setArtistasFavoritos(artistasFavoritos);
    repository.save(usuario);
  }

  /* Eliminar artista favorito por usuario identificado por el correo. */
  public void eliminarArtistaFav (Artista artista, String correo_usuario) {
    Usuario usuario = obtenerUsuario(correo_usuario);
    List<Artista> artistasFavoritos = usuario.getArtistasFavoritos();
    if(artistasFavoritos.contains(artista)){
      artistasFavoritos.remove(artista);
    }
    usuario.setArtistasFavoritos(artistasFavoritos);
    repository.save(usuario);
  }
  
  /* Obtener los usuarios favoritos por usuario identificado por el correo. */
  public List<Usuario> obtenerUsuariosFavoritos (String correo_usuario) {
    Usuario usuario = obtenerUsuario(correo_usuario);
    List<Usuario> usuariosFavoritos = usuario.getUsuariosFavoritos();
    return usuariosFavoritos;
  }

  /* Guardar usuario favorito por usurio identificado por el correo. */
  public void guardarUsuarioFav (Usuario u, String correo_usuario) {
    Usuario usuario = obtenerUsuario(correo_usuario);
    List<Usuario> usuariosFavoritos = usuario.getUsuariosFavoritos();
    usuariosFavoritos.add(u);
    usuario.setUsuariosFavoritos(usuariosFavoritos);
    repository.save(usuario);
  }

  /* Eliminar usuario favorito por usuario identificado por el correo. */
  public void eliminarUsuarioFav (Usuario u, String correo_usuario) {
    Usuario usuario = obtenerUsuario(correo_usuario);
    List<Usuario> usuariosFavoritos = usuario.getUsuariosFavoritos();
    if(usuariosFavoritos.contains(u)){
      usuariosFavoritos.remove(u);
    }
    usuario.setUsuariosFavoritos(usuariosFavoritos);
    repository.save(usuario);
  }

}
