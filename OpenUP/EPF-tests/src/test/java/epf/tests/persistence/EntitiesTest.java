/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.tests.persistence;

import epf.client.gateway.GatewayUtil;
import epf.client.persistence.Entities;
import epf.client.util.Client;
import epf.delivery_processes.schema.DeliveryProcesses;
import epf.tests.client.ClientUtil;
import epf.tests.security.SecurityUtil;
import epf.util.StringUtil;
import epf.util.logging.Logging;
import epf.work_products.schema.Artifact;
import epf.work_products.schema.WorkProducts;
import epf.work_products.schema.section.Description;
import epf.work_products.schema.section.Illustrations;
import epf.work_products.schema.section.MoreInformation;
import epf.work_products.schema.section.Relationships;
import epf.work_products.schema.section.Tailoring;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
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
public class EntitiesTest {
	
	private static final Logger logger = Logging.getLogger(EntitiesTest.class.getName());
	private static URI persistenceUrl;
    private static String token;
    private Client client;
    
    @BeforeClass
    public static void beforeClass() throws Exception{
    	persistenceUrl = GatewayUtil.get("persistence");
    	token = SecurityUtil.login();
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
    public void testPersistOK() throws Exception{
    	Artifact artifact = new Artifact();
        artifact.setName(StringUtil.randomString("Artifact Entities"));
        artifact.setSummary("Artifact Entities testPersistOK");
        artifact.setDescription(new Description());
        artifact.setIllustrations(new Illustrations());
        artifact.setMoreInformation(new MoreInformation());
        artifact.setRelationships(new Relationships());
        artifact.setTailoring(new Tailoring());
        Artifact updatedArtifact = null;
        try(Client adminClient = ClientUtil.newClient(persistenceUrl)){
        	adminClient.authorization(token);
        	updatedArtifact = Entities.persist(adminClient, Artifact.class, WorkProducts.SCHEMA, WorkProducts.ARTIFACT, artifact);
        }
        Assert.assertNotNull("Artifact", updatedArtifact);
        Assert.assertEquals("Artifact.name", artifact.getName(), updatedArtifact.getName());
        Assert.assertEquals("Artifact.summary", artifact.getSummary(), updatedArtifact.getSummary());
        
        PersistenceUtil.remove(token, WorkProducts.SCHEMA, WorkProducts.ARTIFACT, artifact.getName());
    }
    
    @Test
    public void testMergeOK() throws Exception{
    	Artifact artifact = new Artifact();
        artifact.setName(StringUtil.randomString("Artifact Entities"));
        artifact.setSummary(StringUtil.randomString("Artifact Entities testMergeOK"));
        artifact.setDescription(new Description());
        artifact.setIllustrations(new Illustrations());
        artifact.setMoreInformation(new MoreInformation());
        artifact.setRelationships(new Relationships());
        artifact.setTailoring(new Tailoring());
        PersistenceUtil.persist(token, Artifact.class, WorkProducts.SCHEMA, WorkProducts.ARTIFACT, artifact);
        Artifact updatedArtifact = null;
        try(Client adminClient = ClientUtil.newClient(persistenceUrl)){
        	adminClient.authorization(token);
            updatedArtifact = new Artifact();
            updatedArtifact.setName(artifact.getName());
            updatedArtifact.setSummary(StringUtil.randomString("Artifact Entities testMergeOK"));
            updatedArtifact.setDescription(new Description());
            updatedArtifact.setIllustrations(new Illustrations());
            updatedArtifact.setMoreInformation(new MoreInformation());
            updatedArtifact.setRelationships(new Relationships());
            updatedArtifact.setTailoring(new Tailoring());
        	Entities.merge(adminClient, WorkProducts.SCHEMA, WorkProducts.ARTIFACT, artifact.getName(), updatedArtifact);
        }
        PersistenceUtil.remove(token, WorkProducts.SCHEMA, WorkProducts.ARTIFACT, updatedArtifact.getName());
    }
    
    @Test
    public void testRemoveOK() throws Exception{
    	Artifact artifact = new Artifact();
        artifact.setName(StringUtil.randomString("Artifact Entities"));
        artifact.setSummary("Artifact Entities testRemoveOK");
        artifact.setDescription(new Description());
        artifact.setIllustrations(new Illustrations());
        artifact.setMoreInformation(new MoreInformation());
        artifact.setRelationships(new Relationships());
        artifact.setTailoring(new Tailoring());
        PersistenceUtil.persist(token, Artifact.class, WorkProducts.SCHEMA, WorkProducts.ARTIFACT, artifact);
        try(Client adminClient = ClientUtil.newClient(persistenceUrl)){
        	adminClient.authorization(token);
            Entities.remove(adminClient, WorkProducts.SCHEMA, WorkProducts.ARTIFACT, artifact.getName());
        }
    }
    
    
    @Ignore
    //https://github.com/OpenLiberty/open-liberty/issues/14217
    @Test(expected = NotFoundException.class)
    public void testPersistEmptyName() {
    	Artifact artifact = new Artifact();
        artifact.setName(StringUtil.randomString("Artifact Entities"));
        artifact.setSummary("Artifact Entities testPersistEmptyName");
        artifact = Entities.persist(client, Artifact.class, WorkProducts.SCHEMA, "", artifact);
    }
    
    @Test(expected = BadRequestException.class)
    public void testPersistBlankName() {
    	Artifact artifact = new Artifact();
        artifact.setName(StringUtil.randomString("Artifact Entities"));
        artifact.setSummary("Artifact Entities testPersistBlankName");
        artifact = Entities.persist(client, Artifact.class, WorkProducts.SCHEMA, "    ", artifact);
    }
    
    @Test(expected = NotFoundException.class)
    public void testPersistInvalidName() {
    	Artifact artifact = new Artifact();
        artifact = Entities.persist(client, Artifact.class, WorkProducts.SCHEMA, "Invalid", artifact);
    }
    
    @Test(expected = BadRequestException.class)
    public void testPersistEmptyEntity() {
    	Artifact artifact = new Artifact();
        artifact = Entities.persist(client, Artifact.class, WorkProducts.SCHEMA, WorkProducts.ARTIFACT, artifact);
    }
    
    @Test(expected = BadRequestException.class)
    public void testPersistEmptyEntityId() {
    	Artifact artifact = new Artifact();
        artifact.setName("");
        artifact = Entities.persist(client, Artifact.class, WorkProducts.SCHEMA, WorkProducts.ARTIFACT, artifact);
    }
    
    @Test(expected = BadRequestException.class)
    public void testPersistBlankEntityId() {
    	Artifact artifact = new Artifact();
        artifact.setName("    ");
        artifact = Entities.persist(client, Artifact.class, WorkProducts.SCHEMA, WorkProducts.ARTIFACT, artifact);
    }
    
    @Test(expected = BadRequestException.class)
    public void testPersistNullEntityRequiredField() {
    	Artifact artifact = new Artifact();
        artifact.setName(StringUtil.randomString("Artifact Entities"));
        artifact.setSummary("Artifact Entities testPersistNullEntityRequiredField");
        artifact = Entities.persist(client, Artifact.class, WorkProducts.SCHEMA, WorkProducts.ARTIFACT, artifact);
    }
    
    @Test(expected = BadRequestException.class)
    public void testPersistInvalidEntityType() {
    	Artifact artifact = new Artifact();
        artifact.setName(StringUtil.randomString("Artifact Entities"));
        artifact.setSummary("Artifact Entities testPersistInvalidEntityType");
        artifact = Entities.persist(client, Artifact.class, DeliveryProcesses.SCHEMA, DeliveryProcesses.ACTIVITY, artifact);
    }
}
