package epf.client.util;

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
	
	URI uri;
	ClientBuilder mockClientBuilder;
	javax.ws.rs.client.Client mockClient;
	Client client;
	
	void add(URI uri, javax.ws.rs.client.Client rsClient) {
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		uri = new URI("https://locahost:9443");
		mockClientBuilder = Mockito.mock(ClientBuilder.class);
		mockClient = Mockito.mock(javax.ws.rs.client.Client.class);
		client = new Client(mockClient, uri, this::add);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link epf.client.util.Client#Client(epf.client.util.ClientQueue, java.net.URI, java.util.function.Function)}.
	 */
	@Test
	public void testClient() {
		Assert.assertSame("Client.client", mockClient, client.getClient());
		Assert.assertSame("Client.uri", uri, client.getUri());
	}

	/**
	 * Test method for {@link epf.client.util.Client#authorization(java.lang.String)}.
	 */
	@Test
	public void testAuthorization() {
		Client client = new Client(mockClient, uri, this::add);
		Client returnClient = client.authorization("authorization".toCharArray());
		Assert.assertSame(client, returnClient);
		Assert.assertArrayEquals("Client.authToken", "authorization".toCharArray(), returnClient.getAuthToken());
		returnClient = client.authorization("".toCharArray());
		Assert.assertSame(client, returnClient);
		Assert.assertArrayEquals("Client.authToken", "".toCharArray(), returnClient.getAuthToken());
	}
	
	/**
	 * Test method for {@link epf.client.util.Client#authorization(java.lang.String)}.
	 */
	@Test(expected = NullPointerException.class)
	public void testAuthorization_NullToken() {
		client.authorization(null);
	}

	/**
	 * Test method for {@link epf.client.util.Client#close()}.
	 * @throws Exception 
	 */
	@Test
	public void testClose() throws Exception {
		client.close();
	}

	/**
	 * Test method for {@link epf.client.util.Client#request(java.util.function.Function, java.util.function.Function)}.
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
	 * Test method for {@link epf.client.util.Client#request(java.util.function.Function, java.util.function.Function)}.
	 */
	@Test
	public void testRequest_Authorization() {
		WebTarget mockTarget = Mockito.mock(WebTarget.class);
		Mockito.when(mockClient.target(Mockito.same(uri))).thenReturn(mockTarget);
		Builder mockRequest = Mockito.mock(Builder.class);
		Mockito.when(mockTarget.request()).thenReturn(mockRequest);
		Mockito.when(mockRequest.header(Mockito.eq("Authorization"), Mockito.eq("Bearer authorization"))).thenReturn(mockRequest);
		client.authorization("authorization".toCharArray());
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
