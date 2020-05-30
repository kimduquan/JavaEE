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
import java.io.IOException;
import java.io.PrintWriter;
import java.security.KeyFactory;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;
import javax.security.enterprise.identitystore.LdapIdentityStoreDefinition;
import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.ServletSecurity.TransportGuarantee;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.auth.LoginConfig;

/**
 *
 * @author FOXCONN
 */
 @LoginConfig(authMethod="BASIC", realmName="file")
@WebServlet(name = "AuthServlet", urlPatterns = {"/auth"})
@ServletSecurity(
        value = @HttpConstraint(
                transportGuarantee = TransportGuarantee.CONFIDENTIAL,
                rolesAllowed = {"AnyRole"}
        )
)
@BasicAuthenticationMechanismDefinition( realmName = "file" )
@LdapIdentityStoreDefinition(
        url = "ldap://localhost:10389",
        bindDn = "uid=admin,ou=system",
        bindDnPassword = "admin",
        callerBaseDn = "dc=OpenUP,dc=WSO2,dc=ORG",
        callerSearchBase = "ou=Roles,dc=OpenUP,dc=WSO2,dc=ORG",
        groupSearchBase = "ou=Roles,dc=OpenUP,dc=WSO2,dc=ORG"
)
public class AuthServlet extends HttpServlet {
    
    private PrivateKey privateKey;
    private Decoder decoder;
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
    void postConstruct(){
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
            Logger.getLogger(AuthServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AuthServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AuthServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
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
        String token = "";
        try {
            signedJWT.sign(signer);
            token = signedJWT.serialize();
        } catch (JOSEException ex) {
            Logger.getLogger(AuthServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        response.addHeader(AUTHORIZATION, "Bearer ".concat(token));
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
