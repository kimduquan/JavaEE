/**
 * 
 */
package epf.util.websocket;

import javax.websocket.Session;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * @author PC
 *
 */
public class SessionUtilTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link epf.util.websocket.SessionUtil#toString(javax.websocket.Session)}.
	 */
	@Test
	public void testToStringSession() {
		Session session = Mockito.mock(Session.class);
		String string = SessionUtil.toString(session);
		Assert.assertFalse(string.isEmpty());
	}

}
