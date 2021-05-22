/**
 * 
 */
package epf.shell;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * @author PC
 *
 */
public interface SYSTEM {

	/**
	 * 
	 */
	String OUT ="System.out";
	/**
	 * 
	 */
	String ERR = "System.err";
	
	/**
	 * @return
	 */
	static PrintStream disableErr() {
		final PrintStream sysErr = System.err;
		final ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		final PrintStream tempOut = new PrintStream(bytes);
		System.setErr(tempOut);
		return sysErr;
	}
	
	static void enableErr(final PrintStream sysErr) {
		System.setErr(sysErr);
	}
}
