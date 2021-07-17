/**
 * 
 */
package epf.lang.server.event;

import org.eclipse.lsp4j.ExecuteCommandParams;
import epf.lang.util.Call;

/**
 * @author PC
 *
 */
public class ExecuteCommand extends Call<ExecuteCommandParams, Object> {

	/**
	 * @param params
	 */
	public ExecuteCommand(final ExecuteCommandParams params) {
		super(params);
	}

	@Override
	public void run() {
		
	}

}
