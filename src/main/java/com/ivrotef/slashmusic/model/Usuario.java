package com.ivrotef.slashmusic.model;

import com.ivrotef.slashmusic.model.Persona;
import com.ivrotef.slashmusic.model.Cancion;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import javax.persistence.OneToOne;
import javax.persistence.CascadeType;
import javax.persistence.PrimaryKeyJoinColumn;
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
import java.util.ArrayList;

@Entity
@Table(name="Usuario")
public class Usuario {

  @Id
  @Column(name = "correo")
  private String correo;

  @OneToOne(cascade = CascadeType.ALL)
  @PrimaryKeyJoinColumn
  @NotFound(action=NotFoundAction.IGNORE)
  private Persona persona;


  /* Lista de usuarios favoritos del usuario actual */
  @ManyToMany
  @JoinTable(
    name = "Agregar_Usuario_Favorito",
    joinColumns = { @JoinColumn(name = "usuario_uno", referencedColumnName="correo") },
    inverseJoinColumns = {@JoinColumn(name = "usuario_dos", referencedColumnName="correo")}
  )
  private List<Usuario> usuariosFavoritos;

  /* Lista de usuarios que marcaron como favorito al usuario actual */
  @ManyToMany(mappedBy = "usuariosFavoritos")
  private List<Usuario> seguidores;

  /* Lista de amigos del usuario actual */
  @ManyToMany
  @JoinTable(
    name = "Agregar_Amigo",
    joinColumns = { @JoinColumn(name = "usuario_uno", referencedColumnName="correo") },
    inverseJoinColumns = { @JoinColumn(name = "usuario_dos", referencedColumnName="correo")}
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
    joinColumns = { @JoinColumn(name = "usuario", referencedColumnName="correo")},
    inverseJoinColumns = { @JoinColumn(name = "artista", referencedColumnName="nombre")}
  )
  private List<Artista> artistasFavoritos;

  /* Lista de canciones favoritas del usuario */
  @ManyToMany
  @JoinTable(
    name = "Agregar_Favorito",
    joinColumns = { @JoinColumn(name = "usuario", referencedColumnName="correo")},
    inverseJoinColumns = { @JoinColumn(name = "cancion", referencedColumnName="nombre")}
  )
  private List<Cancion> cancionesFavoritas;

  /* Lista de canciones que le pertenecen al usuario */
  @ManyToMany
  @JoinTable(
    name = "Pertenece_Usuario",
    joinColumns = { @JoinColumn(name = "usuario", referencedColumnName="correo")},
    inverseJoinColumns = { @JoinColumn(name = "cancion", referencedColumnName="nombre")}
  )
  private List<Cancion> cancionesPropias;

  /* Comentarios que ha hecho el usuario. */
  @OneToMany(mappedBy = "usuarioComentario")
  private List<Comentario> comentarios;

  /* Publicaciones que ha compartido el usuario. */
  @OneToMany(mappedBy = "usuarioPC")
  private List<PublicacionCompartida> publicacionesCompartidas;

<<<<<<< HEAD
  /* Publicaciones del usuario. */ 
=======
  /* Publicaciones del usuario. */
>>>>>>> origin
  @OneToMany(mappedBy = "usuarioPublicacion")
  private List<Publicacion> publicaciones;

  public Usuario (){
    usuariosFavoritos = new ArrayList<Usuario>();
    seguidores = new ArrayList<Usuario>();
    amigos = new ArrayList<Usuario>();
    vecinos = new ArrayList<Usuario>();
    artistasFavoritos = new ArrayList<Artista>();
    cancionesFavoritas = new ArrayList<Cancion>();
    cancionesPropias = new ArrayList<Cancion>();
    comentarios = new ArrayList<Comentario>();
    publicacionesCompartidas = new ArrayList<PublicacionCompartida>();
    publicaciones = new ArrayList<Publicacion>();
  }

  public Usuario(String correo) {
    this.correo = correo;
    usuariosFavoritos = new ArrayList<Usuario>();
    seguidores = new ArrayList<Usuario>();
    amigos = new ArrayList<Usuario>();
    vecinos = new ArrayList<Usuario>();
    artistasFavoritos = new ArrayList<Artista>();
    cancionesFavoritas = new ArrayList<Cancion>();
    cancionesPropias = new ArrayList<Cancion>();
    comentarios = new ArrayList<Comentario>();
    publicacionesCompartidas = new ArrayList<PublicacionCompartida>();
    publicaciones = new ArrayList<Publicacion>();
  }

  public Usuario (Persona persona){
    this.correo = persona.getCorreo();
    this.persona = persona;
  }

  public Persona getPersona () {
    return this.persona;
  }

  public void setPersona (Persona persona) {
    this.persona = persona;
  }

  public String getCorreo () {
    return this.correo;
  }

  public void setCorreo (String correo) {
    this.correo = correo;
  }

  public void agregarCancion (Cancion cancion) {
    this.cancionesPropias.add(cancion);
  }

  public List<Artista> getArtistasFavoritos () {
    return this.artistasFavoritos;
  }

  public void setArtistasFavoritos(List<Artista> artistasFavoritos) {
    this.artistasFavoritos = artistasFavoritos;
  }

  public List<Usuario> getUsuariosFavoritos () {
    return this.usuariosFavoritos;
  }

  public void setUsuariosFavoritos(List<Usuario> usuariosFavoritos) {
    this.usuariosFavoritos = usuariosFavoritos;
  }

  public List<Comentario> getComentarios () {
    return this.comentarios;
  }

  public void setcomentarios (List<Comentario> comentarios) {
    this.comentarios = comentarios;
  }

  public List<PublicacionCompartida> getPublicacionesCompartidas () {
    return this.publicacionesCompartidas;
  }

  public void setPublicacionesCompartidas (List<PublicacionCompartida> publicacionesCompartidas) {
    this.publicacionesCompartidas = publicacionesCompartidas;
  }

  public List<Publicacion> getPublicaciones () {
    return this.publicaciones;
  }

  public void setPublicaciones (List<Publicacion> publicaciones) {
    this.publicaciones = publicaciones;
  }
<<<<<<< HEAD
=======
  public List<Cancion> getCancionesFavoritas () {
    return this.cancionesFavoritas;
  }

  public void setCancionesFavoritas(List<Cancion> cancionesFavoritas) {
    this.cancionesFavoritas = cancionesFavoritas;
  }

  public void setCancionesPropias (List<Cancion> cancionesPropias) {
    this.cancionesPropias = cancionesPropias;
  }

  public List<Cancion> getCancionesPropias() {
    return this.cancionesPropias;
  }

  public List<Usuario> getAmigos () {
    return this.amigos;
  }

  public void setAmigos (List<Usuario> amigos) {
    this.amigos = amigos;
  }

  public void agregarAmigo (Usuario amigo) {
    this.amigos.add(amigo);
  }

>>>>>>> origin
}
