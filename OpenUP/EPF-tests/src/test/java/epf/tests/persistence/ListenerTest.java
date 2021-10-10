package epf.tests.persistence;

import java.net.URI;
import java.time.Duration;
import java.util.HashMap;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.sse.SseEventSource;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import epf.client.gateway.GatewayUtil;
import epf.client.persistence.Entities;
import epf.messaging.client.MessageDecoder;
import epf.schema.PostPersist;
import epf.schema.PostRemove;
import epf.tests.TestUtil;
import epf.tests.client.ClientUtil;
import epf.tests.security.SecurityUtil;
import epf.util.StringUtil;
import epf.util.websocket.Client;
import epf.work_products.schema.Artifact;
import epf.work_products.schema.WorkProducts;
import epf.work_products.schema.section.Description;
import epf.work_products.schema.section.Illustrations;
import epf.work_products.schema.section.MoreInformation;
import epf.work_products.schema.section.Relationships;
import epf.work_products.schema.section.Tailoring;

import org.junit.Test;

public class ListenerTest {
	
	private static int timeout = 60;
	private static URI persistenceUrl;
	private static URI listenerUrl;
	private static URI streamUrl;
	private static String token;
	private static String tokenId;
	private static ExecutorService executor;
	private Client client;
	private Queue<String> messages;
	private static epf.client.util.Client streamClient;
	private static SseEventSource event;
	private static Queue<String> events;
    
    @BeforeClass
    public static void beforeClass() throws Exception{
    	persistenceUrl = GatewayUtil.get("persistence");
    	URI messagingUrl = UriBuilder.fromUri(GatewayUtil.get("messaging")).scheme("ws").port(9080).build();
    	token = SecurityUtil.login();
    	tokenId = SecurityUtil.auth(token).getTokenID();
    	listenerUrl = new URI(messagingUrl.toString() + "/persistence?tid=" + tokenId);
    	streamUrl = GatewayUtil.get("stream");
    	executor = Executors.newFixedThreadPool(1);
    	streamClient = ClientUtil.newClient(streamUrl);
    	events = new ConcurrentLinkedQueue<>();
    	event = streamClient.stream(target -> target.path("persistence").matrixParam("tid", tokenId), b -> b);
    	event.register(e -> {
    		String data = e.readData();
    		events.add(data);
    	});
    	executor.submit(() -> event.open());
    }
    
    @AfterClass
    public static void afterClass() throws Exception{
    	SecurityUtil.logOut(token);
    	event.close();
    	executor.shutdown();
    	streamClient.close();
    }
    
    @After
    public void after() throws Exception {
    	client.close();
    	events.clear();
    	messages.clear();
    }
    
    @Before
    public void before() throws Exception {
    	messages = new ConcurrentLinkedQueue<>();
    	client = Client.connectToServer(listenerUrl);
    	client.onMessage(messages::add);
    	TestUtil.waitUntil(t -> client.getSession().isOpen(), Duration.ofSeconds(timeout));
    }

    @Test
    public void test() throws Exception {
    	Artifact artifact = new Artifact();
        artifact.setName(StringUtil.randomString("Artifact Listener"));
        artifact.setSummary("Artifact Listener test");
        artifact.setDescription(new Description());
        artifact.setIllustrations(new Illustrations());
        artifact.setMoreInformation(new MoreInformation());
        artifact.setRelationships(new Relationships());
        artifact.setTailoring(new Tailoring());
    	try(epf.client.util.Client persistenceClient = ClientUtil.newClient(persistenceUrl)){
    		persistenceClient.authorization(token);
    		Entities.persist(persistenceClient, Artifact.class, WorkProducts.SCHEMA, WorkProducts.ARTIFACT, artifact);
            Entities.remove(persistenceClient, WorkProducts.SCHEMA, WorkProducts.ARTIFACT, artifact.getName());
    	}
    	
    	TestUtil.waitUntil((t) -> messages.stream().anyMatch(msg -> msg.contains(PostPersist.class.getName()) && msg.contains(artifact.getName())), Duration.ofSeconds(timeout));
    	TestUtil.waitUntil((t) -> messages.stream().anyMatch(msg -> msg.contains(PostRemove.class.getName()) && msg.contains(artifact.getName())), Duration.ofSeconds(timeout));
    	MessageDecoder decoder = new MessageDecoder();
    	String message = messages.stream().filter(msg -> msg.contains(PostPersist.class.getName()) && msg.contains(artifact.getName())).findFirst().get();
    	PostPersist persist = (PostPersist) decoder.decode(message);
    	message = messages.stream().filter(msg -> msg.contains(PostRemove.class.getName()) && msg.contains(artifact.getName())).findFirst().get();
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
    
    @Test
    public void testStream() throws Exception {
    	Artifact artifact = new Artifact();
        artifact.setName(StringUtil.randomString("Artifact Listener"));
        artifact.setSummary("Artifact Listener testStream");
        artifact.setDescription(new Description());
        artifact.setIllustrations(new Illustrations());
        artifact.setMoreInformation(new MoreInformation());
        artifact.setRelationships(new Relationships());
        artifact.setTailoring(new Tailoring());
    	try(epf.client.util.Client persistenceClient = ClientUtil.newClient(persistenceUrl)){
    		persistenceClient.authorization(token);
    		Entities.persist(persistenceClient, Artifact.class, WorkProducts.SCHEMA, WorkProducts.ARTIFACT, artifact);
            Entities.remove(persistenceClient, WorkProducts.SCHEMA, WorkProducts.ARTIFACT, artifact.getName());
    	}
    	
    	TestUtil.waitUntil((t) -> events.stream().anyMatch(msg -> msg.contains(PostPersist.class.getName()) && msg.contains(artifact.getName())), Duration.ofSeconds(timeout));
    	TestUtil.waitUntil((t) -> events.stream().anyMatch(msg -> msg.contains(PostRemove.class.getName()) && msg.contains(artifact.getName())), Duration.ofSeconds(timeout));
    	MessageDecoder decoder = new MessageDecoder();
    	String message = events.stream().filter(msg -> msg.contains(PostPersist.class.getName()) && msg.contains(artifact.getName())).findFirst().get();
    	PostPersist persist = (PostPersist) decoder.decode(message);
    	message = events.stream().filter(msg -> msg.contains(PostRemove.class.getName()) && msg.contains(artifact.getName())).findFirst().get();
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
