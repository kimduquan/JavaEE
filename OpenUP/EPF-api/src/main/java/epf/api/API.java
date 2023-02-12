package epf.api;

import java.util.List;
import epf.api.info.Info;
import epf.api.security.SecurityRequirement;
import epf.api.server.Server;
import epf.api.tag.Tag;

/**
 * 
 */
public class API extends Extensible {

	/**
	 *
	 */
	private String openapi;
	
	/**
	 *
	 */
	private Info info;
	
	/**
	 *
	 */
	private ExternalDocumentation externalDocs;
	
	/**
	 *
	 */
	private List<Server> servers;
	
	/**
	 *
	 */
	private List<SecurityRequirement> security;
	
	/**
	 *
	 */
	private List<Tag> tags;
	
	/**
	 *
	 */
	private Paths paths;
    
	/**
	 *
	 */
	private Components components;

	public String getOpenapi() {
		return openapi;
	}

	public void setOpenapi(final String openapi) {
		this.openapi = openapi;
	}

	public Info getInfo() {
		return info;
	}

	public void setInfo(final Info info) {
		this.info = info;
	}

	public ExternalDocumentation getExternalDocs() {
		return externalDocs;
	}

	public void setExternalDocs(final ExternalDocumentation externalDocs) {
		this.externalDocs = externalDocs;
	}

	public List<Server> getServers() {
		return servers;
	}

	public void setServers(final List<Server> servers) {
		this.servers = servers;
	}

	public List<SecurityRequirement> getSecurity() {
		return security;
	}

	public void setSecurity(final List<SecurityRequirement> security) {
		this.security = security;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(final List<Tag> tags) {
		this.tags = tags;
	}

	public Paths getPaths() {
		return paths;
	}

	public void setPaths(final Paths paths) {
		this.paths = paths;
	}

	public Components getComponents() {
		return components;
	}

	public void setComponents(final Components components) {
		this.components = components;
	}
}
