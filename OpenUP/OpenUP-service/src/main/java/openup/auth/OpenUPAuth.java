/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.auth;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import java.util.UUID;
import javax.annotation.security.PermitAll;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import openup.config.OpenUPConfigs;
import openup.persistence.OpenUPFactory;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.config.Names;

/**
 *
 * @author FOXCONN
 */
@RequestScoped
@Path("/auth")
@PermitAll
public class OpenUPAuth {
    
    @Inject
    private OpenUPFactory persistence;
    
    @Inject
    private JWTGenerator generator;
    
    @ConfigProperty(name = Names.ISSUER)
    private String issuer;
    
    @ConfigProperty(name = OpenUPConfigs.JWT_EXP_DURATION)
    private Long jwtExpDuration;
    
    @ConfigProperty(name = OpenUPConfigs.JWT_EXP_TIMEUNIT)
    private String jwtExpTimeUnit;
    
    @POST
    Response login(String userName, String password) throws Exception{
        boolean ok = persistence.createEntityManager(userName, password);
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
            jwt.setJti(userName + UUID.randomUUID());
            jwt.setKid("");
            jwt.setSub(userName);
            jwt.setUpn(userName);
            return Response.ok().entity(generator.generate(jwt)).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
}
