package epf.tests.net;

import java.net.URI;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import epf.client.gateway.GatewayUtil;
import epf.tests.client.ClientUtil;
import epf.tests.security.SecurityUtil;
import epf.util.client.Client;

public class NetTest {
	
	private static String token;
	private static URI netUrl;
	
	private Client client;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		token = SecurityUtil.login();
		netUrl = GatewayUtil.get("net");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		SecurityUtil.logOut(token);
	}

	@Before
	public void setUp() throws Exception {
		client = ClientUtil.newClient(netUrl);
    	client.authorization(token);
	}

	@After
	public void tearDown() throws Exception {
		client.close();
	}

	@Test
	public void testRewriteUrlOk() {
		
	}
}
