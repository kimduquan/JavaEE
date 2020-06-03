/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.micro.auth;

import com.nimbusds.jose.JOSEException;
import static com.nimbusds.jose.JOSEObjectType.JWT;
import static com.nimbusds.jose.JWSAlgorithm.RS256;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import java.security.KeyFactory;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import javax.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 *
 * @author FOXCONN
 */
@Path("/")
@RequestScoped
public class Auth {
    
    private PrivateKey privateKey;
    private Base64.Decoder decoder;
    private RSASSASigner signer;
    
    @Inject
    @ConfigProperty(name = "mp.jwt.issue.privatekey")
    private String privateKeyText;
    
    @Inject
    @ConfigProperty(name = "mp.jwt.verify.issuer")
    private String issuer;
    
    @Inject
    private Principal principal;
    
    @PostConstruct
    void initilize(){
        try {
            if(decoder == null){
                decoder = Base64.getUrlDecoder();
            }
            if(privateKey == null){
                privateKey = KeyFactory.getInstance("RSA")
                        .generatePrivate(
                                new PKCS8EncodedKeySpec(
                                    decoder.decode(privateKeyText)
                                )
                        );
            }
            if(signer == null){
                signer = new RSASSASigner(privateKey);
            }
        } catch (Exception ex) {
            Logger.getLogger(AuthApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @GET
    public Response generateToken(){
        
        JWSHeader header = new JWSHeader.Builder(RS256)
                                        .keyID(principal.getName())
                                        .type(JWT)
                                        .build();
        
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                                                .audience(issuer)
                                                .expirationTime(new Date())
                                                .issueTime(new Date())
                                                .issuer(issuer)
                                                .jwtID(UUID.randomUUID().toString())
                                                .notBeforeTime(new Date())
                                                .subject(principal.getName())
                                                .build();
        
        SignedJWT signedJWT = new SignedJWT(header, claimsSet);
        
        try {
            signedJWT.sign(signer);
            String token = signedJWT.serialize();
            return Response.ok()
                    .header(AUTHORIZATION, "Bearer ".concat(token))
                    .entity(token)
                    .build();
        } 
        catch (JOSEException ex) {
            Logger.getLogger(AuthApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.UNAUTHORIZED)
                .build();
    }
}
