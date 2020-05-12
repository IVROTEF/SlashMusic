package com.ivrotef.slashmusic.model;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Blob;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "Cancion")
public class Cancion {

  @Id
  @Column(name = "nombre")
  private String nombre;

  @Column(name = "archivo")
  private Blob archivo;

  /* La persona que subio la cancion */
  @ManyToOne
  @JoinColumn(name = "autor")
  private Persona autor;

  /* Lista de listas de reproducción donde aparece esta cancion */
  @ManyToMany(mappedBy = "canciones",fetch = FetchType.EAGER)
  private List<Lista> listas;

/*
  // Lista de usuarios que han comentado esta cancion
  @ManyToMany(mappedBy = "comentariosCanciones")
  private List<Usuario> comentarios;
*/

  /* Lista de comentarios que han hecho a esta cancion */
  @OneToMany(mappedBy = "cancion")
  private List<Comentario> comentarios;


  /* Lista de usuarios que añadieron esta cancion a favoritos */
  @ManyToMany(mappedBy = "cancionesFavoritas")
  private List<Usuario> seguidores;

  /* Lista de usuarios a los que le pertenece la canción */
  @ManyToMany(mappedBy = "cancionesPropias")
  private List<Usuario> propietarios;

  /* Lista de usuarios que han compartido esta cancion */
  @ManyToMany(mappedBy = "cancionesCompartidas")
  private List<Usuario> distribuidores;

  /* Artistas a los que les pertenece esta cancion */
  @ManyToMany(mappedBy = "cancionesCreadas")
  private List<Artista> autores;

  public Cancion (){
  }

  public Cancion (String nombre, Blob archivo) {
    this.nombre = nombre;
    this.archivo = archivo;
    this.listas = new ArrayList<Lista>();
  }

  public String getNombre () {
    return this.nombre;
  }

  public void setNombre (String nombre) {
    this.nombre = nombre;
  }

  public Blob getArchivo () {
    return this.archivo;
  }

  public void setArchivo (Blob archivo) {
    this.archivo = archivo;
  }

  public List<Comentario> getComentarios(){
    return this.comentarios;
  }

  public void setComentarios (List<Comentario> comentarios) {
    this.comentarios = comentarios;
  }

  public void agregarLista (Lista lista) {
    this.listas.add(lista);
  }

  public Persona getAutor () {
    return this.autor;
  }

  public void setAutor (Persona autor) {
    this.autor = autor;
  }

  public List<Lista> getListas() {
    return this.listas;
  }

  public void setListas(List<Lista> listas) {
    this.listas = listas;
  }


  public List<Usuario> getSeguidores () {
    return this.seguidores;
  }

  public void setSeguidores (List<Usuario> seguidores) {
    this.seguidores = seguidores;
  }

  public List<Usuario> getPropietarios () {
    return this.propietarios;
  }

  public void setPropietarios (List<Usuario> propietarios) {
    this.propietarios = propietarios;
  }

  public List<Usuario> getDistribuidores () {
    return this.distribuidores;
  }

  public void setDistribuidores (List<Usuario> distribuidores){
    this.distribuidores = distribuidores;
  }

  public List<Artista> getAutores () {
    return this.autores;
  }

  public void setAutores (List<Artista> autores) {
    this.autores = autores;
  }

}
