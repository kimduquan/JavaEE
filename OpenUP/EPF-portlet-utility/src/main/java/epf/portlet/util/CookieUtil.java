/**
 * 
 */
package epf.portlet.util;

import java.util.Optional;
import java.util.stream.Stream;
import javax.inject.Inject;
import javax.inject.Named;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.annotations.PortletRequestScoped;
import javax.servlet.http.Cookie;

/**
 * @author PC
 *
 */
@PortletRequestScoped
public class CookieUtil {
	
	/**
	 * 
	 */
	@Inject @Named(Bridge.PORTLET_REQUEST)
	private transient PortletRequest request;

	/**
	 * 
	 */
	@Inject @Named(Bridge.PORTLET_RESPONSE)
	private transient PortletResponse response;
	
	/**
	 * @param cookie
	 */
	public void addCookie(final Cookie cookie) {
		response.addProperty(cookie);
	}
	
	/**
	 * @param name
	 */
	public void deleteCookie(final String name) {
		final Cookie[] cookies = request.getCookies() != null ? request.getCookies() : new Cookie[0];
		Stream.of(cookies)
		.filter(cookie -> name.equals(cookie.getName()))
		.findFirst()
		.ifPresent(cookie -> {
			cookie.setMaxAge(0);
			response.addProperty(cookie);
		});
	}
	
	/**
	 * @param name
	 * @return
	 */
	public Optional<Cookie> getCookie(final String name) {
		final Cookie[] cookies = request.getCookies() != null ? request.getCookies() : new Cookie[0];
		return Stream.of(cookies)
				.filter(cookie -> name.equals(cookie.getName()))
				.findFirst();
	}
}
