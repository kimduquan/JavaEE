/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.tests.service.persistence;

import epf.client.persistence.Entities;
import epf.schema.EPF;
import epf.schema.delivery_processes.DeliveryProcess;
import epf.schema.work_products.Artifact;
import epf.schema.work_products.section.Description;
import epf.schema.work_products.section.Illustrations;
import epf.schema.work_products.section.MoreInformation;
import epf.schema.work_products.section.Relationships;
import epf.schema.work_products.section.Tailoring;
import epf.tests.client.ClientUtil;
import epf.tests.service.RegistryUtil;
import epf.tests.service.SecurityUtil;
import epf.util.client.Client;
import epf.util.logging.Logging;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
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
	
	private static final Logger logger = Logging.getLogger(EntitiesTest.class.getName());
    
	private static URI persistenceUrl;
    private static String token;
    private static String adminToken;
    private Client client;
    
    @BeforeClass
    public static void beforeClass(){
    	persistenceUrl = RegistryUtil.lookup("persistence", null);
    	token = SecurityUtil.login(null, "any_role1", "any_role");
    	adminToken = SecurityUtil.login(null, "admin1", "admin");
    }
    
    @AfterClass
    public static void afterClass(){
    	SecurityUtil.logOut(null, token);
    	SecurityUtil.logOut(null, adminToken);
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
        artifact.setName("Artifact 1");
        artifact.setSummary("Artifact 1 Summary");
        artifact.setDescription(new Description());
        artifact.setIllustrations(new Illustrations());
        artifact.setMoreInformation(new MoreInformation());
        artifact.setRelationships(new Relationships());
        artifact.setTailoring(new Tailoring());
        Artifact updatedArtifact = null;
        try(Client adminClient = ClientUtil.newClient(persistenceUrl)){
        	adminClient.authorization(adminToken);
        	updatedArtifact = Entities.persist(adminClient, Artifact.class, EPF.SCHEMA, EPF.ARTIFACT, artifact);
            Entities.remove(adminClient, EPF.SCHEMA, EPF.ARTIFACT, artifact.getName());
        }
        Assert.assertNotNull("Artifact", updatedArtifact);
        Assert.assertEquals("Artifact.name", "Artifact 1", updatedArtifact.getName());
        Assert.assertEquals("Artifact.summary", "Artifact 1 Summary", updatedArtifact.getSummary());
    }
    
    @Test(expected = ForbiddenException.class)
    public void testPersist_InvalidUnit() {
    	DeliveryProcess dp = new DeliveryProcess();
        dp.setName("Delivery Process 1");
        dp.setSummary("Delivery Process 1");
        Entities.persist(client, DeliveryProcess.class, "OpenUP", EPF.DELIVERY_PROCESS, dp);
    }
    
    //@Test(expected = NotFoundException.class)
    //https://github.com/OpenLiberty/open-liberty/issues/14217
    @Test(expected = InternalServerErrorException.class)
    public void testPersistEmptyName() {
    	Artifact artifact = new Artifact();
        artifact.setName("Artifact 1");
        artifact.setSummary("Artifact 1 Summary");
        artifact = Entities.persist(client, Artifact.class, EPF.SCHEMA, "", artifact);
    }
    
    @Test(expected = BadRequestException.class)
    public void testPersistBlankName() {
    	Artifact artifact = new Artifact();
        artifact.setName("Artifact 1");
        artifact.setSummary("Artifact 1 Summary");
        artifact = Entities.persist(client, Artifact.class, EPF.SCHEMA, "    ", artifact);
    }
    
    @Test(expected = NotFoundException.class)
    public void testPersistInvalidName() {
    	Artifact artifact = new Artifact();
        artifact = Entities.persist(client, Artifact.class, EPF.SCHEMA, "Invalid", artifact);
    }
    
    @Test(expected = BadRequestException.class)
    public void testPersistEmptyEntity() {
    	Artifact artifact = new Artifact();
        artifact = Entities.persist(client, Artifact.class, EPF.SCHEMA, EPF.ARTIFACT, artifact);
    }
    
    @Test(expected = BadRequestException.class)
    public void testPersistEmptyEntityId() {
    	Artifact artifact = new Artifact();
        artifact.setName("");
        artifact = Entities.persist(client, Artifact.class, EPF.SCHEMA, EPF.ARTIFACT, artifact);
    }
    
    @Test(expected = BadRequestException.class)
    public void testPersistBlankEntityId() {
    	Artifact artifact = new Artifact();
        artifact.setName("    ");
        artifact = Entities.persist(client, Artifact.class, EPF.SCHEMA, EPF.ARTIFACT, artifact);
    }
    
    @Test(expected = BadRequestException.class)
    public void testPersistNullEntityRequiredField() {
    	Artifact artifact = new Artifact();
        artifact.setName("Artifact 1");
        artifact.setSummary("Artifact 1 Summary");
        artifact = Entities.persist(client, Artifact.class, EPF.SCHEMA, EPF.ARTIFACT, artifact);
    }
    
    @Test(expected = BadRequestException.class)
    public void testPersistInvalidEntityType() {
    	Artifact artifact = new Artifact();
        artifact.setName("Artifact 1");
        artifact.setSummary("Artifact 1 Summary");
        artifact = Entities.persist(client, Artifact.class, EPF.SCHEMA, EPF.ACTIVITY, artifact);
    }
}
