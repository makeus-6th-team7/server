package com.example.demo.utils.customBeanValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;
import com.example.demo.utils.customBeanValidation.DateValidator;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {DateValidator.class})
public @interface DateCheck {
    String message() default "";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
