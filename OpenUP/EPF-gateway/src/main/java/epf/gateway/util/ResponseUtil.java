package epf.gateway.util;

import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.Link.Builder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import epf.util.StringUtil;
import epf.util.io.IOUtil;
import java.util.HashSet;
import java.util.HashMap;
import javax.ws.rs.core.MultivaluedHashMap;

/**
 * @author PC
 *
 */
public class ResponseUtil extends Response {
	
	/**
	 * 
	 */
	private int status;
	
	/**
	 * 
	 */
	private StatusType statusInfo;
	
	/**
	 * 
	 */
	private Object entity;
	
	/**
	 * 
	 */
	private boolean hasEntity;
	
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
	private int length;
	
	/**
	 * 
	 */
	private Set<String> allowedMethods;
	
	/**
	 * 
	 */
	private Map<String, NewCookie> cookies;
	
	/**
	 * 
	 */
	private EntityTag entityTag;
	
	/**
	 * 
	 */
	private Date date;
	
	/**
	 * 
	 */
	private Date lastModified;
	
	/**
	 * 
	 */
	private URI location;
	
	/**
	 * 
	 */
	private Set<Link> links;
	
	/**
	 * 
	 */
	private MultivaluedMap<String, Object> metadata;
	
	/**
	 * 
	 */
	private MultivaluedMap<String, String> stringHeaders;

	@Override
	public int getStatus() {
		return status;
	}

	@Override
	public StatusType getStatusInfo() {
		return statusInfo;
	}

	@Override
	public Object getEntity() {
		return entity;
	}

	@Override
	public <T> T readEntity(Class<T> entityType) {
		return null;
	}

	@Override
	public <T> T readEntity(GenericType<T> entityType) {
		return null;
	}

	@Override
	public <T> T readEntity(Class<T> entityType, Annotation[] annotations) {
		return null;
	}

	@Override
	public <T> T readEntity(GenericType<T> entityType, Annotation[] annotations) {
		return null;
	}

	@Override
	public boolean hasEntity() {
		return hasEntity;
	}

	@Override
	public boolean bufferEntity() {
		entity = IOUtil.clone((InputStream)entity);
		return true;
	}

	@Override
	public void close() {
		
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
	public int getLength() {
		return length;
	}

	@Override
	public Set<String> getAllowedMethods() {
		return allowedMethods;
	}

	@Override
	public Map<String, NewCookie> getCookies() {
		return cookies;
	}

	@Override
	public EntityTag getEntityTag() {
		return entityTag;
	}

	@Override
	public Date getDate() {
		return date;
	}

	@Override
	public Date getLastModified() {
		return lastModified;
	}

	@Override
	public URI getLocation() {
		return location;
	}

	@Override
	public Set<Link> getLinks() {
		return links;
	}

	@Override
	public boolean hasLink(final String relation) {
		return links.stream().filter(link -> relation.equals(link.getRel())).findFirst().isPresent();
	}

	@Override
	public Link getLink(final String relation) {
		return links.stream().filter(link -> relation.equals(link.getRel())).findFirst().orElse(null);
	}

	@Override
	public Builder getLinkBuilder(final String relation) {
		Builder builder = null;
		final Link link = getLink(relation);
		if(link != null) {
			builder = Link.fromLink(link);
		}
		return builder;
	}

	@Override
	public MultivaluedMap<String, Object> getMetadata() {
		return metadata;
	}

	@Override
	public MultivaluedMap<String, String> getStringHeaders() {
		return stringHeaders;
	}

	@Override
	public String getHeaderString(final String name) {
		String headerString = null;
		final List<String> header = stringHeaders.get(name);
		if(header != null) {
			headerString = StringUtil.valueOf(stringHeaders.get(name), ",");
		}
		return headerString;
	}
	
	/**
	 * @param response
	 * @return
	 */
	public static Response clone(final Response response) {
		final ResponseUtil newResponse = new ResponseUtil();
		newResponse.allowedMethods = new HashSet<>(response.getAllowedMethods());
		newResponse.cookies = new HashMap<>(response.getCookies());
		newResponse.date = response.getDate();
		newResponse.entity = response.readEntity(InputStream.class);
		newResponse.entityTag = response.getEntityTag();
		newResponse.hasEntity = response.hasEntity();
		newResponse.language = response.getLanguage();
		newResponse.lastModified = response.getLastModified();
		newResponse.length = response.getLength();
		newResponse.links = new HashSet<>(response.getLinks());
		newResponse.location = response.getLocation();
		newResponse.mediaType = response.getMediaType();
		newResponse.metadata = new MultivaluedHashMap<>(response.getMetadata());
		newResponse.status = response.getStatus();
		newResponse.statusInfo = response.getStatusInfo();
		newResponse.stringHeaders = new MultivaluedHashMap<>(response.getStringHeaders());
		return newResponse;
	}
    
    /**
     * @param response
     * @param baseUri
     * @return
     */
    public static Response buildResponse(final Response response, final URI baseUri){
    	ResponseBuilder builder = Response.fromResponse(response);
		Set<Link> links = response.getLinks();
		if(links != null){
			links = links
					.stream()
					.filter(link -> link.getType() == null)
					.collect(Collectors.toSet());
			builder = builder.links().links(links.toArray(new Link[0]));
		}
		final Response newResponse = builder.build();
		return newResponse;
    }
}
