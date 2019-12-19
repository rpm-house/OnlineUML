package org.infosys.vo.json;

import java.util.List;
import java.util.Map;

public class JavaJsonMapper {

	String type;
	
	String name;
	
	private Map<String, String> nameIdMap;
	
	List<String> attributes;
	
	private List<Map<String,String>> attributeList;
	
	List<String> methods;
	
	private List<Map<String,String>> methodList;
	
	Position position;
	
	Size size;
	
	int angle;
	
	String id;
	
	int z;
	
	Attrs attrs;
	
	private Source source;

	private Target target;
	
	private List<String> importList;
	
	private String classModifiers;
	
	private String packageName;
	
	private String superClass;

	private String superInterfaces;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public Size getSize() {
		return size;
	}

	public void setSize(Size size) {
		this.size = size;
	}

	public int getAngle() {
		return angle;
	}

	public void setAngle(int angle) {
		this.angle = angle;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}

	public Attrs getAttrs() {
		return attrs;
	}

	public void setAttrs(Attrs attrs) {
		this.attrs = attrs;
	}

	public List<String> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<String> attributes) {
		this.attributes = attributes;
	}

	public List<String> getMethods() {
		return methods;
	}

	public void setMethods(List<String> methods) {
		this.methods = methods;
	}

	public Source getSource() {
		return source;
	}

	public void setSource(Source source) {
		this.source = source;
	}

	public Target getTarget() {
		return target;
	}

	public void setTarget(Target target) {
		this.target = target;
	}

	

	public List<Map<String, String>> getMethodList() {
		return methodList;
	}

	public void setMethodList(List<Map<String, String>> methodList) {
		this.methodList = methodList;
	}

	public List<Map<String, String>> getAttributeList() {
		return attributeList;
	}

	public void setAttributeList(List<Map<String, String>> attributeList) {
		this.attributeList = attributeList;
	}

	public List<String> getImportList() {
		return importList;
	}

	public void setImportList(List<String> importList) {
		this.importList = importList;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	

	public String getClassModifiers() {
		return classModifiers;
	}

	public void setClassModifiers(String classModifiers) {
		this.classModifiers = classModifiers;
	}

	public String getSuperClass() {
		return superClass;
	}

	public void setSuperClass(String superClass) {
		this.superClass = superClass;
	}

	

	public String getSuperInterfaces() {
		return superInterfaces;
	}

	public void setSuperInterfaces(String superInterfaces) {
		this.superInterfaces = superInterfaces;
	}

	public Map<String, String> getNameIdMap() {
		return nameIdMap;
	}

	public void setNameIdMap(Map<String, String> nameIdMap) {
		this.nameIdMap = nameIdMap;
	}

	@Override
	public String toString() {
		return "JavaJsonMapper [type=" + type + ", name=" + name + ", nameIdMap=" + nameIdMap + ", attributes="
				+ attributes + ", attributeList=" + attributeList + ", methods=" + methods + ", methodList="
				+ methodList + ", position=" + position + ", size=" + size + ", angle=" + angle + ", id=" + id + ", z="
				+ z + ", attrs=" + attrs + ", source=" + source + ", target=" + target + ", importList=" + importList
				+ ", classModifiers=" + classModifiers + ", packageName=" + packageName + ", superClass=" + superClass
				+ ", superInterfaces=" + superInterfaces + "]";
	}

	

	

	
}
