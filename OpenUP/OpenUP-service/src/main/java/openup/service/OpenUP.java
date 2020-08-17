/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.service;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import org.eclipse.microprofile.auth.LoginConfig;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

/**
 *
 * @author FOXCONN
 */
@ApplicationScoped
@ApplicationPath("/openup")
@LoginConfig(authMethod="MP-JWT", realmName="OpenUP")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("Any_Role")
@OpenAPIDefinition(
        info = @Info(
            title = "OpenUP",
            description = "OpenUP",
            contact = @Contact(
                    name = "kimduquan",
                    url = "www.kdq.io",
                    email = "kimduquan@gmail.com"
            ),
            version = "1.0-SNAPSHOT"
        ),
        tags = {
            @Tag(
                    name = "OpenUP",
                    description = "OpenUP"
            )
        }
)
public class OpenUP extends Application {
    
}
