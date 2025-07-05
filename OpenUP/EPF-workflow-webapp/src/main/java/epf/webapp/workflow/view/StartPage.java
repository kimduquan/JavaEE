package epf.webapp.workflow.view;

import java.io.InputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.json.JsonObject;
import jakarta.json.JsonValue;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.Response;
import epf.naming.Naming.Workflow.Schema;
import epf.client.util.Client;
import epf.json.schema.JsonSchema;
import epf.util.json.ext.JsonUtil;
import epf.util.logging.LogManager;
import epf.webapp.internal.GatewayUtil;
import epf.webapp.internal.Session;
import epf.webapp.naming.Naming;
import epf.webapp.workflow.client.Management;
import epf.webapp.workflow.client.Workflow;
import epf.workflow.view.StartView;

@ViewScoped
@Named(Naming.Workflow.WORKFLOW_START)
public class StartPage implements StartView, Serializable {

	private static final long serialVersionUID = 1L;
	
	private static transient final Logger LOGGER = LogManager.getLogger(StartPage.class.getName());
	
	private String workflow;
	
	private String version;
	
	private JsonSchema schema;
	
	private JsonValue input;
	
	private JsonValue output;
	
	@Inject
	private transient GatewayUtil gateway;
	
	@Inject
	private transient Session session;
	
	@Inject
	private HttpServletRequest request;

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

	public JsonValue getInput() {
		return input;
	}

	public void setInput(final JsonValue input) {
		this.input = input;
	}

	public JsonValue getOutput() {
		return output;
	}

	public void setOutput(final JsonValue output) {
		this.output = output;
	}
	
	@PostConstruct
	protected void postConstruct() {
		workflow = request.getParameter("workflow");
		version = request.getParameter("version");
		try(Client client = gateway.newClient(epf.naming.Naming.WORKFLOW)){
			client.authorization(session.getToken());
			try(Response response = Management.getWorkflowDefinition(client, workflow, version)){
				try(InputStream stream = response.readEntity(InputStream.class)){
					final JsonObject data = JsonUtil.readObject(stream);
					final String dataInputSchema = data.get(Schema.DATA_INPUT_SCHEMA).toString();
					schema = JsonUtil.fromJson(dataInputSchema, JsonSchema.class);
				}
			}
		}
		catch (Exception e) {
			LOGGER.log(Level.SEVERE, "postConstruct", e);
		}
	}

	@Override
	public String start() throws Exception {
		try(Client client = gateway.newClient(epf.naming.Naming.WORKFLOW)){
			client.authorization(session.getToken());
			try(Response response = Workflow.start(client, workflow, version, input)){
				try(InputStream stream = response.readEntity(InputStream.class)){
					output = JsonUtil.readValue(stream);
				}
			}
		}
		return "";
	}
}
