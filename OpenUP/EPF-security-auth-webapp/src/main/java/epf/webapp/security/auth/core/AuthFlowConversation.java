package epf.webapp.security.auth.core;

import java.io.Serializable;
import javax.enterprise.context.ConversationScoped;
import epf.security.auth.discovery.ProviderMetadata;

/**
 * 
 */
@ConversationScoped
public class AuthFlowConversation implements Serializable {
	
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 *
	 */
	private Flow flow;

	/**
	 * 
	 */
	private ProviderMetadata providerMetadata;
	
	/**
	 *
	 */
	private String provider;
	
	/**
	 *
	 */
	private String url;

	public Flow getFlow() {
		return flow;
	}

	public void setFlow(final Flow flow) {
		this.flow = flow;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(final String provider) {
		this.provider = provider;
	}

	public ProviderMetadata getProviderMetadata() {
		return providerMetadata;
	}

	public void setProviderMetadata(final ProviderMetadata providerMetadata) {
		this.providerMetadata = providerMetadata;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(final String url) {
		this.url = url;
	}
}
