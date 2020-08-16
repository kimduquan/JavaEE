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
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import openup.config.OpenUPConfigs;
import openup.persistence.OpenUPPersistence;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.config.Names;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

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
    @Operation(
            summary = "login", 
            description = "login"
    )
    @RequestBody(
            required = true,
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = OpenUPCredential.class)
            )
    )
    public String login(OpenUPCredential credential) throws Exception{
        boolean ok = persistence.createEntityManager(credential.getUsername(), credential.getPassword());
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
            jwt.setJti(credential.getUsername() + UUID.randomUUID());
            jwt.setKid("");
            jwt.setSub(credential.getUsername());
            jwt.setUpn(credential.getUsername());
            jwt.setRaw_token(generator.generate(jwt));
            return jwt.getRaw_token();
        }
        return "";
    }
}
