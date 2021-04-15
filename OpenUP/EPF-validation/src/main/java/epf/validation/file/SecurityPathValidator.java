/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.validation.file;

import java.security.Principal;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.security.enterprise.SecurityContext;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.PathSegment;

/**
 *
 * @author FOXCONN
 */
@RequestScoped
public class SecurityPathValidator implements ConstraintValidator<SecurityPath, List<PathSegment>> {

	/**
	 * 
	 */
	@Context
	private transient SecurityContext context;
	
    @Override
    public boolean isValid(final List<PathSegment> paths, final ConstraintValidatorContext arg1) {
    	final Principal principal = context.getCallerPrincipal();
    	if(principal != null){
    		if(paths.isEmpty()) {
    			throw new NotFoundException();
    		}
    		if(!principal.getName().equals(paths.get(0).getPath())) {
        		throw new ForbiddenException();
    		}
    	}
    	return true;
    }
    
}
