package com.ivrotef.slashmusic.model;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import javax.persistence.Transient;
import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
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

  @Transient
  private String passwordConfirm;

  @OneToMany(mappedBy="autor")
  private List<Cancion> canciones;

  @OneToOne(mappedBy = "persona")
  @NotFound(action=NotFoundAction.IGNORE)
  private Usuario usuario;

  @OneToOne(mappedBy = "persona")
  @NotFound(action=NotFoundAction.IGNORE)
  private Administrador administrador;

  public Persona (){
  }

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

  public Usuario getUsuario () {
    return this.usuario;
  }

  public void setUsuario (Usuario usuario) {
    this.usuario = usuario;
  }

  public Administrador getAdministrador () {
    return this.administrador;
  }

  public void setAdministrador (Administrador administrador) {
    this.administrador = administrador;
  }

  public String getPasswordConfirm () {
    return this.passwordConfirm;
  }

  public void setPasswordConfirm (String passwordConfirm) {
    this.passwordConfirm = passwordConfirm;
  }

}
