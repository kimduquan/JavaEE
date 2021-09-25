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
import org.junit.Ignore;
import org.junit.Test;
import epf.client.cache.Cache;
import epf.tests.TestUtil;
import epf.tests.client.ClientUtil;
import epf.tests.health.HealthUtil;
import epf.tests.persistence.PersistenceUtil;
import epf.tests.security.SecurityUtil;
import epf.util.StringUtil;
import epf.util.client.Client;
import epf.work_products.schema.Artifact;
import epf.work_products.schema.WorkProducts;
import epf.work_products.schema.section.Description;
import epf.work_products.schema.section.Illustrations;
import epf.work_products.schema.section.MoreInformation;
import epf.work_products.schema.section.Relationships;
import epf.work_products.schema.section.Tailoring;
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
		HealthUtil.readỵ̣();
		cacheUrl = GatewayUtil.get("cache");
		token = SecurityUtil.login();
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
        PersistenceUtil.persist(token, Artifact.class, WorkProducts.ARTIFACT, artifact);
        TestUtil.waitUntil(
        		(t) -> {
				        	Artifact art = null;
				        	try {
				        		art = Cache.getEntity(client, Artifact.class, WorkProducts.ARTIFACT, artifact.getName());
				        	}
				        	catch(Exception ex) {
				        		ex.printStackTrace();
				        	}
				        	return art != null;
				        }, 
        		Duration.ofSeconds(10)
        		);
        Artifact cachedArtifact = Cache.getEntity(client, Artifact.class, WorkProducts.ARTIFACT, artifact.getName());
        Assert.assertNotNull("Artifact", cachedArtifact);
        Assert.assertEquals("Artifact.name", artifact.getName(), cachedArtifact.getName());
        Assert.assertEquals("Artifact.summary", artifact.getSummary(), cachedArtifact.getSummary());
        PersistenceUtil.remove(token, WorkProducts.ARTIFACT, artifact.getName());
	}
	
	@Test(expected = NotFoundException.class)
	public void testGetEntity_AfterRemoveEntity_NotFound() throws Exception {
		final Artifact artifact = new Artifact();
        artifact.setName(StringUtil.randomString("Artifact Cache"));
        artifact.setSummary("Artifact Cache testGetEntity_AfterRemoveEntity_NotFound");
        artifact.setDescription(new Description());
        artifact.setIllustrations(new Illustrations());
        artifact.setMoreInformation(new MoreInformation());
        artifact.setRelationships(new Relationships());
        artifact.setTailoring(new Tailoring());
        PersistenceUtil.persist(token, Artifact.class, WorkProducts.ARTIFACT, artifact);
        TestUtil.waitUntil(
        		(t) -> {
				        	Artifact art = null;
				        	try {
				        		art = Cache.getEntity(client, Artifact.class, WorkProducts.ARTIFACT, artifact.getName());
				        	}
				        	catch(Exception ex) {
				        		ex.printStackTrace();
				        	}
				        	return art != null;
				        }, 
        		Duration.ofSeconds(10)
        		);
        PersistenceUtil.remove(token, WorkProducts.ARTIFACT, artifact.getName());
        Thread.sleep(80);
        Cache.getEntity(client, Artifact.class, WorkProducts.ARTIFACT, artifact.getName());
	}
	
	@Test
	public void testGetEntity_AfterUpdateEntity_TheEntityFieldUpdated() throws Exception {
		final Artifact artifact = new Artifact();
        artifact.setName(StringUtil.randomString("Artifact Cache"));
        artifact.setSummary("Artifact Cache testGetEntity_AfterUpdateEntity_TheEntityFieldUpdated");
        artifact.setDescription(new Description());
        artifact.setIllustrations(new Illustrations());
        artifact.setMoreInformation(new MoreInformation());
        artifact.setRelationships(new Relationships());
        artifact.setTailoring(new Tailoring());
        PersistenceUtil.persist(token, Artifact.class, WorkProducts.ARTIFACT, artifact);
        TestUtil.waitUntil(
        		(t) -> {
				        	Artifact art = null;
				        	try {
				        		art = Cache.getEntity(client, Artifact.class, WorkProducts.ARTIFACT, artifact.getName());
				        	}
				        	catch(Exception ex) {
				        		ex.printStackTrace();
				        	}
				        	return art != null;
				        }, 
        		Duration.ofSeconds(10)
        		);
        artifact.setSummary(StringUtil.randomString("Artifact Cache Summary"));
        PersistenceUtil.merge(token, WorkProducts.ARTIFACT, artifact.getName(), artifact);
        Thread.sleep(80);
        Artifact actualArtifact = Cache.getEntity(client, Artifact.class, WorkProducts.ARTIFACT, artifact.getName());
        Assert.assertEquals("Artifact.summary", artifact.getSummary(), actualArtifact.getSummary());
        PersistenceUtil.remove(token, WorkProducts.ARTIFACT, artifact.getName());
	}
	
	@Test(expected = NotFoundException.class)
	public void testGetEntity_NotFound() throws Exception {
		Thread.sleep(20);
        Cache.getEntity(client, Artifact.class, WorkProducts.ARTIFACT, StringUtil.randomString("Artifact Cache"));
	}

	@Test
	@Ignore
	public void testForEachEntityOk() throws Exception {
		final Artifact artifact = new Artifact();
        artifact.setName(StringUtil.randomString("Artifact Cache"));
        artifact.setSummary("Artifact Cache testForEachEntityOk");
        artifact.setDescription(new Description());
        artifact.setIllustrations(new Illustrations());
        artifact.setMoreInformation(new MoreInformation());
        artifact.setRelationships(new Relationships());
        artifact.setTailoring(new Tailoring());
        PersistenceUtil.persist(token, Artifact.class, WorkProducts.ARTIFACT, artifact);
        final List<String> data = new ArrayList<>();
        try(SseEventSource stream = Cache.forEachEntity(client, WorkProducts.ARTIFACT)){
        	stream.register(
        			e -> {
        				data.add(e.getId());
        				}
        			,
        			error -> {
        				error.printStackTrace();
        			});
        	stream.open();
        }
        Assert.assertEquals(1, data.size());
        Assert.assertTrue(data.contains(artifact.getName()));
        PersistenceUtil.remove(token, WorkProducts.ARTIFACT, artifact.getName());
	}
}
