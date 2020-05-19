package com.ivrotef.slashmusic.controller;

import com.ivrotef.slashmusic.model.Cancion;
import com.ivrotef.slashmusic.model.Persona;
import com.ivrotef.slashmusic.model.Artista;
import com.ivrotef.slashmusic.model.Administrador;
import com.ivrotef.slashmusic.model.Usuario;
import com.ivrotef.slashmusic.config.PersonaWrapper;
import com.ivrotef.slashmusic.controller.CancionService;
import com.ivrotef.slashmusic.controller.ArtistaService;

import com.ivrotef.slashmusic.controller.FileService;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.ArrayList;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FileController {

  @Autowired
  FileService fileService;

  @Autowired
  ArtistaService artistaService;

  @Autowired
  CancionService cancionService;

  /* Carga la pantalla principal con los artistas en la base de datos */
  @GetMapping("/upload")
  public ModelAndView index(@AuthenticationPrincipal PersonaWrapper persona) {
      Persona actual = persona.getPersona();
      ModelAndView modelAndView = new ModelAndView ("SubirCancion");
      ArrayList<Artista> artistas = artistaService.getArtistas();
      boolean hayArtistas = (artistas.size() == 0)? false : true;
      boolean esAdmin = (actual.getAdministrador() != null)? true : false;
      modelAndView.addObject("esAdmin", esAdmin);
      modelAndView.addObject("hayArtistas", hayArtistas);
      modelAndView.addObject("artistas",artistas);
      return modelAndView;
  }

  @PostMapping("/uploadFile")
  public String uploadFile(@RequestParam("file") MultipartFile file,
                            @RequestParam("autor") String autor,
                            @AuthenticationPrincipal PersonaWrapper persona,
                            RedirectAttributes redirectAttributes) {
      Persona actual = persona.getPersona();
      String info[] = fileService.uploadFile(file);
      String ruta = info[0];
      String nombre = info[1];
      Cancion cancion = new Cancion(nombre, ruta);
      /* Si esta loggeado un Administrador*/
      if (actual.getAdministrador() != null) {
        Artista artista = artistaService.obtenerArtistaNombre(autor);
        artista.asignarCancion(cancion);
        cancion.asignarArtista(artista);
      } else {
        Usuario usuario = actual.getUsuario();
        usuario.agregarCancion(cancion);
        cancion.asignarPropietario(usuario);
      }
      /* Asignamos quien subio la cancion,
        esta mal nombrado el campo, porque no es el autor */
      cancion.setAutor (actual);

      cancionService.guardar(cancion);

      redirectAttributes.addFlashAttribute("message",
          "Se subio exitosamente el archivo " + file.getOriginalFilename() );

      return "redirect:/upload";
  }

}
