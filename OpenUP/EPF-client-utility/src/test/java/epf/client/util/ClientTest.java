package epf.client.util;

import java.net.URI;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Invocation.Builder;
import jakarta.ws.rs.client.WebTarget;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class ClientTest {
	
	URI uri;
	ClientBuilder mockClientBuilder;
	jakarta.ws.rs.client.Client mockClient;
	Client client;
	
	void add(URI uri, jakarta.ws.rs.client.Client rsClient) {
		
	}

	@Before
	public void setUp() throws Exception {
		uri = new URI("https://locahost:9443");
		mockClientBuilder = Mockito.mock(ClientBuilder.class);
		mockClient = Mockito.mock(jakarta.ws.rs.client.Client.class);
		client = new Client(mockClient, uri, this::add);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testClient() {
		Assert.assertSame("Client.client", mockClient, client.getClient());
		Assert.assertSame("Client.uri", uri, client.getUri());
	}

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
	
	@Test(expected = NullPointerException.class)
	public void testAuthorization_NullToken() {
		client.authorization(null);
	}

	@Test
	public void testClose() throws Exception {
		client.close();
	}

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
