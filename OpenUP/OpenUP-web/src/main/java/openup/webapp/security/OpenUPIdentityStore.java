/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.webapp.security;

import java.net.URL;
import java.util.Set;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.CallerPrincipal;
import javax.security.enterprise.authentication.mechanism.http.AutoApplySession;
import javax.security.enterprise.authentication.mechanism.http.RememberMe;
import javax.security.enterprise.credential.BasicAuthenticationCredential;
import javax.security.enterprise.credential.RememberMeCredential;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;
import javax.security.enterprise.identitystore.RememberMeIdentityStore;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import openup.webapp.config.ConfigNames;
import openup.webapp.config.Config;

/**
 *
 * @author FOXCONN
 */
@RememberMe
@ApplicationScoped
public class OpenUPIdentityStore implements IdentityStore, RememberMeIdentityStore {
    
    @Inject
    private Config config;
    
    public CredentialValidationResult validate(BasicAuthenticationCredential credential){
        CredentialValidationResult result = CredentialValidationResult.INVALID_RESULT;
        Client client = ClientBuilder.newClient();
        MultivaluedMap<String, String> formData = new MultivaluedHashMap<>();
        formData.putSingle("username", credential.getCaller());
        formData.putSingle("password", credential.getPasswordAsString());
        String security = config.getConfig(ConfigNames.OPENUP_SECURITY_URL, "");
        String header = config.getConfig(ConfigNames.OPENUP_SECURITY_JWT_HEADER, "");
        String format = config.getConfig(ConfigNames.OPENUP_SECURITY_JWT_FORMAT, "");
        Response response = client.target(security)
                .queryParam("url", "http://localhost:9080/")
                .request(MediaType.TEXT_PLAIN)
                .post(Entity.form(formData));
        String token = "";
        if(response.getStatus() == Response.Status.OK.getStatusCode()){
            token = response.readEntity(String.class);
        }
        client.close();
        
        if(!token.isEmpty()){
            client = ClientBuilder.newClient();
            response = client
                    .target(security)
                    .request(MediaType.APPLICATION_JSON)
                    .header(
                            header, 
                            String.format(
                                    format, 
                                    token
                            )
                    )
                    .get();
            if(response.getStatus() == Response.Status.OK.getStatusCode()){
                Token jwt = response.readEntity(Token.class);
                JWTPrincipal principal = new JWTPrincipal(jwt.getName());
                principal.setToken(jwt.getRawToken());
                result = new CredentialValidationResult(principal, jwt.getGroups());
            }
            client.close();
        }
        return result;
    }
    
    @Override
    public Set<String> getCallerGroups(CredentialValidationResult validationResult){
        return validationResult.getCallerGroups();
    }

    @Override
    public CredentialValidationResult validate(RememberMeCredential credential) {
        CredentialValidationResult result = CredentialValidationResult.INVALID_RESULT;
        Client client = ClientBuilder.newClient();
        String security = config.getConfig(ConfigNames.OPENUP_SECURITY_URL, "");
        String header = config.getConfig(ConfigNames.OPENUP_SECURITY_JWT_HEADER, "");
        String format = config.getConfig(ConfigNames.OPENUP_SECURITY_JWT_FORMAT, "");
        Response response = client.target(security)
                    .request(MediaType.APPLICATION_JSON)
                    .header(
                            header, 
                            String.format(
                                    format, 
                                    credential.getToken()
                            )
                    )
                    .get();
        if(response.getStatus() == Status.OK.getStatusCode()){
            Token jwt = response.readEntity(Token.class);
            JWTPrincipal principal = new JWTPrincipal(jwt.getName());
            principal.setToken(jwt.getRawToken());
            result = new CredentialValidationResult(principal, jwt.getGroups());
        }
        client.close();
        return result;
    }

    @Override
    public String generateLoginToken(CallerPrincipal caller, Set<String> groups) {
        if(caller instanceof JWTPrincipal){
            JWTPrincipal principal = (JWTPrincipal) caller;
            return principal.getToken();
        }
        return "";
    }

    @Override
    public void removeLoginToken(String token) {
        String security = config.getConfig(ConfigNames.OPENUP_SECURITY_URL, "");
        String header = config.getConfig(ConfigNames.OPENUP_SECURITY_JWT_HEADER, "");
        String format = config.getConfig(ConfigNames.OPENUP_SECURITY_JWT_FORMAT, "");
        Client client = ClientBuilder.newClient();
        client.target(security)
                .request(MediaType.TEXT_PLAIN)
                .header(
                        header, 
                        String.format(
                                format, 
                                token
                        )
                )
                .delete()
                .readEntity(String.class);
        client.close();
    }
}
