package epf.tests.query;

import java.net.URI;
import java.time.Duration;
import javax.ws.rs.NotFoundException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import epf.tests.TestUtil;
import epf.tests.client.ClientUtil;
import epf.tests.health.HealthUtil;
import epf.tests.persistence.PersistenceUtil;
import epf.tests.security.SecurityUtil;
import epf.util.StringUtil;
import epf.work_products.schema.Artifact;
import epf.work_products.schema.WorkProducts;
import epf.work_products.schema.section.Description;
import epf.work_products.schema.section.Illustrations;
import epf.work_products.schema.section.MoreInformation;
import epf.work_products.schema.section.Relationships;
import epf.work_products.schema.section.Tailoring;
import epf.client.gateway.GatewayUtil;
import epf.client.query.Query;
import epf.client.util.Client;
import epf.naming.Naming;

/**
 * @author PC
 *
 */
public class QueryTest {
	
	@Rule
    public TestName testName = new TestName();
	
	static URI queryUrl;
	static String token;
	Client client;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		HealthUtil.isReady();
		queryUrl = GatewayUtil.get(Naming.QUERY);
		token = SecurityUtil.login();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		SecurityUtil.logOut(token);
		ClientUtil.afterClass();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		client = ClientUtil.newClient(queryUrl);
		client.authorization(token.toCharArray());
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
        artifact.setName(StringUtil.randomString("Artifact Query"));
        artifact.setSummary("Artifact Query testGetEntityOk");
        artifact.setDescription(new Description());
        artifact.setIllustrations(new Illustrations());
        artifact.setMoreInformation(new MoreInformation());
        artifact.setRelationships(new Relationships());
        artifact.setTailoring(new Tailoring());
        PersistenceUtil.persist(token, Artifact.class, WorkProducts.SCHEMA, WorkProducts.ARTIFACT, artifact);
        TestUtil.waitUntil(
        		(t) -> {
				        	Artifact art = null;
				        	try {
				        		art = Query.getEntity(client, Artifact.class, WorkProducts.SCHEMA, WorkProducts.ARTIFACT, artifact.getName());
				        	}
				        	catch(Exception ex) {
				        		//ex.printStackTrace();
				        	}
				        	return art != null;
				        }, 
        		Duration.ofSeconds(10)
        		);
        Artifact cachedArtifact = Query.getEntity(client, Artifact.class, WorkProducts.SCHEMA, WorkProducts.ARTIFACT, artifact.getName());
        Assert.assertNotNull("Artifact", cachedArtifact);
        Assert.assertEquals("Artifact.name", artifact.getName(), cachedArtifact.getName());
        Assert.assertEquals("Artifact.summary", artifact.getSummary(), cachedArtifact.getSummary());
        PersistenceUtil.remove(token, WorkProducts.SCHEMA, WorkProducts.ARTIFACT, artifact.getName());
	}
	
	@Test(expected = NotFoundException.class)
	public void testGetEntity_AfterRemoveEntity_NotFound() throws Exception {
		final Artifact artifact = new Artifact();
        artifact.setName(StringUtil.randomString("Artifact Query"));
        artifact.setSummary("Artifact Query testGetEntity_AfterRemoveEntity_NotFound");
        artifact.setDescription(new Description());
        artifact.setIllustrations(new Illustrations());
        artifact.setMoreInformation(new MoreInformation());
        artifact.setRelationships(new Relationships());
        artifact.setTailoring(new Tailoring());
        PersistenceUtil.persist(token, Artifact.class, WorkProducts.SCHEMA, WorkProducts.ARTIFACT, artifact);
        TestUtil.waitUntil(
        		(t) -> {
				        	Artifact art = null;
				        	try {
				        		art = Query.getEntity(client, Artifact.class, WorkProducts.SCHEMA, WorkProducts.ARTIFACT, artifact.getName());
				        	}
				        	catch(Exception ex) {
				        		//ex.printStackTrace();
				        	}
				        	return art != null;
				        }, 
        		Duration.ofSeconds(10)
        		);
        PersistenceUtil.remove(token, WorkProducts.SCHEMA, WorkProducts.ARTIFACT, artifact.getName());
        TestUtil.waitUntil(
        		(t) -> {
				        	Artifact art = null;
				        	try {
				        		art = Query.getEntity(client, Artifact.class, WorkProducts.SCHEMA, WorkProducts.ARTIFACT, artifact.getName());
				        	}
				        	catch(Exception ex) {
				        		//ex.printStackTrace();
				        	}
				        	return art == null;
				        }, 
        		Duration.ofSeconds(10)
        		);
        Query.getEntity(client, Artifact.class, WorkProducts.SCHEMA, WorkProducts.ARTIFACT, artifact.getName());
	}
	
	@Test
	public void testGetEntity_AfterUpdateEntity_TheEntityFieldUpdated() throws Exception {
		final Artifact artifact = new Artifact();
        artifact.setName(StringUtil.randomString("Artifact Query"));
        artifact.setSummary("Artifact Query testGetEntity_AfterUpdateEntity_TheEntityFieldUpdated");
        artifact.setDescription(new Description());
        artifact.setIllustrations(new Illustrations());
        artifact.setMoreInformation(new MoreInformation());
        artifact.setRelationships(new Relationships());
        artifact.setTailoring(new Tailoring());
        PersistenceUtil.persist(token, Artifact.class, WorkProducts.SCHEMA, WorkProducts.ARTIFACT, artifact);
        TestUtil.waitUntil(
        		(t) -> {
				        	Artifact art = null;
				        	try {
				        		art = Query.getEntity(client, Artifact.class, WorkProducts.SCHEMA, WorkProducts.ARTIFACT, artifact.getName());
				        	}
				        	catch(Exception ex) {
				        		//ex.printStackTrace();
				        	}
				        	return art != null;
				        }, 
        		Duration.ofSeconds(10)
        		);
        artifact.setSummary(StringUtil.randomString("Artifact Query Summary"));
        PersistenceUtil.merge(token, WorkProducts.SCHEMA, WorkProducts.ARTIFACT, artifact.getName(), artifact);
        TestUtil.doWhile(() -> Query.getEntity(client, Artifact.class, WorkProducts.SCHEMA, WorkProducts.ARTIFACT, artifact.getName()), art -> !artifact.getSummary().equals(art.getSummary()), Duration.ofSeconds(10));
        Artifact actualArtifact = Query.getEntity(client, Artifact.class, WorkProducts.SCHEMA, WorkProducts.ARTIFACT, artifact.getName());
        Assert.assertEquals("Artifact.summary", artifact.getSummary(), actualArtifact.getSummary());
        PersistenceUtil.remove(token, WorkProducts.SCHEMA, WorkProducts.ARTIFACT, artifact.getName());
	}
	
	@Test(expected = NotFoundException.class)
	public void testGetEntity_NotFound() throws Exception {
		Thread.sleep(20);
		Query.getEntity(client, Artifact.class, WorkProducts.SCHEMA, WorkProducts.ARTIFACT, StringUtil.randomString("Artifact Query"));
	}
}
