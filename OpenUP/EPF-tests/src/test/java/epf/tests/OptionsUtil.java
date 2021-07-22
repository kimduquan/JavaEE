/**
 * 
 */
package epf.tests;

import java.io.File;
import java.util.logging.Level;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

/**
 * @author PC
 *
 */
public class OptionsUtil {

	/**
	 * @return
	 */
	public static FirefoxOptions newFirefoxOptions() {
		final FirefoxOptions options = new FirefoxOptions();
		final String args = System.getProperty("webdriver.options.args");
		if(args != null) {
			options.addArguments(args.split(" "));
		}
		
		final String binary = System.getProperty("webdriver.options.binary");
		if(binary != null) {
			options.setBinary(binary);
		}
		
		final String log = System.getProperty("webdriver.options.log");
		if(log != null) {
			final Level level = Level.parse(log);
			options.setLogLevel(FirefoxDriverLogLevel.fromLevel(level));
		}
		
		final String profile = System.getProperty("webdriver.options.profile");
		if(profile != null) {
			options.setProfile(new FirefoxProfile(new File(profile)));
		}
		
//		final Properties props = System.getProperties();
//		props.entrySet().parallelStream()
//		.filter(prop -> prop.getKey().toString().startsWith("webdriver.options.prefs."))
//		.forEach(prop -> options.addPreference(prop.getKey().toString(), prop.getValue().toString()));
		return options;
	}
}
