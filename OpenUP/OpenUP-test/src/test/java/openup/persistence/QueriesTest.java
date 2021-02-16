/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.persistence;

import epf.client.RestClient;
import epf.client.persistence.Queries;
import epf.client.persistence.Target;
import epf.client.security.Security;
import epf.util.client.Client;
import openup.schema.OpenUP;
import java.net.URI;
import java.util.List;
import javax.ws.rs.core.MediaType;
import openup.TestUtil;
import openup.client.security.PasswordHash;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author FOXCONN
 */
public class QueriesTest {
    
	private static URI persistenceUrl;
	private static RestClient restClient;
    private static String token;
    
    @BeforeClass
    public static void beforeClass() throws Exception{
    	persistenceUrl = new URI(TestUtil.url().toString() + "persistence");
    	restClient = TestUtil.newRestClient(TestUtil.url().toURI());
    	Security security = restClient.build(Security.class);
        token = security.login(
        		OpenUP.Schema, 
        		"any_role1", 
        		PasswordHash.hash(
        				"any_role1", 
        				"any_role".toCharArray()
        				), 
        		TestUtil.url()
        		);
    }
    
    @AfterClass
    public static void afterClass() throws Exception{
    	restClient.authorization(token).build(Security.class).logOut(OpenUP.Schema);
    }
    
    @Test
    public void testSearchOK() throws Exception {
    	List<Target> results = restClient
    			.authorization(token)
    			.build(Queries.class)
    			.search("Any", 0, 100);
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
