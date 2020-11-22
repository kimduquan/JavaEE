/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.webapp.security;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.annotation.FacesConfig;
import javax.inject.Inject;
import javax.security.enterprise.CallerPrincipal;
import javax.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;
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
import openup.api.config.ConfigNames;
import openup.api.security.Token;
import openup.webapp.config.Config;

/**
 *
 * @author FOXCONN
 */
@ApplicationScoped
@BasicAuthenticationMechanismDefinition(realmName = "OpenUP")
@FacesConfig
public class OpenUPIdentityStore implements IdentityStore, RememberMeIdentityStore {
    
    private Map<String, String> tokens;
    
    @Inject
    private Config config;
    
    @PostConstruct
    void postConstruct(){
        tokens = new ConcurrentHashMap<>();
    }
    
    public CredentialValidationResult validate(UsernamePasswordCredential credential){
        CredentialValidationResult result = CredentialValidationResult.INVALID_RESULT;
        Client client = ClientBuilder.newClient();
        MultivaluedMap<String, String> formData = new MultivaluedHashMap<>();
        formData.putSingle("username", credential.getCaller());
        formData.putSingle("password", credential.getPasswordAsString());
        String security = (String)config.getConfig(ConfigNames.OPENUP_SECURITY_URL, "");
        String url = (String)config.getConfig(ConfigNames.OPENUP_URL, "");
        String header = (String)config.getConfig(ConfigNames.OPENUP_SECURITY_JWT_HEADER, "");
        String format = (String)config.getConfig(ConfigNames.OPENUP_SECURITY_JWT_FORMAT, "");
        Response response = client.target(security)
                .queryParam("url", url)
                .request(MediaType.TEXT_PLAIN)
                .post(Entity.form(formData));
        if(response.getStatus() == Response.Status.OK.getStatusCode()){
            String token = response.readEntity(String.class);
            tokens.put(credential.getCaller(), token);
            Token jwt = client.target(security)
                    .request(MediaType.APPLICATION_JSON)
                    .header(
                            header, 
                            String.format(
                                    format, 
                                    token
                            )
                    )
                    .get()
                    .readEntity(Token.class);
            result = new CredentialValidationResult(jwt.getName(), jwt.getGroups());
        }
        client.close();
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
        String security = (String)config.getConfig(ConfigNames.OPENUP_SECURITY_URL, "");
        String header = (String)config.getConfig(ConfigNames.OPENUP_SECURITY_JWT_HEADER, "");
        String format = (String)config.getConfig(ConfigNames.OPENUP_SECURITY_JWT_FORMAT, "");
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
            result = new CredentialValidationResult(jwt.getName(), jwt.getGroups());
        }
        client.close();
        return result;
    }

    @Override
    public String generateLoginToken(CallerPrincipal arg0, Set<String> arg1) {
        return tokens.get(arg0.getName());
    }

    @Override
    public void removeLoginToken(String token) {
        String security = (String)config.getConfig(ConfigNames.OPENUP_SECURITY_URL, "");
        String header = (String)config.getConfig(ConfigNames.OPENUP_SECURITY_JWT_HEADER, "");
        String format = (String)config.getConfig(ConfigNames.OPENUP_SECURITY_JWT_FORMAT, "");
        Client client = ClientBuilder.newClient();
        String user = client.target(security)
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
        tokens.remove(user);
        client.close();
    }
}
