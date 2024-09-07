package erp.base.schema.ir.actions;

import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import erp.schema.util.EnumAttributeConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 * 
 */
@Entity
@Table(name = "ir_act_client")
@Description("Client Action")
@NodeEntity("Client Action")
public class ActClient extends Actions {
	
	/**
	 * 
	 */
	public enum TargetWindow {
		/**
		 * 
		 */
		current,
		/**
		 * 
		 */
		_new,
		/**
		 * 
		 */
		fullscreen,
		/**
		 * 
		 */
		main
	}
	
	/**
	 * 
	 */
	public class TargetWindowAttributeConverter extends EnumAttributeConverter<TargetWindow> {
		/**
		 * 
		 */
		public TargetWindowAttributeConverter() {
			super(TargetWindow.class, "_", null, null);
		}
	}

	/**
	 * 
	 */
	@Column
	@DefaultValue("ir.actions.client")
	@Property
	private String type = "ir.actions.client";
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@Description("Client action tag")
	@Property
	private String tag;
	
	/**
	 * 
	 */
	@Column
	@Enumerated(EnumType.STRING)
	@Convert(converter = TargetWindowAttributeConverter.class)
	@DefaultValue("current")
	@Description("Target Window")
	@Property
	private TargetWindow target = TargetWindow.current;
	
	/**
	 * 
	 */
	@Column
	@Description("Destination Model")
	@Property
	private String res_model;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@DefaultValue("{}")
	@Description("Context Value")
	@Property
	private String context = "{}";
	
	/**
	 * 
	 */
	@Column
	@Description("Supplementary arguments")
	@Property
	private byte[] params;
	
	/**
	 * 
	 */
	@Column(updatable = false)
	@Description("Params storage")
	@Property
	private byte[] params_store;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public TargetWindow getTarget() {
		return target;
	}

	public void setTarget(TargetWindow target) {
		this.target = target;
	}

	public String getRes_model() {
		return res_model;
	}

	public void setRes_model(String res_model) {
		this.res_model = res_model;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public byte[] getParams() {
		return params;
	}

	public void setParams(byte[] params) {
		this.params = params;
	}

	public byte[] getParams_store() {
		return params_store;
	}

	public void setParams_store(byte[] params_store) {
		this.params_store = params_store;
	}
}