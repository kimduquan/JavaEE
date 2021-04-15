/**
 * 
 */
package epf.util.client;

import java.net.URI;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * @author PC
 *
 */
public class ClientTest {
	
	ClientQueue mockClientQueue;
	URI uri;
	ClientBuilder mockClientBuilder;
	javax.ws.rs.client.Client mockClient;
	Client client;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		mockClientQueue = Mockito.mock(ClientQueue.class);
		uri = new URI("https://locahost:9443");
		mockClientBuilder = Mockito.mock(ClientBuilder.class);
		mockClient = Mockito.mock(javax.ws.rs.client.Client.class);
		Mockito.when(mockClientQueue.poll(Mockito.same(uri), Mockito.any())).thenReturn(mockClient);
		client = new Client(mockClientQueue, uri, b -> mockClientBuilder);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link epf.util.client.Client#Client(epf.util.client.ClientQueue, java.net.URI, java.util.function.Function)}.
	 */
	@Test
	public void testClient() {
		Mockito.verify(mockClientQueue, Mockito.times(1)).poll(Mockito.same(uri), Mockito.any());
		Assert.assertSame("Client.client", mockClient, client.getClient());
		Assert.assertSame("Client.clients", mockClientQueue, client.getClients());
		Assert.assertSame("Client.uri", uri, client.getUri());
	}

	/**
	 * Test method for {@link epf.util.client.Client#authorization(java.lang.String)}.
	 */
	@Test
	public void testAuthorization() {
		Client client = new Client(mockClientQueue, uri, b -> mockClientBuilder);
		Client returnClient = client.authorization("authorization");
		Assert.assertSame(client, returnClient);
		Assert.assertEquals("Client.authHeader", "Bearer authorization", returnClient.getAuthHeader());
		returnClient = client.authorization("");
		Assert.assertSame(client, returnClient);
		Assert.assertEquals("Client.authHeader", "Bearer ", returnClient.getAuthHeader());
	}
	
	/**
	 * Test method for {@link epf.util.client.Client#authorization(java.lang.String)}.
	 */
	@Test(expected = NullPointerException.class)
	public void testAuthorization_NullToken() {
		client.authorization(null);
	}

	/**
	 * Test method for {@link epf.util.client.Client#close()}.
	 * @throws Exception 
	 */
	@Test
	public void testClose() throws Exception {
		client.close();
		Mockito.verify(mockClientQueue, Mockito.times(1)).add(Mockito.same(uri), Mockito.same(mockClient));
	}

	/**
	 * Test method for {@link epf.util.client.Client#request(java.util.function.Function, java.util.function.Function)}.
	 */
	@Test
	public void testRequest() {
		WebTarget mockTarget = Mockito.mock(WebTarget.class);
		Mockito.when(mockClient.target(Mockito.same(uri))).thenReturn(mockTarget);
		Builder mockRequest = Mockito.mock(Builder.class);
		Mockito.when(mockTarget.request()).thenReturn(mockRequest);
		client.request(
				target -> {
					Assert.assertSame(mockTarget, target);
					return target;
					}, 
				req -> {
					Assert.assertSame(mockRequest, req);
					return req;
				});
		Mockito.verify(mockClient, Mockito.times(1)).target(Mockito.same(uri));
		Mockito.verify(mockTarget, Mockito.times(1)).request();
	}

	/**
	 * Test method for {@link epf.util.client.Client#request(java.util.function.Function, java.util.function.Function)}.
	 */
	@Test
	public void testRequest_Authorization() {
		WebTarget mockTarget = Mockito.mock(WebTarget.class);
		Mockito.when(mockClient.target(Mockito.same(uri))).thenReturn(mockTarget);
		Builder mockRequest = Mockito.mock(Builder.class);
		Mockito.when(mockTarget.request()).thenReturn(mockRequest);
		Mockito.when(mockRequest.header(Mockito.eq("Authorization"), Mockito.eq("Bearer authorization"))).thenReturn(mockRequest);
		client.authorization("authorization");
		client.request(
				target -> {
					Assert.assertSame(mockTarget, target);
					return target;
					}, 
				req -> {
					Assert.assertSame(mockRequest, req);
					return req;
				});
		Mockito.verify(mockClient, Mockito.times(1)).target(Mockito.same(uri));
		Mockito.verify(mockTarget, Mockito.times(1)).request();
		Mockito.verify(mockRequest, Mockito.times(1)).header(Mockito.eq("Authorization"), Mockito.eq("Bearer authorization"));
	}
}
