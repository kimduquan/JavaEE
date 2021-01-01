/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.webapp.security;

import epf.schema.OpenUP;
import java.net.URL;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.CallerPrincipal;
import javax.security.enterprise.credential.BasicAuthenticationCredential;
import javax.security.enterprise.credential.RememberMeCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;
import javax.security.enterprise.identitystore.RememberMeIdentityStore;
import openup.client.security.Token;
import openup.client.config.ConfigNames;
import openup.client.config.ConfigSource;
import openup.client.security.Security;
import openup.client.security.Header;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

/**
 *
 * @author FOXCONN
 */
@ApplicationScoped
public class OpenUPIdentityStore implements IdentityStore, RememberMeIdentityStore {
    
    private Map<String, Session> sessions;
    
    @Inject
    private ConfigSource config;
    
    @PostConstruct
    void postConstruct(){
        sessions = new ConcurrentHashMap<>();
    }
    
    @PreDestroy
    void preDestroy(){
        sessions.clear();
    }
    
    public CredentialValidationResult validate(BasicAuthenticationCredential credential) throws Exception{
        CredentialValidationResult result = CredentialValidationResult.INVALID_RESULT;
        sessions.computeIfPresent(credential.getCaller(), (name, session) -> {
            if(session != null 
                    && !session.getCredential().compareTo(
                            credential.getCaller(), 
                            credential.getPasswordAsString())
                    ){
                session = null;
            }
            if(session == null){
                try {
                    Token token = login(credential);
                    if(token != null){
                        TokenPrincipal principal = new TokenPrincipal(credential.getCaller(), token);
                        session = new Session(credential, principal);
                    }
                } 
                catch (Exception ex) {
                    Logger.getLogger(OpenUPIdentityStore.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return session;
        });
        sessions.computeIfAbsent(credential.getCaller(), (name) -> {
            try {
                Token token = login(credential);
                if(token != null){
                    TokenPrincipal principal = new TokenPrincipal(credential.getCaller(), token);
                    return new Session(credential, principal);
                }
            } 
            catch (Exception ex) {
                Logger.getLogger(OpenUPIdentityStore.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        });
        Session session = sessions.get(credential.getCaller());
        if(session != null){
            result = new CredentialValidationResult(
                    session.getPrincipal(), 
                    session.getPrincipal()
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
        Header header = new Header();
        Security service = RestClientBuilder
                .newBuilder()
                .baseUrl(baseUrl)
                .register(header)
                .build(Security.class);
        String token = service.login(
                OpenUP.Schema,
                credential.getCaller(),
                credential.getPasswordAsString(),
                audienceUrl
        );
        Token jwt = null;
        if(!token.isEmpty()){
            header.setToken(token);
            jwt = service.authenticate();
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
        try {
            String base = config.getConfig(ConfigNames.OPENUP_GATEWAY_URL, "");
            URL baseUrl = new URL(base);
            Header header = new Header();
            Security service = RestClientBuilder
                    .newBuilder()
                    .baseUrl(baseUrl)
                    .register(header)
                    .build(Security.class);
            header.setToken(credential.getToken());
            Token jwt = service.authenticate();
            if(jwt != null){
                TokenPrincipal principal = new TokenPrincipal(jwt.getName(), jwt);
                result = new CredentialValidationResult(principal, jwt.getGroups());
            }
        } 
        catch (Exception ex) {
            Logger.getLogger(OpenUPIdentityStore.class.getName()).log(Level.SEVERE, null, ex);
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
        try {
            String base = config.getConfig(ConfigNames.OPENUP_GATEWAY_URL, "");
            URL baseUrl = new URL(base);
            Header header = new Header();
            Security service = RestClientBuilder
                    .newBuilder()
                    .baseUrl(baseUrl)
                    .register(header)
                    .build(Security.class);
            header.setToken(token);
            service.logOut(OpenUP.Schema);
        } 
        catch (Exception ex) {
            Logger.getLogger(OpenUPIdentityStore.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
