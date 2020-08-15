/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.auth;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Set;
import java.util.UUID;
import javax.annotation.security.PermitAll;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import openup.auth.JWT;
import openup.auth.JWTGenerator;
import openup.config.OpenUPConfigs;
import openup.persistence.OpenUPPersistence;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.config.Names;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

/**
 *
 * @author FOXCONN
 */
@RequestScoped
@Path("auth")
@PermitAll
public class OpenUPAuth {
    
    @Inject
    private OpenUPPersistence persistence;
    
    @Inject
    private JWTGenerator generator;
    
    @ConfigProperty(name = Names.ISSUER)
    private String issuer;
    
    @ConfigProperty(name = OpenUPConfigs.JWT_EXP_DURATION)
    private Long jwtExpDuration;
    
    @ConfigProperty(name = OpenUPConfigs.JWT_EXP_TIMEUNIT)
    private String jwtExpTimeUnit;
    
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Operation(
            summary = "login", 
            description = "login"
    )
    @APIResponse(
            description = "JWT",
            content = @Content(
                    schema = @Schema(implementation = JWT.class)
            )
    )
    public JWT login(
            @Parameter(
                name = "username",
                required = true,
                allowEmptyValue = false,
                schema = @Schema(type = SchemaType.STRING)
            )
            @FormParam("username")
            String username, 
            @Parameter(
                name = "password",
                required = true,
                allowEmptyValue = false,
                schema = @Schema(type = SchemaType.STRING, format = "password")
            )
            @FormParam("password")        
            String password
    ) throws Exception{
        boolean ok = persistence.createEntityManager(username, password);
        if(ok){
            JWT jwt = new JWT();
            jwt.setExp(
                    Date.from(
                        Instant.now()
                                .plus(
                                    jwtExpDuration, 
                                    ChronoUnit.valueOf(jwtExpTimeUnit)
                                )
                ).getTime()
            );
            jwt.setGroups(Set.of("Any_Role"));
            jwt.setIat(new Date().getTime());
            jwt.setIss(issuer);
            jwt.setJti(username + UUID.randomUUID());
            jwt.setKid("");
            jwt.setSub(username);
            jwt.setUpn(username);
            jwt.setRaw_token(generator.generate(jwt));
            return jwt;
        }
        return null;
    }
}
