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
@Table(name="Administrador")
public class Administrador {

  @Id
  @Column(name = "correo")
  private String correo;

  public Administrador (String correo) {
    this.correo = correo;
  }

  public String getCorreo () {
    return this.correo;
  }

  public void setCorreo (String correo) {
    this.correo = correo;
  }

}
