package epf.tests.registry;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.UriBuilder;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import epf.client.registry.Registry;
import epf.client.util.Client;
import epf.naming.Naming;
import epf.tests.client.ClientUtil;
import epf.util.config.ConfigUtil;
import epf.util.logging.LogManager;

public class RegistryTest {
	
	@Rule
    public TestName testName = new TestName();

	private static final Logger logger = LogManager.getLogger(RegistryTest.class.getName());
	private static URI registryUrl;
	
	private Client client;
    
    @BeforeClass
    public static void beforeClass() throws Exception{
    	try {
			registryUrl = ConfigUtil.getURI(Naming.Registry.REGISTRY_URL);
		} 
    	catch (URISyntaxException e) {
			logger.log(Level.SEVERE, "beforeClass", e);
		}
    }
    
    @AfterClass
    public static void afterClass(){
    }
    
    @Before
    public void before(){
    	client = ClientUtil.newClient(registryUrl);
    }
    
    @After
    public void after(){
    	try {
			client.close();
		} 
    	catch (Exception e) {
			logger.log(Level.WARNING, "after", e);
		}
    }
    
    @Test
    public void testList_OK() throws Exception {
    	Set<Link> links = Registry.list(client, null);
    	Set<URI> URIs = links.stream().map(link -> link.getUri()).collect(Collectors.toSet());
    	Set<URI> expected = new HashSet<>();
    	URI baseUri = new URI("https://localhost:9443/");
    	try {
			expected.add(baseUri.resolve("config/config/"));
	    	expected.add(baseUri.resolve("file/file/"));
	    	expected.add(new URI("http://localhost:9181/persistence/persistence/"));
	    	expected.add(baseUri.resolve("registry/registry/"));
	    	expected.add(baseUri.resolve("security/security/"));
	    	expected.add(baseUri.resolve("cache/cache/"));
	    	expected.add(baseUri.resolve("script/script/"));
	    	expected.add(baseUri.resolve("management/management/"));
	    	expected.add(baseUri.resolve("rules/rules/"));
	    	expected.add(new URI("http://localhost:9181/persistence/schema/"));
	    	expected.add(baseUri.resolve("planning/planning/"));
	    	expected.add(baseUri.resolve("image/image/"));
	    	expected.add(baseUri.resolve("net/net/"));
	    	URI messagingUrl = UriBuilder.fromUri(baseUri.resolve("messaging/messaging/")).scheme("ws").port(9080).build();
	    	expected.add(messagingUrl);
	    	URI langUrl = UriBuilder.fromUri(baseUri.resolve("lang/lang/")).scheme("ws").port(9080).build();
	    	expected.add(langUrl);
		} 
    	catch (Exception e) {
			logger.log(Level.SEVERE, "testList_OK", e);
		}
    	Assert.assertEquals("list", expected, URIs);
    }
}
