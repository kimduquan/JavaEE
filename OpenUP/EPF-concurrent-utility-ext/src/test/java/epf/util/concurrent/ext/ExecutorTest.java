/**
 * 
 */
package epf.util.concurrent.ext;

import org.junit.Test;

/**
 * @author PC
 *
 */
public class ExecutorTest {

	/**
	 * Test method for {@link epf.util.concurrent.Executor#getExecutor()}.
	 */
	@Test(expected = IllegalStateException.class)
	public void testGetExecutor() {
		new Executor().getExecutor();
	}

}
