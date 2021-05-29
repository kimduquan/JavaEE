/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.persistence.security;

import com.ibm.websphere.security.jwt.InvalidBuilderException;
import com.ibm.websphere.security.jwt.InvalidClaimException;
import com.ibm.websphere.security.jwt.JwtBuilder;
import com.ibm.websphere.security.jwt.JwtException;
import com.ibm.websphere.security.jwt.JwtToken;
import com.ibm.websphere.security.jwt.KeyException;
import epf.client.EPFException;
import epf.client.security.Token;
import epf.client.security.jwt.JWT;
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
    @ConfigProperty(name = JWT.PRIVATE_KEY)
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
     * @throws InvalidBuilderException
     * @throws InvalidClaimException
     * @throws KeyException
     * @throws JwtException
     */
    public Token generate(final Token jwt) throws EPFException {
    	try {
    		final JwtBuilder builder = JwtBuilder.create();
            if(jwt.getAudience() != null && !jwt.getAudience().isEmpty()){
                builder.audience(
                        jwt.getAudience()
                                .stream()
                                .collect(Collectors.toList())
                );
            }
            builder.issuer(jwt.getIssuer())
                    .subject(jwt.getSubject())
                    .expirationTime(jwt.getExpirationTime())
                    .notBefore(jwt.getIssuedAtTime())
                    .jwtId(true)
                    .claim(Claims.iat.name(), jwt.getIssuedAtTime())
                    .claim(Claims.upn.name(), jwt.getName());
            if(jwt.getGroups() != null){
                builder.claim(Claims.groups.name(), jwt.getGroups().toArray(new String[jwt.getGroups().size()]));
            }
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
