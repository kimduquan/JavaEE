/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.tests.persistence;

import epf.client.gateway.GatewayUtil;
import epf.client.persistence.Entities;
import epf.schema.EPF;
import epf.schema.work_products.Artifact;
import epf.schema.work_products.section.Description;
import epf.schema.work_products.section.Illustrations;
import epf.schema.work_products.section.MoreInformation;
import epf.schema.work_products.section.Relationships;
import epf.schema.work_products.section.Tailoring;
import epf.tests.client.ClientUtil;
import epf.tests.security.SecurityUtil;
import epf.util.StringUtil;
import epf.util.client.Client;
import epf.util.logging.Logging;
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
        	updatedArtifact = Entities.persist(adminClient, Artifact.class, EPF.ARTIFACT, artifact);
        }
        Assert.assertNotNull("Artifact", updatedArtifact);
        Assert.assertEquals("Artifact.name", artifact.getName(), updatedArtifact.getName());
        Assert.assertEquals("Artifact.summary", artifact.getSummary(), updatedArtifact.getSummary());
        
        PersistenceUtil.remove(token, EPF.ARTIFACT, artifact.getName());
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
        PersistenceUtil.persist(token, Artifact.class, EPF.ARTIFACT, artifact);
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
        	Entities.merge(adminClient, EPF.ARTIFACT, artifact.getName(), updatedArtifact);
        }
        PersistenceUtil.remove(token, EPF.ARTIFACT, updatedArtifact.getName());
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
        PersistenceUtil.persist(token, Artifact.class, EPF.ARTIFACT, artifact);
        try(Client adminClient = ClientUtil.newClient(persistenceUrl)){
        	adminClient.authorization(token);
            Entities.remove(adminClient, EPF.ARTIFACT, artifact.getName());
        }
    }
    
    
    @Ignore
    //https://github.com/OpenLiberty/open-liberty/issues/14217
    @Test(expected = NotFoundException.class)
    public void testPersistEmptyName() {
    	Artifact artifact = new Artifact();
        artifact.setName(StringUtil.randomString("Artifact Entities"));
        artifact.setSummary("Artifact Entities testPersistEmptyName");
        artifact = Entities.persist(client, Artifact.class, "", artifact);
    }
    
    @Test(expected = BadRequestException.class)
    public void testPersistBlankName() {
    	Artifact artifact = new Artifact();
        artifact.setName(StringUtil.randomString("Artifact Entities"));
        artifact.setSummary("Artifact Entities testPersistBlankName");
        artifact = Entities.persist(client, Artifact.class, "    ", artifact);
    }
    
    @Test(expected = NotFoundException.class)
    public void testPersistInvalidName() {
    	Artifact artifact = new Artifact();
        artifact = Entities.persist(client, Artifact.class, "Invalid", artifact);
    }
    
    @Test(expected = BadRequestException.class)
    public void testPersistEmptyEntity() {
    	Artifact artifact = new Artifact();
        artifact = Entities.persist(client, Artifact.class, EPF.ARTIFACT, artifact);
    }
    
    @Test(expected = BadRequestException.class)
    public void testPersistEmptyEntityId() {
    	Artifact artifact = new Artifact();
        artifact.setName("");
        artifact = Entities.persist(client, Artifact.class, EPF.ARTIFACT, artifact);
    }
    
    @Test(expected = BadRequestException.class)
    public void testPersistBlankEntityId() {
    	Artifact artifact = new Artifact();
        artifact.setName("    ");
        artifact = Entities.persist(client, Artifact.class, EPF.ARTIFACT, artifact);
    }
    
    @Test(expected = BadRequestException.class)
    public void testPersistNullEntityRequiredField() {
    	Artifact artifact = new Artifact();
        artifact.setName(StringUtil.randomString("Artifact Entities"));
        artifact.setSummary("Artifact Entities testPersistNullEntityRequiredField");
        artifact = Entities.persist(client, Artifact.class, EPF.ARTIFACT, artifact);
    }
    
    @Test(expected = BadRequestException.class)
    public void testPersistInvalidEntityType() {
    	Artifact artifact = new Artifact();
        artifact.setName(StringUtil.randomString("Artifact Entities"));
        artifact.setSummary("Artifact Entities testPersistInvalidEntityType");
        artifact = Entities.persist(client, Artifact.class, EPF.ACTIVITY, artifact);
    }
}
