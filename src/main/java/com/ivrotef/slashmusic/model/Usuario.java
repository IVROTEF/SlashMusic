package com.ivrotef.slashmusic.model;

import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import java.util.List;


@Entity
@Table(name="Usuario")
public class Usuario {

  @Id
  @Column(name = "correo")
  private String correo;

  /* Lista de usuarios favoritos del usuario actual */
  @ManyToMany
  @JoinTable(
    name = "Agregar_Usuario_Favorito",
    joinColumns = { @JoinColumn(name = "usuario_uno") },
    inverseJoinColumns = {@JoinColumn(name = "usuario_dos")}
  )
  private List<Usuario> usuariosFavoritos;

  /* Lista de usuarios que marcaron como favorito al usuario actual */
  @ManyToMany(mappedBy = "usuariosFavoritos")
  private List<Usuario> seguidores;

  /* Lista de comentarios que han hecho el usuario */
  @OneToMany(mappedBy = "usuario")
  private List<Comentario> comentarios;

  /* Lista de amigos del usuario actual */
  @ManyToMany
  @JoinTable(
    name = "Agregar_Amigo",
    joinColumns = { @JoinColumn(name = "usuario_uno") },
    inverseJoinColumns = { @JoinColumn(name = "usuario_dos")}
  )
  private List<Usuario> amigos;

  /* Lista de usuarios que agregaron como amigo al usuario actual
   aunque en realidad es la misma lista que amigos */
  @ManyToMany(mappedBy = "amigos")
  private List<Usuario> vecinos;

  /* Lista de artistas favoritos del usuario actual */
  @ManyToMany
  @JoinTable(
    name = "Agregar_Artista_Fav",
    joinColumns = { @JoinColumn(name = "usuario")},
    inverseJoinColumns = { @JoinColumn(name = "artista")}
  )
  private List<Artista> artistasFavoritos;





/*
  // Lista de canciones que el usuario ha comentado
  @ManyToMany
  @JoinTable(
    name = "Comentar",
    joinColumns = { @JoinColumns({
                      @JoinColumn(name = "usuario"),
                      @JoinColumn(name = "comentario")
                    }) },
    inverseJoinColumns = { @JoinColumn(name = "cancion")}
  )
  private List<Cancion> comentariosCanciones;

  */

  /* Lista de canciones favoritas del usuario */
  @ManyToMany
  @JoinTable(
    name = "Agregar_Favorito",
    joinColumns = { @JoinColumn(name = "usuario")},
    inverseJoinColumns = { @JoinColumn(name = "cancion")}
  )
  private List<Cancion> cancionesFavoritas;

  /* Lista de canciones que le pertenecen al usuario */
  @ManyToMany
  @JoinTable(
    name = "Pertenece_Usuario",
    joinColumns = { @JoinColumn(name = "usuario")},
    inverseJoinColumns = { @JoinColumn(name = "cancion")}
  )
  private List<Cancion> cancionesPropias;

  /* Lista de canciones que el usuario ha compartido */
  @ManyToMany
  @JoinTable(
    name = "Compartir",
    joinColumns = { @JoinColumn(name = "usuario")},
    inverseJoinColumns = { @JoinColumn(name = "cancion")}
  )
  private List<Cancion> cancionesCompartidas;

  public Usuario(String correo) {
    this.correo = correo;
  }

  public String getCorreo () {
    return this.correo;
  }

  public void setCorreo (String correo) {
    this.correo = correo;
  }

}
