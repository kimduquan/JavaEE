package epf.client.util;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import java.util.ArrayList;
import java.util.HashMap;
import epf.util.StringUtil;

/**
 * @author PC
 *
 */
public class HttpHeadersUtil implements HttpHeaders {
	
	/**
	 * 
	 */
	private MultivaluedMap<String, String> requestHeaders;
	
	/**
	 * 
	 */
	private List<MediaType> acceptableMediaTypes;
	
	/**
	 * 
	 */
	private List<Locale> acceptableLanguages;
	
	/**
	 * 
	 */
	private MediaType mediaType;
	
	/**
	 * 
	 */
	private Locale language;
	
	/**
	 * 
	 */
	private Map<String, Cookie> cookies;
	
	/**
	 * 
	 */
	private Date date;
	
	/**
	 * 
	 */
	private int length;

	@Override
	public List<String> getRequestHeader(final String name) {
		return requestHeaders.get(name);
	}

	@Override
	public String getHeaderString(final String name) {
		String headerString = null;
		final List<String> header = requestHeaders.get(name);
		if(header != null) {
			headerString = StringUtil.valueOf(header, ",");
		}
		return headerString;
	}

	@Override
	public MultivaluedMap<String, String> getRequestHeaders() {
		return requestHeaders;
	}

	@Override
	public List<MediaType> getAcceptableMediaTypes() {
		return acceptableMediaTypes;
	}

	@Override
	public List<Locale> getAcceptableLanguages() {
		return acceptableLanguages;
	}

	@Override
	public MediaType getMediaType() {
		return mediaType;
	}

	@Override
	public Locale getLanguage() {
		return language;
	}

	@Override
	public Map<String, Cookie> getCookies() {
		return cookies;
	}

	@Override
	public Date getDate() {
		return date;
	}

	@Override
	public int getLength() {
		return length;
	}

	/**
	 * @param httpHeaders
	 * @return
	 */
	public static HttpHeaders clone(final HttpHeaders httpHeaders) {
		final HttpHeadersUtil newHttpHeaders = new HttpHeadersUtil();
		newHttpHeaders.acceptableLanguages = new ArrayList<>(httpHeaders.getAcceptableLanguages());
		newHttpHeaders.acceptableMediaTypes = new ArrayList<>(httpHeaders.getAcceptableMediaTypes());
		newHttpHeaders.cookies = new HashMap<>(httpHeaders.getCookies());
		newHttpHeaders.date = httpHeaders.getDate();
		newHttpHeaders.language = httpHeaders.getLanguage();
		newHttpHeaders.length = httpHeaders.getLength();
		newHttpHeaders.mediaType = httpHeaders.getMediaType();
		newHttpHeaders.requestHeaders = new MultivaluedHashMap<>(httpHeaders.getRequestHeaders());
		return newHttpHeaders;
	}
}
