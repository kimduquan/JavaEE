/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.auth;

import java.io.Serializable;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import javax.annotation.security.PermitAll;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import openup.error.ErrorHandler;
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
import openup.config.ConfigNames;
import openup.persistence.Application;

/**
 *
 * @author FOXCONN
 */
@RequestScoped
@Path("auth")
@PermitAll
public class OpenUPAuth implements Serializable {
    
    @Inject
    private Application persistence;
    
    @Inject
    private JWTGenerator generator;
    
    @Inject
    @ConfigProperty(name = Names.ISSUER)
    private String issuer;
    
    @Inject
    @ConfigProperty(name = ConfigNames.JWT_EXP_DURATION)
    private Long jwtExpDuration;
    
    @Inject
    @ConfigProperty(name = ConfigNames.JWT_EXP_TIMEUNIT)
    private String jwtExpTimeUnit;
    
    /**
     *
     * @param credential
     * @return
     * @throws java.lang.Exception
     */
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Operation(
            summary = "login", 
            description = "login",
            operationId = "login"
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
            name = "login", 
            description = "Token",
            responseCode = "200",
            content = @Content(
                    mediaType = MediaType.TEXT_PLAIN
            )
    )
    @Asynchronous
    @Bulkhead(value = 10, waitingTaskQueue = 16)
    @Timeout(4000)
    @Fallback(value = ErrorHandler.class, applyOn = {Exception.class})
    @CircuitBreaker(requestVolumeThreshold = 40, failureRatio = 0.618, successThreshold = 15)
    public CompletionStage<Response> login(final OpenUPCredential credential ) throws Exception{
        persistence.authenticate(credential.getUsername(), credential.getPassword());
        Set<String> roles = getUserRoles(credential.getUsername(), persistence.getDefaultManager());
        Response response = Response.ok(generateJWT(credential, roles)).build();
        return CompletableFuture.completedStage(response);
    }
    
    String generateJWT(OpenUPCredential credential, Set<String> roles) throws Exception{
        JWT jwt = new JWT();
        jwt.setExp(
                new Date().getTime() 
                        + jwtExpDuration 
                                * ChronoUnit.valueOf(jwtExpTimeUnit)
                                        .getDuration()
                                        .toMillis()
        );
        jwt.setGroups(roles);
        jwt.setIss(issuer);
        jwt.setJti(credential.getUsername() + UUID.randomUUID());
        jwt.setKid("");
        jwt.setSub(credential.getUsername());
        jwt.setUpn(credential.getUsername());
        return generator.generate(jwt);
    }
    
    Set<String> getUserRoles(String userName, EntityManager manager){
        Set<String> roles = new HashSet<>();
        Query query = manager.createNamedQuery("Role.GetUserRoles");
        query.setParameter(1, userName.toUpperCase());
        query.setParameter(2, userName.toUpperCase());
        query.setParameter(3, userName.toUpperCase());
        query.setParameter(4, userName.toUpperCase());
        query.getResultList().forEach(result ->{
            roles.add(result.toString());
        });
        return roles;
    }
}
