/**
 * 
 */
package epf.shell.persistence;

import java.net.URI;
import epf.shell.Function;
import epf.shell.client.ClientUtil;
import epf.util.Var;
import epf.util.client.Client;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
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
	public static final String TOKEN_ARG = "--token";
	/**
	 * 
	 */
	public static final String TOKEN_DESC = "Token";

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
			@Option(names = {"-t", TOKEN_ARG}, description = TOKEN_DESC) 
			final String token,
			@Option(names = {"-u", UNIT_ARG}, description = UNIT_DESC)
			final String unit,
			@Option(names = {"-n", "--name"}, description = "Name")
			final String name, 
			@Option(names = {"-e", "--entity"}, description = "Entity", interactive = true, echo = true)
			final String entity) throws Exception {
		try(Client client = clientUtil.newClient(persistenceUrl.get())){
			client.authorization(token);
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
			@Option(names = {"-t", TOKEN_ARG}, description = TOKEN_DESC) 
			final String token,
			@Option(names = {"-u", UNIT_ARG}, description = UNIT_DESC)
			final String unit,
			@Option(names = {"-n", "--name"}, description = "Name")
			final String name, 
			@Option(names = {"-i", "--id"}, description = "ID")
			final String entityId,
			@Option(names = {"-e", "--entity"}, description = "Entity", interactive = true, echo = true)
			final String entity) throws Exception {
		try(Client client = clientUtil.newClient(persistenceUrl.get())){
			client.authorization(token);
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
			@Option(names = {"-t", TOKEN_ARG}, description = TOKEN_DESC) 
			final String token,
			@Option(names = {"-u", UNIT_ARG}, description = UNIT_DESC)
			final String unit,
			@Option(names = {"-n", "--name"}, description = "Name")
			final String name, 
			@Option(names = {"-i", "--id"}, description = "ID")
			final String entityId) throws Exception {
		try(Client client = clientUtil.newClient(persistenceUrl.get())){
			client.authorization(token);
			epf.client.persistence.Entities.remove(client, unit, name, entityId);
		}
	}
}
