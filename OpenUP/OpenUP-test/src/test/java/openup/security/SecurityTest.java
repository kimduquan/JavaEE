/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.security;

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
import openup.client.security.PasswordHash;
import static openup.client.security.Security.AUDIENCE_URL_FORMAT;
import openup.client.security.Token;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author FOXCONN
 */
public class SecurityTest {
    
    @BeforeClass
    public static void beforeClass() throws Exception{
        TestUtil.beforeClass();
    }
    
    @AfterClass
    public static void afterClass(){
        TestUtil.afterClass();
    }
    
    @Before
    public void before(){
        TestUtil.header().setToken(null);
    }
    
    @After
    public void after(){
        TestUtil.header().setToken(null);
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
        return TestUtil.client().target(TestUtil.url().toString() + "security/")
                .path(unit)
                .queryParam("url", queryUrl)
                .request(MediaType.TEXT_PLAIN)
                .post(Entity.form(form), String.class);
    }
    
    String logOut(String unit) throws Exception{
        return TestUtil.client().target(TestUtil.url().toString() + "security/")
                .path(unit)
                .request(MediaType.TEXT_PLAIN)
                .delete(String.class);
    }
    
    Token authenticate() throws Exception{
        return TestUtil.client().target(TestUtil.url().toString() + "security/")
                .request(MediaType.APPLICATION_JSON)
                .get(Token.class);
    }
    
    @Test
    public void testLoginOK() throws Exception{
        String token = login("OpenUP", "any_role1", "any_role", TestUtil.url(), true);
        Assert.assertNotNull("Token", token);
        Assert.assertNotEquals("Token", "", token);
        
        TestUtil.header().setToken(token);
        logOut("OpenUP");
    }
    
    @Test
    public void testLoginOKAfterLoginInvalidPassword() throws Exception{
        Assert.assertThrows(
        		NotAuthorizedException.class, 
                () -> {
                    login("OpenUP", "any_role1", "Invalid", TestUtil.url(), true);
                }
        );
        String token = login("OpenUP", "any_role1", "any_role", TestUtil.url(), true);
        Assert.assertNotNull("Token", token);
        Assert.assertNotEquals("Token", "", token);
        
        TestUtil.header().setToken(token);
        logOut("OpenUP");
    }
    
    //@Ignore
    @Test(expected = NotAllowedException.class)
    public void testLoginEmptyUnit() throws Exception{
        login("", "any_role1", "any_role", TestUtil.url(), false);
    }
    
    @Test(expected = BadRequestException.class)
    public void testLoginBlankUnit() throws Exception{
        login("     ", "any_role1", "any_role", TestUtil.url(), false);
    }
    
    @Test(expected = BadRequestException.class)
    public void testLoginInvalidUnit() throws Exception{
        login("Invalid", "any_role1", "any_role", TestUtil.url(), false);
    }
    
    @Test(expected = BadRequestException.class)
    public void testLoginEmptyUser() throws Exception{
        login("OpenUP", "", "any_role", TestUtil.url(), false);
    }
    
    @Test(expected = BadRequestException.class)
    public void testLoginBlankUser() throws Exception{
        login("OpenUP", "     ", "any_role", TestUtil.url(), false);
    }
    
    @Test(expected = NotAuthorizedException.class)
    public void testLoginInvalidUser() throws Exception{
        login("OpenUP", "Invalid", "any_role", TestUtil.url(), true);
    }
    
    @Test(expected = BadRequestException.class)
    public void testLoginEmptyPassword() throws Exception{
        login("OpenUP", "any_role1", "", TestUtil.url(), false);
    }
    
    @Test(expected = BadRequestException.class)
    public void testLoginBlankPassword() throws Exception{
        login("OpenUP", "any_role1", "    ", TestUtil.url(), false);
    }
    
    @Test(expected = NotAuthorizedException.class)
    public void testLoginInvalidPassword() throws Exception{
        login("OpenUP", "any_role1", "Invalid", TestUtil.url(), true);
    }
    
    @Test(expected = NotAuthorizedException.class)
    public void testLoginInvalidPasswordAfterLoginOK() throws Exception{
        login("OpenUP", "any_role1", "any_role", TestUtil.url(), true);
        login("OpenUP", "any_role1", "Invalid", TestUtil.url(), true);
    }
    
    @Test(expected = NotFoundException.class)
    public void testLoginEmptyUrl() throws Exception{
        login("OpenUP", "any_role1", "any_role", null, false);
    }
    
    @Test(expected = BadRequestException.class)
    public void testLoginBlankUrl() throws Exception{
        MultivaluedMap<String, String> form = new MultivaluedHashMap<>();
        form.putSingle("username", "any_role1");
        form.putSingle("password_hash", "any_role");
        form.putSingle("url", "    ");
        TestUtil.client().target(TestUtil.url().toURI())
                .path("security")
                .path("OpenUP")
                .request(MediaType.TEXT_PLAIN)
                .post(Entity.form(form), String.class);
    }
    
    @Test(expected = BadRequestException.class)
    public void testLoginInvalidUrl() throws Exception{
        MultivaluedMap<String, String> form = new MultivaluedHashMap<>();
        form.putSingle("username", "any_role1");
        form.putSingle("password_hash", "any_role");
        form.putSingle("url", "Invalid");
        TestUtil.client().target(TestUtil.url().toURI())
                .path("security")
                .path("OpenUP")
                .request(MediaType.TEXT_PLAIN)
                .post(Entity.form(form), String.class);
    }
    
    @Test
    public void testAuthenticateOK() throws Exception{
        String token = login("OpenUP", "any_role1", "any_role", TestUtil.url(), true);
        
        TestUtil.header().setToken(token);
        
        Token jwt = authenticate();
        
        Assert.assertNotNull("JWT", jwt);
        Assert.assertNotNull("Audience", jwt.getAudience());
        Assert.assertEquals("Audience.size", 1, jwt.getAudience().size());
        Assert.assertArrayEquals(
                "Audience", 
                new String[]{
                    String.format(
                            AUDIENCE_URL_FORMAT, 
                            TestUtil.url().getProtocol(), 
                            TestUtil.url().getHost(), 
                            TestUtil.url().getPort()
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
                    "ANY_ROLE"
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
        
        logOut("OpenUP");
    }
    
    @Test(expected = NotAuthorizedException.class)
    public void testAuthenticateEmptyToken() throws Exception{
        TestUtil.header().setToken("");
        authenticate();
    }
    
    @Test(expected = NotAuthorizedException.class)
    public void testAuthenticateBlankToken() throws Exception{
        TestUtil.header().setToken("    ");
        authenticate();
    }
    
    @Test(expected = NotAuthorizedException.class)
    public void testAuthenticateInvalidToken() throws Exception{
        TestUtil.header().setToken("Invalid");
        authenticate();
    }
    
    @Test(expected = NotAuthorizedException.class)
    public void testLogoutOK() throws Exception {
        String token = login("OpenUP", "any_role1", "any_role", TestUtil.url(), true);
        TestUtil.header().setToken(token);
        
        logOut("OpenUP");
        
        authenticate();
    }
    
    //@Ignore
    @Test
    public void testLogoutEmptyUnit() throws Exception{
        String token = login("OpenUP", "any_role1", "any_role", TestUtil.url(), true);
        TestUtil.header().setToken(token);
        
       Assert.assertThrows(
        		NotAllowedException.class, 
                () -> {
                    logOut("");
                }
        );
        logOut("OpenUP");
    }
    
    @Test
    public void testLogoutBlankUnit() throws Exception{
        String token = login("OpenUP", "any_role1", "any_role", TestUtil.url(), true);
        TestUtil.header().setToken(token);
        
        Assert.assertThrows(
        		BadRequestException.class, 
                () -> {
                    logOut("    ");
                }
        );
        
        logOut("OpenUP");
    }
    
    @Test
    public void testLogoutInvalidUnit() throws Exception{
        String token = login("OpenUP", "any_role1", "any_role", TestUtil.url(), true);
        TestUtil.header().setToken(token);
        
        Assert.assertThrows(
        		BadRequestException.class, 
                () -> {
                    logOut("Invalid");
                }
        );
        
        logOut("OpenUP");
    }
}
