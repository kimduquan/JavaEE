/**
 * 
 */
package epf.shell.rules;

import epf.client.rules.admin.Admin;
import epf.shell.Function;
import jakarta.enterprise.context.RequestScoped;
import picocli.CommandLine.Command;

/**
 * @author PC
 *
 */
@Command(name = "rules", subcommands = {Admin.class})
@RequestScoped
@Function
public class Rules {

}
