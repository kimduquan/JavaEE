package epf.service.cache;

import java.net.URI;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import epf.client.messaging.Client;
import epf.client.persistence.Entities;
import epf.schema.EPF;
import epf.schema.PostPersist;
import epf.schema.PostRemove;
import epf.schema.work_products.Artifact;
import epf.schema.work_products.section.Description;
import epf.schema.work_products.section.Illustrations;
import epf.schema.work_products.section.MoreInformation;
import epf.schema.work_products.section.Relationships;
import epf.schema.work_products.section.Tailoring;
import epf.service.CacheUtil;
import epf.service.ClientUtil;
import epf.service.RegistryUtil;
import epf.service.SecurityUtil;
import epf.service.TestUtil;
import org.junit.Test;

public class CacheTest {
	
	private static URI persistenceUrl;
	private static String token;
	private Client client;
	private List<Object> messages = Collections.synchronizedList(new ArrayList<>());
    
    @BeforeClass
    public static void beforeClass() throws Exception{
    	persistenceUrl = RegistryUtil.lookup("persistence", null);
    	token = SecurityUtil.login(null, "admin1", "admin");
    }
    
    @AfterClass
    public static void afterClass(){
    	SecurityUtil.logOut(null, token);
    }
    
    @After
    public void after() throws Exception {
    	client.close();
    	messages.clear();
    }
    
    @Before
    public void before() throws Exception {
    	client = CacheUtil.connectToServer();
    	client.onMessage(messages::add);
    }

    @Test
    public void test() throws Exception {
    	Artifact artifact = new Artifact();
        artifact.setName("Artifact 1");
        artifact.setSummary("Artifact 1 Summary");
        artifact.setDescription(new Description());
        artifact.setIllustrations(new Illustrations());
        artifact.setMoreInformation(new MoreInformation());
        artifact.setRelationships(new Relationships());
        artifact.setTailoring(new Tailoring());
    	try(epf.util.client.Client persistenceClient = ClientUtil.newClient(persistenceUrl)){
    		persistenceClient.authorization(token);
    		Entities.persist(persistenceClient, Artifact.class, EPF.SCHEMA, EPF.ARTIFACT, artifact);
            Entities.remove(persistenceClient, EPF.SCHEMA, EPF.ARTIFACT, artifact.getName());
    	}
    	
    	TestUtil.waitUntil((t) -> messages.stream().anyMatch(msg -> msg instanceof PostPersist), Duration.ofSeconds(20));
    	TestUtil.waitUntil((t) -> messages.stream().anyMatch(msg -> msg instanceof PostRemove), Duration.ofSeconds(20));
    	
    	PostPersist persist = (PostPersist) messages.stream().filter(object -> object instanceof PostPersist).findFirst().get();
    	Assert.assertNotNull("PostPersist", persist);
    	Assert.assertTrue("PostPersist.entity", persist.getEntity() instanceof HashMap);
    	@SuppressWarnings("unchecked")
		HashMap<String, Object> persistEntity = (HashMap<String, Object>)persist.getEntity();
    	Assert.assertEquals("PostPersist.entity.class", Artifact.class.getName(), persistEntity.get("class"));
    	Assert.assertEquals("PostPersist.entity.name", artifact.getName(), persistEntity.get("name"));
    	
    	PostRemove remove = (PostRemove) messages.stream().filter(object -> object instanceof PostRemove).findFirst().get();
    	Assert.assertNotNull("PostRemove", remove);
    	Assert.assertTrue("PostRemove.entity", remove.getEntity() instanceof HashMap);
    	@SuppressWarnings("unchecked")
		HashMap<String, Object> removeEntity = (HashMap<String, Object>)remove.getEntity();
    	Assert.assertEquals("PostRemove.entity.class", Artifact.class.getName(), removeEntity.get("class"));
    	Assert.assertEquals("PostRemove.entity.name", artifact.getName(), removeEntity.get("name"));
    }
}
