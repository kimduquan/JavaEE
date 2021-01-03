/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.validation.persistence;

import epf.schema.EPF;
import epf.schema.OpenUP;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author FOXCONN
 */
public class UnitValidator implements ConstraintValidator<Unit, String> {

    @Override
    public boolean isValid(String unit, ConstraintValidatorContext arg1) {
        return OpenUP.Schema.equals(unit) || EPF.Schema.equals(unit);
    }
    
}
