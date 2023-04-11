package com.lcwd.electroinic.store.valid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ImageNameValidator implements ConstraintValidator<ImageNameValid,String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        System.out.println("Yes this is working Image Name Validator");
        if(value.isBlank())
        {
            return false;
        }else{
            return true;
        }
        }
}
