/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.security;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import javax.net.ssl.SSLContext;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.NotAllowedException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response.Status;
import openup.client.config.ConfigNames;
import openup.client.security.Header;
import openup.client.security.PasswordHash;
import static openup.client.security.Security.AUDIENCE_URL_FORMAT;
import openup.client.security.Token;
import openup.client.ssl.DefaultHostnameVerifier;
import openup.client.ssl.DefaultSSLContext;
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
    
    private static SSLContext sslContext;
    private static ClientBuilder builder;
    private static URL url;
    private static Header header;
    private static Client client;
    
    @BeforeClass
    public static void beforeClass() throws Exception{
        url = new URL(System.getProperty(ConfigNames.OPENUP_GATEWAY_URL, ""));
        sslContext = DefaultSSLContext.build();
        header = new Header();
        
        builder = ClientBuilder.newBuilder()
                .hostnameVerifier(new DefaultHostnameVerifier())
                .sslContext(sslContext)
                .register(JacksonJsonProvider.class)
                .register(header);
        
        client = builder.build();
    }
    
    @AfterClass
    public static void afterClass(){
        client.close();
    }
    
    @Before
    public void before(){
        header.setToken(null);
    }
    
    @After
    public void after(){
        header.setToken(null);
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
        return client.target(url.toString() + "security/")
                .path(unit)
                .queryParam("url", queryUrl)
                .request(MediaType.TEXT_PLAIN)
                .post(Entity.form(form), String.class);
    }
    
    String logOut(String unit) throws Exception{
        return client.target(url.toString() + "security/")
                .path(unit)
                .request(MediaType.TEXT_PLAIN)
                .delete(String.class);
    }
    
    Token authenticate() throws Exception{
        return client.target(url.toString() + "security/")
                .request(MediaType.APPLICATION_JSON)
                .get(Token.class);
    }
    
    @Test
    public void testLoginOK() throws Exception{
        String token = login("OpenUP", "any_role1", "any_role", url, true);
        Assert.assertNotNull("Token", token);
        Assert.assertNotEquals("Token", "", token);
        
        header.setToken(token);
        logOut("OpenUP");
    }
    
    @Test
    public void testLoginOKAfterLoginInvalidPassword() throws Exception{
        WebApplicationException ex = Assert.assertThrows(
                WebApplicationException.class, 
                () -> {
                    login("OpenUP", "any_role1", "Invalid", url, true);
                }
        );
        Assert.assertNotNull("WebApplicationException", ex);
        String token = login("OpenUP", "any_role1", "any_role", url, true);
        Assert.assertNotNull("Token", token);
        Assert.assertNotEquals("Token", "", token);
        
        header.setToken(token);
        logOut("OpenUP");
    }
    
    @Test(expected = NotAllowedException.class)
    public void testLoginEmptyUnit() throws Exception{
        login("", "any_role1", "any_role", url, false);
    }
    
    @Test(expected = BadRequestException.class)
    public void testLoginBlankUnit() throws Exception{
        login("     ", "any_role1", "any_role", url, false);
    }
    
    @Test(expected = BadRequestException.class)
    public void testLoginInvalidUnit() throws Exception{
        login("Invalid", "any_role1", "any_role", url, false);
    }
    
    @Test(expected = BadRequestException.class)
    public void testLoginEmptyUser() throws Exception{
        login("OpenUP", "", "any_role", url, false);
    }
    
    @Test(expected = BadRequestException.class)
    public void testLoginBlankUser() throws Exception{
        login("OpenUP", "     ", "any_role", url, false);
    }
    
    @Test(expected = NotAuthorizedException.class)
    public void testLoginInvalidUser() throws Exception{
        login("OpenUP", "Invalid", "any_role", url, true);
    }
    
    @Test(expected = BadRequestException.class)
    public void testLoginEmptyPassword() throws Exception{
        login("OpenUP", "any_role1", "", url, false);
    }
    
    @Test(expected = BadRequestException.class)
    public void testLoginBlankPassword() throws Exception{
        login("OpenUP", "any_role1", "    ", url, false);
    }
    
    @Test(expected = NotAuthorizedException.class)
    public void testLoginInvalidPassword() throws Exception{
        login("OpenUP", "any_role1", "Invalid", url, true);
    }
    
    @Test(expected = NotAuthorizedException.class)
    public void testLoginInvalidPasswordAfterLoginOK() throws Exception{
        login("OpenUP", "any_role1", "any_role", url, true);
        login("OpenUP", "any_role1", "Invalid", url, true);
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
        client.target(url.toURI())
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
        client.target(url.toURI())
                .path("security")
                .path("OpenUP")
                .request(MediaType.TEXT_PLAIN)
                .post(Entity.form(form), String.class);
    }
    
    @Test
    public void testAuthenticateOK() throws Exception{
        String token = login("OpenUP", "any_role1", "any_role", url, true);
        
        header.setToken(token);
        
        Token jwt = authenticate();
        
        Assert.assertNotNull("JWT", jwt);
        Assert.assertNotNull("Audience", jwt.getAudience());
        Assert.assertEquals("Audience.size", 1, jwt.getAudience().size());
        Assert.assertArrayEquals(
                "Audience", 
                new String[]{
                    String.format(
                            AUDIENCE_URL_FORMAT, 
                            url.getProtocol(), 
                            url.getHost(), 
                            url.getPort()
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
        header.setToken("");
        authenticate();
    }
    
    @Test(expected = NotAuthorizedException.class)
    public void testAuthenticateBlankToken() throws Exception{
        header.setToken("    ");
        authenticate();
    }
    
    @Test(expected = NotAuthorizedException.class)
    public void testAuthenticateInvalidToken() throws Exception{
        header.setToken("Invalid");
        authenticate();
    }
    
    @Test(expected = NotAuthorizedException.class)
    public void testLogoutOK() throws Exception {
        String token = login("OpenUP", "any_role1", "any_role", url, true);
        header.setToken(token);
        
        logOut("OpenUP");
        
        authenticate();
    }
    
    @Test
    public void testLogoutEmptyUnit() throws Exception{
        String token = login("OpenUP", "any_role1", "any_role", url, true);
        header.setToken(token);
        
        ClientErrorException ex = Assert.assertThrows(
                ClientErrorException.class, 
                () -> {
                    logOut("");
                }
        );
        Assert.assertEquals(
                "Status",
                Status.METHOD_NOT_ALLOWED.getStatusCode(), 
                ex.getResponse().getStatus()
        );
        
        logOut("OpenUP");
    }
    
    @Test
    public void testLogoutBlankUnit() throws Exception{
        String token = login("OpenUP", "any_role1", "any_role", url, true);
        header.setToken(token);
        
        ClientErrorException ex = Assert.assertThrows(
                ClientErrorException.class, 
                () -> {
                    logOut("    ");
                }
        );
        Assert.assertEquals(
                "Status",
                Status.BAD_REQUEST.getStatusCode(), 
                ex.getResponse().getStatus()
        );
        
        logOut("OpenUP");
    }
    
    @Test
    public void testLogoutInvalidUnit() throws Exception{
        String token = login("OpenUP", "any_role1", "any_role", url, true);
        header.setToken(token);
        
        WebApplicationException ex = Assert.assertThrows(
                WebApplicationException.class, 
                () -> {
                    logOut("Invalid");
                }
        );
        Assert.assertEquals(
                "Status",
                Status.BAD_REQUEST.getStatusCode(), 
                ex.getResponse().getStatus()
        );
        
        logOut("OpenUP");
    }
}
