package erp.base.schema.ir.model;

import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "ir_model_data")
@Description("Model Data")
public class Data {
	
	@Id
	private int id;

	@Column(nullable = false)
	@NotNull
	@Description("External Identifier")
	private String name;
	
	@Transient
	@Description("Complete ID")
	private String complete_name;
	
	@Column(nullable = false)
	@NotNull
	@Description("Model Name")
	private String model;
	
	@Column(nullable = false)
	@NotNull
	@DefaultValue("")
	private String module = "";
	
	@Column
	@Description("Record ID")
	private Integer res_id;
	
	@Column
	@DefaultValue("false")
	@Description("Non Updatable")
	private Boolean noupdate = false;
	
	@Transient
	@Description("Reference")
	private String reference;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getComplete_name() {
		return complete_name;
	}

	public void setComplete_name(String complete_name) {
		this.complete_name = complete_name;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public Integer getRes_id() {
		return res_id;
	}

	public void setRes_id(Integer res_id) {
		this.res_id = res_id;
	}

	public Boolean getNoupdate() {
		return noupdate;
	}

	public void setNoupdate(Boolean noupdate) {
		this.noupdate = noupdate;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}
}
