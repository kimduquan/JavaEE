package epf.shell.query;

import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import epf.naming.Naming;
import epf.naming.Naming.Query;
import epf.shell.Function;
import epf.shell.security.CallerPrincipal;
import epf.shell.security.Credential;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = Query.SEARCH)
@RequestScoped
@Function
public class Search {

	@RestClient
	transient SearchClient search;
	
	@Command(name = "count")
	public int count(
			@ArgGroup(exclusive = true, multiplicity = "1")
			@CallerPrincipal
			final Credential credential,
			@Option(names = {"-txt", "--text"}, description = "Text")
			final String text) {
		try(Response response = search.count(credential.getAuthHeader(), text)){
			return Integer.parseInt(response.getHeaderString(Naming.Query.Client.ENTITY_COUNT));
		}
	}
	
	@Command(name = "fetch")
	public String search(
			@ArgGroup(exclusive = true, multiplicity = "1")
			@CallerPrincipal
			final Credential credential,
			@Option(names = {"-txt", "--text"}, description = "Text")
			final String text,
			@Option(names = {"-f", "--first"}, description = "First Result")
			final Integer firstResult,
			@Option(names = {"-m", "--max"}, description = "Max Results")
			final Integer maxResults) {
		try(Response response = search.search(credential.getAuthHeader(), text, firstResult, maxResults)){
			return response.readEntity(String.class);
		}
	}
}
