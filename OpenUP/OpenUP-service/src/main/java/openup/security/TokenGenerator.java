/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.security;

import com.ibm.websphere.security.jwt.JwtBuilder;
import java.io.Serializable;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import openup.config.Config;

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
	
	private PrivateKey privateKey;
    private Base64.Decoder decoder;
    
    @Inject
    @ConfigProperty(name = Config.MP_JWT_PRIVATE_KEY)
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
            Logger.getLogger(getClass().getName()).throwing(getClass().getName(), "postConstruct", ex);
        }
    }
    
    public String generate(JsonWebToken jwt) throws Exception{
        return JwtBuilder
                .create()
                .issuer(jwt.getIss())
                .subject(jwt.getSub())
                .expirationTime(jwt.getExp())
                .jwtId(true)
                .claim("upn", jwt.getUpn())
                .claim("groups", jwt.getGroups().toArray(new String[jwt.getGroups().size()]))
                .signWith("RS256", privateKey)
                .buildJwt()
                .compact();
    }
}
