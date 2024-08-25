package epf.util.websocket.ext;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import jakarta.websocket.EncodeException;
import jakarta.websocket.RemoteEndpoint.Basic;
import jakarta.websocket.Session;
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
public class MessageTest {
	
	static ExecutorService executor;
	Message message;
	Object object;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		executor = Executors.newFixedThreadPool(5);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		executor.shutdownNow();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		object = Mockito.mock(Object.class);
		message = new Message(object);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link epf.util.websocket.Message#Message(java.lang.Object)}.
	 */
	@Test
	public void testMessage() {
		
	}

	/**
	 * Test method for {@link epf.util.websocket.Message#send(javax.websocket.Session)}.
	 * @throws EncodeException 
	 * @throws IOException 
	 */
	@Test
	public void testSend_SendObject() throws IOException, EncodeException {
		Session session = Mockito.mock(Session.class);
		Basic basic = Mockito.mock(Basic.class);
		Mockito.when(session.getBasicRemote()).thenReturn(basic);
		message.send(session);
		Mockito.verify(basic, Mockito.times(1)).sendObject(Mockito.same(object));
	}
	
	/**
	 * Test method for {@link epf.util.websocket.Message#send(javax.websocket.Session)}.
	 * @throws EncodeException 
	 * @throws IOException 
	 */
	@Test
	public void testSend_SendText() throws IOException, EncodeException {
		Session session = Mockito.mock(Session.class);
		Basic basic = Mockito.mock(Basic.class);
		Mockito.when(session.getBasicRemote()).thenReturn(basic);
		message = new Message("this is a test");
		message.send(session);
		Mockito.verify(basic, Mockito.times(1)).sendText(Mockito.eq("this is a test"));
	}
}
