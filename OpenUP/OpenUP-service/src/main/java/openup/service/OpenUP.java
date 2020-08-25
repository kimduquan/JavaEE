/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.service;

import javax.annotation.security.DenyAll;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import org.eclipse.microprofile.auth.LoginConfig;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeIn;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.eclipse.microprofile.openapi.annotations.security.SecuritySchemes;
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
@DenyAll
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
        security = {
            @SecurityRequirement(
                    name = "MP-JWT"
            )
        },
        tags = {
            @Tag(
                    name = "OpenUP",
                    description = "OpenUP"
            )
        }
)
@SecuritySchemes({
    @SecurityScheme(
            securitySchemeName = "MP-JWT",
            type = SecuritySchemeType.HTTP,
            description = "MP-JWT",
            scheme = "bearer",
            bearerFormat = "JWT"
    )
})
public class OpenUP extends Application {
    
}
