/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.security;

import com.ibm.websphere.security.jwt.JwtBuilder;
import com.ibm.websphere.security.jwt.JwtToken;
import epf.client.config.ConfigNames;
import epf.client.security.Token;
import java.io.Serializable;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.logging.Level;
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
    private static final Logger logger = Logger.getLogger(TokenGenerator.class.getName());

    private PrivateKey privateKey;
    private Base64.Decoder decoder;
    
    @Inject
    @ConfigProperty(name = ConfigNames.MP_JWT_PRIVATE_KEY)
    private String privateKeyText;
    
    @PostConstruct
    void postConstruct(){
        try {
            decoder = Base64.getUrlDecoder();
            privateKey = KeyFactory.getInstance("RSA")
                        .generatePrivate(
                                new PKCS8EncodedKeySpec(
                                    decoder.decode(privateKeyText)
                                )
                        );
        } 
        catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
    
    public Token generate(Token jwt) throws Exception{
        JwtBuilder builder = JwtBuilder.create();
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
        JwtToken generatedJwt = builder.buildJwt();
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
        return jwt;
    }
}
