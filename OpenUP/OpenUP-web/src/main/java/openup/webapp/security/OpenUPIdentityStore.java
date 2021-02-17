/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.webapp.security;

import openup.schema.OpenUP;
import java.net.URI;
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
import epf.client.config.ConfigNames;
import epf.client.config.ConfigSource;
import epf.client.security.Security;
import epf.client.security.Token;
import epf.util.client.RestClient;
import epf.util.security.PasswordHash;

/**
 *
 * @author FOXCONN
 */
@ApplicationScoped
public class OpenUPIdentityStore implements IdentityStore, RememberMeIdentityStore {
	
	private final static Logger logger = Logger.getLogger(OpenUPIdentityStore.class.getName());
    
    @Inject
    private ConfigSource config;
    
    public CredentialValidationResult validate(BasicAuthenticationCredential credential) throws Exception{
        CredentialValidationResult result = CredentialValidationResult.INVALID_RESULT;
        Token token = login(credential);
        if(token != null){
            TokenPrincipal principal = new TokenPrincipal(credential.getCaller(), token);
            result = new CredentialValidationResult(
                    principal, 
                    principal
                            .getToken()
                            .getGroups()
            );
        }
        return result;
    }
    
    Token login(BasicAuthenticationCredential credential) throws Exception{
        String base = config.getConfig(ConfigNames.OPENUP_GATEWAY_URL, "");
        URL baseUrl = new URL(base);
        URL audienceUrl = new URL(String.format(
                                    Security.AUDIENCE_URL_FORMAT,
                                    baseUrl.getProtocol(), 
                                    baseUrl.getHost(), 
                                    baseUrl.getPort()
                            ));
        String token;
        Token jwt = null;
        try(RestClient restClient = new RestClient(baseUrl.toURI(), b -> b)){
        	token = restClient
        			.build(Security.class)
        			.login(
        					OpenUP.Schema,
        					credential.getCaller(),
        					PasswordHash.hash(
        							credential.getCaller(), 
        							credential.getPassword().getValue()
        							),
        					audienceUrl
        					);
            if(!token.isEmpty()){
                jwt = restClient
                		.authorization(token)
                		.build(Security.class)
                		.authenticate();
            }
        }
        return jwt;
    }
    
    @Override
    public Set<String> getCallerGroups(CredentialValidationResult validationResult){
        return validationResult.getCallerGroups();
    }

    @Override
    public CredentialValidationResult validate(RememberMeCredential credential) {
        CredentialValidationResult result = CredentialValidationResult.INVALID_RESULT;
        try(RestClient restClient = new RestClient(new URI(config.getConfig(ConfigNames.OPENUP_GATEWAY_URL, "")), b -> b)) {
            Token jwt = restClient
            		.authorization(credential.getToken())
            		.build(Security.class)
            		.authenticate();
            if(jwt != null){
                TokenPrincipal principal = new TokenPrincipal(jwt.getName(), jwt);
                result = new CredentialValidationResult(principal, jwt.getGroups());
            }
        } 
        catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
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
        try(RestClient restClient = new RestClient(new URI(config.getConfig(ConfigNames.OPENUP_GATEWAY_URL, "")), b -> b)) {
            restClient
            .authorization(token)
            .build(Security.class)
            .logOut(OpenUP.Schema);
        } 
        catch (Exception ex) {
        	logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
}
