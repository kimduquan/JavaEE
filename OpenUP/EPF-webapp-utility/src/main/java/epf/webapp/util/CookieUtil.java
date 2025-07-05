package epf.webapp.util;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import jakarta.faces.context.ExternalContext;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

public interface CookieUtil {

	static Stream<Cookie> getCookie(final HttpServletRequest request, final String name){
		final Cookie[] cookies = request.getCookies() != null ? request.getCookies() : new Cookie[0];
		return Stream.of(cookies)
				.filter(cookie -> name.equals(cookie.getName()));
	}
	
	static void deleteCookie(final ExternalContext context, final Cookie cookie) {
		cookie.setMaxAge(0);
		final Map<String, Object> map = new HashMap<String, Object>();
		if(cookie.getComment() != null) {
			map.put("comment", cookie.getComment());
		}
		if(cookie.getDomain() != null){
			map.put("domain", cookie.getDomain());
		}
		map.put("maxAge", cookie.getMaxAge());
		map.put("secure", cookie.getSecure());
		if(cookie.getPath() != null) {
			map.put("path", cookie.getPath());
		}
		map.put("httpOnly", cookie.isHttpOnly());
		context.addResponseCookie(cookie.getName(), cookie.getValue(), map);
	}
}
