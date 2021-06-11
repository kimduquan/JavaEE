package epf.tests.persistence;

import java.net.URI;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.websocket.ContainerProvider;
import javax.ws.rs.sse.SseEventSource;
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
import epf.tests.TestUtil;
import epf.tests.client.ClientUtil;
import epf.tests.registry.RegistryUtil;
import epf.tests.security.SecurityUtil;
import epf.util.websocket.Client;
import org.junit.Test;

public class ListenerTest {
	
	private static URI persistenceUrl;
	private static URI listenerUrl;
	private static URI streamUrl;
	private static String token;
	private static ExecutorService executor;
	private Client client;
	private static epf.util.client.Client streamClient;
	private static SseEventSource event;
	private static Queue<String> events;
    
    @BeforeClass
    public static void beforeClass() throws Exception{
    	persistenceUrl = RegistryUtil.lookup("persistence", null);
    	URI messagingUrl = RegistryUtil.lookup("messaging", null);
    	listenerUrl = new URI(messagingUrl.toString() + "/persistence");
    	token = SecurityUtil.login(null, "admin1", "admin");
    	streamUrl = RegistryUtil.lookup("stream", null);
    	executor = Executors.newFixedThreadPool(1);
    	streamClient = ClientUtil.newClient(streamUrl);
    	events = new ConcurrentLinkedQueue<>();
    	event = streamClient.stream(target -> target.path("persistence"), b -> b);
    	event.register(e -> {
    		String data = e.readData();
    		events.add(data);
    	});
    	executor.submit(() -> event.open());
    }
    
    @AfterClass
    public static void afterClass() throws Exception{
    	SecurityUtil.logOut(null, token);
    	event.close();
    	executor.shutdown();
    	streamClient.close();
    }
    
    @After
    public void after() throws Exception {
    	client.close();
    	events.clear();
    }
    
    @Before
    public void before() throws Exception {
    	client = Client.connectToServer(ContainerProvider.getWebSocketContainer(), listenerUrl);
    	TestUtil.waitUntil(t -> client.getSession().isOpen(), Duration.ofSeconds(20));
    }

    @Test
    public void test() throws Exception {
    	Artifact artifact = new Artifact();
        artifact.setName("Artifact Listener" + String.valueOf(Instant.now().toEpochMilli()));
        artifact.setSummary("Artifact Listener Summary");
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
    	
    	TestUtil.waitUntil((t) -> client.getMessages().stream().anyMatch(msg -> msg.contains(PostPersist.class.getName()) && msg.contains(artifact.getName())), Duration.ofSeconds(20));
    	TestUtil.waitUntil((t) -> client.getMessages().stream().anyMatch(msg -> msg.contains(PostRemove.class.getName()) && msg.contains(artifact.getName())), Duration.ofSeconds(20));
    	MessageDecoder decoder = new MessageDecoder();
    	String message = client.getMessages().stream().filter(msg -> msg.contains(PostPersist.class.getName()) && msg.contains(artifact.getName())).findFirst().get();
    	PostPersist persist = (PostPersist) decoder.decode(message);
    	message = client.getMessages().stream().filter(msg -> msg.contains(PostRemove.class.getName()) && msg.contains(artifact.getName())).findFirst().get();
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
        artifact.setName("Artifact Listener Event" + Instant.now().toEpochMilli());
        artifact.setSummary("Artifact Listener Event Summary");
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
    	
    	TestUtil.waitUntil((t) -> events.stream().anyMatch(msg -> msg.contains(PostPersist.class.getName()) && msg.contains(artifact.getName())), Duration.ofSeconds(10));
    	TestUtil.waitUntil((t) -> events.stream().anyMatch(msg -> msg.contains(PostRemove.class.getName()) && msg.contains(artifact.getName())), Duration.ofSeconds(10));
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
