/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.service.persistence;

import epf.client.persistence.Entities;
import epf.schema.EPF;
import epf.schema.delivery_processes.DeliveryProcess;
import epf.schema.work_products.Artifact;
import epf.schema.work_products.section.Description;
import epf.schema.work_products.section.Illustrations;
import epf.schema.work_products.section.MoreInformation;
import epf.schema.work_products.section.Relationships;
import epf.schema.work_products.section.Tailoring;
import epf.service.ClientUtil;
import epf.service.RegistryUtil;
import epf.service.SecurityUtil;
import epf.util.client.Client;
import epf.util.logging.Logging;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotFoundException;
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
public class EntitiesTest {
	
	private static final Logger logger = Logging.getLogger(EntitiesTest.class.getName());
    
	private static URI persistenceUrl;
    private static String token;
    private Client client;
    
    @BeforeClass
    public static void beforeClass(){
    	persistenceUrl = RegistryUtil.lookup("persistence");
    	token = SecurityUtil.login(null, "any_role1", "any_role");
    }
    
    @AfterClass
    public static void afterClass(){
    	SecurityUtil.logOut(null, token);
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
    public void testPersistOK(){
    	Artifact artifact = new Artifact();
        artifact.setName("Artifact 1");
        artifact.setSummary("Artifact 1 Summary");
        artifact.setDescription(new Description());
        artifact.setIllustrations(new Illustrations());
        artifact.setMoreInformation(new MoreInformation());
        artifact.setRelationships(new Relationships());
        artifact.setTailoring(new Tailoring());
        artifact = Entities.persist(client, Artifact.class, EPF.Schema, EPF.Artifact, artifact);
        Assert.assertNotNull("Artifact", artifact);
        Assert.assertEquals("Artifact.name", "Artifact 1", artifact.getName());
        Assert.assertEquals("Artifact.summary", "Artifact 1 Summary", artifact.getSummary());
        Entities.remove(client, EPF.Schema, EPF.Artifact, artifact.getName());
    }
    
    @Test(expected = ForbiddenException.class)
    public void testPersist_InvalidUnit() {
    	DeliveryProcess dp = new DeliveryProcess();
        dp.setName("Delivery Process 1");
        dp.setSummary("Delivery Process 1");
        Entities.persist(client, DeliveryProcess.class, "OpenUP", EPF.DeliveryProcess, dp);
    }
    
    @Test(expected = NotFoundException.class)
    public void testPersistEmptyName() {
    	Artifact artifact = new Artifact();
        artifact.setName("Artifact 1");
        artifact.setSummary("Artifact 1 Summary");
        artifact = Entities.persist(client, Artifact.class, EPF.Schema, "", artifact);
    }
    
    @Test(expected = BadRequestException.class)
    public void testPersistBlankName() {
    	Artifact artifact = new Artifact();
        artifact.setName("Artifact 1");
        artifact.setSummary("Artifact 1 Summary");
        artifact = Entities.persist(client, Artifact.class, EPF.Schema, "    ", artifact);
    }
    
    @Test(expected = NotFoundException.class)
    public void testPersistInvalidName() {
    	Artifact artifact = new Artifact();
        artifact = Entities.persist(client, Artifact.class, EPF.Schema, "Invalid", artifact);
    }
    
    @Test(expected = BadRequestException.class)
    public void testPersistEmptyEntity() {
    	Artifact artifact = new Artifact();
        artifact = Entities.persist(client, Artifact.class, EPF.Schema, EPF.Artifact, artifact);
    }
    
    @Test(expected = BadRequestException.class)
    public void testPersistEmptyEntityId() {
    	Artifact artifact = new Artifact();
        artifact.setName("");
        artifact = Entities.persist(client, Artifact.class, EPF.Schema, EPF.Artifact, artifact);
    }
    
    @Test(expected = BadRequestException.class)
    public void testPersistBlankEntityId() {
    	Artifact artifact = new Artifact();
        artifact.setName("    ");
        artifact = Entities.persist(client, Artifact.class, EPF.Schema, EPF.Artifact, artifact);
    }
    
    @Test(expected = BadRequestException.class)
    public void testPersistNullEntityRequiredField() {
    	Artifact artifact = new Artifact();
        artifact.setName("Artifact 1");
        artifact.setSummary("Artifact 1 Summary");
        artifact = Entities.persist(client, Artifact.class, EPF.Schema, EPF.Artifact, artifact);
    }
    
    @Test(expected = BadRequestException.class)
    public void testPersistInvalidEntityType() {
    	Artifact artifact = new Artifact();
        artifact.setName("Artifact 1");
        artifact.setSummary("Artifact 1 Summary");
        artifact = Entities.persist(client, Artifact.class, EPF.Schema, EPF.Activity, artifact);
    }
}
