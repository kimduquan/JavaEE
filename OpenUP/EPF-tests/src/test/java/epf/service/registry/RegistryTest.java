package epf.service.registry;

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
import epf.client.config.ConfigNames;
import epf.client.registry.Registry;
import epf.service.ClientUtil;
import epf.service.MessagingUtil;
import epf.util.client.Client;
import epf.util.logging.Logging;

public class RegistryTest {

	private static final Logger logger = Logging.getLogger(RegistryTest.class.getName());
	private static URI registryUrl;
	
	private Client client;
    
    @BeforeClass
    public static void beforeClass(){
    	try {
			registryUrl = new URI(System.getProperty(ConfigNames.REGISTRY_URL, ""));
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
    	String path = registryUrl.getPath().split("/")[1];
    	URI base = UriBuilder.fromUri(registryUrl).replacePath(path).build();
    	try {
			expected.add(new URI(base.toString() + "/config"));
	    	expected.add(new URI(base.toString() + "/file"));
	    	expected.add(new URI(base.toString() + "/persistence"));
	    	expected.add(new URI(base.toString() + "/registry"));
	    	expected.add(new URI(base.toString() + "/schema"));
	    	expected.add(new URI(base.toString() + "/security"));
	    	expected.add(new URI(base.toString() + "/system"));
	    	expected.add(new URI(base.toString() + "/stream"));
	    	String messagingPath = MessagingUtil.getMessagingUrl().getPath();
	    	messagingPath = (path + "/" + messagingPath.split("/")[2]);
	    	URI messagingUrl = UriBuilder.fromUri(MessagingUtil.getMessagingUrl()).replacePath(messagingPath).build();
	    	expected.add(messagingUrl);
		} 
    	catch (URISyntaxException e) {
			logger.log(Level.SEVERE, "testList_OK", e);
		}
    	Assert.assertEquals("list", expected, URIs);
    }
}
