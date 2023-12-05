package epf.workflow;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.logging.Level;
import java.util.logging.Logger;
import epf.util.json.ext.JsonUtil;
import epf.util.logging.LogManager;
import jakarta.json.JsonValue;

/**
 * @author PC
 *
 */
public class WorkflowData implements Externalizable {
	
	private transient static final Logger LOGGER = LogManager.getLogger(WorkflowData.class.getName());
	
	/**
	 * 
	 */
	private JsonValue input = JsonValue.NULL;
	/**
	 * 
	 */
	private JsonValue output = JsonValue.NULL;
	
	public JsonValue getInput() {
		return input;
	}
	
	public void setInput(JsonValue input) {
		this.input = input;
	}
	
	public JsonValue getOutput() {
		return output;
	}
	
	public void setOutput(JsonValue output) {
		this.output = output;
	}
	
	@Override
	public void writeExternal(final ObjectOutput out) throws IOException {
		try {
			out.writeUTF(JsonUtil.toJsonValue(this).toString());
		}
		catch(Exception ex) {
			LOGGER.log(Level.SEVERE, "writeExternal", ex);
		}
	}
	@Override
	public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
		try {
			final String string = in.readUTF();
			final WorkflowData workflowData = JsonUtil.fromJson(string, WorkflowData.class);
			this.input = workflowData.input;
			this.output = workflowData.output;
		}
		catch(Exception ex) {
			LOGGER.log(Level.SEVERE, "readExternal", ex);
		}
	}
}
