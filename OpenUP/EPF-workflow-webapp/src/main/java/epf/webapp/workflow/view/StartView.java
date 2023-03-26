package epf.webapp.workflow.view;

import java.io.Serializable;
import java.util.List;
import java.util.Map.Entry;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import epf.json.schema.JsonSchema;
import epf.json.schema.Value;
import epf.util.MapUtil;
import epf.util.StringComparator;
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
	private JsonSchema schema;
	
	/**
	 * 
	 */
	private List<Entry<String, Value>> properties;

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

	public List<Entry<String, Value>> getProperties() {
		if(properties == null) {
			properties = MapUtil.entrySet(schema.getProperties(), new StringComparator());
		}
		return properties;
	}

	public void setProperties(List<Entry<String, Value>> properties) {
		this.properties = properties;
	}
}
