package org.infosys.vo.common;

import java.util.List;

public class TableMembers {

	private String name;
	
	private List<ColumnMembers> columnList;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ColumnMembers> getColumnList() {
		return columnList;
	}

	public void setColumnList(List<ColumnMembers> columnList) {
		this.columnList = columnList;
	}

	@Override
	public String toString() {
		return "TableMembers [name=" + name + ", columnList=" + columnList + "]";
	}
	
	
	
}
