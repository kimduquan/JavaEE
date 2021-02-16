/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.persistence;

import epf.client.RestClient;
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
import openup.client.security.PasswordHash;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author FOXCONN
 */
public class EntitiesTest {
    
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
        
        try(Client client = TestUtil.newClient(persistenceUrl)){
        	String id = String.valueOf(artifact.getId());
        	client
        	.authorization(token)
        	.request(
        			target -> target.path(OpenUP.Schema).path(OpenUP.Artifact).path(id),
        			req -> req.accept(MediaType.APPLICATION_JSON)
        			)
        	.delete();
        }
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
}
