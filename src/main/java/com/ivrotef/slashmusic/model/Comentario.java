package com.ivrotef.slashmusic.model;

import com.ivrotef.slashmusic.model.Usuario;
import com.ivrotef.slashmusic.model.Cancion;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

/* Clase que representa la relacion comentar de muchos a muchos */

@Entity
@Table(name = "Comentar")
public class Comentario implements Serializable {

  @Id
  @ManyToOne
  @JoinColumn(name = "usuario")
  private Usuario usuario;

  @Id
  @ManyToOne
  @JoinColumn(name = "cancion")
  private Cancion cancion;

  @Column(name = "comentario")
  private String comentario;

  public Comentario (Usuario usuario, Cancion cancion, String comentario) {
    this.usuario = usuario;
    this.cancion = cancion;
    this.comentario = comentario;
  }

  public Usuario getUsuario () {
    return this.usuario;
  }

  public void setUsuario (Usuario usuario) {
    this.usuario = usuario;
  }

  public Cancion getCancion () {
    return this.cancion;
  }

  public void setCancion (Cancion c) {
    this.cancion = c;
  }

  public String getComentario () {
    return this.comentario;
  }

  public void setComentario (String comentario) {
    this.comentario = comentario;
  }

}
