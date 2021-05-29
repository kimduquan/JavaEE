/**
 * 
 */
package epf.tests.persistence;

import java.net.URI;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import epf.client.persistence.EntityType;
import epf.client.persistence.EntityTypes;
import epf.schema.EPF;
import epf.tests.client.ClientUtil;
import epf.tests.registry.RegistryUtil;
import epf.tests.security.SecurityUtil;
import epf.util.client.Client;
import epf.util.logging.Logging;

/**
 * @author PC
 *
 */
public class EntityTypesTest {

	private static final Logger logger = Logging.getLogger(EntityTypesTest.class.getName());
	private static URI persistenceUrl;
    private static String token;
    private static String adminToken;
    private Client client;
    
    @BeforeClass
    public static void beforeClass(){
    	persistenceUrl = RegistryUtil.lookup("persistence", null);
    	token = SecurityUtil.login(null, "any_role1", "any_role");
    	adminToken = SecurityUtil.login(null, "admin1", "admin");
    }
    
    @AfterClass
    public static void afterClass(){
    	SecurityUtil.logOut(null, token);
    	SecurityUtil.logOut(null, adminToken);
    }
    
    @Before
    public void before() {
    	client = ClientUtil.newClient(persistenceUrl);
    	client.authorization(token);
    }
    
    @After
    public void after() {
    	try {
			client.close();
		} 
    	catch (Exception e) {
    		logger.log(Level.WARNING, "after", e);
		}
    }

	@Test
	public void testGetEntityTypesOk() {
		List<EntityType> types = EntityTypes.getEntityTypes(client, EPF.SCHEMA);
		types.forEach(type -> {
			System.out.println(type.getName());
		});
	}
	
	@Test
	public void testGetEntityTypeOk() {
		EntityType type = EntityTypes.getEntityType(client, EPF.SCHEMA, EPF.ARTIFACT);
		Assert.assertNotNull("EntityType", type);
		Assert.assertEquals("EntityType.name", EPF.ARTIFACT, type.getName());
	}
}
