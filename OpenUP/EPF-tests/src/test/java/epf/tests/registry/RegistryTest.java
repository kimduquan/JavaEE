package epf.tests.registry;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;
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
import epf.tests.health.HealthUtil;
import epf.util.config.ConfigUtil;

public class RegistryTest {
	
	@Rule
    public TestName testName = new TestName();
	
	private static URI registryUrl;
	
	private Client client;
    
    @BeforeClass
    public static void beforeClass() throws Exception{
    	HealthUtil.isReady();
    	try {
			registryUrl = ConfigUtil.getURI(Naming.Registry.REGISTRY_URL);
		} 
    	catch (Exception e) {
			e.printStackTrace();
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
    		e.printStackTrace();
		}
    }
    
    @Test
    public void testList_OK() throws Exception {
    	Set<Link> links = Registry.list(client, null);
    	Set<URI> URIs = links.stream().map(link -> link.getUri()).collect(Collectors.toSet());
    	Set<URI> expected = new HashSet<>();
    	URI baseUri = new URI("http://localhost:9080/");
    	try {
			expected.add(baseUri.resolve("config/config/"));
	    	expected.add(baseUri.resolve("file/file/"));
	    	expected.add(new URI("http://localhost:9181/persistence/persistence/"));
	    	expected.add(baseUri.resolve("registry/registry/"));
	    	expected.add(baseUri.resolve("security/security/"));
	    	expected.add(new URI("http://localhost:9182/transaction/transaction/"));
	    	expected.add(baseUri.resolve("cache/cache/"));
	    	expected.add(baseUri.resolve("query/query/"));
	    	expected.add(baseUri.resolve("query/search/"));
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
    		e.printStackTrace();
		}
    	Assert.assertEquals("list", expected, URIs);
    }
}
