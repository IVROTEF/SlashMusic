package com.ivrotef.slashmusic.model;

import com.ivrotef.slashmusic.model.Usuario;
import com.ivrotef.slashmusic.model.ComentarioID;
import javax.persistence.EmbeddedId;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

/* Clase que representa la relacion comentar de muchos a muchos */

@Entity
@Table(name = "Comentario")
public class Comentario {
  /* La llave compuesta del id_publicacion de la publicación y el id_Comentario del comentario. */
  @EmbeddedId
  private ComentarioID comentarioID;

  @Column(name = "comentario")
  private String comentario;

  @ManyToOne
  @JoinColumn(name = "usuario")
  private Usuario usuarioComentario;

  public Comentario(){
  }

  public Comentario (ComentarioID comentarioID, Usuario usuarioComentario) {
    this.comentarioID = comentarioID;
    this.usuarioComentario = usuarioComentario;
  }

  public ComentarioID getComentarioID () {
    return this.comentarioID;
  }

  public void setComentarioID (ComentarioID comentarioID) {
    this.comentarioID = comentarioID;
  }

  public String getComentario () {
    return this.comentario;
  }

  public void setComentario (String comentario) {
    this.comentario = comentario;
  }

  public Usuario getUsuarioComentario () {
    return this.usuarioComentario;
  }

  public void setUsuarioComentario (Usuario usuarioComentario) {
    this.usuarioComentario = usuarioComentario;
  }
}
