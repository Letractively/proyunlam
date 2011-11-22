package ar.com.AmberSoft.iEvenTask.client.menu;

import java.io.Serializable;

public class ColumnDefine implements Serializable {

	private String id;
	private String name;
	private Integer size;
	private Boolean hidden;
	private Integer horizontalAlignment;
	
	public Integer getHorizontalAlignment() {
		return horizontalAlignment;
	}
	public void setHorizontalAlignment(Integer horizontalAlignment) {
		this.horizontalAlignment = horizontalAlignment;
	}
	public Boolean getHidden() {
		return hidden;
	}
	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	
}
