package epf.tests.persistence;

import epf.client.gateway.GatewayUtil;
import epf.client.search.Search;
import epf.client.util.Client;
import epf.naming.Naming;
import epf.tests.client.ClientUtil;
import epf.tests.security.SecurityUtil;
import java.net.URI;
import java.util.Set;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.Ignore;
import org.junit.Rule;

/**
 *
 * @author FOXCONN
 */
public class QueriesTest {
	
	@Rule
    public TestName testName = new TestName();
    
	private static URI persistenceUrl;
    private static String token;
    private Client client;
    
    @BeforeClass
    public static void beforeClass() throws Exception{
    	token = SecurityUtil.login();
    	persistenceUrl = GatewayUtil.get(Naming.PERSISTENCE);
    }
    
    @AfterClass
    public static void afterClass() throws Exception{
    	SecurityUtil.logOut(token);
    }
    
    @Before
    public void before() {
    	client = ClientUtil.newClient(persistenceUrl);
    	client.authorization(token.toCharArray());
    }
    
    @After
    public void after() throws Exception {
    	client.close();
    }
    
    @Test
    @Ignore
    public void testSearchOK() throws Exception {
    	Set<Link> entityLinks = Search.search(client, "Any", 0, 100).getLinks();
    	Assert.assertFalse("Response.links.empty", entityLinks.isEmpty());
    	entityLinks.forEach(entityLink -> {
    		Assert.assertNotNull("Link", entityLink);
    		Assert.assertNotNull("Link.title", entityLink.getTitle());
    		Assert.assertNotEquals("Link.title", "", entityLink.getTitle());
    		try(Client client = ClientUtil.newClient(entityLink.getUri())){
    			Object entity = client
    					.authorization(token.toCharArray())
    					.request(
    							t -> t, 
    							req -> req.accept(MediaType.APPLICATION_JSON)
    							)
    					.get(Object.class);
    			Assert.assertNotNull("Link.entity", entity);
    		} 
    		catch (Exception e) {
				e.printStackTrace();
			}
    	});
    }
    
    @Test
    public void testSearchOK_EmptyResult() {
    	Set<Link> entityLinks = Search.search(client, "EPF", 0, 100).getLinks();
    	Assert.assertTrue("Response.links.empty", entityLinks.isEmpty());
    }
    
    @Test(expected = BadRequestException.class)
    @Ignore
    public void testSearch_EmptyText() {
    	Search.search(client, "", 0, 100);
    }
    
    @Test(expected = BadRequestException.class)
    @Ignore
    public void testSearch_BlankText() {
    	Search.search(client, "    ", 0, 100);
    }
    
    @Test(expected = BadRequestException.class)
    @Ignore
    public void testSearch_InvalidText() {
    	Search.search(client, "'abc'", 0, 100);
    }
}
