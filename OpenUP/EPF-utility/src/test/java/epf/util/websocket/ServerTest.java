/**
 * 
 */
package epf.util.websocket;

import java.time.Instant;
import javax.websocket.Session;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * @author PC
 *
 */
public class ServerTest {
	
	Session session;
	Server server;

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
		session = Mockito.mock(Session.class);
		server = new Server();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link epf.util.websocket.Server#onOpen(javax.websocket.Session)}.
	 */
	@Test
	public void testOnOpen() {
		String id = String.valueOf(Instant.now().toEpochMilli());
		Mockito.when(session.getId()).thenReturn(id);
		server.onOpen(session);
	}

	/**
	 * Test method for {@link epf.util.websocket.Server#onClose(javax.websocket.Session, javax.websocket.CloseReason)}.
	 */
	@Test
	public void testOnClose() {
		String id = String.valueOf(Instant.now().toEpochMilli());
		Mockito.when(session.getId()).thenReturn(id);
		server.onOpen(session);
		server.onClose(session, null);
	}
}
