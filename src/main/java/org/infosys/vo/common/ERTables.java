package org.infosys.vo.common;

import java.util.List;

public class ERTables {
	
	String tableName;
	
	List<String> attributes;
	
	List<String> keys;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public List<String> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<String> attributes) {
		this.attributes = attributes;
	}

	public List<String> getKeys() {
		return keys;
	}

	public void setKeys(List<String> keys) {
		this.keys = keys;
	}

	@Override
	public String toString() {
		return "ERTables [tableName=" + tableName + ", attributes=" + attributes + ", keys=" + keys + "]";
	}
	
	
}
