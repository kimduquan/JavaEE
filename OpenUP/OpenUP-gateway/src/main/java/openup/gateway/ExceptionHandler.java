/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.gateway;

import java.io.Serializable;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author FOXCONN
 */
@Provider
public class ExceptionHandler implements 
        Serializable,
        ExceptionMapper<ClientErrorException> {

    /**
    * 
    */
    private static final long serialVersionUID = 1L;
    
    @Override
    public Response toResponse(ClientErrorException ex) {
        return Response.fromResponse(ex.getResponse()).build();
    }
}
