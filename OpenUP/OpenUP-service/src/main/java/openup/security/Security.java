/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.security;

import epf.schema.roles.Role;
import java.io.Serializable;
import java.net.URI;
import java.net.URL;
import java.security.Principal;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import openup.epf.schema.Roles;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.config.Names;
import openup.persistence.Application;
import openup.persistence.Credential;
import openup.persistence.Session;
import org.eclipse.microprofile.jwt.JsonWebToken;
import openup.api.config.ConfigNames;

/**
 *
 * @author FOXCONN
 */
@RequestScoped
public class Security implements openup.api.security.Security, Serializable {
    
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
    @ConfigProperty(name = ConfigNames.JWT_EXPIRE_DURATION)
    private Long jwtExpDuration;
    
    @Inject
    @ConfigProperty(name = ConfigNames.JWT_EXPIRE_TIMEUNIT)
    private ChronoUnit jwtExpTimeUnit;
    
    @Override
    public Response login(
            String username,
            String password,
            URL url) throws Exception{
        ResponseBuilder response = Response.ok();
        
        long time = System.currentTimeMillis() / 1000;
        persistence.putCredential(username, password, time);
        
        Token jwt = buildToken(username, time);
        
        buildTokenID(jwt);
        
        Set<String> aud = new HashSet<>();
        aud.add(url.toString());
        jwt.setAudience(aud);
        
        Set<String> roles = getUserRoles(username, persistence.getDefaultManager());
        jwt.setGroups(roles);
        
        String token = generator.generate(jwt);
        
        jwt.setRawToken(token);
        
        return response.entity(token).build();
    }
    
    @Override
    public Response runAs(
            String role,
            SecurityContext context,
            UriInfo uriInfo
            ) throws Exception{
        ResponseBuilder response = Response.ok();
        Principal principal = context.getUserPrincipal();
        long time = System.currentTimeMillis() / 1000;
        Token jwt = buildToken(principal.getName(), time);
        buildTokenID(jwt);
        buildAudience(jwt, uriInfo.getBaseUri());
        if(buildRoles(jwt, principal, role, response)){
            String token = generator.generate(jwt);
            response.entity(token);
            jwt.setRawToken(token);
        }
        return response.build();
    }
    
    @Override
    public Response logOut(
            SecurityContext context
            ) throws Exception{
        ResponseBuilder response = Response.status(Response.Status.UNAUTHORIZED);
        Principal principal = context.getUserPrincipal();
        if(principal != null){
            Credential credential = persistence.getCredential(principal.getName());
            if(credential != null){
                Session session = credential.getSession(principal);
                if(session != null){
                    credential.removeSession(principal);
                    response = Response.ok(principal.getName());
                }
            }
        }
        return response.build();
    }
    
    @Override
    public Response authenticate(SecurityContext context){
        ResponseBuilder response = Response.status(Response.Status.UNAUTHORIZED);
        Principal principal = context.getUserPrincipal();
        if(principal instanceof JsonWebToken){
            JsonWebToken jwt = (JsonWebToken) principal;
            response.entity(jwt).status(Response.Status.OK);
        }
        return response.build();
    }
    
    void buildAudience(Token token, URI uri){
        Set<String> aud = new HashSet<>();
        aud.add(String.format("%s://%s:%s/", uri.getScheme(), uri.getHost(), uri.getPort()));
        token.setAudience(aud);
    }
    
    void buildTokenID(Token token){
        token.setTokenID(
                String.format(
                        "%s-%s-%s",
                        token.getName(), 
                        UUID.randomUUID(),
                        System.currentTimeMillis()));
    }
    
    boolean buildRoles(Token token, Principal principal, String role, ResponseBuilder response){
        boolean ok = true;
        if(Roles.ADMIN.equalsIgnoreCase(role)){
            if(isAdmin(principal.getName(), persistence.getDefaultManager())){
                Set<String> groups = new HashSet<>();
                groups.add(Roles.ADMIN);
                token.setGroups(groups);
            }
            else{
                response.status(Response.Status.FORBIDDEN);
                ok = false;
            }
        }
        else{
            Set<String> roles = getUserRoles(principal.getName(), persistence.getDefaultManager());
            token.setGroups(roles);
        }
        return ok;
    }
    
    Token buildToken(String username, long time) throws Exception{
        Token jwt = new Token();
        jwt.setIssuedAtTime(time);
        jwt.setExpirationTime(time + Duration.of(jwtExpDuration, jwtExpTimeUnit).getSeconds());
        jwt.setIssuer(issuer);
        jwt.setSubject(username);
        return jwt;
    }
    
    Set<String> getUserRoles(String userName, EntityManager manager){
        Query query = manager.createNamedQuery(Role.GET_USER_ROLES);
        query.setParameter(1, userName.toUpperCase());
        query.setParameter(2, userName.toUpperCase());
        query.setParameter(3, userName.toUpperCase());
        query.setParameter(4, userName.toUpperCase());
        Stream<?> result = query.getResultStream();
        return result.map(Object::toString).collect(Collectors.toSet());
    }
    
    boolean isAdmin(String userName, EntityManager manager){
        Query query = manager.createNamedQuery(Role.IS_ADMIN);
        query.setParameter(1, userName.toUpperCase());
        query.setParameter(2, String.valueOf(true));
        return query.getResultStream().count() > 0;
    }
}
