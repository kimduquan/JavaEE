/**
 * 
 */
package epf.util.http;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import epf.util.StringUtil;

/**
 * @author PC
 *
 */
public interface SessionUtil {

	/**
	 * 
	 */
	String HTTP_SESSION_ATTRIBUTE = "epf.http.session.attribute";
	
	/**
	 * 
	 */
	String MAP_ATTRIBUTE_SEPARATOR = "key";
	
	/**
	 * @param service
	 * @param name
	 * @return
	 */
	static String getAttributeName(final String service, final String name) {
		return StringUtil.join(HTTP_SESSION_ATTRIBUTE, service, name);
	}
	
	/**
	 * @param service
	 * @param name
	 * @param key
	 * @return
	 */
	static String getMapAttributeName(final String service, final String name, final String key) {
		final String attributeKey = StringUtil.join(name, MAP_ATTRIBUTE_SEPARATOR, key);
		return StringUtil.join(HTTP_SESSION_ATTRIBUTE, service, attributeKey);
	}
	
	/**
	 * @param request
	 * @param name
	 * @return
	 */
	static Object getAttribute(final HttpServletRequest request, final String service, final String name) {
		final Object value = request.getSession().getAttribute(getAttributeName(service, name));
		return Optional.ofNullable(value);
	}
	
	/**
	 * @param request
	 * @param name
	 * @param value
	 */
	static void setAttribute(final HttpServletRequest request, final String service, final String name, final Object value) {
		request.getSession().setAttribute(getAttributeName(service, name), value);
	}
	
	/**
	 * @param request
	 * @param service
	 * @param name
	 * @param key
	 * @return
	 */
	static Optional<Object> getMapAttribute(final HttpServletRequest request, final String service, final String name, final String key) {
		final Object value = request.getSession().getAttribute(getMapAttributeName(service, name, key));
		return Optional.ofNullable(value);
	}
	
	/**
	 * @param request
	 * @param service
	 * @param name
	 * @param key
	 * @param value
	 */
	static void setMapAttribute(final HttpServletRequest request, final String service, final String name, final String key, final Object value) {
		request.getSession().setAttribute(getMapAttributeName(service, name, key), value);
	}
}
