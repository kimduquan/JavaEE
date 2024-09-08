package erp.base.schema.ir.cron;

import org.eclipse.microprofile.graphql.Description;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.Transient;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
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
	@jakarta.persistence.Id
	@Id
	private int id;

	/**
	 * 
	 */
	@Column
	@Transient
	private Integer cron_id;
	
	/**
	 * 
	 */
	@ManyToOne(targetEntity = Cron.class)
	@JoinColumn(name = "cron_id")
	@Relationship(type = "CRON")
	private Cron cron;
	
	/**
	 * 
	 */
	@Column
	@Property
	private String call_at;

	public Integer getCron_id() {
		return cron_id;
	}

	public void setCron_id(Integer cron_id) {
		this.cron_id = cron_id;
	}

	public String getCall_at() {
		return call_at;
	}

	public void setCall_at(String call_at) {
		this.call_at = call_at;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Cron getCron() {
		return cron;
	}

	public void setCron(Cron cron) {
		this.cron = cron;
	}
}
