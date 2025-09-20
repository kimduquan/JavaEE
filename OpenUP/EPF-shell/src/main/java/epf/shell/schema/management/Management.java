package epf.shell.schema.management;

import epf.naming.Naming;
import epf.shell.Function;
import epf.shell.security.CallerPrincipal;
import epf.shell.security.Credential;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Command;

@Command(name = Naming.MANAGEMENT)
@RequestScoped
@Function
public class Management {

	@Inject
	transient EntityManager manager;
	
	@Command(name = "create")
	public void generate(
			@ArgGroup(exclusive = true, multiplicity = "1")
			@CallerPrincipal
			final Credential credential) throws Exception {
		manager.getEntityManagerFactory().getSchemaManager().create(true);
	}
}
