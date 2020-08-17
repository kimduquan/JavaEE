/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.auth;

import java.io.Serializable;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletionStage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.PermitAll;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import openup.config.OpenUPConfigs;
import openup.error.ErrorHandler;
import openup.persistence.OpenUPPersistence;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.faulttolerance.Asynchronous;
import org.eclipse.microprofile.faulttolerance.Bulkhead;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.jwt.config.Names;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

/**
 *
 * @author FOXCONN
 */
@SessionScoped
@Path("auth")
@PermitAll
public class OpenUPAuth implements Serializable {
    
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
    
    /**
     *
     * @param credential
     * @return
     */
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Operation(
            summary = "login", 
            description = "login"
    )
    @RequestBody(
            required = true,
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(
                            hidden = true, 
                            implementation = OpenUPCredential.class
                    )
            )
    )
    @APIResponse(
            name = "token", 
            content = @Content(
                    mediaType = MediaType.TEXT_PLAIN
            )
    )
    @Asynchronous
    @Bulkhead(value = 10, waitingTaskQueue = 16)
    @Timeout(4000)
    @Fallback(value = ErrorHandler.class, applyOn = {Exception.class})
    @CircuitBreaker(requestVolumeThreshold = 40, failureRatio = 0.618, successThreshold = 15)
    public CompletionStage<Response> login( OpenUPCredential credential ){
        CompletionStage<EntityManager> manager = persistence.createEntityManager(credential.getUsername(), credential.getPassword());
        CompletionStage<Response> response = manager.thenApplyAsync(em -> {
            try {
                return generateJWT(credential);
            } 
            catch (Exception ex) {
                Logger.getLogger(OpenUPAuth.class.getName()).log(Level.SEVERE, null, ex);
            }
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        });
        return response;
    }
    
    Response generateJWT(OpenUPCredential credential) throws Exception{
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
        return Response.ok(jwt.getRaw_token()).build();
    }
}
