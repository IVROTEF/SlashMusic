package com.ivrotef.slashmusic.controller;

import com.ivrotef.slashmusic.controller.ArtistaRepository;
import com.ivrotef.slashmusic.model.Artista;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.ArrayList;

@Service
public class ArtistaService {

    @Autowired
    ArtistaRepository repository;

    public Artista obtenerArtistaNombre (String nombre) {
        Optional<Artista> artista =  repository.findById(nombre);
        if (artista.isPresent()) {
          return artista.get();
        }
        return null;
    }

    public ArrayList<Artista> getArtistas () {
      ArrayList<Artista> artistas = (ArrayList<Artista>) repository.findAll();
      return artistas;
    }

}
