/**
 * 
 */
package epf.shell.rules;

import epf.shell.Function;
import epf.shell.rules.admin.Admin;
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
