package ${basepackage}.domain.dto;


public class ResouceActionDTO extends BaseDTO {
	private Integer resourceId;
	private String divId;
	private String actionScript;
	private String name;
	private String actionAlias;
	private String description;
	public Integer getResourceId() {
		return resourceId;
	}
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
	public String getDivId() {
		return divId;
	}
	public void setDivId(String divId) {
		this.divId = divId;
	}
	public String getActionScript() {
		return actionScript;
	}
	public void setActionScript(String actionScript) {
		this.actionScript = actionScript;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getActionAlias() {
		return actionAlias;
	}
	public void setActionAlias(String actionAlias) {
		this.actionAlias = actionAlias;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	

}
