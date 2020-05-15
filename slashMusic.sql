CREATE DATABASE  IF NOT EXISTS slashMusic;
USE slashMusic;

CREATE TABLE Persona (
	correo VARCHAR(40) NOT NULL UNIQUE,
	contraseña TEXT NOT NULL,
    nombre VARCHAR(30) NOT NULL, 
    CONSTRAINT Persona_PK PRIMARY KEY (correo)
);

CREATE TABLE Administrador (
	correo VARCHAR(40) NOT NULL UNIQUE,
    CONSTRAINT Administrador_PK PRIMARY KEY (correo),
    CONSTRAINT Administrador_Persona FOREIGN KEY (correo) 
    REFERENCES Persona (correo)
);

CREATE TABLE Usuario (
	correo VARCHAR(40) NOT NULL UNIQUE, 
    CONSTRAINT Usuario_PK PRIMARY KEY (correo),
    CONSTRAINT Usuario_Persona FOREIGN KEY (correo) 
    REFERENCES Persona (correo)    
);

CREATE TABLE Lista (
	usuario VARCHAR(40) NOT NULL,
    nombre_lista VARCHAR(20) NOT NULL,
    CONSTRAINT Lista_PK PRIMARY KEY (usuario, nombre_lista),
    CONSTRAINT Lista_Usuario FOREIGN KEY (usuario) 
    REFERENCES Usuario (correo)
);

CREATE TABLE Agregar_Amigo (
	usuario_uno VARCHAR(40), 
    usuario_dos VARCHAR(40),
    CONSTRAINT PK_Agregar_Amigo_PK PRIMARY KEY (usuario_uno, usuario_dos),
    CONSTRAINT FK_Agregar_Amigo_Uno FOREIGN KEY (usuario_uno)
    REFERENCES Usuario (correo),
	CONSTRAINT FK_Agregar_Amigo_Dos FOREIGN KEY  (usuario_dos) 
    REFERENCES Usuario (correo)
);

CREATE TABLE Agregar_Usuario_Favorito (
	usuario_uno VARCHAR(40), 
    usuario_dos VARCHAR(40),
    CONSTRAINT PK_Agregar_Usuario_Favorito PRIMARY KEY (usuario_uno, usuario_dos),
    CONSTRAINT FK_Agregar_Usuario_Favorito_Uno FOREIGN KEY (usuario_uno)
    REFERENCES Usuario (correo),
	CONSTRAINT FK_Agregar_Usuario_Favorito_Dos FOREIGN KEY (usuario_dos) 
    REFERENCES Usuario (correo)	
);

CREATE TABLE Cancion (
	nombre VARCHAR(30) NOT NULL,	
    archivo BLOB NULL,
    autor VARCHAR(40) NOT NULL,
    CONSTRAINT PK_Cancion PRIMARY KEY (nombre),
    CONSTRAINT FK_Cancion_Persona FOREIGN KEY (autor) 
    REFERENCES Persona (correo)
);

CREATE TABLE Artista (
	nombre VARCHAR(30) NOT NULL, 
    CONSTRAINT PK_Artista PRIMARY KEY (nombre)
);

CREATE TABLE Pertenece_Artista (
	artista VARCHAR(30),
	cancion VARCHAR(30),    
    CONSTRAINT PK_Cancion_Artista PRIMARY KEY (artista, cancion),
    CONSTRAINT FK_Cancion_Artista_Artista FOREIGN KEY (artista) 
    REFERENCES Artista (nombre),
    CONSTRAINT FK_Cancion_Artista_Cancion FOREIGN KEY (cancion) 
    REFERENCES Cancion (nombre)
);

CREATE TABLE Compartir (
	usuario VARCHAR(20),
    cancion VARCHAR(30),
    CONSTRAINT PK_Compartir PRIMARY KEY (usuario, cancion),
    CONSTRAINT FK_Compartir_Usuario FOREIGN KEY (usuario)
    REFERENCES Usuario (correo),
    CONSTRAINT FK_Compartir_Cancion FOREIGN KEY (cancion) 
    REFERENCES Cancion (nombre)
);

CREATE TABLE Pertenece_Usuario (
	usuario VARCHAR(20),
    cancion VARCHAR(30),
    CONSTRAINT PK_Pertenece_Usuario PRIMARY KEY (usuario, cancion),
    CONSTRAINT FK_Pertenece_Usuario_Usuario FOREIGN KEY (usuario)
    REFERENCES Usuario (correo),
	CONSTRAINT FK_Pertenece_Usuario_Cancion FOREIGN KEY (cancion) 
	REFERENCES Cancion (nombre)	
);

CREATE TABLE Agregar_Favorito (
	usuario VARCHAR(20),
    cancion VARCHAR(30),
    CONSTRAINT PK_Agregar_Favorito PRIMARY KEY (usuario, cancion),
    CONSTRAINT FK_Agregar_Favorito_Usuario FOREIGN KEY (usuario)
    REFERENCES Usuario (correo),
	CONSTRAINT FK_Agregar_Favorito_Cancion FOREIGN KEY (cancion) 
	REFERENCES Cancion (nombre)		
);

CREATE TABLE Comentar (
	usuario VARCHAR(20),
    cancion VARCHAR(30),
    comentario TEXT,
    CONSTRAINT PK_Comentar PRIMARY KEY (usuario, cancion),
    CONSTRAINT FK_Comentar_Usuario FOREIGN KEY (usuario)
    REFERENCES Usuario (correo),
	CONSTRAINT FK_Comentar_Cancion FOREIGN KEY (cancion) 
	REFERENCES Cancion (nombre)	
);

CREATE TABLE Contener (
	usuario VARCHAR(40) NOT NULL,
    nombre_lista VARCHAR(20) NOT NULL,
	cancion VARCHAR(30) NOT NULL,
	CONSTRAINT PK_Contener PRIMARY KEY (usuario, nombre_lista, cancion)	,
    CONSTRAINT FK_Contener_Lista FOREIGN KEY (usuario, nombre_lista)
    REFERENCES Lista (usuario, nombre_lista),
    CONSTRAINT FK_Contener_Cancion FOREIGN KEY (cancion)
    REFERENCES Cancion (nombre)
);

CREATE TABLE Agregar_Artista_Fav (
	usuario VARCHAR(20),
    artista VARCHAR(30),
    CONSTRAINT PK_Agregar_Artista_Fav PRIMARY KEY (usuario, artista),
    CONSTRAINT FK_Agregar_Artista_Fav_Usuario FOREIGN KEY (usuario) 
    REFERENCES Usuario (correo),
    CONSTRAINT FK_Agregar_Artista_Fav_Artista FOREIGN KEY (artista) 
    REFERENCES Artista (nombre)
);