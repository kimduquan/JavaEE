package epf.client.util;

import java.net.URI;
import java.security.KeyStore;
import javax.ws.rs.client.ClientBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import javax.ws.rs.client.Client;

public class ClientUtilTest {
	
	ClientUtil clientUtil;
	ClientQueue clients;
	KeyStore keyStore;
	KeyStore trustStore;

	@Before
	public void setUp() throws Exception {
		clients = Mockito.mock(ClientQueue.class);
		Client client = Mockito.mock(Client.class);
		Mockito.when(clients.poll(Mockito.any(), Mockito.any())).thenReturn(client);
		clientUtil = new ClientUtil();
		clientUtil.clients = clients;
		keyStore = Mockito.mock(KeyStore.class);
		trustStore = Mockito.mock(KeyStore.class);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testNewClient() throws Exception {
		clientUtil.newClient(new URI("https://locahost:9443"));
	}

	@Test(expected = NullPointerException.class)
	public void testSetKeyStoreWithoutPassword_ThrowNullPointerException() throws Exception {
		clientUtil.newClient(new URI("https://locahost:9443"));
		clientUtil.setKeyStore(keyStore, null);
	}
	
	@Test(expected = NullPointerException.class)
	public void testSetNullTrustStore_ThrowNullPointerException() throws Exception {
		clientUtil.newClient(new URI("https://locahost:9443"));
		clientUtil.setTrustStore(null);
	}
	
	@Test
	public void testSetKeyStore_KeyStoreIsAffected() throws Exception {
		clientUtil.newClient(new URI("https://locahost:9443"));
		clientUtil.setKeyStore(keyStore, "".toCharArray());
		ClientBuilder builder = Mockito.mock(ClientBuilder.class);
		clientUtil.buildClient(builder);
		Mockito.verify(builder, Mockito.times(1)).keyStore(Mockito.same(keyStore), Mockito.eq("".toCharArray()));
	}
	
	@Test
	public void testNotSetKeyStore_KeyStoreIsNotAffected() throws Exception {
		clientUtil.newClient(new URI("https://locahost:9443"));
		ClientBuilder builder = Mockito.mock(ClientBuilder.class);
		clientUtil.buildClient(builder);
		Mockito.verify(builder, Mockito.never()).keyStore(Mockito.any(), Mockito.anyString());
	}
	
	@Test
	public void testSetTrustStore_TrustStoreIsAffected() throws Exception {
		clientUtil.newClient(new URI("https://locahost:9443"));
		clientUtil.setTrustStore(trustStore);
		ClientBuilder builder = Mockito.mock(ClientBuilder.class);
		clientUtil.buildClient(builder);
		Mockito.verify(builder, Mockito.times(1)).trustStore(Mockito.same(trustStore));
	}
	
	@Test
	public void testNotSetTrustStore_TrustStoreIsNotAffected() throws Exception {
		clientUtil.newClient(new URI("https://locahost:9443"));
		ClientBuilder builder = Mockito.mock(ClientBuilder.class);
		clientUtil.buildClient(builder);
		Mockito.verify(builder, Mockito.never()).trustStore(Mockito.any());
	}
}
