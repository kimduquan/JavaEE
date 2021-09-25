package epf.tests.messaging;

import java.net.URI;
import java.time.Duration;
import java.util.HashMap;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import javax.ws.rs.core.UriBuilder;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import epf.client.gateway.GatewayUtil;
import epf.client.messaging.Client;
import epf.client.messaging.Messaging;
import epf.schema.PostPersist;
import epf.schema.PostRemove;
import epf.tests.TestUtil;
import epf.tests.health.HealthUtil;
import epf.tests.persistence.PersistenceUtil;
import epf.tests.security.SecurityUtil;
import epf.util.StringUtil;
import epf.work_products.schema.Artifact;
import epf.work_products.schema.WorkProducts;
import epf.work_products.schema.section.Description;
import epf.work_products.schema.section.Illustrations;
import epf.work_products.schema.section.MoreInformation;
import epf.work_products.schema.section.Relationships;
import epf.work_products.schema.section.Tailoring;

import org.junit.Test;

public class MessagingTest {
	
	private static URI listenerUrl;
	private static String token;
	private static String tokenId;
	private Client client;
	private Queue<Object> messages;
    
    @BeforeClass
    public static void beforeClass() throws Exception{
    	URI messagingUrl = UriBuilder.fromUri(GatewayUtil.getUrl().resolve("messaging")).scheme("ws").port(9080).build();
    	HealthUtil.readỵ̣();
    	token = SecurityUtil.login();
    	tokenId = SecurityUtil.auth(token).getTokenID();
    	listenerUrl = new URI(messagingUrl.toString() + "/persistence?tid=" + tokenId);
    }
    
    @AfterClass
    public static void afterClass(){
    	SecurityUtil.logOut(token);
    }
    
    @After
    public void after() throws Exception {
    	client.close();
    	messages.clear();
    }
    
    @Before
    public void before() throws Exception {
    	client = Messaging.connectToServer(listenerUrl);
    	messages = new ConcurrentLinkedQueue<>();
    	client.onMessage(messages::add);
    	TestUtil.waitUntil(t -> client.getSession().isOpen(), Duration.ofSeconds(10));
    }

    @Test
    public void test() throws Exception {
    	Artifact artifact = new Artifact();
        artifact.setName(StringUtil.randomString("Artifact Messaging"));
        artifact.setSummary("Artifact Messaging test");
        artifact.setDescription(new Description());
        artifact.setIllustrations(new Illustrations());
        artifact.setMoreInformation(new MoreInformation());
        artifact.setRelationships(new Relationships());
        artifact.setTailoring(new Tailoring());
        PersistenceUtil.persist(token, Artifact.class, WorkProducts.ARTIFACT, artifact);
        PersistenceUtil.remove(token, WorkProducts.ARTIFACT, artifact.getName());
    	
    	TestUtil.waitUntil((t) -> messages.stream().anyMatch(msg -> msg instanceof PostPersist), Duration.ofSeconds(10));
    	TestUtil.waitUntil((t) -> messages.stream().anyMatch(msg -> msg instanceof PostRemove), Duration.ofSeconds(10));
    	PostPersist persist = (PostPersist)messages.stream().filter(msg -> msg instanceof PostPersist).findFirst().get();
    	PostRemove remove = (PostRemove)messages.stream().filter(msg -> msg instanceof PostRemove).findFirst().get();
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
    
    @Test
    public void testInvalidTokenId() throws Exception {
    	URI messagingUrl = UriBuilder.fromUri(GatewayUtil.getUrl().resolve("messaging")).scheme("ws").port(9080).build();
    	URI url = new URI(messagingUrl.toString() + "/persistence");
    	try(Client invalidClient = Messaging.connectToServer(url)){
        	TestUtil.waitUntil(t -> !invalidClient.getSession().isOpen(), Duration.ofSeconds(10));
        	Assert.assertFalse("Client.session.open", invalidClient.getSession().isOpen());
    	}
    }
}
