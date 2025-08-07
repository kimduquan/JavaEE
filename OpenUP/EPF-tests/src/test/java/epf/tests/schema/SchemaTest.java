package epf.tests.schema;

import java.net.URI;
import java.util.List;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import epf.client.util.Client;
import epf.naming.Naming;
import epf.persistence.schema.Entity;
import epf.persistence.client.Schema;
import epf.tests.client.ClientUtil;
import epf.tests.security.SecurityUtil;
import epf.client.gateway.GatewayUtil;

public class SchemaTest {
	
	@Rule
    public TestName testName = new TestName();
	
	private static URI schemaUrl;
    private static String token;
    private Client client;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		schemaUrl = GatewayUtil.get(Naming.SCHEMA);
    	token = SecurityUtil.login();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		SecurityUtil.logOut(token);
		ClientUtil.afterClass();
	}

	@Before
	public void setUp() throws Exception {
		client = ClientUtil.newClient(schemaUrl);
    	client.authorization(token.toCharArray());
	}

	@After
	public void tearDown() throws Exception {
		client.close();
	}
	
    @Test
    public void testGetEntitiesOK() throws Exception{
    	Response res = Schema.getEntities(client);
    	List<Entity> entities = res.readEntity(new GenericType<List<Entity>>() {});
    	Assert.assertFalse("List<EntityType>.empty", entities.isEmpty());
    }
}
