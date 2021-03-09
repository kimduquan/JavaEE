package epf.service.registry;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;
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
			logger.severe(e.getMessage());
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
    public void after() throws Exception{
    	client.close();
    }
    
    @Test
    public void testList_OK() throws Exception {
    	Set<Link> links = Registry.list(client).getLinks();
    	Set<URI> URIs = links.stream().map(link -> link.getUri()).collect(Collectors.toSet());
    	Set<URI> expected = new HashSet<>();
    	URI base = UriBuilder
    			.fromUri(registryUrl)
    			.replacePath(
    					registryUrl
    					.getPath()
    					.split("/")[2]
    							)
    			.build();
    	expected.add(base.resolve("/config"));
    	expected.add(base.resolve("/file"));
    	expected.add(base.resolve("/persistence"));
    	expected.add(base.resolve("/registry"));
    	expected.add(base.resolve("/schema"));
    	expected.add(base.resolve("/security"));
    	expected.add(base.resolve("/system"));
    	Assert.assertEquals("list", expected, URIs);
    }
}
