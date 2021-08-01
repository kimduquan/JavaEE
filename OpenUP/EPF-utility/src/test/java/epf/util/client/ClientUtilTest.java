package epf.util.client;

import java.net.URI;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class ClientUtilTest {
	
	ClientUtil clientUtil;
	ClientQueue clients;

	@Before
	public void setUp() throws Exception {
		clients = Mockito.mock(ClientQueue.class);
		clientUtil = new ClientUtil();
		clientUtil.clients = clients;
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testNewClient() throws Exception {
		clientUtil.newClient(new URI("https://locahost:9443"));
	}

}
