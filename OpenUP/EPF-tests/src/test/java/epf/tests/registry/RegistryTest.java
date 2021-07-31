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
import org.junit.Test;
import epf.client.registry.Registry;
import epf.tests.client.ClientUtil;
import epf.tests.gateway.GatewayUtil;
import epf.util.client.Client;
import epf.util.logging.Logging;

public class RegistryTest {

	private static final Logger logger = Logging.getLogger(RegistryTest.class.getName());
	private static URI registryUrl;
	
	private Client client;
    
    @BeforeClass
    public static void beforeClass(){
    	try {
			registryUrl = GatewayUtil.getGatewayUrl().resolve("registry");
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
    public void testList_OK() {
    	Set<Link> links = Registry.list(client, null);
    	Set<URI> URIs = links.stream().map(link -> link.getUri()).collect(Collectors.toSet());
    	Set<URI> expected = new HashSet<>();
    	try {
			expected.add(GatewayUtil.getGatewayUrl().resolve("config"));
	    	expected.add(GatewayUtil.getGatewayUrl().resolve("file"));
	    	expected.add(GatewayUtil.getGatewayUrl().resolve("persistence"));
	    	expected.add(GatewayUtil.getGatewayUrl().resolve("registry"));
	    	expected.add(GatewayUtil.getGatewayUrl().resolve("security"));
	    	expected.add(GatewayUtil.getGatewayUrl().resolve("stream"));
	    	expected.add(GatewayUtil.getGatewayUrl().resolve("cache"));
	    	expected.add(GatewayUtil.getGatewayUrl().resolve("script"));
	    	expected.add(GatewayUtil.getGatewayUrl().resolve("management"));
	    	expected.add(GatewayUtil.getGatewayUrl().resolve("rules"));
	    	expected.add(GatewayUtil.getGatewayUrl().resolve("schema"));
	    	expected.add(GatewayUtil.getGatewayUrl().resolve("planning"));
	    	expected.add(GatewayUtil.getGatewayUrl().resolve("image"));
	    	URI messagingUrl = UriBuilder.fromUri(GatewayUtil.getGatewayUrl().resolve("messaging")).scheme("ws").port(9080).build();
	    	expected.add(messagingUrl);
	    	URI langUrl = UriBuilder.fromUri(GatewayUtil.getGatewayUrl().resolve("lang")).scheme("ws").port(9080).build();
	    	expected.add(langUrl);
	    	expected.add(GatewayUtil.getGatewayUrl().resolve("delivery-processes"));
	    	expected.add(GatewayUtil.getGatewayUrl().resolve("roles"));
	    	expected.add(GatewayUtil.getGatewayUrl().resolve("tasks"));
	    	expected.add(GatewayUtil.getGatewayUrl().resolve("work-products"));
		} 
    	catch (URISyntaxException e) {
			logger.log(Level.SEVERE, "testList_OK", e);
		}
    	Assert.assertEquals("list", expected, URIs);
    }
}
