/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.security;

import epf.schema.OpenUP;
import epf.schema.openup.Role;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.concurrent.TimeUnit;
import openup.client.config.ConfigNames;
import openup.client.security.Header;
import openup.client.security.Security;
import openup.client.security.Token;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author FOXCONN
 */
public class SecurityTest {
    
    private static RestClientBuilder builder;
    private static URL url;
    
    private Security security;
    private Header header;
    
    @BeforeClass
    public static void beforeClass() throws Exception{
        url = new URL(System.getProperty(ConfigNames.OPENUP_URL, ""));
        builder = RestClientBuilder.newBuilder()
                .baseUrl(url);
    }
    
    @Before
    public void before(){
        header = new Header();
        security = builder.register(header)
                .build(Security.class);
    }
    
    @Test
    public void testAuthenticateOK() throws Exception{
        String token = security.login("OpenUP", "any_role1", "any_role", url);
        Assert.assertNotNull("Token", token);
        Assert.assertNotEquals("Token", "", token);
        
        header.setToken(token);
        
        Token jwt = security.authenticate();
        
        Assert.assertNotNull("JWT", jwt);
        Assert.assertNotNull("Audience", jwt.getAudience());
        Assert.assertEquals("Audience.size", 1, jwt.getAudience().size());
        Assert.assertArrayEquals("Audience", new String[]{url.toString()}, jwt.getAudience().toArray());
        
        Assert.assertTrue("ExpirationTime", jwt.getExpirationTime() > 0);
        Assert.assertTrue("ExpirationTime", jwt.getExpirationTime() > jwt.getIssuedAtTime());
        
        Assert.assertNotNull("Groups", jwt.getGroups());
        Assert.assertEquals("Groups.size", 
                1, 
                jwt.getGroups().size()
        );
        Assert.assertArrayEquals(
                "Groups", 
                new String[]{
                    "ANY_ROLE"
                }, 
                jwt.getGroups().toArray()
        );
        
        Assert.assertTrue("IssuedAtTime", jwt.getIssuedAtTime() > 0);
        
        Instant.ofEpochSecond(jwt.getExpirationTime());
        Instant.ofEpochSecond(jwt.getIssuedAtTime());
        Duration duration = Duration.between(
                Instant.ofEpochSecond(jwt.getExpirationTime()), 
                Instant.ofEpochSecond(jwt.getIssuedAtTime())
        );
        Assert.assertEquals("duration", 30, duration.toMinutes());
        
        Assert.assertNotNull("Issuer", jwt.getIssuer());
        Assert.assertEquals("Issuer", "OpenUP", jwt.getIssuer());
        
        Assert.assertNotNull("Name", jwt.getName());
        Assert.assertEquals("Name", "any_role1", jwt.getName());
        
        Assert.assertNotNull("RawToken", jwt.getRawToken());
        Assert.assertEquals("RawToken", token, jwt.getRawToken());
        
        Assert.assertNotNull("Subject", jwt.getSubject());
        Assert.assertEquals("Subject", "any_role1", jwt.getSubject());
        
        Assert.assertNotNull("TokenID", jwt.getTokenID());
        Assert.assertNotEquals("TokenID", "", jwt.getTokenID());
    }
}
