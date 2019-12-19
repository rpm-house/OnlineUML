package org.infosys.vo.common;

public class ColumnMembers {

	private String name;
	
	private String type;
	
	private String key;
	
	private boolean isPrimaryKey;
	
	private boolean isForeignKey;
	
	private boolean isNotNull;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public boolean isPrimaryKey() {
		return isPrimaryKey;
	}

	public void setPrimaryKey(boolean isPrimaryKey) {
		this.isPrimaryKey = isPrimaryKey;
	}

	public boolean isForeignKey() {
		return isForeignKey;
	}

	public void setForeignKey(boolean isForeignKey) {
		this.isForeignKey = isForeignKey;
	}

	public boolean isNotNull() {
		return isNotNull;
	}

	public void setNotNull(boolean isNotNull) {
		this.isNotNull = isNotNull;
	}

	@Override
	public String toString() {
		return "ColumnMembers [name=" + name + ", type=" + type + ", key=" + key + ", isPrimaryKey=" + isPrimaryKey
				+ ", isForeignKey=" + isForeignKey + ", isNotNull=" + isNotNull + "]";
	}

	
}
