package epf.webapp.view;

/**
 * @author PC
 *
 */
public interface MessageView {
	
	/**
	 * 
	 */
	String ERROR = "ERROR";
	
	/**
	 * 
	 */
	String INFO = "INFO";
	
	/**
	 * 
	 */
	String WARN = "WARN";
	
	/**
	 * 
	 */
	String FATAL = "FATAL";

	/**
	 * @return
	 */
	String getSeverity();
	
	/**
	 * @return
	 */
	String getSummary();
	
	/**
	 * @return
	 */
	String getDetail();
}
