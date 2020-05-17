package com.ivrotef.slashmusic.model;

import com.ivrotef.slashmusic.model.Persona;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import javax.persistence.OneToOne;
import javax.persistence.CascadeType;
import javax.persistence.PrimaryKeyJoinColumn;
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
@Table(name="Administrador")
public class Administrador {

  @Id
  @Column(name = "correo")
  private String correo;

  @OneToOne(cascade = CascadeType.ALL)
  @PrimaryKeyJoinColumn
  @NotFound(action=NotFoundAction.IGNORE)
  private Persona persona;

  public Administrador(){
  }

  public Administrador (String correo) {
    this.correo = correo;
  }

  public Administrador(Persona persona) {
    this.persona = persona;
    this.correo = persona.getCorreo();
  }

  public void setPersona (Persona persona) {
    this.persona = persona;
  }

  public Persona getPersona () {
    return this.persona;
  }

  public String getCorreo () {
    return this.correo;
  }

  public void setCorreo (String correo) {
    this.correo = correo;
  }

}
