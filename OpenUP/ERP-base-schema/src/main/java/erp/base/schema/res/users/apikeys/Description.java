package erp.base.schema.res.users.apikeys;

import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "res_users_apikeys_description")
@org.eclipse.microprofile.graphql.Description("API Key Description")
public class Description {
	
	public enum Duration {
		@org.eclipse.microprofile.graphql.Description("1 Day")
		_1,
		@org.eclipse.microprofile.graphql.Description("1 Week")
        _7,
        @org.eclipse.microprofile.graphql.Description("1 Month")
        _30,
        @org.eclipse.microprofile.graphql.Description("3 Months")
        _90,
        @org.eclipse.microprofile.graphql.Description("6 Months")
        _180,
        @org.eclipse.microprofile.graphql.Description("1 Year")
        _365,
        @org.eclipse.microprofile.graphql.Description("Persistent Key")
		_0,
		@org.eclipse.microprofile.graphql.Description("Custom Date")
		__1
	}

	@Id
	private int id;
	
	@Column(nullable = false)
	@NotNull
	@org.eclipse.microprofile.graphql.Description("Description")
	private String name;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@NotNull
	@org.eclipse.microprofile.graphql.Description("Duration")
	private Duration duration;
	
	@Column
	@org.eclipse.microprofile.graphql.Description("Expiration Date")
	private Date expiration_date;

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

	public Duration getDuration() {
		return duration;
	}

	public void setDuration(Duration duration) {
		this.duration = duration;
	}

	public Date getExpiration_date() {
		return expiration_date;
	}

	public void setExpiration_date(Date expiration_date) {
		this.expiration_date = expiration_date;
	}
}
