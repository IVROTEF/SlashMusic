package com.ivrotef.slashmusic.model;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.Column;

/* Representa la llave compuesta de la clase Compartir */

@Embeddable
public class PublicacionCompartidaID implements Serializable {
  
  private static final long serialVersionUID = 1L; 

  @Column(name = "id_publicacion")
  private int idPublicacion;

  @Column(name = "id_compartir")
  private int idCompartir;

  public PublicacionCompartidaID () {
  }

  public PublicacionCompartidaID (int idPublicacion, int idCompartir) {
    this.idPublicacion = idPublicacion;
    this.idCompartir = idCompartir;
  }

  public int getIdPublicacion () {
    return this.idPublicacion;
  }

  public void setIdPublicacion (int idPublicacion) {
    this.idPublicacion = idPublicacion;
  }

  public int getIdCompartir () {
    return this.idCompartir;
  }

  public void setIdCompartir(int idCompartir){
    this.idCompartir = idCompartir;
  }

}
