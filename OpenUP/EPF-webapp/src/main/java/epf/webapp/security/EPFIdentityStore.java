/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.webapp.security;

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
public class EPFIdentityStore implements IdentityStore, RememberMeIdentityStore {
	
	@Inject
    private ConfigSource config;
    
    @Inject
    private ClientQueue clients;
    
    @Inject
    private Logger logger;
    
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
        URI securityUrl = new URI(config.getValue(ConfigNames.SECURITY_URL));
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
        			null,
					credential.getCaller(),
					PasswordHash.hash(
							credential.getCaller(), 
							credential.getPassword().getValue()
							),
					audienceUrl
					);
            if(!token.isEmpty()){
            	client.authorization(token);
            	jwt = Security.authenticate(client, null);
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
        String securityUrl = config.getValue(ConfigNames.SECURITY_URL);
        try(Client client = new Client(clients, new URI(securityUrl), b -> b)) {
        	client.authorization(credential.getToken());
        	Token jwt = Security.authenticate(client, null);
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
    	String securityUrl = config.getValue(ConfigNames.SECURITY_URL);
        try(Client client = new Client(clients, new URI(securityUrl), b -> b)) {
            client.authorization(token);
            Security.logOut(client, null);
        } 
        catch (Exception ex) {
        	logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
}
