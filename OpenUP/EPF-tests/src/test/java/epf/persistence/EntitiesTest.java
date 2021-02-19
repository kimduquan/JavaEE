/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.persistence;

import epf.TestUtil;
import epf.client.persistence.Entities;
import epf.client.persistence.Queries;
import epf.schema.EPF;
import epf.util.client.Client;
import openup.schema.OpenUP;
import openup.schema.Artifact;
import openup.schema.DeliveryProcess;
import java.net.URI;
import java.util.List;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.core.GenericType;

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
public class EntitiesTest {
    
	private static URI persistenceUrl;
    private static String token;
    private Client client;
    
    @BeforeClass
    public static void beforeClass() throws Exception{
    	persistenceUrl = new URI(TestUtil.gateway_url().toString() + "persistence");
    	token = TestUtil.login(OpenUP.Schema, "any_role1", "any_role");
    }
    
    @AfterClass
    public static void afterClass() throws Exception{
    	TestUtil.logOut(OpenUP.Schema, token);
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
    public void testPersistOK() throws Exception{
    	List<epf.schema.work_products.Artifact> epfArtifacts = Queries
    			.getCriteriaQueryResult(
    					client, 
    					new GenericType<List<epf.schema.work_products.Artifact>>() {},
						OpenUP.Schema, 
						target -> target.path(EPF.Artifact).matrixParam("name", "Work Items List"), 
						null, 
						null
						);
        
        epf.schema.work_products.Artifact epfArtifact = epfArtifacts.get(0);
        Artifact artifact = new Artifact();
        artifact.setArtifact(epfArtifact);
        artifact.setName("Artifact 1");
        artifact.setSummary("Artifact 1 Summary");
        
        artifact = Entities.persist(client, Artifact.class, OpenUP.Schema, OpenUP.Artifact, artifact);
        
        Assert.assertNotNull("Artifact", artifact);
        Assert.assertNotNull("Artifact.id", artifact.getId());
        Assert.assertEquals("Artifact.name", "Artifact 1", artifact.getName());
        Assert.assertEquals("Artifact.summary", "Artifact 1 Summary", artifact.getSummary());
        Assert.assertNotNull("Artifact.artifact", artifact.getArtifact());
        Assert.assertEquals("Artifact.artifact.name", "Work Items List", artifact.getArtifact().getName());
        
        Entities.remove(client, OpenUP.Schema, OpenUP.Artifact, String.valueOf(artifact.getId()));
    }
    
    @Test(expected = ForbiddenException.class)
    public void testPersist_InvalidPermission() throws Exception {
    	DeliveryProcess dp = new DeliveryProcess();
        dp.setName("OpenUP Lifecycle 1");
        dp.setSummary("OpenUP Lifecycle 1");
        Entities.persist(client, DeliveryProcess.class, OpenUP.Schema, OpenUP.DeliveryProcess, dp);
    }
}
