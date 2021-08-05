/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.persistence.security;

import com.ibm.websphere.security.jwt.InvalidClaimException;
import com.ibm.websphere.security.jwt.JwtBuilder;
import com.ibm.websphere.security.jwt.JwtToken;
import epf.client.EPFException;
import epf.client.security.Token;
import epf.client.security.jwt.JWTConfig;
import java.io.Serializable;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.Claims;

/**
 *
 * @author FOXCONN
 */
@Dependent
public class TokenGenerator implements Serializable {
    
    /**
    * 
    */
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    private transient PrivateKey privateKey;
    
    /**
     * 
     */
    @Inject
    @ConfigProperty(name = JWTConfig.PRIVATE_KEY)
    private transient String privateKeyText;
    
    /**
     * 
     */
    @Inject
    private transient Logger logger;
    
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
     * @param jwt
     * @return
     * @throws EPFException
     */
    public Token generate(final Token jwt) throws EPFException {
    	try {
    		final JwtBuilder builder = JwtBuilder.create();
    		builder.audience(
                    jwt.getAudience()
                            .stream()
                            .collect(Collectors.toList())
                            )
    				.issuer(jwt.getIssuer())
                    .subject(jwt.getSubject())
                    .expirationTime(jwt.getExpirationTime())
                    .notBefore(jwt.getIssuedAtTime())
                    .jwtId(true)
                    .claim(Claims.iat.name(), jwt.getIssuedAtTime())
                    .claim(Claims.upn.name(), jwt.getName())
                    .claim(Claims.groups.name(), jwt.getGroups().toArray(new String[jwt.getGroups().size()]));
            jwt.getClaims().forEach((claim, value) -> {
            	try {
					builder.claim(claim, value);
				} 
            	catch (InvalidClaimException e) {
            		logger.throwing(getClass().getName(), "generate", e);
				}
            });
            builder.signWith("RS256", privateKey);
            final JwtToken generatedJwt = builder.buildJwt();
            jwt.setAudience(
            		generatedJwt
            		.getClaims()
            		.getAudience()
            		.stream()
            		.collect(Collectors.toSet())
            		);
            jwt.setExpirationTime(generatedJwt.getClaims().getExpiration());
            jwt.setIssuedAtTime(generatedJwt.getClaims().getIssuedAt());
            jwt.setIssuer(generatedJwt.getClaims().getIssuer());
            jwt.setName(generatedJwt.getClaims().get(Claims.upn.name()).toString());
            jwt.setSubject(generatedJwt.getClaims().getSubject());
            jwt.setTokenID(generatedJwt.getClaims().getJwtId());
            jwt.setRawToken(generatedJwt.compact());
    	}
    	catch(Exception ex) {
    		throw new EPFException(ex);
    	}
        return jwt;
    }
}
