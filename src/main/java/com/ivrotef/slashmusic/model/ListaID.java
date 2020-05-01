package com.ivrotef.slashmusic.model;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.Column;

/* Representa la llave compuesta de la clase Lista */

@Embeddable
public class ListaID implements Serializable {

  @Column(name = "usuario")
  private String usuario;

  @Column(name = "nombre_lista")
  private String nombreLista;

  public ListaID() {
  }

  public ListaID (String usuario, String nombreLista) {
    this.usuario = usuario;
    this.nombreLista = nombreLista;
  }

  public String getUsuario () {
    return this.usuario;
  }

  public void setUsuario (String usuario) {
    this.usuario = usuario;
  }

  public String getNombreLista () {
    return this.nombreLista;
  }

  public void setNombreLista(String nombreLista){
    this.nombreLista = nombreLista;
  }

}
