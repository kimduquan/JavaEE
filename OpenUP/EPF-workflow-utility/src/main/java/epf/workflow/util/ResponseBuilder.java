package epf.workflow.util;

import jakarta.ws.rs.core.Link;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

/**
 * 
 */
public class ResponseBuilder {

	/**
	 * 
	 */
	private transient Response.ResponseBuilder builder = Response.ok().type(MediaType.APPLICATION_JSON);
	
	/**
	 * 
	 */
	private int size = 0;
	
	/**
	 * @param status
	 * @return
	 */
	public ResponseBuilder status(final Status status) {
		builder = builder.status(status);
		return this;
	}
	
	/**
	 * @param entity
	 * @return
	 */
	public ResponseBuilder entity(final Object entity) {
		builder = builder.entity(entity);
		return this;
	}
	
	/**
	 * @param name
	 * @param value
	 * @return
	 */
	public ResponseBuilder header(final String name, final Object value) {
		builder = builder.header(name, value);
		return this;
	}
	
	/**
	 * @param links
	 * @return
	 */
	public ResponseBuilder links(final Link... links) {
		builder = builder.links(links);
		if(links == null) {
			size = 0;
		}
		else {
			size += links.length;
		}
		return this;
	}
	
	/**
	 * @return
	 */
	public Response build() {
		return builder.build();
	}
	
	/**
	 * @return
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * @param builder
	 * @param response
	 * @return
	 */
	public static ResponseBuilder fromResponse(final ResponseBuilder builder, final Response response) {
		builder.builder = Response.fromResponse(response);
		builder.size = 0;
		return builder;
	}
}
