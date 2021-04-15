package epf.service.persistence;

import java.net.URI;
import java.time.Duration;
import java.util.HashMap;
import javax.websocket.ContainerProvider;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import epf.client.messaging.MessageDecoder;
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
import epf.service.ClientUtil;
import epf.service.RegistryUtil;
import epf.service.SecurityUtil;
import epf.service.TestUtil;
import epf.util.websocket.Client;
import org.junit.Test;

public class ListenerTest {
	
	private static URI persistenceUrl;
	private static URI messagingUrl;
	private static URI listenerUrl;
	private static String token;
	private Client client;
    
    @BeforeClass
    public static void beforeClass() throws Exception{
    	persistenceUrl = RegistryUtil.lookup("persistence", null);
    	messagingUrl = RegistryUtil.lookup("messaging", null);
    	listenerUrl = new URI(messagingUrl.toString() + "/persistence");
    	System.out.println(listenerUrl);
    	token = SecurityUtil.login(null, "admin1", "admin");
    }
    
    @AfterClass
    public static void afterClass(){
    	SecurityUtil.logOut(null, token);
    }
    
    @After
    public void after() throws Exception {
    	client.close();
    }
    
    @Before
    public void before() throws Exception {
    	client = Client.connectToServer(ContainerProvider.getWebSocketContainer(), listenerUrl);
    	TestUtil.waitUntil(t -> client.getSession().isOpen(), Duration.ofSeconds(10));
    }

    @Test
    public void test() throws Exception {
    	Artifact artifact = new Artifact();
        artifact.setName("Artifact Cache");
        artifact.setSummary("Artifact Cache Summary");
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
    	
    	TestUtil.waitUntil((t) -> client.getMessages().stream().anyMatch(msg -> msg.contains(PostPersist.class.getName())), Duration.ofSeconds(10));
    	TestUtil.waitUntil((t) -> client.getMessages().stream().anyMatch(msg -> msg.contains(PostRemove.class.getName())), Duration.ofSeconds(10));
    	client.getMessages().forEach(msg -> System.out.println(String.valueOf(msg)));
    	MessageDecoder decoder = new MessageDecoder();
    	String message = client.getMessages().stream().filter(msg -> msg.contains(PostPersist.class.getName())).findFirst().get();
    	PostPersist persist = (PostPersist) decoder.decode(message);
    	message = client.getMessages().stream().filter(msg -> msg.contains(PostRemove.class.getName())).findFirst().get();
    	PostRemove remove = (PostRemove) decoder.decode(message);
    	
    	Assert.assertNotNull("PostPersist", persist);
    	Assert.assertTrue("PostPersist.entity", persist.getEntity() instanceof HashMap);
    	@SuppressWarnings("unchecked")
		HashMap<String, Object> persistEntity = (HashMap<String, Object>)persist.getEntity();
    	Assert.assertEquals("PostPersist.entity.class", Artifact.class.getName(), persistEntity.get("class"));
    	Assert.assertEquals("PostPersist.entity.name", artifact.getName(), persistEntity.get("name"));
    	
    	Assert.assertNotNull("PostRemove", remove);
    	Assert.assertTrue("PostRemove.entity", remove.getEntity() instanceof HashMap);
    	@SuppressWarnings("unchecked")
		HashMap<String, Object> removeEntity = (HashMap<String, Object>)remove.getEntity();
    	Assert.assertEquals("PostRemove.entity.class", Artifact.class.getName(), removeEntity.get("class"));
    	Assert.assertEquals("PostRemove.entity.name", artifact.getName(), removeEntity.get("name"));
    }
}
