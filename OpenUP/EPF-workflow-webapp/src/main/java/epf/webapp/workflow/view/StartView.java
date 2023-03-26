package epf.webapp.workflow.view;

import java.io.Serializable;
import javax.faces.view.ViewScoped;
import epf.json.schema.JsonSchema;

/**
 * @author PC
 *
 */
@ViewScoped
public class StartView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
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
}
