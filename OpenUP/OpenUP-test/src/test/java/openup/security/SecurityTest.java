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
import javax.ws.rs.NotAllowedException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import openup.client.config.ConfigNames;
import openup.client.security.Header;
import openup.client.security.Security;
import static openup.client.security.Security.AUDIENCE_URL_FORMAT;
import openup.client.security.Token;
import openup.client.ssl.DefaultHostnameVerifier;
import openup.client.ssl.DefaultSSLContext;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
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
    private static RestClientBuilder restBuilder;
    private static ClientBuilder builder;
    private static URL url;
    private static Header header;
    
    private static Security security;
    private static Client client;
    
    @BeforeClass
    public static void beforeClass() throws Exception{
        url = new URL(System.getProperty(ConfigNames.OPENUP_GATEWAY_URL, ""));
        sslContext = DefaultSSLContext.build();
        header = new Header();
        restBuilder = RestClientBuilder.newBuilder()
                .hostnameVerifier(new DefaultHostnameVerifier())
                .sslContext(sslContext)
                .register(JacksonJsonProvider.class)
                .baseUrl(url)
                .register(header);
        
        builder = ClientBuilder.newBuilder()
                .hostnameVerifier(new DefaultHostnameVerifier())
                .sslContext(sslContext)
                .register(JacksonJsonProvider.class)
                .register(header);
        
        security = restBuilder.build(Security.class);
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
    
    @Test
    public void testLoginOK() throws Exception{
        String token = security.login("OpenUP", "any_role1", "any_role", url);
        Assert.assertNotNull("Token", token);
        Assert.assertNotEquals("Token", "", token);
        
        header.setToken(token);
        security.logOut("OpenUP");
    }
    
    @Test
    public void testLoginOKAfterLoginInvalidPassword() throws Exception{
        Assert.assertThrows(
                WebApplicationException.class, 
                () -> {
                    security.login("OpenUP", "any_role1", "Invalid", url);
                }
        );
        String token = security.login("OpenUP", "any_role1", "any_role", url);
        Assert.assertNotNull("Token", token);
        Assert.assertNotEquals("Token", "", token);
        
        header.setToken(token);
        security.logOut("OpenUP");
    }
    
    @Test(expected = NotAllowedException.class)
    public void testLoginEmptyUnit() throws Exception{
        MultivaluedMap<String, String> form = new MultivaluedHashMap<>();
        form.putSingle("username", "any_role1");
        form.putSingle("password", "any_role");
        form.putSingle("url", url.toString());
        client.target(url.toString() + "security/")
                .path("")
                .request(MediaType.TEXT_PLAIN)
                .post(Entity.form(form), String.class);
    }
    
    @Test(expected = BadRequestException.class)
    public void testLoginBlankUnit() throws Exception{
        MultivaluedMap<String, String> form = new MultivaluedHashMap<>();
        form.putSingle("username", "any_role1");
        form.putSingle("password", "any_role");
        form.putSingle("url", url.toString());
        client.target(url.toString() + "security/")
                .path("     ")
                .request(MediaType.TEXT_PLAIN)
                .post(Entity.form(form), String.class);
    }
    
    @Test
    public void testLoginInvalidUnit() throws Exception{
        WebApplicationException ex = Assert.assertThrows(
                WebApplicationException.class, 
                () -> {
                    security.login("Invalid", "any_role1", "any_role", url);
                }
        );
        Assert.assertEquals(
                "Status",
                Status.BAD_REQUEST.getStatusCode(), 
                ex.getResponse().getStatus()
        );
    }
    
    @Test
    public void testLoginEmptyUser() throws Exception{
        WebApplicationException ex = Assert.assertThrows(
                WebApplicationException.class, 
                () -> {
                    security.login("OpenUP", "", "any_role", url);
                }
        );
        Assert.assertEquals(
                "Status",
                Status.BAD_REQUEST.getStatusCode(), 
                ex.getResponse().getStatus()
        );
    }
    
    @Test
    public void testLoginBlankUser() throws Exception{
        WebApplicationException ex = Assert.assertThrows(
                WebApplicationException.class, 
                () -> {
                    security.login("OpenUP", "     ", "any_role", url);
                }
        );
        Assert.assertEquals(
                "Status",
                Status.BAD_REQUEST.getStatusCode(), 
                ex.getResponse().getStatus()
        );
    }
    
    @Test
    public void testLoginInvalidUser() throws Exception{
        WebApplicationException ex = Assert.assertThrows(
                WebApplicationException.class, 
                () -> {
                    security.login("OpenUP", "Invalid", "any_role", url);
                }
        );
        Assert.assertEquals(
                "Status",
                Status.UNAUTHORIZED.getStatusCode(), 
                ex.getResponse().getStatus()
        );
    }
    
    @Test
    public void testLoginEmptyPassword() throws Exception{
        WebApplicationException ex = Assert.assertThrows(
                WebApplicationException.class, 
                () -> {
                    security.login("OpenUP", "any_role1", "", url);
                }
        );
        Assert.assertEquals(
                "Status",
                Status.BAD_REQUEST.getStatusCode(), 
                ex.getResponse().getStatus()
        );
    }
    
    @Test
    public void testLoginBlankPassword() throws Exception{
        WebApplicationException ex = Assert.assertThrows(
                WebApplicationException.class, 
                () -> {
                    security.login("OpenUP", "any_role1", "    ", url);
                }
        );
        Assert.assertEquals(
                "Status",
                Status.BAD_REQUEST.getStatusCode(), 
                ex.getResponse().getStatus()
        );
    }
    
    @Test
    public void testLoginInvalidPassword() throws Exception{
        WebApplicationException ex = Assert.assertThrows(
                WebApplicationException.class, 
                () -> {
                    security.login("OpenUP", "any_role1", "Invalid", url);
                }
        );
        Assert.assertEquals(
                "Status",
                Status.UNAUTHORIZED.getStatusCode(), 
                ex.getResponse().getStatus()
        );
    }
    
    @Test
    public void testLoginInvalidPasswordAfterLoginOK() throws Exception{
        String token = security.login("OpenUP", "any_role1", "any_role", url);
        WebApplicationException ex = Assert.assertThrows(
                WebApplicationException.class, 
                () -> {
                    security.login("OpenUP", "any_role1", "Invalid", url);
                }
        );
        Assert.assertEquals(
                "Status",
                Status.UNAUTHORIZED.getStatusCode(), 
                ex.getResponse().getStatus()
        );
        
        header.setToken(token);
        security.logOut("OpenUP");
    }
    
    @Test(expected = BadRequestException.class)
    public void testLoginEmptyUrl() throws Exception{
        MultivaluedMap<String, String> form = new MultivaluedHashMap<>();
        form.putSingle("username", "any_role1");
        form.putSingle("password", "any_role");
        form.putSingle("url", "");
        client.target(url.toURI())
                .path("security")
                .path("OpenUP")
                .request(MediaType.TEXT_PLAIN)
                .post(Entity.form(form), String.class);
    }
    
    @Test(expected = BadRequestException.class)
    public void testLoginBlankUrl() throws Exception{
        MultivaluedMap<String, String> form = new MultivaluedHashMap<>();
        form.putSingle("username", "any_role1");
        form.putSingle("password", "any_role");
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
        form.putSingle("password", "any_role");
        form.putSingle("url", "Invalid");
        client.target(url.toURI())
                .path("security")
                .path("OpenUP")
                .request(MediaType.TEXT_PLAIN)
                .post(Entity.form(form), String.class);
    }
    
    @Test
    public void testAuthenticateOK() throws Exception{
        String token = security.login("OpenUP", "any_role1", "any_role", url);
        
        header.setToken(token);
        
        Token jwt = security.authenticate();
        
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
        
        security.logOut("OpenUP");
    }
    
    @Test
    public void testAuthenticateEmptyToken() throws Exception{
        header.setToken("");
        WebApplicationException ex = Assert.assertThrows(
                WebApplicationException.class, 
                () -> {
                    security.authenticate();
                }
        );
        Assert.assertEquals(
                "Status",
                Status.UNAUTHORIZED.getStatusCode(), 
                ex.getResponse().getStatus()
        );
    }
    
    @Test
    public void testAuthenticateBlankToken() throws Exception{
        header.setToken("    ");
        WebApplicationException ex = Assert.assertThrows(
                WebApplicationException.class, 
                () -> {
                    security.authenticate();
                }
        );
        Assert.assertEquals(
                "Status",
                Status.UNAUTHORIZED.getStatusCode(), 
                ex.getResponse().getStatus()
        );
    }
    
    @Test
    public void testAuthenticateInvalidToken() throws Exception{
        header.setToken("Invalid");
        WebApplicationException ex = Assert.assertThrows(
                WebApplicationException.class, 
                () -> {
                    security.authenticate();
                }
        );
        Assert.assertEquals(
                "Status",
                Status.UNAUTHORIZED.getStatusCode(), 
                ex.getResponse().getStatus()
        );
    }
    
    @Test
    public void testLogoutOK() throws Exception {
        String token = security.login("OpenUP", "any_role1", "any_role", url);
        header.setToken(token);
        Token jwt = security.authenticate();
        
        header.setToken(jwt.getRawToken());
        
        security.logOut("OpenUP");
        
        WebApplicationException ex = Assert.assertThrows(
                WebApplicationException.class, 
                () -> {
                    security.authenticate();
                }
        );
        Assert.assertEquals(
                "Status",
                Status.UNAUTHORIZED.getStatusCode(), 
                ex.getResponse().getStatus()
        );
    }
    
    @Test
    public void testLogoutEmptyUnit() throws Exception{
        WebApplicationException ex = Assert.assertThrows(
                WebApplicationException.class, 
                () -> {
                    security.logOut("");
                }
        );
        Assert.assertEquals(
                "Status",
                Status.BAD_REQUEST.getStatusCode(), 
                ex.getResponse().getStatus()
        );
    }
    
    @Test(expected = BadRequestException.class)
    public void testLogoutBlankUnit() throws Exception{
        security.logOut("    ");
    }
    
    @Test
    public void testLogoutInvalidUnit() throws Exception{
        WebApplicationException ex = Assert.assertThrows(
                WebApplicationException.class, 
                () -> {
                    security.logOut("Invalid");
                }
        );
        Assert.assertEquals(
                "Status",
                Status.BAD_REQUEST.getStatusCode(), 
                ex.getResponse().getStatus()
        );
    }
}
