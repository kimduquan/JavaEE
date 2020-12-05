/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.webapp.security;

import java.net.URL;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.CallerPrincipal;
import javax.security.enterprise.credential.BasicAuthenticationCredential;
import javax.security.enterprise.credential.RememberMeCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;
import javax.security.enterprise.identitystore.RememberMeIdentityStore;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import openup.client.Client;
import openup.client.security.Token;
import openup.client.config.ConfigNames;
import openup.client.security.Security;
import openup.webapp.config.Config;

/**
 *
 * @author FOXCONN
 */
@ApplicationScoped
public class OpenUPIdentityStore implements IdentityStore, RememberMeIdentityStore {
    
    @Inject
    private Config config;
    
    public CredentialValidationResult validate(BasicAuthenticationCredential credential){
        CredentialValidationResult result = CredentialValidationResult.INVALID_RESULT;
        String url = config.getConfig(ConfigNames.OPENUP_SECURITY_URL, "");
        MultivaluedMap<String, String> formData = new MultivaluedHashMap<>();
        formData.putSingle("username", credential.getCaller());
        formData.putSingle("password", credential.getPasswordAsString());
        String header = config.getConfig(ConfigNames.OPENUP_SECURITY_JWT_HEADER, Security.REQUEST_HEADER_NAME);
        String format = config.getConfig(ConfigNames.OPENUP_SECURITY_JWT_FORMAT, Security.REQUEST_HEADER_FORMAT);
        String baseUrl = config.getConfig(ConfigNames.OPENUP_URL, url);
        String token = "";
        try(Client client = new Client(url)){
            URL localUrl = new URL(baseUrl);
            try(Response response = client
                    .getWebTarget()
                    .queryParam(
                            "url", 
                            String.format(
                                    Security.AUDIENCE_URL_FORMAT,
                                    localUrl.getProtocol(), 
                                    localUrl.getHost(), 
                                    localUrl.getPort()
                            )
                    )
                    .request(MediaType.TEXT_PLAIN)
                    .post(Entity.form(formData))){
                if(response.getStatus() == Response.Status.OK.getStatusCode()){
                    token = response.readEntity(String.class);
                }
            }
        } 
        catch (Exception ex) {
            Logger.getLogger(OpenUPIdentityStore.class.getName()).log(Level.SEVERE, null, ex);
        }
        Token jwt = null;
        if(!token.isEmpty()){
            try(Client client = new Client(url)){
                try(Response response = client
                        .getWebTarget()
                        .request(MediaType.APPLICATION_JSON)
                        .header(
                                header, 
                                String.format(
                                        format, 
                                        token
                                ))
                        .get()){
                    jwt = response.readEntity(Token.class);
                }
            } 
            catch (Exception ex) {
                Logger.getLogger(OpenUPIdentityStore.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(jwt != null){
            TokenPrincipal principal = new TokenPrincipal(jwt.getName());
            principal.setToken(jwt);
            result = new CredentialValidationResult(principal, jwt.getGroups());
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
        String url = config.getConfig(ConfigNames.OPENUP_SECURITY_URL, "");
        String header = config.getConfig(ConfigNames.OPENUP_SECURITY_JWT_HEADER, Security.REQUEST_HEADER_NAME);
        String format = config.getConfig(ConfigNames.OPENUP_SECURITY_JWT_FORMAT, Security.REQUEST_HEADER_FORMAT);
        Token jwt = null;
        try(Client client = new Client(url)){
            try(Response response = client
                    .getWebTarget()
                    .request(MediaType.APPLICATION_JSON)
                    .header(
                            header, 
                            String.format(
                                    format, 
                                    credential.getToken()
                            )
                    )
                    .get()){
                jwt = response.readEntity(Token.class);
            }
        } 
        catch (Exception ex) {
            Logger.getLogger(OpenUPIdentityStore.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(jwt != null){
            TokenPrincipal principal = new TokenPrincipal(jwt.getName());
            principal.setToken(jwt);
            result = new CredentialValidationResult(principal, jwt.getGroups());
        }
        return result;
    }

    @Override
    public String generateLoginToken(CallerPrincipal caller, Set<String> groups) {
        if(caller instanceof TokenPrincipal){
            TokenPrincipal principal = (TokenPrincipal) caller;
            return principal.getToken().getRawToken();
        }
        return "";
    }

    @Override
    public void removeLoginToken(String token) {
        String url = config.getConfig(ConfigNames.OPENUP_SECURITY_URL, "");
        String header = config.getConfig(ConfigNames.OPENUP_SECURITY_JWT_HEADER, Security.REQUEST_HEADER_NAME);
        String format = config.getConfig(ConfigNames.OPENUP_SECURITY_JWT_FORMAT, Security.REQUEST_HEADER_FORMAT);
        try(Client client = new Client(url)){
            try(Response response = client
                    .getWebTarget()
                    .request(MediaType.TEXT_PLAIN)
                    .header(
                        header, 
                        String.format(
                                format, 
                                token
                        ))
                    .delete()){
            }
        } 
        catch (Exception ex) {
            Logger.getLogger(OpenUPIdentityStore.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
