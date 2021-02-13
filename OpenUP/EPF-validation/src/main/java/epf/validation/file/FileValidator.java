/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.validation.file;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author FOXCONN
 */
public class FileValidator implements ConstraintValidator<File, String> {

    @Override
    public boolean isValid(String path, ConstraintValidatorContext arg1) {
        java.io.File file = new java.io.File(path);
        return file.exists();
    }
    
}
