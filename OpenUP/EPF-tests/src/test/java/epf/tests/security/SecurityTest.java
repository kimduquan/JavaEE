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
import org.junit.rules.TestName;
import org.junit.Ignore;
import org.junit.Rule;
import epf.client.util.Client;
import epf.naming.Naming;
import epf.security.client.Security;
import epf.security.schema.Token;
import epf.tests.client.ClientUtil;
import epf.tests.health.HealthUtil;
import epf.client.gateway.GatewayUtil;

/**
 *
 * @author FOXCONN
 */
public class SecurityTest {
	
	@Rule
    public TestName testName = new TestName();
	
	private static URI securityUrl;
    
    @BeforeClass
    public static void beforeClass() throws Exception{
    	HealthUtil.isReady();
    	securityUrl = GatewayUtil.get(Naming.SECURITY);
    }
    
    String login(String username, String password, URL targetUrl) throws Exception{
        try(Client client = ClientUtil.newClient(securityUrl)){
        	return Security.login(client, username, password, targetUrl);
        }
    }
    
    Token authenticate(String token) throws Exception{
    	try(Client client = ClientUtil.newClient(securityUrl)){
    		client.authorization(token.toCharArray());
    		return Security.authenticate(client);
    	}
    }
    
    String revoke(String token, Duration duration) throws Exception{
    	try(Client client = ClientUtil.newClient(securityUrl)){
			client.authorization(token.toCharArray());
    		return Security.revoke(client, duration);
    	}
    }
    
    @Test
    public void testLoginOK() throws Exception {
    	String token = SecurityUtil.login();
        Assert.assertNotNull("Token", token);
        Assert.assertNotEquals("Token", "", token);
        SecurityUtil.logOut(token);
    }
    
    @Test
	@Ignore
    public void testLoginOK_Admin() throws Exception {
    	Entry<String, String> adminCredential = SecurityUtil.getAdminCredential();
    	String token = SecurityUtil.login(adminCredential.getKey(), adminCredential.getValue());
        Assert.assertNotNull("Token", token);
        Assert.assertNotEquals("Token", "", token);
        SecurityUtil.logOut(token);
    }
    
    @Test
    public void testLoginOKAfterLoginInvalidPassword() throws Exception {
    	Entry<String, String> credential = SecurityUtil.peekCredential();
        Assert.assertThrows(
        		NotAuthorizedException.class, 
                () -> {
                    SecurityUtil.login(credential.getKey(), "Invalid");
                }
        );
        String token = SecurityUtil.login(credential.getKey(), credential.getValue());
		Assert.assertNotNull("Token", token);
		Assert.assertFalse("Token", token.isEmpty());
		SecurityUtil.logOut(token);
    }
    
    @Test(expected = BadRequestException.class)
    public void testLoginEmptyUser() throws Exception{
    	SecurityUtil.login("", "Any_Role1*");
    }
    
    @Test(expected = BadRequestException.class)
    public void testLoginBlankUser() throws Exception{
    	SecurityUtil.login("     ", "Any_Role1*");
    }
    
    @Test(expected = NotAuthorizedException.class)
    public void testLoginInvalidUser() throws Exception{
    	SecurityUtil.login("Invalid", "Any_Role1*");
    }
    
    @Test(expected = BadRequestException.class)
    public void testLoginEmptyPassword() throws Exception{
    	Entry<String, String> credential = SecurityUtil.peekCredential();
    	SecurityUtil.login(credential.getKey(), "");
    }
    
    @Test(expected = BadRequestException.class)
    public void testLoginBlankPassword() throws Exception{
    	Entry<String, String> credential = SecurityUtil.peekCredential();
    	SecurityUtil.login(credential.getValue(), "    ");
    }
    
    @Test(expected = NotAuthorizedException.class)
    public void testLoginInvalidPassword() throws Exception{
    	Entry<String, String> credential = SecurityUtil.peekCredential();
    	SecurityUtil.login(credential.getKey(), "Invalid");
    }
    
    @Test(expected = NotAuthorizedException.class)
    public void testLoginInvalidPasswordAfterLoginOK() throws Exception{
    	Entry<String, String> credential = SecurityUtil.peekCredential();
    	String token = SecurityUtil.login(credential.getKey(), credential.getValue());
    	try {
        	SecurityUtil.login(credential.getKey(), "Invalid");
    	}
    	finally {
        	SecurityUtil.logOut(token);
    	}
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
        form.putSingle("password", credential.getValue());
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
        form.putSingle("password", credential.getValue());
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
        String token = SecurityUtil.login(credential.getKey(), credential.getValue());
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
        Assert.assertEquals("duration", 60, duration.toMinutes());
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
        Assert.assertEquals("Token.claims.email", String.format("%s", credential.getKey()) , jwt.getClaims().get("email"));
        SecurityUtil.logOut(token);
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
        String token = SecurityUtil.login(credential.getKey(), credential.getValue());
        SecurityUtil.logOut(token);
        authenticate(token);
    }
    
    @Test
    public void testUpdateOk_Password() throws Exception {
    	Entry<String, String> credential = SecurityUtil.peekCredential();
    	String token = SecurityUtil.login(credential.getKey(), credential.getValue());
    	try(Client client = ClientUtil.newClient(securityUrl)){
    		client.authorization(token.toCharArray());
    		Response response = Security.update(client, credential.getValue() + "1");
    		Assert.assertEquals("Response.status", Response.Status.OK.getStatusCode(), response.getStatus());
    	}
    	SecurityUtil.logOut(token);
    	token = SecurityUtil.login(credential.getKey(), credential.getValue() + "1");
    	try(Client client = ClientUtil.newClient(securityUrl)){
    		client.authorization(token.toCharArray());
    		Response response = Security.update(client, credential.getValue());
    		Assert.assertEquals("Response.status", Response.Status.OK.getStatusCode(), response.getStatus());
    	}
    	SecurityUtil.logOut(token);
    }
    
    @Test
    public void testUpdateInvalid_PasswordNull() throws Exception {
    	Entry<String, String> credential = SecurityUtil.peekCredential();
    	String token = SecurityUtil.login(credential.getKey(), credential.getValue());
    	try(Client client = ClientUtil.newClient(securityUrl)){
    		client.authorization(token.toCharArray());
    		Response response = Security.update(client, null);
    		Assert.assertEquals("Response.status", Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    	}
    	SecurityUtil.logOut(token);
    }
    
    @Test
    public void testUpdateInvalid_PasswordEmpty() throws Exception {
    	Entry<String, String> credential = SecurityUtil.peekCredential();
    	String token = SecurityUtil.login(credential.getKey(), credential.getValue());
    	try(Client client = ClientUtil.newClient(securityUrl)){
    		client.authorization(token.toCharArray());
    		Response response = Security.update(client, "");
    		Assert.assertEquals("Response.status", Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    	}
    	SecurityUtil.logOut(token);
    }
    
    @Test
    public void testRevokeOk() throws Exception {
    	Entry<String, String> credential = SecurityUtil.peekCredential();
    	final String token = SecurityUtil.login(credential.getKey(), credential.getValue());
    	Token jwt = authenticate(token);
    	try(Client client = ClientUtil.newClient(securityUrl)){
    		client.authorization(token.toCharArray());
    		String newToken = Security.revoke(client, null);
    		Assert.assertNotEquals(token, newToken);
    		Assert.assertThrows(NotAuthorizedException.class, () -> {
    			authenticate(token);
    		});
    		Token newJwt = authenticate(newToken);
    		Assert.assertNotNull("Token", newJwt);
    		Assert.assertNotEquals("Token.tokenID", jwt.getTokenID(), newJwt.getTokenID());
    		SecurityUtil.logOut(newToken);
    	}
    }
    
    @Test
    public void testRevokeOk_AfterRevoke() throws Exception {
    	Entry<String, String> credential = SecurityUtil.peekCredential();
    	final String token = SecurityUtil.login(credential.getKey(), credential.getValue());
    	try(Client client = ClientUtil.newClient(securityUrl)){
    		String newToken = revoke(token, null);
    		Token newJwt = authenticate(newToken);
    		client.authorization(newToken.toCharArray());
    		String newToken2 = Security.revoke(client, null);
    		Token newJwt2 = authenticate(newToken2);
    		Assert.assertNotNull("Token", newJwt2);
    		Assert.assertNotEquals("Token.tokenID", newJwt.getTokenID(), newJwt2.getTokenID());
    		SecurityUtil.logOut(newToken2);
    	}
    }
}
