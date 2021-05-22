/**
 * 
 */
package epf.tests;

import java.time.Duration;
import java.util.function.Predicate;

/**
 * @author PC
 *
 */
public class TestUtil {

	public static void waitUntil(Predicate<? extends Object> predicate, Duration duration) throws InterruptedException {
		long currentTime = System.currentTimeMillis();
		long targetTime = currentTime + duration.toMillis();
		while(!predicate.test(null) && (System.currentTimeMillis() < targetTime)) {
			Thread.sleep(40);
		}
	}
}
