package epf.webapp;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
import javax.faces.context.ExternalContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * 
 */
public interface CookieUtil {

	/**
	 * @param request
	 * @param name
	 * @return
	 */
	static Optional<Cookie> getCookie(final HttpServletRequest request, final String name){
		final Cookie[] cookies = request.getCookies() != null ? request.getCookies() : new Cookie[0];
		return Stream.of(cookies)
				.filter(cookie -> name.equals(cookie.getName()))
				.findFirst();
	}
	
	/**
	 * @param response
	 * @param cookie
	 */
	static void deleteCookie(final ExternalContext context, final Cookie cookie) {
		cookie.setMaxAge(0);
		final Map<String, Object> map = new HashMap<String, Object>();
		map.put("comment", cookie.getComment());
		map.put("domain", cookie.getDomain());
		map.put("maxAge", cookie.getMaxAge());
		map.put("secure", cookie.getSecure());
		map.put("path", cookie.getPath());
		map.put("httpOnly", cookie.isHttpOnly());
		context.addResponseCookie(cookie.getName(), cookie.getValue(), map);
	}
}
