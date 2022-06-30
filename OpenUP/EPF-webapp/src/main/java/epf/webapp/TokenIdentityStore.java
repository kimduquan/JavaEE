package epf.webapp;

import java.util.Set;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;
import epf.webapp.internal.GatewayUtil;
import epf.webapp.internal.TokenCredential;
import epf.webapp.internal.TokenPrincipal;
import epf.client.util.Client;
import epf.naming.Naming;
import epf.security.client.Security;
import epf.security.schema.Token;

/**
 *
 * @author FOXCONN
 */
@ApplicationScoped
public class TokenIdentityStore implements IdentityStore {
    
    /**
     * 
     */
    @Inject
    private transient GatewayUtil gatewayUtil;
    
    /**
     * @param credential
     * @return
     * @throws Exception
     */
    public CredentialValidationResult validate(final TokenCredential credential) throws Exception {
        try(Client client = gatewayUtil.newClient(Naming.SECURITY)){
        	client.authorization(credential.getToken());
    		final Token token = Security.authenticate(client);
    		final TokenPrincipal principal = new TokenPrincipal(token.getName(), credential.getToken());
    		return new CredentialValidationResult(principal, token.getGroups());
        }
    }
    
    @Override
    public Set<String> getCallerGroups(final CredentialValidationResult validationResult){
        return validationResult.getCallerGroups();
    }
}
