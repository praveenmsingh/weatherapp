package com.my.weatherapp.validator;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.Set;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class BeanValidationHelper {

  private final Validator validator;

  public BeanValidationHelper(Validator validator) {
    this.validator = validator;
  }

  public <R> R applyValidations(R object) {
    final Set<ConstraintViolation<R>> violations = validator.validate(object);
    if (!CollectionUtils.isEmpty(violations)) {
      throw new ConstraintViolationException(violations);
    }

    return object;
  }
}
