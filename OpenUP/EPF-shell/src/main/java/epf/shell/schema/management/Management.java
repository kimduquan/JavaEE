package epf.shell.schema.management;

import epf.naming.Naming;
import epf.shell.Function;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import picocli.CommandLine.Command;

@Command(name = Naming.MANAGEMENT)
@RequestScoped
@Function
public class Management {

	@Inject
	transient EntityManager manager;
}
