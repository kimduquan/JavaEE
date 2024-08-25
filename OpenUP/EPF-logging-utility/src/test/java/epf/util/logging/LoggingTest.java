package epf.util.logging;

import java.util.logging.Logger;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author PC
 *
 */
public class LoggingTest {
	
	String mockMethod(String arg) throws Exception{
		if("".equals(arg)) {
			throw new Exception();
		}
		return arg;
	}

	/**
	 * Test method for {@link epf.util.logging.LogManager#getLogger(java.lang.String)}.
	 */
	@Test
	public void testGetLogger() {
		Logger logger = LogManager.getLogger(LoggingTest.class.getName());
		Assert.assertNotNull(logger);
		logger = LogManager.getLogger("");
		Assert.assertNotNull(logger);
		logger = LogManager.getLogger("invalid");
		Assert.assertNotNull(logger);
	}

	/**
	 * Test method for {@link epf.util.logging.LogManager#getLogger(java.lang.String)}.
	 */
	@Test(expected = NullPointerException.class)
	public void testGetLogger_NullName() {
		LogManager.getLogger(null);
	}
}
