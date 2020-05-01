package com.ivrotef.slashmusic.model;

import com.ivrotef.slashmusic.model.Cancion;
import com.ivrotef.slashmusic.model.ListaID;

import java.util.List;
import java.util.ArrayList;

import javax.persistence.EmbeddedId;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import javax.persistence.EmbeddedId;


@Entity
@Table(name = "Lista")
public class Lista {
  /* La llave compuesta del nombre de lista y el correo de usuario */
  @EmbeddedId
  private ListaID listaID;

  @ManyToMany
  @JoinTable(
    name = "Contener",
    joinColumns = {@JoinColumn(name = "usuario"),
                   @JoinColumn(name = "nombre_lista")},
    inverseJoinColumns = @JoinColumn(name = "cancion")
  )
  /* Lista de canciones que contiene */
  private List<Cancion> canciones;

  public Lista (ListaID listaID) {
    this.listaID = listaID;
    canciones = new ArrayList<Cancion>();
  }

  public ListaID getListaID () {
    return this.listaID;
  }

  public void setListaID (ListaID listaID) {
    this.listaID = listaID;
  }

  public List<Cancion> getCanciones () {
    return this.canciones;
  }

  public void setCanciones (ArrayList<Cancion> canciones ) {
    this.canciones = canciones;
  }

  public void agregarCancion (Cancion c) {
    this.canciones.add(c);
  }

  public void eliminarCancion (Cancion c) {
    this.canciones.remove(c);
  }

}
