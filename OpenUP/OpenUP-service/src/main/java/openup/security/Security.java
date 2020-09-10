/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.security;

import java.io.Serializable;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.logging.Logger;
import java.util.stream.Stream;
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
import openup.error.ExceptionHandler;
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
import openup.config.Config;
import openup.persistence.Application;

/**
 *
 * @author FOXCONN
 */
@RequestScoped
@Path("security")
@PermitAll
public class Security implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
    private Application persistence;
    
    @Inject
    private TokenGenerator generator;
    
    @Inject
    @ConfigProperty(name = Names.ISSUER)
    private String issuer;
    
    @Inject
    @ConfigProperty(name = Config.JWT_EXP_DURATION)
    private Long jwtExpDuration;
    
    @Inject
    @ConfigProperty(name = Config.JWT_EXP_TIMEUNIT)
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
                            implementation = Account.class
                    )
            )
    )
    @APIResponse(
            name = "token", 
            description = "Token",
            responseCode = "200",
            content = @Content(
                    mediaType = MediaType.TEXT_PLAIN
            )
    )
    @Asynchronous
    @Bulkhead(value = 10, waitingTaskQueue = 16)
    @Timeout(4000)
    @Fallback(value = ExceptionHandler.class, applyOn = {Exception.class})
    @CircuitBreaker(requestVolumeThreshold = 40, failureRatio = 0.618, successThreshold = 15)
    public CompletionStage<Response> login(Account credential ) throws Exception{
        persistence.createFactory(credential.getUsername(), credential.getPassword());
        Set<String> roles = getUserRoles(credential.getUsername(), persistence.getDefaultManager());
        CompletionStage<Response> response = CompletableFuture.supplyAsync(() -> {
            String token = "";
			try {
	        	JsonWebToken jwt = createJWT(credential);
	            jwt.setGroups(roles);
	            token = generator.generate(jwt);
			} 
			catch (Exception e) {
				Logger.getLogger(getClass().getName()).throwing(getClass().getName(), "login", e);
			}
            return Response.ok(token).build();
        });
        return response;
    }
    
    JsonWebToken createJWT(Account credential) throws Exception{
        JsonWebToken jwt = new JsonWebToken();
        jwt.setExp(
                new Date().getTime() 
                        + jwtExpDuration 
                                * ChronoUnit.valueOf(jwtExpTimeUnit)
                                        .getDuration()
                                        .toMillis()
        );
        jwt.setIss(issuer);
        jwt.setJti(credential.getUsername() + UUID.randomUUID());
        jwt.setKid("");
        jwt.setSub(credential.getUsername());
        jwt.setUpn(credential.getUsername());
        return jwt;
    }
    
    Set<String> getUserRoles(String userName, EntityManager manager){
        Set<String> roles = new HashSet<>();
        Query query = manager.createNamedQuery("Role.GetUserRoles");
        query.setParameter(1, userName.toUpperCase());
        query.setParameter(2, userName.toUpperCase());
        query.setParameter(3, userName.toUpperCase());
        query.setParameter(4, userName.toUpperCase());
        Stream<?> result = query.getResultStream();
        result.forEach(value -> {
            roles.add(value.toString());
        });
        return roles;
    }
}
