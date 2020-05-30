/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.micro.test;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 *
 * @author FOXCONN
 */
@Path("/test")
@RequestScoped
@RolesAllowed("AnyRole")
public class Test {
    
    @GET
    public String helloWorld(){
        return "Hello World!";
    }
}
