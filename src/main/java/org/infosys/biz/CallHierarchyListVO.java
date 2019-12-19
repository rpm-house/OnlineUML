package org.infosys.biz;

import java.util.ArrayList;
import java.util.List;

public class CallHierarchyListVO {

	List<CallHierarchyVO> ListMethods = new ArrayList<CallHierarchyVO>();

	public List<CallHierarchyVO> getListMethods() {
		return ListMethods;
	}

	public void setListMethods(List<CallHierarchyVO> listMethods) {
		ListMethods = listMethods;
	}

	@Override
	public String toString() {
		return "{ListMethods:" + ListMethods + "}";
	}
	
}
