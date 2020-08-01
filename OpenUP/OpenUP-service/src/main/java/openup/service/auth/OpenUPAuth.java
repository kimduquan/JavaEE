/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.service.auth;

import com.nimbusds.jose.JOSEException;
import static com.nimbusds.jose.JOSEObjectType.JWT;
import static com.nimbusds.jose.JWSAlgorithm.RS256;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.auth.LoginConfig;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.config.Names;

@ApplicationScoped
@ApplicationPath("/auth")
@LoginConfig(authMethod="BASIC", realmName="OpenUP")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("Any_Role")
public class OpenUPAuth extends Application {
    
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(OpenUPAuth.class);
        return classes;
    }
    
    private PrivateKey privateKey;
    private Base64.Decoder decoder;
    private RSASSASigner signer;
    
    @ConfigProperty(name = "mp.jwt.issue.privatekey")
    private String privateKeyText;
    
    @ConfigProperty(name = Names.ISSUER)
    private String issuer;
    
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
            signer = new RSASSASigner(privateKey);
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @GET
    public Response generateToken(@Context SecurityContext context){
        
        JWSHeader header = new JWSHeader.Builder(RS256)
                                        .keyID(context.getUserPrincipal().getName())
                                        .type(JWT)
                                        .build();
        
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                                                .audience(issuer)
                                                .expirationTime(new Date())
                                                .issueTime(new Date())
                                                .issuer(issuer)
                                                .jwtID(UUID.randomUUID().toString())
                                                .notBeforeTime(new Date())
                                                .subject(context.getUserPrincipal().getName())
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
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.UNAUTHORIZED)
                .build();
    }
    
    @POST
    public String[] generateKeyPair(String algorithm, int keysize) throws NoSuchAlgorithmException{
        
        KeyPairGenerator generator = KeyPairGenerator.getInstance(algorithm);
        generator.initialize(keysize);
        KeyPair keyPair = generator.generateKeyPair();
        
        PublicKey newPublicKey = keyPair.getPublic();
        PKCS8EncodedKeySpec encodedPublicKey = new PKCS8EncodedKeySpec(newPublicKey.getEncoded());
        byte[] publicKeyBytes = encodedPublicKey.getEncoded();
        
        PrivateKey newPrivateKey = keyPair.getPrivate();
        byte[] privateKeyBytes = newPrivateKey.getEncoded();
        
        Encoder encoder = Base64.getUrlEncoder();
        
        String newPublicKeyText = encoder.encodeToString(publicKeyBytes);
        
        String newPrivateKeyText = encoder.encodeToString(privateKeyBytes);
        
        return new String[]{ newPublicKeyText, newPrivateKeyText};
    }
}
