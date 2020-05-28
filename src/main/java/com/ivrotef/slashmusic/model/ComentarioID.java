package com.ivrotef.slashmusic.model;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.Column;

/* Representa la llave compuesta de la clase Comentario */

@Embeddable
public class ComentarioID implements Serializable {
  
  private static final long serialVersionUID = 1L; 

  @Column(name = "id_publicacion")
  private int idPublicacion;

  @Column(name = "id_comentario")
  private int idComentario;

  public ComentarioID() {
  }

  public ComentarioID (int idPublicacion, int idComentario) {
    this.idPublicacion = idPublicacion;
    this.idComentario = idComentario;
  }

  public int getIdPublicacion () {
    return this.idPublicacion;
  }

  public void setIdPublicacion (int idPublicacion) {
    this.idPublicacion = idPublicacion;
  }

  public int getIdComentario () {
    return this.idComentario;
  }

  public void setIdComentario(int idComentario){
    this.idComentario = idComentario;
  }

}
