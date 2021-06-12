/**
 * 
 */
package epf.shell.persistence;

import java.net.URI;

import epf.shell.Function;
import epf.shell.client.ClientUtil;
import epf.shell.security.Credential;
import epf.shell.security.CallerPrincipal;
import epf.util.Var;
import epf.util.client.Client;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

/**
 * @author PC
 *
 */
@Command(name = "persistence")
@RequestScoped
@Function
public class Persistence {
	
	/**
	 * 
	 */
	public static final String UNIT_ARG = "--unit";
	/**
	 * 
	 */
	public static final String UNIT_DESC = "Unit";
	/**
	 * 
	 */
	@Inject @Named(epf.client.persistence.Persistence.PERSISTENCE_URL)
	private transient Var<URI> persistenceUrl;
	
	/**
	 * 
	 */
	@Inject
	private transient ClientUtil clientUtil;
	
	/**
	 * @param name
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	@Command(name = "persist")
	public String persist(
			@ArgGroup(exclusive = true, multiplicity = "1")
			@CallerPrincipal
			final Credential credential,
			@Option(names = {"-u", UNIT_ARG}, description = UNIT_DESC)
			final String unit,
			@Option(names = {"-n", "--name"}, description = "Name")
			final String name, 
			@Option(names = {"-e", "--entity"}, description = "Entity", interactive = true, echo = true)
			final String entity) throws Exception {
		try(Client client = clientUtil.newClient(persistenceUrl.get())){
			client.authorization(credential.getToken());
			return epf.client.persistence.Entities.persist(client, unit, name, entity);
		}
	}
	
	/**
	 * @param name
	 * @param id
	 * @param entity
	 * @throws Exception
	 */
	@Command(name = "merge")
	public void merge(
			@ArgGroup(exclusive = true, multiplicity = "1")
			@CallerPrincipal
			final Credential credential,
			@Option(names = {"-u", UNIT_ARG}, description = UNIT_DESC)
			final String unit,
			@Option(names = {"-n", "--name"}, description = "Name")
			final String name, 
			@Option(names = {"-i", "--id"}, description = "ID")
			final String entityId,
			@Option(names = {"-e", "--entity"}, description = "Entity", interactive = true, echo = true)
			final String entity) throws Exception {
		try(Client client = clientUtil.newClient(persistenceUrl.get())){
			client.authorization(credential.getToken());
			epf.client.persistence.Entities.merge(client, unit, name, entityId, entity);
		}
	}
	
	/**
	 * @param token
	 * @param unit
	 * @param name
	 * @param entityId
	 * @throws Exception
	 */
	@Command(name = "remove")
	public void remove(
			@ArgGroup(exclusive = true, multiplicity = "1")
			@CallerPrincipal
			final Credential credential,
			@Option(names = {"-u", UNIT_ARG}, description = UNIT_DESC)
			final String unit,
			@Option(names = {"-n", "--name"}, description = "Name")
			final String name, 
			@Option(names = {"-i", "--id"}, description = "ID")
			final String entityId) throws Exception {
		try(Client client = clientUtil.newClient(persistenceUrl.get())){
			client.authorization(credential.getToken());
			epf.client.persistence.Entities.remove(client, unit, name, entityId);
		}
	}
}
