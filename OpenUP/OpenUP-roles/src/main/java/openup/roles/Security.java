/**
 * 
 */
package openup.roles;

import java.net.URI;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.jwt.JsonWebToken;
import com.ibm.websphere.security.jwt.JwtBuilder;
import com.ibm.websphere.security.jwt.JwtToken;
import epf.client.gateway.Gateway;
import epf.client.persistence.Queries;
import epf.util.SystemUtil;
import epf.util.client.Client;
import epf.util.client.ClientQueue;
import openup.schema.OpenUP;
import openup.schema.roles.Role;

/**
 * @author PC
 *
 */
@Path("roles")
@RolesAllowed(epf.schema.roles.Role.DEFAULT_ROLE)
@ApplicationScoped
public class Security implements openup.client.security.Security {
	
	/**
     * 
     */
    private transient PrivateKey privateKey;
	
	/**
	 * 
	 */
	@Inject
    private JsonWebToken jwt;
	
	/**
	 * 
	 */
	@Inject
    @ConfigProperty(name = "mp.jwt.issue.privatekey")
    private transient String privateKeyText;
	
	/**
     * 
     */
    @Inject
    private transient Logger logger;
    
    /**
     * 
     */
    @Inject
    private transient ClientQueue clients;
	
	/**
     * 
     */
    @PostConstruct
    protected void postConstruct(){
        try {
        	final Base64.Decoder decoder = Base64.getUrlDecoder();
            privateKey = KeyFactory.getInstance("RSA")
                        .generatePrivate(
                                new PKCS8EncodedKeySpec(
                                    decoder.decode(privateKeyText)
                                )
                        );
        } 
        catch (Exception ex) {
            logger.throwing(getClass().getName(), "postConstruct", ex);
        }
    }
    
    /**
     * @return
     * @throws Exception
     */
    protected Set<String> getRoles() throws Exception{
    	try(Client client = new Client(clients, new URI(SystemUtil.getenv(Gateway.GATEWAY_URL)).resolve("persistence"), b -> b)){
    		client.authorization(jwt.getRawToken());
    		try(Response response = Queries.executeQuery(
    				client, 
    				target -> target.path(OpenUP.ROLE).matrixParam("name", jwt.getName()).path("roles"), 
    				null, 
    				null)){
    			final List<Role> roles = response.readEntity(new GenericType<List<Role>>() {});
    			return roles.stream().map(r -> r.getName()).collect(Collectors.toSet());
    		}
    	}
    }

	@Override
	public String authorize() throws Exception {
		final Set<String> roles = getRoles();
		final JwtBuilder builder = JwtBuilder.create()
				.audience(jwt.getAudience().stream().collect(Collectors.toList()))
				.expirationTime(jwt.getExpirationTime())
				.issuer(jwt.getIssuer())
				.jwtId(true)
				.notBefore(jwt.getIssuedAtTime())
				.subject(jwt.getSubject())
				.claim(Claims.iat.name(), jwt.getIssuedAtTime())
                .claim(Claims.upn.name(), jwt.getName())
                .claim(Claims.groups.name(), roles)
                .signWith("RS256", privateKey);
		final JwtToken token = builder.buildJwt();
		return token.compact();
	}
}
