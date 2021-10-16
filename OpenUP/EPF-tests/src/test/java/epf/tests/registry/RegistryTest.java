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
import epf.client.gateway.GatewayUtil;
import epf.client.registry.Registry;
import epf.client.util.Client;
import epf.naming.Naming;
import epf.tests.client.ClientUtil;
import epf.util.logging.LogManager;

public class RegistryTest {

	private static final Logger logger = LogManager.getLogger(RegistryTest.class.getName());
	private static URI registryUrl;
	
	private Client client;
    
    @BeforeClass
    public static void beforeClass() throws Exception{
    	try {
			registryUrl = GatewayUtil.get(Naming.REGISTRY);
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
			expected.add(GatewayUtil.get("config"));
	    	expected.add(GatewayUtil.get("file"));
	    	expected.add(GatewayUtil.get("persistence"));
	    	expected.add(GatewayUtil.get("registry"));
	    	expected.add(GatewayUtil.get("security"));
	    	expected.add(GatewayUtil.get("stream"));
	    	expected.add(GatewayUtil.get("cache"));
	    	expected.add(GatewayUtil.get("script"));
	    	expected.add(GatewayUtil.get("management"));
	    	expected.add(GatewayUtil.get("rules"));
	    	expected.add(GatewayUtil.get("schema"));
	    	expected.add(GatewayUtil.get("planning"));
	    	expected.add(GatewayUtil.get("image"));
	    	expected.add(GatewayUtil.get("net"));
	    	URI messagingUrl = UriBuilder.fromUri(GatewayUtil.get("messaging")).scheme("ws").port(9080).build();
	    	expected.add(messagingUrl);
	    	URI langUrl = UriBuilder.fromUri(GatewayUtil.get("lang")).scheme("ws").port(9080).build();
	    	expected.add(langUrl);
	    	expected.add(GatewayUtil.get("delivery-processes"));
	    	expected.add(GatewayUtil.get("roles"));
	    	expected.add(GatewayUtil.get("tasks"));
	    	expected.add(GatewayUtil.get("work-products"));
		} 
    	catch (Exception e) {
			logger.log(Level.SEVERE, "testList_OK", e);
		}
    	Assert.assertEquals("list", expected, URIs);
    }
}
