package epf.tests.net;

import java.net.URI;
import javax.ws.rs.core.Response;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import epf.client.gateway.GatewayUtil;
import epf.client.net.Net;
import epf.client.persistence.Entities;
import epf.client.util.Client;
import epf.naming.Naming;
import epf.tests.client.ClientUtil;
import epf.tests.health.HealthUtil;
import epf.tests.security.SecurityUtil;
import epf.util.StringUtil;

public class NetTest {
	
	private static String token;
	private static URI netUrl;
	private static URI persistenceUrl;
	
	private Client client;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		HealthUtil.readỵ̣();
		token = SecurityUtil.login();
		netUrl = GatewayUtil.get(Naming.NET);
		persistenceUrl = GatewayUtil.get(Naming.PERSISTENCE);
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
	public void testRewriteUrlOk() throws Exception {
		String shortUrl = Net.rewriteUrl(client, "https://google.com");
		try(Client gateway = ClientUtil.newClient(netUrl)){
			try(Response response = gateway.request(target -> target.path("url").queryParam("url", shortUrl), req -> req).get()){
				URI uri = response.getLocation();
				Assert.assertEquals("Response.location", new URI("https://google.com"), uri);
				Assert.assertEquals("Response.statusInfo", Response.Status.TEMPORARY_REDIRECT.getStatusCode(), response.getStatus());
			}
		}
		int id = StringUtil.fromShortString(shortUrl);
		try(Client persistence = ClientUtil.newClient(persistenceUrl)){
			persistence.authorization(token);
			Entities.remove(persistence, epf.net.schema.Net.SCHEMA, epf.net.schema.Net.URL, String.valueOf(id));
		}
	}
}
