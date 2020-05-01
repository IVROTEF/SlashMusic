package com.ivrotef.slashmusic.model;

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
@Table(name="Persona")
public class Persona {

  @Id
  @Column(name="correo")
  private String correo;

  @Column(name="contraseña")
  private String password;

  @Column(name="nombre")
  private String nombre;

  @OneToMany(mappedBy="autor")
  private List<Cancion> canciones;

  public Persona (String correo, String password, String nombre) {
    this.correo = correo;
    this.password = password;
    this.nombre = nombre;
  }

  public String getCorreo (){
    return this.correo;
  }

  public void setCorreo (String correo){
    this.correo = correo;
  }

  public String getPassword (){
    return this.password;
  }

  public void setPassword (String password){
    this.password = password;
  }

  public String getNombre (){
    return this.nombre;
  }

  public void setNombre (String nombre){
    this.nombre = nombre;
  }

  public List<Cancion> getCanciones (){
    return this.canciones;
  }

  public void setCanciones (List<Cancion> c){
    this.canciones = c;
  }

}
