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
public class DirectoryValidator implements ConstraintValidator<Directory, String> {

    @Override
    public boolean isValid(final String path, final ConstraintValidatorContext arg1) {
    	final java.io.File dir = new java.io.File(path);
        return dir.isDirectory() && dir.exists();
    }
    
}
