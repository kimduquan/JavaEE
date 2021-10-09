/**
 * 
 */
package epf.tests.schema;

import java.net.URI;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import epf.client.schema.Entity;
import epf.client.schema.Schema;
import epf.client.util.Client;
import epf.tests.client.ClientUtil;
import epf.tests.persistence.EntitiesTest;
import epf.tests.security.SecurityUtil;
import epf.util.logging.Logging;
import epf.client.gateway.GatewayUtil;

/**
 * @author PC
 *
 */
public class SchemaTest {
	
	private static final Logger logger = Logging.getLogger(EntitiesTest.class.getName());
	private static URI schemaUrl;
    private static String token;
    private Client client;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		schemaUrl = GatewayUtil.get("schema");
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
		client = ClientUtil.newClient(schemaUrl);
    	client.authorization(token);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		try {
			client.close();
		} 
    	catch (Exception e) {
    		logger.log(Level.WARNING, "after", e);
		}
	}
	
    @Test
    public void testGetEntitiesOK() throws Exception{
    	Response res = Schema.getEntities(client);
    	List<Entity> entities = res.readEntity(new GenericType<List<Entity>>() {});
    	Assert.assertFalse("List<EntityType>.empty", entities.isEmpty());
    }
}
