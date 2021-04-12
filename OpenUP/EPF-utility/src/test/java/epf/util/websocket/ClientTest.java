/**
 * 
 */
package epf.util.websocket;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import javax.websocket.ClientEndpointConfig;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * @author PC
 *
 */
public class ClientTest {
	
	WebSocketContainer container;
	URI uri;
	Session session;
	ClientEndpointConfig config;
	Client client;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		container = Mockito.mock(WebSocketContainer.class);
		uri = new URI("wss://localhost:9443/cache");
		session = Mockito.mock(Session.class);
		config = Mockito.mock(ClientEndpointConfig.class);
		Mockito.when(container.connectToServer(Mockito.any(Client.class), Mockito.same(config), Mockito.same(uri))).thenReturn(session);
		client = Client.connectToServer(container, config, uri);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link epf.util.websocket.Client#close()}.
	 * @throws Exception 
	 */
	@Test
	public void testClose() throws Exception {
		client.close();
		Mockito.verify(session, Mockito.times(1)).close();
	}

	/**
	 * Test method for {@link epf.util.websocket.Client#onMessage(java.lang.String, javax.websocket.Session)}.
	 */
	@Test
	public void testOnMessageStringSession() {
		client.onMessage("messages", session);
		
		List<Object> messages = new ArrayList<>();
		client.onMessage(msg -> {
			messages.add(msg);
		});
		client.onMessage("messages", session);
		Assert.assertFalse(messages.isEmpty());
		Assert.assertEquals(1, messages.size());
		Assert.assertEquals("messages", messages.get(0));
	}

	/**
	 * Test method for {@link epf.util.websocket.Client#onMessage(java.util.function.Consumer)}.
	 */
	@Test
	public void testOnMessageConsumerOfQsuperObject() {
		List<Object> messages = new ArrayList<>();
		client.onMessage(msg -> {
			messages.add(msg);
		});
		client.onMessage("messages", session);
		Assert.assertFalse(messages.isEmpty());
		Assert.assertEquals(1, messages.size());
		Assert.assertEquals("messages", messages.get(0));
		
		List<Object> otherMessages = new ArrayList<>();
		client.onMessage(msg -> {
			otherMessages.add(msg);
		});
		client.onMessage("otherMessages", session);
		Assert.assertFalse(otherMessages.isEmpty());
		Assert.assertEquals(1, otherMessages.size());
		Assert.assertEquals("otherMessages", otherMessages.get(0));
	}

	/**
	 * Test method for {@link epf.util.websocket.Client#connectToServer(javax.websocket.WebSocketContainer, java.net.URI)}.
	 * @throws Exception
	 */
	@Test
	public void testConnectToServer() throws Exception {
		Assert.assertNotNull("Client", client);
		Assert.assertSame("Client.session", session, client.getSession());
		Mockito.verify(container, Mockito.times(1)).connectToServer(Mockito.same(client), Mockito.same(config), Mockito.same(uri));
	}

	@Test
	public void testOnOpen() {
		client.onOpen(session, null);
	}
}
