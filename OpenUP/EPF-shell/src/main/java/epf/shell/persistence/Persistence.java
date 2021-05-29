/**
 * 
 */
package epf.shell.persistence;

import java.net.URI;
import java.util.List;

import javax.ws.rs.core.GenericType;

import epf.client.persistence.Entity;
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
			@Option(names = {"-t", "--token"}, description = "Token") 
			final String token,
			@Option(names = {"-u", "--unit"}, description = "Unit")
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
			@Option(names = {"-t", "--token"}, description = "Token") 
			final String token,
			@Option(names = {"-u", "--unit"}, description = "Unit")
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
			@Option(names = {"-t", "--token"}, description = "Token") 
			final String token,
			@Option(names = {"-u", "--unit"}, description = "Unit")
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
	
	/**
	 * @param token
	 * @param unit
	 * @return
	 * @throws Exception
	 */
	@Command(name = "get-entities")
	public List<Entity> getEntities(
			@Option(names = {"-t", "--token"}, description = "Token") 
			final String token,
			@Option(names = {"-u", "--unit"}, description = "Unit")
			final String unit) throws Exception{
		try(Client client = clientUtil.newClient(persistenceUrl.get())){
			client.authorization(token);
			return epf.client.persistence.Entities.getEntities(client, unit).readEntity(new GenericType<List<Entity>>() {});
		}
	}
}
