/**
 * 
 */
package epf.util.client;

import java.net.URI;
import javax.net.ssl.SSLContext;
import javax.ws.rs.client.ClientBuilder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * @author PC
 *
 */
public class ClientQueueTest {
	
	ClientBuilder mockClientBuilder;
	javax.ws.rs.client.Client mockClient;
	ClientQueue clientQueue;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		mockClientBuilder = Mockito.mock(ClientBuilder.class);
		mockClient = Mockito.mock(javax.ws.rs.client.Client.class);
		Mockito.when(mockClientBuilder.build()).thenReturn(mockClient);
		clientQueue = new ClientQueue();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link epf.util.client.ClientQueue#ClientQueue()}.
	 */
	@Test
	public void testClientQueue() {
		Assert.assertTrue("ClientQueue.clients.empty", clientQueue.getClients().isEmpty());
		Assert.assertNull("ClientQueue.clients.context", clientQueue.getContext());
	}

	/**
	 * Test method for {@link epf.util.client.ClientQueue#initialize()}.
	 */
	@Test
	public void testInitialize() {
		clientQueue.initialize();
		Assert.assertNotNull("ClientQueue.clients.context", clientQueue.getContext());
	}

	/**
	 * Test method for {@link epf.util.client.ClientQueue#close()}.
	 * @throws Exception 
	 */
	@Test
	@Ignore
	public void testClose() throws Exception {
		clientQueue.poll(new URI("https://localhost:9443"), builder -> mockClientBuilder);
		Assert.assertFalse("ClientQueue.clients.empty", clientQueue.getClients().isEmpty());
		clientQueue.close();
		Assert.assertTrue("ClientQueue.clients.empty", clientQueue.getClients().isEmpty());
		Mockito.verify(mockClient, Mockito.times(1)).close();
	}

	/**
	 * Test method for {@link epf.util.client.ClientQueue#newBuilder(javax.net.ssl.SSLContext)}.
	 */
	@Test
	@Ignore
	public void testNewBuilder() {
		ClientBuilder clientBuilder = ClientQueue.newBuilder(Mockito.mock(SSLContext.class));
		Assert.assertNotNull(clientBuilder);
	}

	/**
	 * Test method for {@link epf.util.client.ClientQueue#poll(java.net.URI, java.util.function.Function)}.
	 * @throws Exception 
	 */
	@Test
	@Ignore
	public void testPoll() throws Exception {
		javax.ws.rs.client.Client client = clientQueue.poll(new URI("https://localhost:9443"), builder -> mockClientBuilder);
		Assert.assertSame(mockClient, client);
		Mockito.verify(mockClientBuilder, Mockito.times(1)).build();
		clientQueue.add(new URI("https://localhost:9443"), client);
		client = clientQueue.poll(new URI("https://localhost:9443"), builder -> mockClientBuilder);
		Mockito.verify(mockClientBuilder, Mockito.never()).build();
		Assert.assertSame(mockClient, client);
	}
	
	/**
	 * Test method for {@link epf.util.client.ClientQueue#poll(java.net.URI, java.util.function.Function)}. 
	 */
	@Test(expected = NullPointerException.class)
	public void testPoll_NullURI() {
		clientQueue.poll(null, builder -> mockClientBuilder);
	}
	
	/**
	 * Test method for {@link epf.util.client.ClientQueue#poll(java.net.URI, java.util.function.Function)}. 
	 * @throws Exception 
	 */
	@Test
	@Ignore
	public void testPoll_NullBuilder() throws Exception {
		clientQueue.poll(new URI("https://localhost:9443"), null);
	}

	/**
	 * Test method for {@link epf.util.client.ClientQueue#add(java.net.URI, javax.ws.rs.client.Client)}.
	 * @throws Exception 
	 */
	@Test
	@Ignore
	public void testAdd() throws Exception {
		javax.ws.rs.client.Client client = clientQueue.poll(new URI("https://localhost:9443"), builder -> mockClientBuilder);
		Assert.assertFalse("ClientQueue.clients.empty", clientQueue.getClients().isEmpty());
		clientQueue.add(new URI("https://localhost:9443"), client);
		client = clientQueue.poll(new URI("https://localhost:9443/invalid"), builder -> mockClientBuilder);
		Assert.assertSame(mockClient, client);
		Assert.assertEquals("ClientQueue.clients.size", 1, clientQueue.getClients().size());
	}

	/**
	 * Test method for {@link epf.util.client.ClientQueue#add(java.net.URI, javax.ws.rs.client.Client)}.
	 * @throws Exception 
	 */
	@Test(expected = NullPointerException.class)
	public void testAdd_NullURI() throws Exception {
		clientQueue.add(null, mockClient);
	}
	
	/**
	 * Test method for {@link epf.util.client.ClientQueue#add(java.net.URI, javax.ws.rs.client.Client)}.
	 * @throws Exception 
	 */
	@Test(expected = NullPointerException.class)
	public void testAdd_NullClient() throws Exception {
		clientQueue.add(new URI("https://localhost:9443"), null);
	}
}
