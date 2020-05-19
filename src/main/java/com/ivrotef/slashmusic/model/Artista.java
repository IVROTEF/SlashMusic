package com.ivrotef.slashmusic.model;

import javax.persistence.JoinTable;
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
@Table(name="Artista")
public class Artista {

  @Id
  @Column(name = "nombre")
  private String nombre;

  /* Lista de usuarios que han agregado al artista como favorito */
  @ManyToMany(mappedBy = "artistasFavoritos")
  private List<Usuario> seguidores;

  /* Lista de canciones que le pertenecen al artista */
  @ManyToMany
  @JoinTable(
    name = "Pertenece_Artista",
    joinColumns = { @JoinColumn(name = "artista", referencedColumnName="nombre")},
    inverseJoinColumns = { @JoinColumn(name = "cancion", referencedColumnName="nombre")}
  )
  private List<Cancion> cancionesCreadas;

  public Artista (){
    seguidores = new ArrayList<Usuario>();
    cancionesCreadas = new ArrayList<Cancion>();
  }

  public Artista (String nombre) {
    this.nombre = nombre;
    seguidores = new ArrayList<Usuario>();
    cancionesCreadas = new ArrayList<Cancion>();
  }

  public String getNombre () {
    return this.nombre;
  }

  public void setNombre (String nombre) {
    this.nombre = nombre;
  }

  public void asignarCancion (Cancion cancion) {
    this.cancionesCreadas.add(cancion);
  }

}
