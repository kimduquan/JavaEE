/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.service.persistence;

import epf.service.ClientUtil;
import epf.service.RegistryUtil;
import epf.service.SecurityUtil;
import epf.util.client.Client;
import epf.util.logging.Logging;
import java.net.URI;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author FOXCONN
 */
public class QueriesTest {
    
	private static final Logger logger = Logging.getLogger(QueriesTest.class.getName());
	
	private static URI persistenceUrl;
    private static String token;
    private Client client;
    
    @BeforeClass
    public static void beforeClass(){
    	token = SecurityUtil.login(null, "any_role1", "any_role");
    	persistenceUrl = RegistryUtil.lookup("persistence", null);
    }
    
    @AfterClass
    public static void afterClass(){
    	SecurityUtil.logOut(null, token);
    }
    
    @Before
    public void before() {
    	client = ClientUtil.newClient(persistenceUrl);
    	client.authorization(token);
    }
    
    @After
    public void after() {
    	try {
			client.close();
		} 
    	catch (Exception e) {
    		logger.log(Level.WARNING, "after", e);
		}
    }
    
    @Test
    @Ignore
    public void testSearchOK() {
    	Response response = client.request(
    			target -> target
    			.queryParam("text", "OpenUP")
    			.queryParam("first", 0)
    			.queryParam("max", 100), 
    			req -> req.accept(MediaType.APPLICATION_JSON)
    			)
    			.get();
    	Assert.assertEquals("Response.status", 200, response.getStatus());
    	Set<Link> entityLinks = response.getLinks();
    	Assert.assertFalse("Response.links.empty", entityLinks.isEmpty());
    	entityLinks.forEach(entityLink -> {
    		Assert.assertNotNull("Link", entityLink);
    		Assert.assertNotNull("Link.title", entityLink.getTitle());
    		Assert.assertNotEquals("Link.title", "", entityLink.getTitle());
    		try(Client client = ClientUtil.newClient(entityLink.getUri())){
    			Object entity = client
    					.authorization(token)
    					.request(
    							t -> t, 
    							req -> req.accept(MediaType.APPLICATION_JSON)
    							)
    					.get(Object.class);
    			Assert.assertNotNull("Link.entity", entity);
    		} 
    		catch (Exception e) {
				logger.log(Level.SEVERE, entityLink.toString(), e);
			}
    	});
    }
    
    @Test
    public void testSearchOK_EmptyResult() {
    	Response response = client.request(
    			target -> target
    			.queryParam("text", "EPF")
    			.queryParam("first", 0)
    			.queryParam("max", 100), 
    			req -> req.accept(MediaType.APPLICATION_JSON)
    			)
    			.get();
    	Assert.assertEquals("Response.status", 200, response.getStatus());
    	Set<Link> entityLinks = response.getLinks();
    	Assert.assertTrue("Response.links.empty", entityLinks.isEmpty());
    }
    
    @Test
    public void testSearch_EmptyText() {
    	Response response = client.request(
    			target -> target
    			.queryParam("text", "")
    			.queryParam("first", 0)
    			.queryParam("max", 100), 
    			req -> req.accept(MediaType.APPLICATION_JSON)
    			)
    			.get();
    	Assert.assertEquals("Response.status", 400, response.getStatus());
    }
    
    @Test
    public void testSearch_BlankText() {
    	Response response = client.request(
    			target -> target
    			.queryParam("text", "    ")
    			.queryParam("first", 0)
    			.queryParam("max", 100), 
    			req -> req.accept(MediaType.APPLICATION_JSON)
    			)
    			.get();
    	Assert.assertEquals("Response.status", 400, response.getStatus());
    }
    
    @Test
    public void testSearch_InvalidText() {
    	Response response = client.request(
    			target -> target
    			.queryParam("text", "'abc'")
    			.queryParam("first", 0)
    			.queryParam("max", 100), 
    			req -> req.accept(MediaType.APPLICATION_JSON)
    			)
    			.get();
    	Assert.assertEquals("Response.status", 400, response.getStatus());
    }
}
