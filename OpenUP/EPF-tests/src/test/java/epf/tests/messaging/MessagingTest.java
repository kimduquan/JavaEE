package epf.tests.messaging;

import java.net.URI;
import java.time.Duration;
import java.util.HashMap;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Rule;
import epf.messaging.client.Client;
import epf.messaging.client.Messaging;
import epf.naming.Naming;
import epf.schema.utility.PostPersist;
import epf.schema.utility.PostRemove;
import epf.tests.TestUtil;
import epf.tests.health.HealthUtil;
import epf.tests.persistence.PersistenceUtil;
import epf.tests.security.SecurityUtil;
import epf.util.StringUtil;
import epf.util.SystemUtil;
import epf.util.config.ConfigUtil;
import epf.work_products.schema.Artifact;
import epf.work_products.schema.WorkProducts;
import epf.work_products.schema.section.Description;
import epf.work_products.schema.section.Illustrations;
import epf.work_products.schema.section.MoreInformation;
import epf.work_products.schema.section.Relationships;
import epf.work_products.schema.section.Tailoring;
import org.junit.Test;
import org.junit.rules.TestName;

@Ignore
public class MessagingTest {
	
	@Rule
    public TestName testName = new TestName();
	
	private static String keyStore;
	private static String keyStoreType;
	private static String keyStorePassword;
	private static String trustStore;
	private static String trustStoreType;
	private static String trustStorePassword;
	
	private static URI listenerUrl;
	private static String token;
	private static String tokenId;
	private Client client;
	private Queue<Object> messages;
    
    @BeforeClass
    public static void beforeClass() throws Exception{
    	
    	keyStore = System.getProperty("javax.net.ssl.keyStore");
    	keyStoreType = System.getProperty("javax.net.ssl.keyStoreType");
    	keyStorePassword = System.getProperty("javax.net.ssl.keyStorePassword");
    	trustStore = System.getProperty("javax.net.ssl.trustStore");
    	trustStoreType = System.getProperty("javax.net.ssl.trustStoreType");
    	trustStorePassword = System.getProperty("javax.net.ssl.trustStorePassword");
    	
    	System.setProperty("javax.net.ssl.keyStore", SystemUtil.getString(Naming.Client.SSL_KEY_STORE));
    	System.setProperty("javax.net.ssl.keyStoreType", SystemUtil.getString(Naming.Client.SSL_KEY_STORE_TYPE));
    	System.setProperty("javax.net.ssl.keyStorePassword", SystemUtil.getString(Naming.Client.SSL_KEY_STORE_PASSWORD));
    	System.setProperty("javax.net.ssl.trustStore", SystemUtil.getString(Naming.Client.SSL_TRUST_STORE));
    	System.setProperty("javax.net.ssl.trustStoreType", SystemUtil.getString(Naming.Client.SSL_TRUST_STORE_TYPE));
    	System.setProperty("javax.net.ssl.trustStorePassword", SystemUtil.getString(Naming.Client.SSL_TRUST_STORE_PASSWORD));
    	
    	URI messagingUrl = ConfigUtil.getURI(Naming.Gateway.MESSAGING_URL);
    	HealthUtil.isReady();
    	token = SecurityUtil.login();
    	tokenId = SecurityUtil.auth(token).getTokenID();
    	listenerUrl = new URI(messagingUrl.toString() + "persistence?tid=" + tokenId);
    }
    
    @AfterClass
    public static void afterClass() throws Exception{
    	SecurityUtil.logOut(token);
    	
    	System.setProperty("javax.net.ssl.keyStore", keyStore);
    	System.setProperty("javax.net.ssl.keyStoreType", keyStoreType);
    	System.setProperty("javax.net.ssl.keyStorePassword", keyStorePassword);
    	System.setProperty("javax.net.ssl.trustStore", trustStore);
    	System.setProperty("javax.net.ssl.trustStoreType", trustStoreType);
    	System.setProperty("javax.net.ssl.trustStorePassword", trustStorePassword);
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
        PersistenceUtil.persist(token, Artifact.class, WorkProducts.SCHEMA, WorkProducts.ARTIFACT, artifact);
        PersistenceUtil.remove(token, WorkProducts.SCHEMA, WorkProducts.ARTIFACT, artifact.getName());
    	
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
    	URI messagingUrl = ConfigUtil.getURI(Naming.Gateway.MESSAGING_URL);
    	URI url = new URI(messagingUrl.toString() + "persistence");
    	try(Client invalidClient = Messaging.connectToServer(url)){
        	TestUtil.waitUntil(t -> !invalidClient.getSession().isOpen(), Duration.ofSeconds(10));
        	Assert.assertFalse("Client.session.open", invalidClient.getSession().isOpen());
    	}
    }
}
