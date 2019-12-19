package org.infosys.vo.json;

public class Relations {

private String id;

private String type;

private String sourceId;

private String targetId;

private String targetName;

public String getType() {
	return type;
}

public void setType(String type) {
	this.type = type;
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

public String getId() {
	return id;
}

public void setId(String id) {
	this.id = id;
}

public String getTargetName() {
	return targetName;
}

public void setTargetName(String targetName) {
	this.targetName = targetName;
}

@Override
public String toString() {
	return "Relations [id=" + id + ", type=" + type + ", sourceId=" + sourceId + ", targetId=" + targetId
			+ ", targetName=" + targetName + "]";
}




}
