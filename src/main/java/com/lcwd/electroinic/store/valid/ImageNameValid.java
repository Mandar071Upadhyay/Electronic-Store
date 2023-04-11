package com.lcwd.electroinic.store.valid;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ImageNameValidator.class)
public @interface ImageNameValid {
public String message() default "image name should not be blank";
    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
