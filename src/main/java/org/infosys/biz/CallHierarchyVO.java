package org.infosys.biz;

import java.util.List;

public class CallHierarchyVO {
 String methodName;
 
 List<String> methodInvokes;

public String getMethodName() {
	return methodName;
}

public void setMethodName(String methodName) {
	this.methodName = methodName;
}



public List<String> getMethodInvokes() {
	return methodInvokes;
}

public void setMethodInvokes(List<String> methodInvokes) {
	this.methodInvokes = methodInvokes;
}

@Override
public String toString() {
	return "{methodName:" + methodName + ", methodInvokes:" + methodInvokes + "}";
}
 
 
}
