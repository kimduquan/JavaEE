/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.persistence;

import epf.TestUtil;
import epf.client.persistence.Entities;
import epf.schema.EPF;
import epf.schema.delivery_processes.DeliveryProcess;
import epf.schema.work_products.Artifact;
import epf.util.client.Client;
import java.net.URI;
import javax.ws.rs.ForbiddenException;
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
    	token = TestUtil.login(null, "any_role1", "any_role");
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
    public void testPersistOK() throws Exception{
    	Artifact artifact = new Artifact();
        artifact.setName("Artifact 1");
        artifact.setSummary("Artifact 1 Summary");
        
        artifact = Entities.persist(client, Artifact.class, EPF.Schema, EPF.Artifact, artifact);
        
        Assert.assertNotNull("Artifact", artifact);
        Assert.assertEquals("Artifact.name", "Artifact 1", artifact.getName());
        Assert.assertEquals("Artifact.summary", "Artifact 1 Summary", artifact.getSummary());
        
        Entities.remove(client, EPF.Schema, EPF.Artifact, artifact.getName());
    }
    
    @Test(expected = ForbiddenException.class)
    public void testPersist_InvalidPermission() throws Exception {
    	DeliveryProcess dp = new DeliveryProcess();
        dp.setName("Delivery Process 1");
        dp.setSummary("Delivery Process 1");
        Entities.persist(client, DeliveryProcess.class, "OpenUP", EPF.DeliveryProcess, dp);
    }
}
