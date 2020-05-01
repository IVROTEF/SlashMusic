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
    joinColumns = { @JoinColumn(name = "artista")},
    inverseJoinColumns = { @JoinColumn(name = "cancion")}
  )
  private List<Cancion> cancionesCreadas;

  public Artista (){
  }

  public Artista (String nombre) {
    this.nombre = nombre;
  }

  public String getNombre () {
    return this.nombre;
  }

  public void setNombre (String nombre) {
    this.nombre = nombre;
  }

}
