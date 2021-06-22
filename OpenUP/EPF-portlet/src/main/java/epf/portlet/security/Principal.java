/**
 * 
 */
package epf.portlet.security;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import epf.client.security.Token;

/**
 * @author PC
 *
 */
@XmlRootElement
public class Principal implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private Token token;

	/**
	 * @return the token
	 */
	public Token getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(Token token) {
		this.token = token;
	}
	
	public String getName() {
		return token.getName();
	}
}
