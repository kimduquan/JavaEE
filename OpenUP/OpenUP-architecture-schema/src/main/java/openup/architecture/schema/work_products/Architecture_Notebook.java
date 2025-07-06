package openup.architecture.schema.work_products;

public class Architecture_Notebook {
	
	private String purpose;
	
	private String responsible;
	
	private String modifiedBy;
	
	private String mainDescription;
	
	private String briefOutline;

	public String getResponsible() {
		return responsible;
	}

	public void setResponsible(final String responsible) {
		this.responsible = responsible;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(final String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(final String purpose) {
		this.purpose = purpose;
	}

	public String getMainDescription() {
		return mainDescription;
	}

	public void setMainDescription(final String mainDescription) {
		this.mainDescription = mainDescription;
	}

	public String getBriefOutline() {
		return briefOutline;
	}

	public void setBriefOutline(final String briefOutline) {
		this.briefOutline = briefOutline;
	}
}
