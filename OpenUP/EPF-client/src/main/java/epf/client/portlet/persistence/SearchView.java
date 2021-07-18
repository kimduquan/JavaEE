/**
 * 
 */
package epf.client.portlet.persistence;

/**
 * @author PC
 *
 */
public interface SearchView extends ResultList {

	/**
	 * @param text
	 */
	void setText(final String text);
	
	/**
	 * @return
	 */
	String getText();
	
	/**
	 * @throws Exception
	 */
	void search() throws Exception;
}
