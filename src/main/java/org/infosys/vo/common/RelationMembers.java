package org.infosys.vo.common;

public class RelationMembers {

	String className;

	String type;
	
	String id;
	
	String sourceId;
	
	String targetId;

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	@Override
	public String toString() {
		return "RelationMembers [className=" + className + ", type=" + type + ", id=" + id + ", sourceId=" + sourceId
				+ ", targetId=" + targetId + "]";
	}

	
}
