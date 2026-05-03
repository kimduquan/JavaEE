package epf.workflow.schema;

import java.util.Map;
import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import jakarta.json.bind.annotation.JsonbProperty;

@JsonClassDescription("Configures the iteration over each item (event or message) consumed by a subscription. It encapsulates configuration for processing tasks, output formatting, and export behavior for every item encountered.")
public class SubscriptionIterator {

	@JsonPropertyDescription("The name of the variable used to store the current item being enumerated. Defaults to item.")
	private String item = "item";
	
	@JsonPropertyDescription("The name of the variable used to store the index of the current item being enumerated. Defaults to index.")
	private String at = "index";
	
	@JsonProperty("do")
	@JsonPropertyDescription("The tasks to perform for each consumed item.")
	@JsonbProperty("do")
	private Map<String, Task> do_;
	
	@JsonPropertyDescription("An object, if any, used to customize the item's output and to document its schema.")
	private Output output;
	
	@JsonPropertyDescription("An object, if any, used to customize the content of the workflow context.")
	private Export export;

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getAt() {
		return at;
	}

	public void setAt(String at) {
		this.at = at;
	}

	public Map<String, Task> getDo() {
		return do_;
	}

	public void setDo(Map<String, Task> do_) {
		this.do_ = do_;
	}

	public Output getOutput() {
		return output;
	}

	public void setOutput(Output output) {
		this.output = output;
	}

	public Export getExport() {
		return export;
	}

	public void setExport(Export export) {
		this.export = export;
	}
}
