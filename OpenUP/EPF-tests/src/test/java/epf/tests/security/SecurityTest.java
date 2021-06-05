/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.tests.security;

import static epf.client.security.Security.AUDIENCE_FORMAT;
import java.net.URI;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import epf.client.security.Security;
import epf.client.security.Token;
import epf.schema.EPF;
import epf.tests.client.ClientUtil;
import epf.tests.registry.RegistryUtil;
import epf.util.client.Client;
import epf.util.security.PasswordHelper;

/**
 *
 * @author FOXCONN
 */
public class SecurityTest {
	
	private static URI securityUrl;
    
    @BeforeClass
    public static void beforeClass(){
    	securityUrl = RegistryUtil.lookup("security", null);
    }
    
    String login(String unit, String username, String password, URL targetUrl, boolean needHash) throws Exception{
        if(needHash){
        	password = PasswordHelper.hash(username, password.toCharArray());
        }
        try(Client client = ClientUtil.newClient(securityUrl)){
        	return Security.login(client, unit, username, password, targetUrl);
        }
    }
    
    String logOut(String token, String unit) throws Exception{
    	try(Client client = ClientUtil.newClient(securityUrl)){
    		client.authorization(token);
    		return Security.logOut(client, unit);
    	}
    }
    
    Token authenticate(String token, String unit) throws Exception{
    	try(Client client = ClientUtil.newClient(securityUrl)){
    		client.authorization(token);
    		return Security.authenticate(client, unit);
    	}
    }
    
    String revoke(String token, String unit) throws Exception{
    	try(Client client = ClientUtil.newClient(securityUrl)){
			client.authorization(token);
    		return Security.revoke(client, unit);
    	}
    }
    
    @Test
    public void testLoginOK() throws Exception {
    	String token = login(null, "any_role1", "any_role", securityUrl.toURL(), true);
        Assert.assertNotNull("Token", token);
        Assert.assertNotEquals("Token", "", token);
        logOut(token, null);
    }
    
    @Test
    public void testLoginOK_Admin() throws Exception {
    	String token = login(null, "admin1", "admin", securityUrl.toURL(), true);
        Assert.assertNotNull("Token", token);
        Assert.assertNotEquals("Token", "", token);
        logOut(token, null);
    }
    
    @Test
    public void testLoginOKAfterLoginInvalidPassword() throws Exception {
        Assert.assertThrows(
        		NotAuthorizedException.class, 
                () -> {
                    login(null, "any_role1", "Invalid", securityUrl.toURL(), true);
                }
        );
        String token = login(null, "any_role1", "any_role", securityUrl.toURL(), true);
		Assert.assertNotNull("Token", token);
		Assert.assertFalse("Token", token.isEmpty());
		logOut(token, null);
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
        Assert.assertNotNull("Token", jwt);
        Assert.assertNotNull("Token.audience", jwt.getAudience());
        Assert.assertEquals("Token.audience.size", 1, jwt.getAudience().size());
        Assert.assertArrayEquals(
                "Token.audience", 
                new String[]{
                    String.format(
                            AUDIENCE_FORMAT, 
                            securityUrl.getScheme(), 
                            securityUrl.getHost(), 
                            securityUrl.getPort()
                    )
                }, 
                jwt.getAudience().toArray()
        );
        Assert.assertTrue("Token.expirationTime", jwt.getExpirationTime() > 0);
        Assert.assertTrue("Token.expirationTime", jwt.getExpirationTime() > jwt.getIssuedAtTime());
        Assert.assertNotNull("Token.groups", jwt.getGroups());
        Assert.assertEquals("Token.groups.size", 
                1, 
                jwt.getGroups().size()
        );
        Assert.assertArrayEquals(
                "Token.groups", 
                new String[]{
                    "Any_Role"
                }, 
                jwt.getGroups().toArray()
        );
        Assert.assertTrue("Token.issuedAtTime", jwt.getIssuedAtTime() > 0);
        Duration duration = Duration.between( 
                Instant.ofEpochSecond(jwt.getIssuedAtTime()),
                Instant.ofEpochSecond(jwt.getExpirationTime())
        );
        Assert.assertEquals("duration", 30, duration.toMinutes());
        Assert.assertNotNull("Token.issuer", jwt.getIssuer());
        Assert.assertEquals("Token.issuer", EPF.SCHEMA, jwt.getIssuer());
        Assert.assertNotNull("Token.name", jwt.getName());
        Assert.assertEquals("Token.name", "any_role1", jwt.getName());
        Assert.assertNull("Token.rawToken", jwt.getRawToken());
        Assert.assertNotNull("Token.subject", jwt.getSubject());
        Assert.assertEquals("Token.subject", "any_role1", jwt.getSubject());
        Assert.assertNotNull("Token.tokenID", jwt.getTokenID());
        Assert.assertNotEquals("Token.tokenID", "", jwt.getTokenID());
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
    
    @Test
    public void testUpdateOk_Password() throws Exception {
    	String token = login(null, "any_role1", "any_role", securityUrl.toURL(), true);
    	try(Client client = ClientUtil.newClient(securityUrl)){
    		client.authorization(token);
    		Map<String, String> fields = new HashMap<>();
    		fields.put("password", "any_role1");
    		Response response = Security.update(client, null, fields);
    		Assert.assertEquals("Response.status", Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
    	}
    	logOut(token, null);
    	token = login(null, "any_role1", "any_role1", securityUrl.toURL(), true);
    	try(Client client = ClientUtil.newClient(securityUrl)){
    		client.authorization(token);
    		Map<String, String> fields = new HashMap<>();
    		fields.put("password", "any_role");
    		Response response = Security.update(client, null, fields);
    		Assert.assertEquals("Response.status", Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
    	}
    	logOut(token, null);
    }
    
    @Test
    public void testUpdateInvalid_PasswordNull() throws Exception {
    	String token = login(null, "any_role1", "any_role", securityUrl.toURL(), true);
    	try(Client client = ClientUtil.newClient(securityUrl)){
    		client.authorization(token);
    		Map<String, String> fields = new HashMap<>();
    		Response response = Security.update(client, null, fields);
    		Assert.assertEquals("Response.status", Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    	}
    	logOut(token, null);
    }
    
    @Test
    public void testUpdateInvalid_PasswordEmpty() throws Exception {
    	String token = login(null, "any_role1", "any_role", securityUrl.toURL(), true);
    	try(Client client = ClientUtil.newClient(securityUrl)){
    		client.authorization(token);
    		Map<String, String> fields = new HashMap<>();
    		fields.put("password", "");
    		Response response = Security.update(client, null, fields);
    		Assert.assertEquals("Response.status", Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    	}
    	logOut(token, null);
    }
    
    @Test
    public void testRevokeOk() throws Exception {
    	String unit = null;
    	final String token = login(unit, "any_role1", "any_role", securityUrl.toURL(), true);
    	Token jwt = authenticate(token, unit);
    	try(Client client = ClientUtil.newClient(securityUrl)){
    		client.authorization(token);
    		String newToken = Security.revoke(client, unit);
    		Assert.assertNotEquals(token, newToken);
    		Assert.assertThrows(NotAuthorizedException.class, () -> {
    			authenticate(token, unit);
    		});
    		Token newJwt = authenticate(newToken, unit);
    		Assert.assertNotNull("Token", newJwt);
    		Assert.assertNotEquals("Token.tokenID", jwt.getTokenID(), newJwt.getTokenID());
    		logOut(newToken, unit);
    	}
    }
    
    @Test
    public void testRevokeOk_AfterRevoke() throws Exception {
    	String unit = null;
    	final String token = login(unit, "any_role1", "any_role", securityUrl.toURL(), true);
    	try(Client client = ClientUtil.newClient(securityUrl)){
    		String newToken = revoke(token, unit);
    		Token newJwt = authenticate(newToken, unit);
    		client.authorization(newToken);
    		String newToken2 = Security.revoke(client, unit);
    		Token newJwt2 = authenticate(newToken2, unit);
    		Assert.assertNotNull("Token", newJwt2);
    		Assert.assertNotEquals("Token.tokenID", newJwt.getTokenID(), newJwt2.getTokenID());
    		logOut(newToken2, unit);
    	}
    }
    
    @Test
    public void testRevokeEmptyUnit() throws Exception{
        String token = login(null, "any_role1", "any_role", securityUrl.toURL(), true);
        Assert.assertThrows(BadRequestException.class, () -> {
            revoke(token, "");
        });
        logOut(token, null);
    }
    
    @Test
    public void testRevokeBlankUnit() throws Exception{
        String token = login(null, "any_role1", "any_role", securityUrl.toURL(), true);
        Assert.assertThrows(BadRequestException.class, () -> {
            revoke(token, "     ");
        });
        logOut(token, null);
    }
    
    @Test
    public void testRevokeInvalidUnit() throws Exception{
        String token = login(null, "any_role1", "any_role", securityUrl.toURL(), true);
        Assert.assertThrows(BadRequestException.class, () -> {
            revoke(token, "Invalid");
        });
        logOut(token, null);
    }
}
