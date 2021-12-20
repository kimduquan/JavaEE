/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.persistence.internal;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.credential.Password;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.CredentialValidationResult.Status;
import javax.ws.rs.ForbiddenException;
import org.eclipse.microprofile.jwt.JsonWebToken;
import epf.persistence.security.internal.EPFPrincipal;
import epf.persistence.security.internal.IdentityStore;
import epf.security.schema.Token;
import epf.util.EPFException;
import epf.util.MapUtil;
import epf.util.Var;

/**
 *
 * @author FOXCONN
 */
@ApplicationScoped
public class Application {
	
	/**
     * 
     */
    private transient final Map<String, Credential> credentials = new ConcurrentHashMap<>();
    
    /**
     * 
     */
    @Inject
    private transient IdentityStore identityStore;
    
    /**
     * 
     */
    @PreDestroy
    protected void preDestroy(){
    	credentials.forEach((name, credential) -> {
    		try {
				credential.close();
			} 
    		catch (IOException e) {
				e.printStackTrace();
			}
		});
        credentials.clear();
    }
    
    /**
     * @param userName
     * @param password
     * @return
     * @throws EPFException
     */
    public Optional<Credential> login(final String userName, final char... password) throws EPFException {
    	final UsernamePasswordCredential identityCred = new UsernamePasswordCredential(userName, new Password(password));
        final Var<Exception> error = new Var<>();
        credentials.computeIfAbsent(userName, name -> {
            return newCredential(identityCred).orElse(null);
        });
        error.get().ifPresent(ex -> {
        	throw new EPFException(ex);
        });
        final Var<Credential> cred = new Var<>();
        credentials.computeIfPresent(userName, (name, credential) -> {
            if(credential == null){
                credential = newCredential(identityCred).orElse(null);
            }
            else{
                synchronized(credential){
                    if(!credential.getPrincipal().equals(identityCred)){
                    	try {
							credential.close();
						} 
                    	catch (IOException e) {
							e.printStackTrace();
						}
                        credential = newCredential(identityCred).orElse(null);
                    }
                }
            }
            cred.set(credential);
            return credential;
        });
        error.get().ifPresent(ex -> {
        	throw new EPFException(ex);
        });
        return cred.get();
    }
    
    /**
     * @param credential
     * @return
     */
    protected Optional<Credential> newCredential(final UsernamePasswordCredential credential){
        final Optional<EPFPrincipal> principal = getPrincipal(credential);
        return principal.isPresent() ? Optional.of(new Credential(principal.get())) : Optional.empty();
    }
    
    /**
     * @param credential
     * @return
     */
    protected Optional<EPFPrincipal> getPrincipal(final UsernamePasswordCredential credential){
    	Optional<EPFPrincipal> principal = Optional.empty();
    	final CredentialValidationResult validationResult = identityStore.validate(credential);
        if(Status.VALID.equals(validationResult.getStatus()) && validationResult.getCallerPrincipal() instanceof EPFPrincipal) {
        	principal = Optional.of((EPFPrincipal)validationResult.getCallerPrincipal());
        }
        return principal;
    }
    
    /**
     * @param jwt
     * @return
     */
    public Optional<Credential> getCredential(final JsonWebToken jwt){
    	Objects.requireNonNull(jwt, "JsonWebToken");
    	return MapUtil.get(credentials, jwt.getName());
    }
    
    /**
     * @param token
     * @return
     */
    public Optional<Credential> getCredential(final Token token){
    	Objects.requireNonNull(token, "Token");
    	return MapUtil.get(credentials, token.getName());
    }
    
    /**
     * @param jwt
     * @return
     */
    public Optional<Credential> removeCredential(final JsonWebToken jwt){
    	Objects.requireNonNull(jwt, "JsonWebToken");
    	return MapUtil.remove(credentials, jwt.getName());
    }
    
    /**
     * @param token
     * @return
     */
    public Optional<Credential> removeCredential(final Token token){
    	Objects.requireNonNull(token, "Token");
    	return MapUtil.remove(credentials, token.getName());
    }
    
    /**
     * @param jwt
     * @return
     */
    public Session getSession(final JsonWebToken jwt){
    	Objects.requireNonNull(jwt, "JsonWebToken");
    	final Optional<Credential> credential = getCredential(jwt);
    	credential.orElseThrow(ForbiddenException::new);
    	final Optional<Session> session = credential.get().getSessioṇ̣(jwt);
    	session.orElseThrow(ForbiddenException::new);
    	return session.get();
    }
    
    /**
     * @param token
     * @return
     */
    public Session getSession(final Token token){
    	Objects.requireNonNull(token, "Token");
    	final Optional<Credential> credential = getCredential(token);
    	credential.orElseThrow(ForbiddenException::new);
    	final Optional<Session> session = credential.get().getSessioṇ̣(token);
    	session.orElseThrow(ForbiddenException::new);
    	return session.get();
    }
    
    /**
     * @param jwt
     * @return
     */
    public Session removeSession(final JsonWebToken jwt) {
    	Objects.requireNonNull(jwt, "JsonWebToken");
    	final Optional<Credential> credential = getCredential(jwt);
    	credential.orElseThrow(ForbiddenException::new);
    	final Optional<Session> session = credential.get().removeSessioṇ̣(jwt);
    	session.orElseThrow(ForbiddenException::new);
    	return session.get();
    }
    
    /**
     * @param token
     * @return
     */
    public Session removeSession(final Token token) {
    	Objects.requireNonNull(token, "Token");
    	final Optional<Credential> credential = getCredential(token);
    	credential.orElseThrow(ForbiddenException::new);
    	final Optional<Session> session = credential.get().removeSessioṇ̣(token);
    	session.orElseThrow(ForbiddenException::new);
    	return session.get();
    }
}
