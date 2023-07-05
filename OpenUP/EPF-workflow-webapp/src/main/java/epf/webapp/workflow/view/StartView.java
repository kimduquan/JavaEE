package epf.webapp.workflow.view;

import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import epf.json.schema.JsonSchema;
import epf.webapp.naming.Naming;

/**
 * @author PC
 *
 */
@ViewScoped
@Named(Naming.Workflow.WORKFLOW_START)
public class StartView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private String workflow;
	
	/**
	 * 
	 */
	private String version;
	
	/**
	 * 
	 */
	private JsonSchema schema;

	public JsonSchema getSchema() {
		return schema;
	}

	public void setSchema(final JsonSchema schema) {
		this.schema = schema;
	}

	public String getWorkflow() {
		return workflow;
	}

	public void setWorkflow(final String workflow) {
		this.workflow = workflow;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(final String version) {
		this.version = version;
	}
}
