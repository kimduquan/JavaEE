/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.tests.persistence;

import epf.client.model.EntityType;
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
import epf.tests.registry.RegistryUtil;
import epf.tests.security.SecurityUtil;
import epf.util.client.Client;
import epf.util.logging.Logging;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
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
        artifact.setName("Artifact Entities" + System.currentTimeMillis());
        artifact.setSummary("Artifact Entities Summary" + UUID.randomUUID());
        artifact.setDescription(new Description());
        artifact.setIllustrations(new Illustrations());
        artifact.setMoreInformation(new MoreInformation());
        artifact.setRelationships(new Relationships());
        artifact.setTailoring(new Tailoring());
        Artifact updatedArtifact = null;
        try(Client adminClient = ClientUtil.newClient(persistenceUrl)){
        	adminClient.authorization(adminToken);
        	updatedArtifact = Entities.persist(adminClient, Artifact.class, EPF.SCHEMA, EPF.ARTIFACT, artifact);
        }
        Assert.assertNotNull("Artifact", updatedArtifact);
        Assert.assertEquals("Artifact.name", artifact.getName(), updatedArtifact.getName());
        Assert.assertEquals("Artifact.summary", artifact.getSummary(), updatedArtifact.getSummary());
        
        PersistenceUtil.remove(adminToken, EPF.SCHEMA, EPF.ARTIFACT, artifact.getName());
    }
    
    @Test
    public void testMergeOK() throws Exception{
    	Artifact artifact = new Artifact();
        artifact.setName("Artifact Entities" + System.currentTimeMillis());
        artifact.setSummary("Artifact Entities Summary" + UUID.randomUUID());
        artifact.setDescription(new Description());
        artifact.setIllustrations(new Illustrations());
        artifact.setMoreInformation(new MoreInformation());
        artifact.setRelationships(new Relationships());
        artifact.setTailoring(new Tailoring());
        PersistenceUtil.persist(adminToken, Artifact.class, EPF.SCHEMA, EPF.ARTIFACT, artifact);
        Artifact updatedArtifact = null;
        try(Client adminClient = ClientUtil.newClient(persistenceUrl)){
        	adminClient.authorization(adminToken);
            updatedArtifact = new Artifact();
            updatedArtifact.setName("Artifact Entities" + System.currentTimeMillis());
            updatedArtifact.setSummary("Artifact Entities Summary" + UUID.randomUUID());
            updatedArtifact.setDescription(new Description());
            updatedArtifact.setIllustrations(new Illustrations());
            updatedArtifact.setMoreInformation(new MoreInformation());
            updatedArtifact.setRelationships(new Relationships());
            updatedArtifact.setTailoring(new Tailoring());
        	Entities.merge(adminClient, EPF.SCHEMA, EPF.ARTIFACT, artifact.getName(), updatedArtifact);
        }
        PersistenceUtil.remove(adminToken, EPF.SCHEMA, EPF.ARTIFACT, updatedArtifact.getName());
    }
    
    @Test
    public void testRemoveOK() throws Exception{
    	Artifact artifact = new Artifact();
        artifact.setName("Artifact Entities" + System.currentTimeMillis());
        artifact.setSummary("Artifact Entities Summary" + UUID.randomUUID());
        artifact.setDescription(new Description());
        artifact.setIllustrations(new Illustrations());
        artifact.setMoreInformation(new MoreInformation());
        artifact.setRelationships(new Relationships());
        artifact.setTailoring(new Tailoring());
        PersistenceUtil.persist(adminToken, Artifact.class, EPF.SCHEMA, EPF.ARTIFACT, artifact);
        try(Client adminClient = ClientUtil.newClient(persistenceUrl)){
        	adminClient.authorization(adminToken);
            Entities.remove(adminClient, EPF.SCHEMA, EPF.ARTIFACT, artifact.getName());
        }
    }
    
    @Test
    public void testGetEntitiesOK() throws Exception{
    	Response res = Entities.getEntities(client, null);
    	List<EntityType> entities = res.readEntity(new GenericType<List<EntityType>>() {});
    	Assert.assertFalse("List<EntityType>", entities.isEmpty());
    	entities.forEach(entity -> System.out.println(entity.getName()));
    }
    
    @Test(expected = ForbiddenException.class)
    public void testPersist_InvalidUnit() {
    	DeliveryProcess dp = new DeliveryProcess();
        dp.setName("Delivery Process 1");
        dp.setSummary("Delivery Process 1");
        Entities.persist(client, DeliveryProcess.class, "OpenUP", EPF.DELIVERY_PROCESS, dp);
    }
    
    
    @Ignore
    //https://github.com/OpenLiberty/open-liberty/issues/14217
    @Test(expected = NotFoundException.class)
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
