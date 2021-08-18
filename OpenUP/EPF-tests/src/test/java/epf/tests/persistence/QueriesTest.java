/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.tests.persistence;

import epf.client.gateway.GatewayUtil;
import epf.client.persistence.Queries;
import epf.tests.client.ClientUtil;
import epf.tests.security.SecurityUtil;
import epf.util.client.Client;
import epf.util.logging.Logging;
import java.net.URI;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Ignore;

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
    public static void beforeClass() throws Exception{
    	token = SecurityUtil.login("any_role1", "any_role");
    	persistenceUrl = GatewayUtil.get("persistence");
    }
    
    @AfterClass
    public static void afterClass(){
    	SecurityUtil.logOut(token);
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
    	Set<Link> entityLinks = Queries.search(client, "Any", 0, 100).getLinks();
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
    	Set<Link> entityLinks = Queries.search(client, "EPF", 0, 100).getLinks();
    	Assert.assertTrue("Response.links.empty", entityLinks.isEmpty());
    }
    
    @Test(expected = BadRequestException.class)
    @Ignore
    public void testSearch_EmptyText() {
    	Queries.search(client, "", 0, 100);
    }
    
    @Test(expected = BadRequestException.class)
    @Ignore
    public void testSearch_BlankText() {
    	Queries.search(client, "    ", 0, 100);
    }
    
    @Test(expected = BadRequestException.class)
    @Ignore
    public void testSearch_InvalidText() {
    	Queries.search(client, "'abc'", 0, 100);
    }
}
