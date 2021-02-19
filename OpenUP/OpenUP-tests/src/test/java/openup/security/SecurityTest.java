/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.security;

import static epf.client.security.Security.AUDIENCE_URL_FORMAT;
import java.net.URI;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotAllowedException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import openup.TestUtil;
import openup.schema.OpenUP;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import epf.client.security.Security;
import epf.client.security.Token;
import epf.util.client.Client;
import epf.util.security.PasswordHash;

/**
 *
 * @author FOXCONN
 */
public class SecurityTest {
	
	private static URI securityUrl;
    
    @BeforeClass
    public static void beforeClass() throws Exception{
    	securityUrl = new URI(TestUtil.gateway_url().toString() + "security");
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
        MultivaluedMap<String, String> form = new MultivaluedHashMap<>();
        form.putSingle("username", username);
        if(needHash){
            password = PasswordHash.hash(username, password.toCharArray());
        }
        form.putSingle("password_hash", password);
        String queryUrl = "";
        if(targetUrl != null){
            queryUrl = targetUrl.toString();
        }
        final String url = queryUrl;
        String token;
        try(Client client = TestUtil.newClient(securityUrl)){
        	token = client
        			.request(
        					target -> target.path(unit).queryParam("url", url), 
        					req -> req.accept(MediaType.TEXT_PLAIN)
        					)
        			.post(Entity.form(form), String.class);
        }
        return token;
    }
    
    String logOut(String token, String unit) throws Exception{
    	String name;
    	try(Client client = TestUtil.newClient(securityUrl)){
    		client.authorization(token);
    		name = Security.logOut(client, unit);
    	}
        return name;
    }
    
    Token authenticate(String token, String unit) throws Exception{
    	Token t;
    	try(Client client = TestUtil.newClient(securityUrl)){
    		client.authorization(token);
    		t = Security.authenticate(client, unit);
    	}
        return t;
    }
    
    @Test
    public void testLoginOK() throws Exception{
        String token = login(OpenUP.Schema, "any_role1", "any_role", TestUtil.gateway_url(), true);
        Assert.assertNotNull("Token", token);
        Assert.assertNotEquals("Token", "", token);
        logOut(token, OpenUP.Schema);
    }
    
    @Test
    public void testLoginOKAfterLoginInvalidPassword() throws Exception{
        Assert.assertThrows(
        		NotAuthorizedException.class, 
                () -> {
                    login(OpenUP.Schema, "any_role1", "Invalid", TestUtil.gateway_url(), true);
                }
        );
        String token = login(OpenUP.Schema, "any_role1", "any_role", TestUtil.gateway_url(), true);
        Assert.assertNotNull("Token", token);
        Assert.assertNotEquals("Token", "", token);
        logOut(token, OpenUP.Schema);
    }
    
    //@Ignore
    @Test(expected = NotAllowedException.class)
    public void testLoginEmptyUnit() throws Exception{
        login("", "any_role1", "any_role", TestUtil.gateway_url(), false);
    }
    
    @Test(expected = BadRequestException.class)
    public void testLoginBlankUnit() throws Exception{
        login("     ", "any_role1", "any_role", TestUtil.gateway_url(), false);
    }
    
    @Test(expected = BadRequestException.class)
    public void testLoginInvalidUnit() throws Exception{
        login("Invalid", "any_role1", "any_role", TestUtil.gateway_url(), false);
    }
    
    @Test(expected = BadRequestException.class)
    public void testLoginEmptyUser() throws Exception{
        login(OpenUP.Schema, "", "any_role", TestUtil.gateway_url(), false);
    }
    
    @Test(expected = BadRequestException.class)
    public void testLoginBlankUser() throws Exception{
        login(OpenUP.Schema, "     ", "any_role", TestUtil.gateway_url(), false);
    }
    
    @Test(expected = NotAuthorizedException.class)
    public void testLoginInvalidUser() throws Exception{
        login(OpenUP.Schema, "Invalid", "any_role", TestUtil.gateway_url(), true);
    }
    
    @Test(expected = BadRequestException.class)
    public void testLoginEmptyPassword() throws Exception{
        login(OpenUP.Schema, "any_role1", "", TestUtil.gateway_url(), false);
    }
    
    @Test(expected = BadRequestException.class)
    public void testLoginBlankPassword() throws Exception{
        login(OpenUP.Schema, "any_role1", "    ", TestUtil.gateway_url(), false);
    }
    
    @Test(expected = NotAuthorizedException.class)
    public void testLoginInvalidPassword() throws Exception{
        login(OpenUP.Schema, "any_role1", "Invalid", TestUtil.gateway_url(), true);
    }
    
    @Test(expected = NotAuthorizedException.class)
    public void testLoginInvalidPasswordAfterLoginOK() throws Exception{
        login(OpenUP.Schema, "any_role1", "any_role", TestUtil.gateway_url(), true);
        login(OpenUP.Schema, "any_role1", "Invalid", TestUtil.gateway_url(), true);
    }
    
    @Test(expected = NotFoundException.class)
    public void testLoginEmptyUrl() throws Exception{
        login(OpenUP.Schema, "any_role1", "any_role", null, false);
    }
    
    @Test(expected = BadRequestException.class)
    public void testLoginBlankUrl() throws Exception{
        MultivaluedMap<String, String> form = new MultivaluedHashMap<>();
        form.putSingle("username", "any_role1");
        form.putSingle("password_hash", "any_role");
        form.putSingle("url", "    ");
        try(Client client = TestUtil.newClient(TestUtil.gateway_url().toURI())){
        	client.request(
        			target -> target.path("security").path(OpenUP.Schema), 
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
        try(Client client = TestUtil.newClient(TestUtil.gateway_url().toURI())){
        	client.request(
        			target -> target.path("security").path(OpenUP.Schema), 
        			req -> req.accept(MediaType.TEXT_PLAIN)
        			)
        	.post(Entity.form(form), String.class);
        }
    }
    
    @Test
    public void testAuthenticateOK() throws Exception{
        String token = login(OpenUP.Schema, "any_role1", "any_role", TestUtil.gateway_url(), true);
        Token jwt = authenticate(token, OpenUP.Schema);
        Assert.assertNotNull("JWT", jwt);
        Assert.assertNotNull("Audience", jwt.getAudience());
        Assert.assertEquals("Audience.size", 1, jwt.getAudience().size());
        Assert.assertArrayEquals(
                "Audience", 
                new String[]{
                    String.format(
                            AUDIENCE_URL_FORMAT, 
                            TestUtil.gateway_url().getProtocol(), 
                            TestUtil.gateway_url().getHost(), 
                            TestUtil.gateway_url().getPort()
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
        Assert.assertEquals("Issuer", "OpenUP", jwt.getIssuer());
        Assert.assertNotNull("Name", jwt.getName());
        Assert.assertEquals("Name", "any_role1", jwt.getName());
        Assert.assertNotNull("RawToken", jwt.getRawToken());
        Assert.assertEquals("RawToken", token, jwt.getRawToken());
        Assert.assertNotNull("Subject", jwt.getSubject());
        Assert.assertEquals("Subject", "any_role1", jwt.getSubject());
        Assert.assertNotNull("TokenID", jwt.getTokenID());
        Assert.assertNotEquals("TokenID", "", jwt.getTokenID());
        logOut(token, OpenUP.Schema);
    }
    
    @Test(expected = NotAuthorizedException.class)
    public void testAuthenticateEmptyToken() throws Exception{
        authenticate("", OpenUP.Schema);
    }
    
    @Test(expected = NotAuthorizedException.class)
    public void testAuthenticateBlankToken() throws Exception{
        authenticate("    ", OpenUP.Schema);
    }
    
    @Test(expected = NotAuthorizedException.class)
    public void testAuthenticateInvalidToken() throws Exception{
        authenticate("Invalid", OpenUP.Schema);
    }
    
    @Test(expected = NotAuthorizedException.class)
    public void testLogoutOK() throws Exception {
        String token = login(OpenUP.Schema, "any_role1", "any_role", TestUtil.gateway_url(), true);
        logOut(token, OpenUP.Schema);
        authenticate(token, OpenUP.Schema);
    }
    
    //@Ignore
    @Test
    public void testLogoutEmptyUnit() throws Exception{
        String token = login(OpenUP.Schema, "any_role1", "any_role", TestUtil.gateway_url(), true);
        Assert.assertThrows(
        		NotAllowedException.class, 
                () -> {
                    logOut(token, "");
                }
        );
        logOut(token, OpenUP.Schema);
    }
    
    @Test
    public void testLogoutBlankUnit() throws Exception{
        String token = login(OpenUP.Schema, "any_role1", "any_role", TestUtil.gateway_url(), true);
        Assert.assertThrows(
        		BadRequestException.class, 
                () -> {
                    logOut(token, "    ");
                }
        );
        logOut(token, OpenUP.Schema);
    }
    
    @Test
    public void testLogoutInvalidUnit() throws Exception{
        String token = login(OpenUP.Schema, "any_role1", "any_role", TestUtil.gateway_url(), true);
        Assert.assertThrows(
        		BadRequestException.class, 
                () -> {
                    logOut(token, "Invalid");
                }
        );
        logOut(token, OpenUP.Schema);
    }
}
