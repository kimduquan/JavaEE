package erp.base.schema.ir.cron;

import org.eclipse.microprofile.graphql.Description;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * 
 */
@Entity
@Table(name = "ir_cron_trigger")
@Description("Triggered actions")
@NodeEntity("Triggered actions")
public class Trigger {

	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = Cron.class)
	@Property
	@Relationship(type = "CRON")
	private String cron_id;
	
	/**
	 * 
	 */
	@Column
	@Property
	private String call_at;

	public String getCron_id() {
		return cron_id;
	}

	public void setCron_id(String cron_id) {
		this.cron_id = cron_id;
	}

	public String getCall_at() {
		return call_at;
	}

	public void setCall_at(String call_at) {
		this.call_at = call_at;
	}
}
