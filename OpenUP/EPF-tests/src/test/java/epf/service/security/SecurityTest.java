/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.service.security;

import static epf.client.security.Security.AUDIENCE_URL_FORMAT;
import java.net.URI;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import epf.client.security.Security;
import epf.client.security.Token;
import epf.schema.EPF;
import epf.service.ClientUtil;
import epf.service.RegistryUtil;
import epf.util.client.Client;
import epf.util.logging.Logging;
import epf.util.security.PasswordHelper;

/**
 *
 * @author FOXCONN
 */
public class SecurityTest {
	
	private static final Logger logger = Logging.getLogger(SecurityTest.class.getName());
	private static URI securityUrl;
    
    @BeforeClass
    public static void beforeClass(){
    	securityUrl = RegistryUtil.lookup("security", null);
    }
    
    @AfterClass
    public static void afterClass(){
    }
    
    @Before
    public void before(){
    }
    
    @After
    public void after(){
    }
    
    String login(String unit, String username, String password, URL targetUrl, boolean needHash) throws Exception{
        if(needHash){
        	password = PasswordHelper.hash(username, password.toCharArray());
        }
        String token;
        try(Client client = ClientUtil.newClient(securityUrl)){
        	token = Security.login(client, unit, username, password, targetUrl);
        }
        return token;
    }
    
    String logOut(String token, String unit) throws Exception{
    	String name;
    	try(Client client = ClientUtil.newClient(securityUrl)){
    		client.authorization(token);
    		name = Security.logOut(client, unit);
    	}
        return name;
    }
    
    Token authenticate(String token, String unit) throws Exception{
    	Token t;
    	try(Client client = ClientUtil.newClient(securityUrl)){
    		client.authorization(token);
    		t = Security.authenticate(client, unit);
    	}
        return t;
    }
    
    @Test
    public void testLoginOK() {
		try {
			String token = login(null, "any_role1", "any_role", securityUrl.toURL(), true);
	        Assert.assertNotNull("Token", token);
	        Assert.assertNotEquals("Token", "", token);
	        logOut(token, null);
		} 
		catch (Exception e) {
			logger.log(Level.SEVERE, "testLoginOK", e);
		}
    }
    
    @Test
    public void testLoginOKAfterLoginInvalidPassword() {
        Assert.assertThrows(
        		NotAuthorizedException.class, 
                () -> {
                    login(null, "any_role1", "Invalid", securityUrl.toURL(), true);
                }
        );
        try {
			String token = login(null, "any_role1", "any_role", securityUrl.toURL(), true);
			Assert.assertNotNull("Token", token);
			Assert.assertNotEquals("Token", "", token);
			logOut(token, null);
		} 
        catch (Exception e) {
			logger.log(Level.SEVERE, "testLoginOKAfterLoginInvalidPassword", e);
		}
    }
    
    @Test(expected = BadRequestException.class)
    public void testLoginEmptyUnit() throws Exception{
        login("", "any_role1", "any_role", securityUrl.toURL(), false);
    }
    
    @Test(expected = BadRequestException.class)
    public void testLoginBlankUnit() throws Exception{
        login("     ", "any_role1", "any_role", securityUrl.toURL(), false);
    }
    
    @Test(expected = BadRequestException.class)
    public void testLoginInvalidUnit() throws Exception{
        login("Invalid", "any_role1", "any_role", securityUrl.toURL(), false);
    }
    
    @Test(expected = BadRequestException.class)
    public void testLoginEmptyUser() throws Exception{
        login(null, "", "any_role", securityUrl.toURL(), false);
    }
    
    @Test(expected = BadRequestException.class)
    public void testLoginBlankUser() throws Exception{
        login(null, "     ", "any_role", securityUrl.toURL(), false);
    }
    
    @Test(expected = NotAuthorizedException.class)
    public void testLoginInvalidUser() throws Exception{
        login(null, "Invalid", "any_role", securityUrl.toURL(), true);
    }
    
    @Test(expected = BadRequestException.class)
    public void testLoginEmptyPassword() throws Exception{
        login(null, "any_role1", "", securityUrl.toURL(), false);
    }
    
    @Test(expected = BadRequestException.class)
    public void testLoginBlankPassword() throws Exception{
        login(null, "any_role1", "    ", securityUrl.toURL(), false);
    }
    
    @Test(expected = NotAuthorizedException.class)
    public void testLoginInvalidPassword() throws Exception{
        login(null, "any_role1", "Invalid", securityUrl.toURL(), true);
    }
    
    @Test(expected = NotAuthorizedException.class)
    public void testLoginInvalidPasswordAfterLoginOK() throws Exception{
        login(null, "any_role1", "any_role", securityUrl.toURL(), true);
        login(null, "any_role1", "Invalid", securityUrl.toURL(), true);
    }
    
    @Test(expected = BadRequestException.class)
    public void testLoginEmptyUrl() throws Exception{
        login(null, "any_role1", "any_role", null, false);
    }
    
    @Test(expected = BadRequestException.class)
    public void testLoginBlankUrl() throws Exception{
        MultivaluedMap<String, String> form = new MultivaluedHashMap<>();
        form.putSingle("username", "any_role1");
        form.putSingle("password_hash", "any_role");
        form.putSingle("url", "    ");
        try(Client client = ClientUtil.newClient(securityUrl)){
        	client.request(
        			target -> target, 
        			req -> req.accept(MediaType.TEXT_PLAIN)
        			)
        	.post(Entity.form(form), String.class);
        }
    }
    
    @Test(expected = BadRequestException.class)
    public void testLoginInvalidUrl() throws Exception{
        MultivaluedMap<String, String> form = new MultivaluedHashMap<>();
        form.putSingle("username", "any_role1");
        form.putSingle("password_hash", "any_role");
        form.putSingle("url", "Invalid");
        try(Client client = ClientUtil.newClient(securityUrl)){
        	client.request(
        			target -> target, 
        			req -> req.accept(MediaType.TEXT_PLAIN)
        			)
        	.post(Entity.form(form), String.class);
        }
    }
    
    @Test
    public void testAuthenticateOK() throws Exception{
        String token = login(null, "any_role1", "any_role", securityUrl.toURL(), true);
        Token jwt = authenticate(token, null);
        Assert.assertNotNull("JWT", jwt);
        Assert.assertNotNull("Audience", jwt.getAudience());
        Assert.assertEquals("Audience.size", 1, jwt.getAudience().size());
        Assert.assertArrayEquals(
                "Audience", 
                new String[]{
                    String.format(
                            AUDIENCE_URL_FORMAT, 
                            securityUrl.getScheme(), 
                            securityUrl.getHost(), 
                            securityUrl.getPort()
                    )
                }, 
                jwt.getAudience().toArray()
        );
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
                    "Any_Role"
                }, 
                jwt.getGroups().toArray()
        );
        Assert.assertTrue("IssuedAtTqime", jwt.getIssuedAtTime() > 0);
        Duration duration = Duration.between( 
                Instant.ofEpochSecond(jwt.getIssuedAtTime()),
                Instant.ofEpochSecond(jwt.getExpirationTime())
        );
        Assert.assertEquals("duration", 30, duration.toMinutes());
        Assert.assertNotNull("Issuer", jwt.getIssuer());
        Assert.assertEquals("Issuer", EPF.SCHEMA, jwt.getIssuer());
        Assert.assertNotNull("Name", jwt.getName());
        Assert.assertEquals("Name", "any_role1", jwt.getName());
        Assert.assertNotNull("RawToken", jwt.getRawToken());
        Assert.assertEquals("RawToken", token, jwt.getRawToken());
        Assert.assertNotNull("Subject", jwt.getSubject());
        Assert.assertEquals("Subject", "any_role1", jwt.getSubject());
        Assert.assertNotNull("TokenID", jwt.getTokenID());
        Assert.assertNotEquals("TokenID", "", jwt.getTokenID());
        logOut(token, null);
    }
    
    @Test(expected = NotAuthorizedException.class)
    public void testAuthenticateEmptyToken() throws Exception{
        authenticate("", null);
    }
    
    @Test(expected = NotAuthorizedException.class)
    public void testAuthenticateBlankToken() throws Exception{
        authenticate("    ", null);
    }
    
    @Test(expected = NotAuthorizedException.class)
    public void testAuthenticateInvalidToken() throws Exception{
        authenticate("Invalid", null);
    }
    
    @Test(expected = NotAuthorizedException.class)
    public void testLogoutOK() throws Exception {
        String token = login(null, "any_role1", "any_role", securityUrl.toURL(), true);
        logOut(token, null);
        authenticate(token, null);
    }
    
    //@Ignore
    @Test
    public void testLogoutEmptyUnit() throws Exception{
        String token = login(null, "any_role1", "any_role", securityUrl.toURL(), true);
        Assert.assertThrows(
        		BadRequestException.class, 
                () -> {
                    logOut(token, "");
                }
        );
        logOut(token, null);
    }
    
    @Test
    public void testLogoutBlankUnit() throws Exception{
        String token = login(null, "any_role1", "any_role", securityUrl.toURL(), true);
        Assert.assertThrows(
        		BadRequestException.class, 
                () -> {
                    logOut(token, "    ");
                }
        );
        logOut(token, null);
    }
    
    @Test
    public void testLogoutInvalidUnit() throws Exception{
        String token = login(null, "any_role1", "any_role", securityUrl.toURL(), true);
        Assert.assertThrows(
        		BadRequestException.class, 
                () -> {
                    logOut(token, "Invalid");
                }
        );
        logOut(token, null);
    }
}
