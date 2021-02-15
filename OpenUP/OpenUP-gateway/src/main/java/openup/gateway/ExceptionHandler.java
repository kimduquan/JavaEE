/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.gateway;

import java.io.Serializable;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author FOXCONN
 */
@Provider
public class ExceptionHandler implements 
        Serializable,
        ExceptionMapper<Exception> {

    /**
    * 
    */
    private static final long serialVersionUID = 1L;
    
    @Override
    public Response toResponse(Exception ex) {
    	ResponseBuilder builder = Response.serverError();
    	if(ex instanceof WebApplicationException) {
    		WebApplicationException error = (WebApplicationException) ex;
    		builder = Response.fromResponse(error.getResponse());
    	}
        return builder.build();
    }
}
