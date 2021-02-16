/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.persistence;

import epf.client.persistence.Entities;
import epf.client.persistence.Queries;
import epf.client.persistence.Target;
import epf.client.security.Header;
import epf.client.security.Security;
import epf.schema.EPF;
import epf.util.client.Client;
import openup.schema.OpenUP;
import openup.schema.Artifact;
import openup.schema.DeliveryProcess;
import java.net.URI;
import java.util.List;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import openup.TestUtil;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author FOXCONN
 */
public class PersistenceTest {
    
	private static URI persistenceUrl;
    private static Header header;
    private static RestClientBuilder restBuilder;
    private static Security security;
    private static Queries queries;
    private static Entities entities;
    private static String token;
    
    @BeforeClass
    public static void beforeClass() throws Exception{
    	persistenceUrl = new URI(TestUtil.url().toString() + "persistence");
        restBuilder = RestClientBuilder.newBuilder();
        header = TestUtil.buildClient(restBuilder);
        security = restBuilder.build(Security.class);
        queries = restBuilder.build(Queries.class);
        entities = restBuilder.build(Entities.class);
        token = TestUtil.login(security, header, "any_role1", "any_role");
    }
    
    @AfterClass
    public static void afterClass() throws Exception{
        TestUtil.logout(security, header);
    }
    
    @Test
    public void testPersistOK() throws Exception{
    	List<epf.schema.work_products.Artifact> epfArtifacts;
    	try(Client client = TestUtil.newClient(persistenceUrl)) {
    		epfArtifacts = client
    				.authorization(token)
    				.request(
    						target -> target.path(EPF.Schema).path(EPF.Artifact).matrixParam("name", "Work Items List"), 
    						req -> req.accept(MediaType.APPLICATION_JSON))
    				.get(new GenericType<List<epf.schema.work_products.Artifact>>() {});
    	}
        
        epf.schema.work_products.Artifact epfArtifact = epfArtifacts.get(0);
        Artifact artifact = new Artifact();
        artifact.setArtifact(epfArtifact);
        artifact.setName("Artifact 1");
        artifact.setSummary("Artifact 1 Summary");
        try(Client client = TestUtil.newClient(persistenceUrl)) {
        	artifact = client
        			.authorization(token)
        			.request(
        					target -> target.path(OpenUP.Schema).path(OpenUP.Artifact), 
        					req -> req.accept(MediaType.APPLICATION_JSON))
        			.post(Entity.json(artifact), Artifact.class);
        }
        
        Assert.assertNotNull("Artifact", artifact);
        Assert.assertNotNull("Artifact.id", artifact.getId());
        Assert.assertEquals("Artifact.name", "Artifact 1", artifact.getName());
        Assert.assertEquals("Artifact.summary", "Artifact 1 Summary", artifact.getSummary());
        Assert.assertNotNull("Artifact.artifact", artifact.getArtifact());
        Assert.assertEquals("Artifact.artifact.name", "Work Items List", artifact.getArtifact().getName());
        
        entities.remove(OpenUP.Schema, OpenUP.Artifact, String.valueOf(artifact.getId()));
    }
    
    @Test(expected = ForbiddenException.class)
    public void testPersist_InvalidPermission() throws Exception {
    	DeliveryProcess dp = new DeliveryProcess();
        dp.setName("OpenUP Lifecycle 1");
        dp.setSummary("OpenUP Lifecycle 1");
        try(Client client = TestUtil.newClient(persistenceUrl)){
        	dp = client
        			.authorization(token)
        			.request(
        					target -> target.path(OpenUP.Schema).path(OpenUP.DeliveryProcess), 
        					req -> req.accept(MediaType.APPLICATION_JSON))
        			.post(Entity.json(dp), DeliveryProcess.class);
        }
    }
    
    @Test
    public void testSearchOK() throws Exception {
    	List<Target> results = queries.search("Any", 0, 100);
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
