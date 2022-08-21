package epf.shell;

import epf.shell.file.FileStore;
import epf.shell.image.Image;
import epf.shell.messaging.Messaging;
import epf.shell.persistence.Persistence;
import epf.shell.query.Query;
import epf.shell.rules.Rules;
import epf.shell.schema.Schema;
import epf.shell.security.Security;
import epf.shell.util.Utility;
import io.quarkus.picocli.runtime.annotations.TopCommand;
import picocli.CommandLine.Command;
import javax.enterprise.context.ApplicationScoped;

/**
 * @author PC
 *
 */
@TopCommand
@Command(name = "epf", subcommands = {Security.class, Persistence.class, Query.class, Schema.class, FileStore.class, Rules.class, Utility.class, Image.class, Messaging.class})
@ApplicationScoped
public class EPFCommand {
	
}
