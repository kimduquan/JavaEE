package epf.transaction.api.media;

import epf.transaction.api.Extensible;

/**
 * 
 */
public class XML extends Extensible {

	/**
	 *
	 */
	private String name;
	
	/**
	 *
	 */
	private String namespace;
	
	/**
	 *
	 */
	private String prefix;
	
	/**
	 *
	 */
	private Boolean attribute;
	
	/**
	 *
	 */
	private Boolean wrapped;

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(final String namespace) {
		this.namespace = namespace;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(final String prefix) {
		this.prefix = prefix;
	}

	public Boolean getAttribute() {
		return attribute;
	}

	public void setAttribute(final Boolean attribute) {
		this.attribute = attribute;
	}

	public Boolean getWrapped() {
		return wrapped;
	}

	public void setWrapped(final Boolean wrapped) {
		this.wrapped = wrapped;
	}
}
