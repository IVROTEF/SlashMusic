package com.ivrotef.slashmusic.controller;

import com.ivrotef.slashmusic.controller.CancionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.io.File;
import java.nio.file.Files;


@Service
public class FileService {

    @Value("${upload.path}")
    public String uploadDir;

    @Autowired
    CancionService cancionService;

    /* Guarda el archivo en la carpeta especificada por uploadDir y
      regresa la ruta del archivo ruta */
    public String uploadFile(MultipartFile file){
        String ruta = null;
        try {
            ruta = obtenerRuta(uploadDir + File.separator + StringUtils.cleanPath(file.getOriginalFilename()));
            Path copyLocation = Paths
                .get(ruta);
            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
          e.printStackTrace();
        }

        return ruta;
    }

    /* Crea unaa ruta para el archivo, si ya existe,
      crea una copia de la ruta y le agrega un numero */
    public String obtenerRuta (String rt) {
      if (cancionService.obtenerCancionRuta(rt) == null) {
        return rt;
      }
      String tmp = "";
      for (int i = 1;; i++) {
        tmp = rt.substring(0, rt.length() - 4) + "(" + Integer.toString(i) + ")" + ".mp3";
        if (cancionService.obtenerCancionRuta(tmp) == null) {
          break;
        }
      }
      return tmp;
    }

}
