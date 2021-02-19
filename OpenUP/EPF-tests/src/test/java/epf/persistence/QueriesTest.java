/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.persistence;

import epf.TestUtil;
import epf.client.persistence.Queries;
import epf.client.persistence.Target;
import epf.util.client.Client;
import java.net.URI;
import java.util.List;
import javax.ws.rs.core.MediaType;
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
public class QueriesTest {
    
	private static URI persistenceUrl;
    private static String token;
    private Client client;
    
    @BeforeClass
    public static void beforeClass() throws Exception{
    	token = TestUtil.login(null, "any_role1", "any_role");
    	persistenceUrl = new URI(TestUtil.gateway_url().toString() + "persistence");
    }
    
    @AfterClass
    public static void afterClass() throws Exception{
    	TestUtil.logOut(null, token);
    }
    
    @Before
    public void before() {
    	client = TestUtil.newClient(persistenceUrl);
    	client.authorization(token);
    }
    
    @After
    public void after() throws Exception {
    	client.close();
    }
    
    @Test
    public void testSearchOK() throws Exception {
    	List<Target> results = Queries.search(client, "Any", 0, 100);
    	Assert.assertNotEquals("results", 0, results.size());
    	results.forEach(target -> {
    		Assert.assertNotNull("Target", target);
    		Assert.assertNotNull("Target.path", target.getPath());
    		Assert.assertNotEquals("Target.path", "", target.getPath());
    		Object object;
    		try(Client client = TestUtil.newClient(persistenceUrl)){
    			object = client
    					.authorization(token)
    					.request(
    							persistenceUrl.toString() + "/" + target.getPath(),
    							t -> t, 
    							req -> req.accept(MediaType.APPLICATION_JSON)
    							)
    					.get(Object.class);
    			Assert.assertNotNull("Target.object", object);
    		} 
    		catch (Exception e) {
				e.printStackTrace();
			}
    	});
    }
}
