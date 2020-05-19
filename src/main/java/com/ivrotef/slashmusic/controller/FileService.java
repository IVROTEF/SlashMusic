package com.ivrotef.slashmusic.controller;

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

    @Value("${app.upload.dir:${user.home}}")
    public String uploadDir;

    /* Guarda el archivo en la carpeta especificada por uploadDir y
      regresa en un arreglo el nombre del archivo y su ruta */
    public String[] uploadFile(MultipartFile file) {
      String[] info = new String[2];
        try {
            Path copyLocation = Paths
                .get(uploadDir + File.separator + StringUtils.cleanPath(file.getOriginalFilename()));
            info[0] = copyLocation.toString();
            info[1] = file.getOriginalFilename();
            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return info;
    }
}
