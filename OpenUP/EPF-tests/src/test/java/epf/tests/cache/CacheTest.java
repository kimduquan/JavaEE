/**
 * 
 */
package epf.tests.cache;

import java.net.URI;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.sse.SseEventSource;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import epf.client.cache.Cache;
import epf.schema.EPF;
import epf.schema.work_products.Artifact;
import epf.schema.work_products.section.Description;
import epf.schema.work_products.section.Illustrations;
import epf.schema.work_products.section.MoreInformation;
import epf.schema.work_products.section.Relationships;
import epf.schema.work_products.section.Tailoring;
import epf.tests.TestUtil;
import epf.tests.client.ClientUtil;
import epf.tests.persistence.PersistenceUtil;
import epf.tests.security.SecurityUtil;
import epf.util.StringUtil;
import epf.util.client.Client;
import epf.client.gateway.GatewayUtil;

/**
 * @author PC
 *
 */
public class CacheTest {
	
	static URI cacheUrl;
	static String token;
	Client client;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		cacheUrl = GatewayUtil.get("cache");
		token = SecurityUtil.login("any_role1", "any_role");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		SecurityUtil.logOut(token);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		client = ClientUtil.newClient(cacheUrl);
		client.authorization(token);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		client.close();
	}

	@Test
	public void testGetEntityOk() throws Exception {
		final Artifact artifact = new Artifact();
        artifact.setName(StringUtil.randomString("Artifact Cache"));
        artifact.setSummary("Artifact Cache testGetEntityOk");
        artifact.setDescription(new Description());
        artifact.setIllustrations(new Illustrations());
        artifact.setMoreInformation(new MoreInformation());
        artifact.setRelationships(new Relationships());
        artifact.setTailoring(new Tailoring());
        PersistenceUtil.persist(token, Artifact.class, EPF.ARTIFACT, artifact);
        TestUtil.waitUntil(
        		(t) -> {
				        	Artifact art = null;
				        	try {
				        		art = Cache.getEntity(client, Artifact.class, EPF.ARTIFACT, artifact.getName());
				        	}
				        	catch(Exception ex) {
				        		ex.printStackTrace();
				        	}
				        	return art != null;
				        }, 
        		Duration.ofSeconds(10)
        		);
        Artifact cachedArtifact = Cache.getEntity(client, Artifact.class, EPF.ARTIFACT, artifact.getName());
        Assert.assertNotNull("Artifact", cachedArtifact);
        Assert.assertEquals("Artifact.name", artifact.getName(), cachedArtifact.getName());
        Assert.assertEquals("Artifact.summary", artifact.getSummary(), cachedArtifact.getSummary());
        PersistenceUtil.remove(token, EPF.ARTIFACT, artifact.getName());
	}
	
	@Test(expected = NotFoundException.class)
	public void testGetEntity_NotFound() throws Exception {
		Thread.sleep(20);
        Cache.getEntity(client, Artifact.class, EPF.ARTIFACT, StringUtil.randomString("Artifact Cache"));
	}

	@Test
	public void testForEachEntityOk() throws Exception {
		final Artifact artifact = new Artifact();
        artifact.setName(StringUtil.randomString("Artifact Cache"));
        artifact.setSummary("Artifact Cache testForEachEntityOk");
        artifact.setDescription(new Description());
        artifact.setIllustrations(new Illustrations());
        artifact.setMoreInformation(new MoreInformation());
        artifact.setRelationships(new Relationships());
        artifact.setTailoring(new Tailoring());
        PersistenceUtil.persist(token, Artifact.class, EPF.ARTIFACT, artifact);
        final List<String> data = new ArrayList<>();
        try(SseEventSource stream = Cache.forEachEntity(client, EPF.ARTIFACT)){
        	stream.register(e -> {
        		data.add(e.getId());
        	});
        	stream.open();
        }
        Assert.assertEquals(1, data.size());
        Assert.assertTrue(data.contains(artifact.getName()));
        PersistenceUtil.remove(token, EPF.ARTIFACT, artifact.getName());
	}
}
