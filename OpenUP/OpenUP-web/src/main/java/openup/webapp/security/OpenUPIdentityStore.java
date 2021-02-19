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
import epf.util.client.Client;
import epf.util.client.ClientQueue;
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
    
    @Inject
    private ClientQueue clients;
    
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
        URI securityUrl = new URI(config.getConfig(ConfigNames.SECURITY_URL, ""));
        URL audienceUrl = new URL(String.format(
                                    Security.AUDIENCE_URL_FORMAT,
                                    securityUrl.getScheme(), 
                                    securityUrl.getHost(), 
                                    securityUrl.getPort()
                            ));
        Token jwt = null;
        try(Client client = new Client(clients, securityUrl, b -> b)){
        	String token = Security.login(
        			client, 
        			OpenUP.Schema,
					credential.getCaller(),
					PasswordHash.hash(
							credential.getCaller(), 
							credential.getPassword().getValue()
							),
					audienceUrl
					);
            if(!token.isEmpty()){
            	client.authorization(token);
            	jwt = Security.authenticate(client, OpenUP.Schema);
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
        String securityUrl = config.getConfig(ConfigNames.SECURITY_URL, "");
        try(Client client = new Client(clients, new URI(securityUrl), b -> b)) {
        	client.authorization(credential.getToken());
        	Token jwt = Security.authenticate(client, OpenUP.Schema);
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
    	String securityUrl = config.getConfig(ConfigNames.SECURITY_URL, "");
        try(Client client = new Client(clients, new URI(securityUrl), b -> b)) {
            client.authorization(token);
            Security.logOut(client, OpenUP.Schema);
        } 
        catch (Exception ex) {
        	logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
}
