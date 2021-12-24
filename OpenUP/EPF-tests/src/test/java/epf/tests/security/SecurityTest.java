/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.tests.security;

import java.net.URI;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.util.Map.Entry;
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
import org.junit.Ignore;
import epf.client.util.Client;
import epf.naming.Naming;
import epf.security.client.Security;
import epf.security.schema.Token;
import epf.tests.client.ClientUtil;
import epf.client.gateway.GatewayUtil;

/**
 *
 * @author FOXCONN
 */
public class SecurityTest {
	
	private static URI securityUrl;
    
    @BeforeClass
    public static void beforeClass() throws Exception{
    	securityUrl = GatewayUtil.get(Naming.SECURITY);
    }
    
    String login(String username, String password, URL targetUrl) throws Exception{
        try(Client client = ClientUtil.newClient(securityUrl)){
        	return Security.login(client, username, password, targetUrl);
        }
    }
    
    String logOut(String token) throws Exception{
    	try(Client client = ClientUtil.newClient(securityUrl)){
    		client.authorization(token);
    		return Security.logOut(client);
    	}
    }
    
    Token authenticate(String token) throws Exception{
    	try(Client client = ClientUtil.newClient(securityUrl)){
    		client.authorization(token);
    		return Security.authenticate(client);
    	}
    }
    
    String revoke(String token) throws Exception{
    	try(Client client = ClientUtil.newClient(securityUrl)){
			client.authorization(token);
    		return Security.revoke(client);
    	}
    }
    
    @Test
    public void testLoginOK() throws Exception {
    	Entry<String, String> credential = SecurityUtil.peekCredential();
    	String token = login(credential.getKey(), credential.getValue(), securityUrl.toURL());
        Assert.assertNotNull("Token", token);
        Assert.assertNotEquals("Token", "", token);
        logOut(token);
    }
    
    @Test
	@Ignore
    public void testLoginOK_Admin() throws Exception {
    	Entry<String, String> adminCredential = SecurityUtil.getAdminCredential();
    	String token = login(adminCredential.getKey(), adminCredential.getValue(), securityUrl.toURL());
        Assert.assertNotNull("Token", token);
        Assert.assertNotEquals("Token", "", token);
        logOut(token);
    }
    
    @Test
    public void testLoginOKAfterLoginInvalidPassword() throws Exception {
    	Entry<String, String> credential = SecurityUtil.peekCredential();
        Assert.assertThrows(
        		NotAuthorizedException.class, 
                () -> {
                    login(credential.getKey(), "Invalid", securityUrl.toURL());
                }
        );
        String token = login(credential.getKey(), credential.getValue(), securityUrl.toURL());
		Assert.assertNotNull("Token", token);
		Assert.assertFalse("Token", token.isEmpty());
		logOut(token);
    }
    
    @Test(expected = BadRequestException.class)
    public void testLoginEmptyUser() throws Exception{
        login("", "Any_Role1*", securityUrl.toURL());
    }
    
    @Test(expected = BadRequestException.class)
    public void testLoginBlankUser() throws Exception{
        login("     ", "Any_Role1*", securityUrl.toURL());
    }
    
    @Test(expected = NotAuthorizedException.class)
    public void testLoginInvalidUser() throws Exception{
        login("Invalid", "Any_Role1*", securityUrl.toURL());
    }
    
    @Test(expected = BadRequestException.class)
    public void testLoginEmptyPassword() throws Exception{
    	Entry<String, String> credential = SecurityUtil.peekCredential();
        login(credential.getKey(), "", securityUrl.toURL());
    }
    
    @Test(expected = BadRequestException.class)
    public void testLoginBlankPassword() throws Exception{
    	Entry<String, String> credential = SecurityUtil.peekCredential();
        login(credential.getValue(), "    ", securityUrl.toURL());
    }
    
    @Test(expected = NotAuthorizedException.class)
    public void testLoginInvalidPassword() throws Exception{
    	Entry<String, String> credential = SecurityUtil.peekCredential();
        login(credential.getKey(), "Invalid", securityUrl.toURL());
    }
    
    @Test(expected = NotAuthorizedException.class)
    public void testLoginInvalidPasswordAfterLoginOK() throws Exception{
    	Entry<String, String> credential = SecurityUtil.peekCredential();
        login(credential.getKey(), credential.getValue(), securityUrl.toURL());
        login(credential.getKey(), "Invalid", securityUrl.toURL());
    }
    
    @Test(expected = BadRequestException.class)
    public void testLoginEmptyUrl() throws Exception{
    	Entry<String, String> credential = SecurityUtil.peekCredential();
        login(credential.getKey(), credential.getValue(), null);
    }
    
    @Test(expected = BadRequestException.class)
    public void testLoginBlankUrl() throws Exception{
    	Entry<String, String> credential = SecurityUtil.peekCredential();
        MultivaluedMap<String, String> form = new MultivaluedHashMap<>();
        form.putSingle("username", credential.getKey());
        form.putSingle("password_hash", credential.getValue());
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
    	Entry<String, String> credential = SecurityUtil.peekCredential();
        MultivaluedMap<String, String> form = new MultivaluedHashMap<>();
        form.putSingle("username", credential.getKey());
        form.putSingle("password_hash", credential.getValue());
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
    	Entry<String, String> credential = SecurityUtil.peekCredential();
        String token = login(credential.getKey(), credential.getValue(), securityUrl.toURL());
        Token jwt = authenticate(token);
        Assert.assertNotNull("Token", jwt);
        Assert.assertNotNull("Token.audience", jwt.getAudience());
        Assert.assertEquals("Token.audience.size", 1, jwt.getAudience().size());
        Assert.assertArrayEquals(
                "Token.audience", 
                new String[]{
                    String.format(
                    		epf.security.client.Security.AUDIENCE_FORMAT, 
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
                2, 
                jwt.getGroups().size()
        );
        Assert.assertTrue("Token.groups", jwt.getGroups().contains("Any_Role"));
        Assert.assertTrue("Token.issuedAtTime", jwt.getIssuedAtTime() > 0);
        Duration duration = Duration.between( 
                Instant.ofEpochSecond(jwt.getIssuedAtTime()),
                Instant.ofEpochSecond(jwt.getExpirationTime())
        );
        Assert.assertEquals("duration", 30, duration.toMinutes());
        Assert.assertNotNull("Token.issuer", jwt.getIssuer());
        Assert.assertEquals("Token.issuer", "EPF", jwt.getIssuer());
        Assert.assertNotNull("Token.name", jwt.getName());
        Assert.assertEquals("Token.name", credential.getKey(), jwt.getName());
        Assert.assertNull("Token.rawToken", jwt.getRawToken());
        Assert.assertNotNull("Token.subject", jwt.getSubject());
        Assert.assertEquals("Token.subject", credential.getKey(), jwt.getSubject());
        Assert.assertNotNull("Token.tokenID", jwt.getTokenID());
        Assert.assertNotEquals("Token.tokenID", "", jwt.getTokenID());
        Assert.assertNotNull("Token.claims", jwt.getClaims());
        Assert.assertEquals("Token.claims.size", 2, jwt.getClaims().size());
        //Assert.assertEquals("Token.claims.full_name", "Any Role 1", jwt.getClaims().get("full_name"));
        Assert.assertEquals("Token.claims.email", String.format("%s@openup.org", credential.getKey()) , jwt.getClaims().get("email"));
        logOut(token);
    }
    
    @Ignore
    @Test(expected = NotAuthorizedException.class)
    public void testAuthenticateEmptyToken() throws Exception{
        authenticate("");
    }
    
    @Ignore
    @Test(expected = NotAuthorizedException.class)
    public void testAuthenticateBlankToken() throws Exception{
        authenticate("    ");
    }
    
    @Ignore
    @Test(expected = NotAuthorizedException.class)
    public void testAuthenticateInvalidToken() throws Exception{
        authenticate("Invalid");
    }
    
    @Test(expected = NotAuthorizedException.class)
    public void testLogoutOK() throws Exception {
    	Entry<String, String> credential = SecurityUtil.peekCredential();
        String token = login(credential.getKey(), credential.getValue(), securityUrl.toURL());
        logOut(token);
        authenticate(token);
    }
    
    @Test
    public void testUpdateOk_Password() throws Exception {
    	Entry<String, String> credential = SecurityUtil.peekCredential();
    	String token = login(credential.getKey(), credential.getValue(), securityUrl.toURL());
    	try(Client client = ClientUtil.newClient(securityUrl)){
    		client.authorization(token);
    		Response response = Security.update(client, credential.getValue() + "1");
    		System.out.println(String.format("SecurityTest.testUpdateOk_Password(\"%s\",\"%s\"", credential.getKey(), credential.getValue() + "1"));
    		Assert.assertEquals("Response.status", Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
    	}
    	logOut(token);
    	token = login(credential.getKey(), credential.getValue() + "1", securityUrl.toURL());
    	try(Client client = ClientUtil.newClient(securityUrl)){
    		client.authorization(token);
    		Response response = Security.update(client, credential.getValue());
    		System.out.println(String.format("SecurityTest.testUpdateOk_Password(\"%s\",\"%s\"", credential.getKey(), credential.getValue()));
    		Assert.assertEquals("Response.status", Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
    	}
    	logOut(token);
    }
    
    @Test
    public void testUpdateInvalid_PasswordNull() throws Exception {
    	Entry<String, String> credential = SecurityUtil.peekCredential();
    	String token = login(credential.getKey(), credential.getValue(), securityUrl.toURL());
    	try(Client client = ClientUtil.newClient(securityUrl)){
    		client.authorization(token);
    		Response response = Security.update(client, null);
    		System.out.println(String.format("SecurityTest.testUpdateInvalid_PasswordNull(\"%s\",\"%s\"", credential.getKey(), credential.getValue()));
    		Assert.assertEquals("Response.status", Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    	}
    	logOut(token);
    }
    
    @Test
    public void testUpdateInvalid_PasswordEmpty() throws Exception {
    	Entry<String, String> credential = SecurityUtil.peekCredential();
    	String token = login(credential.getKey(), credential.getValue(), securityUrl.toURL());
    	try(Client client = ClientUtil.newClient(securityUrl)){
    		client.authorization(token);
    		Response response = Security.update(client, "");
    		System.out.println(String.format("SecurityTest.testUpdateInvalid_PasswordEmpty(\"%s\",\"%s\"", credential.getKey(), credential.getValue()));
    		Assert.assertEquals("Response.status", Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    	}
    	logOut(token);
    }
    
    @Test
    public void testRevokeOk() throws Exception {
    	Entry<String, String> credential = SecurityUtil.peekCredential();
    	final String token = login(credential.getKey(), credential.getValue(), securityUrl.toURL());
    	Token jwt = authenticate(token);
    	try(Client client = ClientUtil.newClient(securityUrl)){
    		client.authorization(token);
    		String newToken = Security.revoke(client);
    		Assert.assertNotEquals(token, newToken);
    		Assert.assertThrows(NotAuthorizedException.class, () -> {
    			authenticate(token);
    		});
    		Token newJwt = authenticate(newToken);
    		Assert.assertNotNull("Token", newJwt);
    		Assert.assertNotEquals("Token.tokenID", jwt.getTokenID(), newJwt.getTokenID());
    		logOut(newToken);
    	}
    }
    
    @Test
    public void testRevokeOk_AfterRevoke() throws Exception {
    	Entry<String, String> credential = SecurityUtil.peekCredential();
    	final String token = login(credential.getKey(), credential.getValue(), securityUrl.toURL());
    	try(Client client = ClientUtil.newClient(securityUrl)){
    		String newToken = revoke(token);
    		Token newJwt = authenticate(newToken);
    		client.authorization(newToken);
    		String newToken2 = Security.revoke(client);
    		Token newJwt2 = authenticate(newToken2);
    		Assert.assertNotNull("Token", newJwt2);
    		Assert.assertNotEquals("Token.tokenID", newJwt.getTokenID(), newJwt2.getTokenID());
    		logOut(newToken2);
    	}
    }
}
