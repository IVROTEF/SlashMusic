package com.ivrotef.slashmusic.controller;

import com.ivrotef.slashmusic.model.Usuario;
import com.ivrotef.slashmusic.model.Cancion;
import com.ivrotef.slashmusic.model.Persona;
import com.ivrotef.slashmusic.model.Artista;
import com.ivrotef.slashmusic.controller.UsuarioRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.Set;
import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService {

  @PersistenceContext
  EntityManager entityManager;

  @Autowired
  private UsuarioRepository repository;

  public Usuario obtenerUsuarioCorreo(String correo) {
    Optional<Usuario> usuario =  repository.findById(correo);
    if (usuario.isPresent()) {
      return usuario.get();
    }
    return null;
  }

  public void guardar (Usuario usuario) {
    repository.save(usuario);
  }

  public ArrayList<Usuario> getUsSimilares (String id) {
    Query query = entityManager.createQuery("FROM Usuario c WHERE c.persona.nombre LIKE :nombre", Usuario.class);
    query.setParameter("nombre", "%"+id+"%");
    ArrayList<Usuario> us = (ArrayList<Usuario>) query.getResultList();
    if (us.size() == 0) {
      return null;
    }
    return us;
  }


  /* Obtener los artistas favoritos por usuario identificado por el correo. */
  public List<Artista> obtenerArtistasFavoritos (String correo_usuario) {
    Usuario usuario = obtenerUsuarioCorreo(correo_usuario);
    List<Artista> artistasFavoritos = usuario.getArtistasFavoritos();
    return artistasFavoritos;
  }

  /* Guardar artista favorito por usurio identificado por el correo. */
  public void guardarArtistaFav (Artista artista, String correo_usuario) {
    Usuario usuario = obtenerUsuarioCorreo(correo_usuario);
    List<Artista> artistasFavoritos = usuario.getArtistasFavoritos();
    artistasFavoritos.add(artista);
    usuario.setArtistasFavoritos(artistasFavoritos);
    repository.save(usuario);
  }

  /* Eliminar artista favorito por usuario identificado por el correo. */
  public void eliminarArtistaFav (Artista artista, String correo_usuario) {
    Usuario usuario = obtenerUsuarioCorreo(correo_usuario);
    List<Artista> artistasFavoritos = usuario.getArtistasFavoritos();
    if(artistasFavoritos.contains(artista)){
      artistasFavoritos.remove(artista);
    }
    usuario.setArtistasFavoritos(artistasFavoritos);
    repository.save(usuario);
  }

  /* Obtener los usuarios favoritos por usuario identificado por el correo. */
  public List<Usuario> obtenerUsuariosFavoritos (String correo_usuario) {
    Usuario usuario = obtenerUsuarioCorreo(correo_usuario);
    List<Usuario> usuariosFavoritos = usuario.getUsuariosFavoritos();
    return usuariosFavoritos;
  }


  /* Guardar usuario favorito por usurio identificado por el correo. */
  public void guardarUsuarioFav (Usuario u, String correo_usuario) {
    Usuario usuario = obtenerUsuarioCorreo(correo_usuario);
    List<Usuario> usuariosFavoritos = usuario.getUsuariosFavoritos();
    usuariosFavoritos.add(u);
    usuario.setUsuariosFavoritos(usuariosFavoritos);
    repository.save(usuario);
  }

  /* Eliminar usuario favorito por usuario identificado por el correo. */
  public void eliminar (Usuario u, String correo_usuario) {
    Usuario usuario = obtenerUsuarioCorreo(correo_usuario);
    List<Usuario> usuariosFavoritos = usuario.getUsuariosFavoritos();
    if(usuariosFavoritos.contains(u)){
      usuariosFavoritos.remove(u);
    }
    usuario.setUsuariosFavoritos(usuariosFavoritos);
    repository.save(usuario);
  }

  /* Obtener los artistas favoritos por usuario identificado por el correo. */
  public List<Cancion> obtenerCancionesFavoritas (String correo_usuario) {
    Usuario usuario = obtenerUsuarioCorreo(correo_usuario);
    List<Cancion> cancionesFavoritas = usuario.getCancionesFavoritas();
    return cancionesFavoritas;
  }

  /* Guardar artista favorito por usurio identificado por el correo. */
  public void guardarCancionFav (Cancion cancion, String correo_usuario) {
    Usuario usuario = obtenerUsuarioCorreo(correo_usuario);
    List<Cancion> cancionesFavoritas = usuario.getCancionesFavoritas();
    /* La cancion ya esta en la lista */
    if (usuario.getCancionesFavoritas().contains(cancion)) {
      return;
    }
    cancionesFavoritas.add(cancion);
    usuario.setCancionesFavoritas(cancionesFavoritas);
    repository.save(usuario);
  }

  /* Eliminar artista favorito por usuario identificado por el correo. */
  public void eliminarCancionFav (Cancion cancion, String correo_usuario) {
    Usuario usuario = obtenerUsuarioCorreo(correo_usuario);
    List<Cancion> cancionesFavoritas = usuario.getCancionesFavoritas();
    if(cancionesFavoritas.contains(cancion)){
      cancionesFavoritas.remove(cancion);
    }
    usuario.setCancionesFavoritas(cancionesFavoritas);
    repository.save(usuario);
  }

}
