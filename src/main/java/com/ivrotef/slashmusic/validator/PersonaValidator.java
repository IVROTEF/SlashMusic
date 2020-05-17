package com.ivrotef.slashmusic.validator;

import com.ivrotef.slashmusic.model.Persona;
import com.ivrotef.slashmusic.controller.PersonaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class PersonaValidator implements Validator {

  @Autowired
  private PersonaService personaService;

  @Override
  public boolean supports(Class<?> aClass) {
      return Persona.class.equals(aClass);
  }

  @Override
  public void validate (Object o, Errors errors) {
      Persona persona = (Persona) o;

      if (personaService.findByNombre(persona.getNombre()) != null) {
          errors.rejectValue("nombre", "Duplicate.userForm.nombre");
      }

      if (personaService.findByCorreo(persona.getCorreo()) != null) {
          errors.rejectValue("correo", "Duplicate.userForm.correo");
      }
  }

}
