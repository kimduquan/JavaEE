package epf.gateway;

import dev.openfeature.contrib.providers.envvar.EnvVarProvider;
import dev.openfeature.sdk.Client;
import dev.openfeature.sdk.OpenFeatureAPI;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Produces;

@ApplicationScoped
public class Feature {
	
	@PostConstruct
	void postConstruct() {
		OpenFeatureAPI.getInstance().setProvider(new EnvVarProvider());
	}

	@Produces @ApplicationScoped
	public OpenFeatureAPI getOpenFeature() {
        return OpenFeatureAPI.getInstance();
    }
	
	@Produces @RequestScoped
	public Client getOpenFeatureClient() {
        return OpenFeatureAPI.getInstance().getClient();
    }
}
