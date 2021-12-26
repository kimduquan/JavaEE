package epf.shell;

/**
 * @author PC
 *
 */
public interface JShell {

	/**
	 * 
	 */
	static void main() {
		final String args = System.getProperty("args");
		Application.main(args.split(" "));
	}
}
