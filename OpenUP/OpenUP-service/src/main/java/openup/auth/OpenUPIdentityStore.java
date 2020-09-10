package openup.auth;

import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.Query;
import javax.security.enterprise.CallerPrincipal;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.RememberMeCredential;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;
import javax.security.enterprise.identitystore.RememberMeIdentityStore;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.config.Names;
import openup.config.ConfigNames;
import openup.persistence.Application;

@ApplicationScoped
public class OpenUPIdentityStore implements IdentityStore, RememberMeIdentityStore {
	
	@Inject
	private Application persistence;
	
	@Inject
    private JWTGenerator generator;
    
    @Inject
    @ConfigProperty(name = Names.ISSUER)
    private String issuer;
    
    @Inject
    @ConfigProperty(name = ConfigNames.JWT_EXP_DURATION)
    private Long jwtExpDuration;
    
    @Inject
    @ConfigProperty(name = ConfigNames.JWT_EXP_TIMEUNIT)
    private String jwtExpTimeUnit;

	public CredentialValidationResult validate(Credential credential) {
    	if(credential instanceof UsernamePasswordCredential) {
    		UsernamePasswordCredential usernamePassword = (UsernamePasswordCredential) credential;
        	if(persistence.authenticate(usernamePassword.getCaller(), usernamePassword.getPasswordAsString())){
        		return new CredentialValidationResult(usernamePassword.getCaller());
            }
            return CredentialValidationResult.INVALID_RESULT;
    	}
    	return CredentialValidationResult.NOT_VALIDATED_RESULT;
    }
    
    public Set<String> getCallerGroups(CredentialValidationResult validationResult){
    	Set<String> roles = new HashSet<>();
        Query query = persistence.getDefaultManager().createNamedQuery("Role.GetUserRoles");
        CallerPrincipal caller = validationResult.getCallerPrincipal();
        query.setParameter(1, caller.getName().toUpperCase());
        query.setParameter(2, caller.getName().toUpperCase());
        query.setParameter(3, caller.getName().toUpperCase());
        query.setParameter(4, caller.getName().toUpperCase());
        Stream<?> result = query.getResultStream();
        result.forEach(value -> {
            roles.add(value.toString());
        });
        return roles;
    }

	@Override
	public CredentialValidationResult validate(RememberMeCredential credential) {
		return null;
	}

	@Override
	public String generateLoginToken(CallerPrincipal callerPrincipal, Set<String> groups) {
		JWT jwt = new JWT();
        jwt.setExp(
                new Date().getTime() 
                        + jwtExpDuration 
                                * ChronoUnit.valueOf(jwtExpTimeUnit)
                                        .getDuration()
                                        .toMillis()
        );
        jwt.setGroups(groups);
        jwt.setIss(issuer);
        jwt.setJti(callerPrincipal.getName() + UUID.randomUUID());
        jwt.setKid("");
        jwt.setSub(callerPrincipal.getName());
        jwt.setUpn(callerPrincipal.getName());
        try {
			return generator.generate(jwt);
		} 
        catch (Exception e) {
		}
        return "";
	}

	@Override
	public void removeLoginToken(String token) {
	}
}
