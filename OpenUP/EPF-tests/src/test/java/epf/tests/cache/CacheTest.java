/**
 * 
 */
package epf.tests.cache;

import java.net.URI;
import java.time.Instant;
import javax.ws.rs.NotFoundException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import epf.client.cache.Cache;
import epf.client.persistence.Entities;
import epf.schema.EPF;
import epf.schema.work_products.Artifact;
import epf.schema.work_products.section.Description;
import epf.schema.work_products.section.Illustrations;
import epf.schema.work_products.section.MoreInformation;
import epf.schema.work_products.section.Relationships;
import epf.schema.work_products.section.Tailoring;
import epf.tests.client.ClientUtil;
import epf.tests.registry.RegistryUtil;
import epf.tests.security.SecurityUtil;
import epf.util.client.Client;

/**
 * @author PC
 *
 */
public class CacheTest {
	
	static URI cacheUrl;
	static URI persistenceUrl;
	static Client persistenceClient;
	static String token;
	Client client;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		cacheUrl = RegistryUtil.lookup("cache", null);
		persistenceUrl = RegistryUtil.lookup("persistence", null);
		persistenceClient = ClientUtil.newClient(persistenceUrl);
		token = SecurityUtil.login(null, "any_role1", "any_role");
		persistenceClient.authorization(token);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		persistenceClient.close();
		SecurityUtil.logOut(null, token);
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
		Artifact artifact = new Artifact();
        artifact.setName("Artifact Cache" + String.valueOf(Instant.now().toEpochMilli()));
        artifact.setSummary("Artifact Cache Summary");
        artifact.setDescription(new Description());
        artifact.setIllustrations(new Illustrations());
        artifact.setMoreInformation(new MoreInformation());
        artifact.setRelationships(new Relationships());
        artifact.setTailoring(new Tailoring());
        Entities.persist(persistenceClient, Artifact.class, EPF.SCHEMA, EPF.ARTIFACT, artifact);
        Artifact cachedArtifact = Cache.getEntity(client, Artifact.class, EPF.SCHEMA, EPF.ARTIFACT, artifact.getName());
        Assert.assertNotNull("Artifact", cachedArtifact);
        Assert.assertEquals("Artifact.name", artifact.getName(), cachedArtifact.getName());
        Assert.assertEquals("Artifact.summary", artifact.getSummary(), cachedArtifact.getSummary());
	}
	
	@Test(expected = NotFoundException.class)
	public void testGetEntity_NotFound() throws Exception {
		Thread.sleep(20);
        Cache.getEntity(client, Artifact.class, EPF.SCHEMA, EPF.ARTIFACT, "Artifact Cache" + String.valueOf(Instant.now().toEpochMilli()));
	}

}
