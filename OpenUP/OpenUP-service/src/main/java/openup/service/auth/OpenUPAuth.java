/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.service.auth;

import java.util.Set;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.eclipse.microprofile.auth.LoginConfig;

@ApplicationScoped
@ApplicationPath("/auth")
@LoginConfig(authMethod="BASIC", realmName="OpenUP")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("Any_Role")
public class OpenUPAuth extends Application {
    
    @Override
    public Set<Class<?>> getClasses() {
        return Set.of(OpenUPAuth.class);
    }
    
    @GET
    public Response getToken(){
        return Response.ok().build();
    }
}
