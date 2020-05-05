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

      ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nombre", "Ingresa un nombre");
      if (persona.getNombre().length() < 6 || persona.getNombre().length() > 32) {
          errors.rejectValue("nombre", "Size.userForm.username");
      }
      if (personaService.findByNombre(persona.getNombre()) != null) {
          errors.rejectValue("nombre", "Duplicate.userForm.username");
      }
      if (personaService.findByCorreo(persona.getCorreo()) != null) {
          errors.rejectValue("correo", "Duplicate.userForm.email");
      }
      ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "Debes ingresar una contraseña");
      if (persona.getPassword().length() < 7 || persona.getPassword().length() > 32) {
          errors.rejectValue("password", "Size.userForm.password");
      }
      if (!persona.getPassword().equals(persona.getPasswordConfirm())) {
          errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
      }
  }
}
