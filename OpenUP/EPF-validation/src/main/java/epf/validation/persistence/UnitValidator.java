/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.validation.persistence;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author FOXCONN
 */
public class UnitValidator implements ConstraintValidator<Unit, String> {
	
	/**
	 * 
	 */
	private static final String OPENUP = "OpenUP";
	/**
	 * 
	 */
	private static final String EPF = "EPF";

    @Override
    public boolean isValid(final String unit, final ConstraintValidatorContext context) {
    	boolean isValid = false;
    	if(OPENUP.equals(unit) || EPF.equals(unit)) {
    		isValid = true;
    	}
        return isValid;
    }
    
}
