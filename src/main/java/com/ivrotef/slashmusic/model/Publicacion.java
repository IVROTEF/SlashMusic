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
@Table(name = "Publicacion")
public class Publicacion {

  @Id
  @Column(name = "id_publicacion")
  private int idPublicacion;

  @Column(name = "descripcion")
  private String descripcion;

  @ManyToOne
  @JoinColumn(name = "usuario")
  private Usuario usuarioPublicacion;

  @ManyToOne
  @JoinColumn(name = "cancion")
  private Cancion cancionPublicacion;

  public Publicacion (){
  }

  public Publicacion (int idPublicacion, String descripcion) {
    this.idPublicacion = idPublicacion;
    this.descripcion = descripcion;
  }

  public int getIdPublicacion () {
    return this.idPublicacion;
  }

  public void setIdPublicacion (int idPublicacion) {
    this.idPublicacion = idPublicacion;
  }

  public String getDescripcion () {
    return this.descripcion;
  }

  public void setDescripcion (String descripcion) {
    this.descripcion = descripcion;
  }

  public Usuario getUsuarioPublicacion () {
    return this.usuarioPublicacion;
  }

  public void setUsuarioPublicacion (Usuario usuarioPublicacion) {
    this.usuarioPublicacion = usuarioPublicacion;
  }

  public Cancion getCancionPublicacion () {
    return this.cancionPublicacion;
  }

  public void setCancionPublicacion (Cancion cancionPublicacion) {
    this.cancionPublicacion = cancionPublicacion;
  }
}
