/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.auth;

import com.ibm.websphere.security.jwt.Claims;
import com.ibm.websphere.security.jwt.JwtBuilder;
import java.io.Serializable;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import openup.config.ConfigNames;

/**
 *
 * @author FOXCONN
 */
@Dependent
public class JWTGenerator implements Serializable {
    
    private PrivateKey privateKey;
    private Base64.Decoder decoder;
    
    @Inject
    @ConfigProperty(name = ConfigNames.MP_JWT_PRIVATE_KEY)
    private String privateKeyText;
    
    @PostConstruct
    void initilize(){
        try {
            decoder = Base64.getUrlDecoder();
            privateKey = KeyFactory.getInstance("RSA")
                        .generatePrivate(
                                new PKCS8EncodedKeySpec(
                                    decoder.decode(privateKeyText)
                                )
                        );
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String generate(JWT jwt) throws Exception{
        return JwtBuilder
                .create()
                .claim(Claims.ISSUER, jwt.getIss())
                .claim(Claims.SUBJECT, jwt.getSub())
                .claim(Claims.EXPIRATION, jwt.getExp().toString())
                .claim(Claims.ISSUED_AT, jwt.getIat().toString())
                .claim(Claims.ID, jwt.getJti())
                .claim("upn", jwt.getUpn())
                .claim("groups", jwt.getGroups().toArray(new String[jwt.getGroups().size()]))
                .signWith("RS256", privateKey)
                .buildJwt()
                .compact();
    }
}
