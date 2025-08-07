package epf.tests;

import java.time.Duration;
import java.util.concurrent.Callable;
import java.util.function.Predicate;

public class TestUtil {

	public static void waitUntil(Predicate<? extends Object> predicate, Duration duration) throws InterruptedException {
		long currentTime = System.currentTimeMillis();
		long targetTime = currentTime + duration.toMillis();
		while(!predicate.test(null) && (System.currentTimeMillis() < targetTime)) {
			Thread.sleep(80);
		}
	}
	
	public static <T> T doWhile(Callable<T> callable, Predicate<T> predicate, Duration duration) throws Exception {
		long currentTime = System.currentTimeMillis();
		long targetTime = currentTime + duration.toMillis();
		T object = null;
		do {
			object = callable.call();
			Thread.sleep(80);
		}
		while(predicate.test(object) && (System.currentTimeMillis() < targetTime));
		return object;
	}
}
