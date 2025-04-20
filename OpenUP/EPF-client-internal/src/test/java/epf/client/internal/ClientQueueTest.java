package epf.client.internal;

import java.net.URI;
import jakarta.ws.rs.client.ClientBuilder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

public class ClientQueueTest {
	
	ClientBuilder mockClientBuilder;
	jakarta.ws.rs.client.Client mockClient;
	ClientQueue clientQueue;

	@Before
	public void setUp() throws Exception {
		mockClientBuilder = Mockito.mock(ClientBuilder.class);
		mockClient = Mockito.mock(jakarta.ws.rs.client.Client.class);
		Mockito.when(mockClientBuilder.build()).thenReturn(mockClient);
		clientQueue = new ClientQueue();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testClientQueue() {
		Assert.assertTrue("ClientQueue.clients.empty", clientQueue.getClients().isEmpty());
	}

	@Test
	@Ignore
	public void testClose() throws Exception {
		clientQueue.poll(new URI("https://localhost:9443"), builder -> mockClientBuilder);
		Assert.assertFalse("ClientQueue.clients.empty", clientQueue.getClients().isEmpty());
		clientQueue.close();
		Assert.assertTrue("ClientQueue.clients.empty", clientQueue.getClients().isEmpty());
		Mockito.verify(mockClient, Mockito.times(1)).close();
	}

	@Test
	@Ignore
	public void testPoll() throws Exception {
		jakarta.ws.rs.client.Client client = clientQueue.poll(new URI("https://localhost:9443"), builder -> mockClientBuilder);
		Assert.assertSame(mockClient, client);
		Mockito.verify(mockClientBuilder, Mockito.times(1)).build();
		clientQueue.add(new URI("https://localhost:9443"), client);
		client = clientQueue.poll(new URI("https://localhost:9443"), builder -> mockClientBuilder);
		Mockito.verify(mockClientBuilder, Mockito.never()).build();
		Assert.assertSame(mockClient, client);
	}
	
	@Test(expected = NullPointerException.class)
	public void testPoll_NullURI() {
		clientQueue.poll(null, builder -> mockClientBuilder);
	}
	
	@Test
	@Ignore
	public void testPoll_NullBuilder() throws Exception {
		clientQueue.poll(new URI("https://localhost:9443"), null);
	}

	@Test
	@Ignore
	public void testAdd() throws Exception {
		jakarta.ws.rs.client.Client client = clientQueue.poll(new URI("https://localhost:9443"), builder -> mockClientBuilder);
		Assert.assertFalse("ClientQueue.clients.empty", clientQueue.getClients().isEmpty());
		clientQueue.add(new URI("https://localhost:9443"), client);
		client = clientQueue.poll(new URI("https://localhost:9443/invalid"), builder -> mockClientBuilder);
		Assert.assertSame(mockClient, client);
		Assert.assertEquals("ClientQueue.clients.size", 1, clientQueue.getClients().size());
	}

	@Test(expected = NullPointerException.class)
	public void testAdd_NullURI() throws Exception {
		clientQueue.add(null, mockClient);
	}
	
	@Test(expected = NullPointerException.class)
	public void testAdd_NullClient() throws Exception {
		clientQueue.add(new URI("https://localhost:9443"), null);
	}
}
