package org.infosys.bo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.infosys.vo.common.DBProperties;
import org.infosys.vo.common.ERTables;
import org.jdom2.Attribute;
import org.jdom2.DocType;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;



public class OrmGenerator {
	
	List<ERTables> tableList = new ArrayList<ERTables>();
	public void createPOM(String projectName,String path) {
		try {
			Element project = new Element("project");
			Element modelVersion = new Element("modelVersion");
			modelVersion.setText("4.0.0");
			Element groupId = new Element("groupId");
			groupId.setText(projectName);
			Element artifactId = new Element("artifactId");
			artifactId.setText(projectName);
			Element version = new Element("version");
			version.setText("0.0.1-SNAPSHOT");
			Element description = new Element("description");
			description.setText(projectName);
			Document doc = new Document(project);
			project.addContent(modelVersion);
			project.addContent(groupId);
			project.addContent(artifactId);
			project.addContent(version);
			project.addContent(description);
			Element dependencies = new Element("dependencies");
			Element dependency1 = new Element("dependency");
			Element groupId1 = new Element("groupId");
			groupId1.setText("org.javassist");
			Element artifactId1 = new Element("artifactId");
			artifactId1.setText("javassist");
			Element version1 = new Element("version");
			version1.setText("3.18.0-GA");
			dependency1.addContent(groupId1);
			dependency1.addContent(artifactId1);
			dependency1.addContent(version1);
			dependencies.addContent(dependency1);
			
			Element dependency2 = new Element("dependency");
			Element groupId2 = new Element("groupId");
			groupId2.setText("org.hibernate");
			Element artifactId2 = new Element("artifactId");
			artifactId2.setText("hibernate-core");
			Element version2 = new Element("version");
			version2.setText("4.3.6.Final");
			dependency2.addContent(groupId2);
			dependency2.addContent(artifactId2);
			dependency2.addContent(version2);
			dependencies.addContent(dependency2);
			
			Element dependency3 = new Element("dependency");
			Element groupId3 = new Element("groupId");
			groupId3.setText("mysql");
			Element artifactId3 = new Element("artifactId");
			artifactId3.setText("mysql-connector-java");
			Element version3 = new Element("version");
			version3.setText("5.1.10");
			dependency3.addContent(groupId3);
			dependency3.addContent(artifactId3);
			dependency3.addContent(version3);
			dependencies.addContent(dependency3);
			project.addContent(dependencies);
			
			// new XMLOutputter().output(doc, System.out);
			XMLOutputter xmlOutput = new XMLOutputter();
			// display nice nice
			xmlOutput.setFormat(Format.getPrettyFormat());
			String fileName = path + File.separator +  "pom.xml";
			xmlOutput.output(doc, new FileWriter(fileName));
			System.out.println("File Saved!");
		} catch (IOException io) {
			System.out.println(io.getMessage());
		}
	}

	
	public void createXML(DBProperties dbProperties, String path, String packageName) {
		try {
			String header1 = "-//Hibernate/Hibernate Configuration DTD 3.0//EN";
			String header2 = "http://hibernate.sourceforge.net/hibernate-configuration-3.0.dtd";
			Element hibernateConfiguration = new Element("hibernate-configuration");
			DocType dtype = new DocType(hibernateConfiguration.getName());
			dtype.setPublicID(header1);
			dtype.setSystemID(header2);
			Document doc = new Document(hibernateConfiguration, dtype);
			Element sessionFactory = new Element("session-factory");
			Element property1 = new Element("property");
			property1.setAttribute(new Attribute("name", "hibernate.connection.driver_class"));
			Element property6 = new Element("property");
			property6.setAttribute(new Attribute("name", "dialect"));
			if(dbProperties.getDB().equalsIgnoreCase("oracle"))
			{
			property1.setText("oracle.jdbc.driver.OracleDriver");
			property6.setText("org.hibernate.dialect.OracleDialect");
			}
			else if(dbProperties.getDB().equalsIgnoreCase("mysql"))
			{
			property1.setText("com.mysql.jdbc.Driver");
			property6.setText("org.hibernate.dialect.MySQLDialect");
			}
			sessionFactory.addContent(property1);
			Element property2 = new Element("property");
			property2.setAttribute(new Attribute("name", "hibernate.connection.url"));
			property2.setText(dbProperties.getURL());
			sessionFactory.addContent(property2);
			Element property3 = new Element("property");
			property3.setAttribute(new Attribute("name", "hibernate.connection.username"));
			property3.setText(dbProperties.getUserName());
			sessionFactory.addContent(property3);
			Element property4 = new Element("property");
			property4.setAttribute(new Attribute("name", "hibernate.connection.password"));
			property4.setText(dbProperties.getPassword());
			sessionFactory.addContent(property4);
			Element property5 = new Element("property");
			property5.setAttribute(new Attribute("name", "connection.pool_size"));
			property5.setText("1");
			sessionFactory.addContent(property5);
			sessionFactory.addContent(property6);
			
			Element property7 = new Element("property");
			property7.setAttribute(new Attribute("name", "current_session_context_class"));
			property7.setText("thread");
			sessionFactory.addContent(property7);
			
			Element property8 = new Element("property");
			property8.setAttribute(new Attribute("name", "cache.provider_class"));
			property8.setText("org.hibernate.cache.NoCacheProvider");
			sessionFactory.addContent(property8);
			
			
			Element property9 = new Element("property");
			property9.setAttribute(new Attribute("name", "show_sql"));
			property9.setText("true");
			sessionFactory.addContent(property9);
			
			
			Element property10 = new Element("property");
			property10.setAttribute(new Attribute("name", "hbm2ddl.auto"));
			property10.setText("create");
			sessionFactory.addContent(property10);
			
			Iterator<ERTables> tableIterator = tableList.iterator();
			while (tableIterator.hasNext()) {
			ERTables erTables = tableIterator.next();
			Element mapping = new Element("mapping");
			mapping.setAttribute(new Attribute("class", packageName+"."+erTables.getTableName()));
			sessionFactory.addContent(mapping);
			}
			
			hibernateConfiguration.addContent(sessionFactory);
			// new XMLOutputter().output(doc, System.out);
			XMLOutputter xmlOutput = new XMLOutputter();
			// display nice nice
			xmlOutput.setFormat(Format.getPrettyFormat());
			String fileName = path + File.separator  + "hibernate.cfg.xml";
			xmlOutput.output(doc, new FileWriter(fileName));
			System.out.println("File Saved!");
		} catch (IOException io) {
			System.out.println(io.getMessage());
		}
	}

	void createHibernateMain(String path, String packageName) {
		StringBuffer mainBuffer = new StringBuffer();
		mainBuffer.append("package ").append(packageName).append(";").append(System.lineSeparator()).append(System.lineSeparator());
		mainBuffer.append("import org.hibernate.Session;").append(System.lineSeparator());
		mainBuffer.append("public class HibernateMain {").append(System.lineSeparator()).append(System.lineSeparator());
		mainBuffer.append("public static void main(String[] args) {").append(System.lineSeparator()).append(System.lineSeparator());
		mainBuffer.append("Session session = HibernateUtil.getSessionFactory().getCurrentSession();").append(System.lineSeparator());
		mainBuffer.append("session.beginTransaction();").append(System.lineSeparator());
		Iterator<ERTables> tableIterator = tableList.iterator();
		while (tableIterator.hasNext()) {
		ERTables erTables = tableIterator.next();
		String className = erTables.getTableName();
		String classRef = erTables.getTableName().toLowerCase();
		mainBuffer.append(className).append(" ").append(classRef).append(" = ").append(" new").append(" ")
		.append(className).append("();").append(System.lineSeparator());
		Iterator variableIterator = erTables.getAttributes().iterator();
		while(variableIterator.hasNext())
		{
			String[] temp = variableIterator.next().toString().split(" ");
			mainBuffer.append(classRef).append(".").append("set").append(temp[1]).append("(");
			if(temp[0].equalsIgnoreCase("String"))
			{
				mainBuffer.append("\"xyz\"").append(")").append(";").append(System.lineSeparator());	
			}
			else if(temp[0].equalsIgnoreCase("int"))
			{
				mainBuffer.append("1").append(")").append(";").append(System.lineSeparator());
			}
		}
		mainBuffer.append("session.save("+classRef+");").append(System.lineSeparator());
		}
		
		mainBuffer.append("session.getTransaction().commit();").append(System.lineSeparator());
		mainBuffer.append("HibernateUtil.getSessionFactory().close();").append(System.lineSeparator());
		mainBuffer.append("}").append(System.lineSeparator()).append("}");
		createFile(mainBuffer.toString(), "HibernateMain", path);
	}
	
	void createHibernateUtil(String path, String packageName) {
		StringBuffer utilBuffer = new StringBuffer();
		utilBuffer.append("package ").append(packageName).append(";").append(System.lineSeparator()).append(System.lineSeparator());
		utilBuffer.append("import org.hibernate.SessionFactory;").append(System.lineSeparator());
		utilBuffer.append("import org.hibernate.cfg.Configuration;").append(System.lineSeparator());
		utilBuffer.append("import org.hibernate.boot.registry.StandardServiceRegistryBuilder;").append(System.lineSeparator());
		utilBuffer.append("import org.hibernate.service.ServiceRegistry;").append(System.lineSeparator()).append(System.lineSeparator());
		utilBuffer.append("public class HibernateUtil {").append(System.lineSeparator()).append(System.lineSeparator());
		utilBuffer.append("private static final SessionFactory sessionFactory = buildSessionFactory();").append(System.lineSeparator()).append(System.lineSeparator());
		utilBuffer.append("private static SessionFactory buildSessionFactory() {").append(System.lineSeparator());
		utilBuffer.append("try {").append(System.lineSeparator()).append("\t");
		utilBuffer.append("Configuration configuration = new Configuration();").append(System.lineSeparator()).append("\t")
		.append("configuration.configure("+"\"hibernate.cfg.xml"+"\");").append(System.lineSeparator()).append("\t")
		.append("ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();").append("\t")
		.append(System.lineSeparator()).append("SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);").append("\t")
		.append(System.lineSeparator()).append("return sessionFactory;").append(System.lineSeparator());
		//utilBuffer.append("return new AnnotationConfiguration().configure().buildSessionFactory();");
		utilBuffer.append(System.lineSeparator()).append(System.lineSeparator()).append("} catch (Throwable ex) {").append(System.lineSeparator()).append("\t");
		utilBuffer.append("throw new ExceptionInInitializerError(ex);").append(System.lineSeparator()).append("}").append(System.lineSeparator());
		utilBuffer.append("}").append(System.lineSeparator()).append(System.lineSeparator());
		utilBuffer.append("public static SessionFactory getSessionFactory() {").append(System.lineSeparator()).append("\t").append("return sessionFactory;");
		utilBuffer.append(System.lineSeparator()).append("}").append(System.lineSeparator()).append("}").append(System.lineSeparator());
		createFile(utilBuffer.toString(), "HibernateUtil", path);
	}

	
	public List<ERTables> getTableList() {
		return tableList;
	}


	public void setTableList(String jsonString) {
		JsonParserBO parser = new JsonParserBO();
		tableList = parser.getTableList(jsonString);
		this.tableList = tableList;
	}


	void createMappingClasses(String jsonString, String path, String packageName) {
		JsonParserBO parser = new JsonParserBO();
		Iterator<ERTables> tableIterator = tableList.iterator();
		while (tableIterator.hasNext()) {
			ERTables erTables = tableIterator.next();
			StringBuffer classBuffer = new StringBuffer();
			classBuffer.append("package").append(" ").append(packageName).append(";").append(System.lineSeparator()).append(System.lineSeparator());
			classBuffer.append("import").append(" ").append("javax.persistence.Column;").append(System.lineSeparator());
			classBuffer.append("import").append(" ").append("javax.persistence.Entity;").append(System.lineSeparator());
			classBuffer.append("import").append(" ").append("javax.persistence.GeneratedValue;").append(System.lineSeparator());
			classBuffer.append("import").append(" ").append("javax.persistence.Id;").append(System.lineSeparator());
			classBuffer.append("import").append(" ").append("javax.persistence.Table;").append(System.lineSeparator()).append(System.lineSeparator()).append(System.lineSeparator());
			classBuffer.append("@Entity").append(System.lineSeparator());
			classBuffer.append("@Table(name=\""+erTables.getTableName()+"\")").append(System.lineSeparator());
			classBuffer.append("public").append(" ").append("class").append(" ").append(erTables.getTableName()).append("{")
			.append(System.lineSeparator()).append(System.lineSeparator());
			Map<String, String> keyMap = new HashMap<String, String>();
			Iterator keyIterator = erTables.getKeys().iterator();
			while(keyIterator.hasNext())
			{
				String[] temp = keyIterator.next().toString().split(" ");
				keyMap.put(temp[1], temp[0]);
			}
			
			List<String> methodList = new ArrayList<String>();
			Iterator<Map<String, String>> attributeIterator = parser.getAttributeMap(erTables.getAttributes())
					.iterator();
			while (attributeIterator.hasNext()) {
				StringBuffer getter = new StringBuffer();
				StringBuffer setter = new StringBuffer();
				Map<String, String> attributeMap = attributeIterator.next();
				String attrName = attributeMap.get("SimpleName");
				String keyName = keyMap.get(attrName);
				if(null != keyName && (keyName.equalsIgnoreCase("PRIMARYKEY") || keyName.startsWith("pri")))
				{
					classBuffer.append("@Id").append(System.lineSeparator()).append("@GeneratedValue").append(System.lineSeparator());	
				}
				else
				{
					classBuffer.append("@Column(name=\""+attrName+"\")").append(System.lineSeparator());
				}
				String dataType = attributeMap.get("DataType");
				if (null != attributeMap.get("Modifiers") && !attributeMap.get("Modifiers").equalsIgnoreCase(" ")) {
					String modifierArray[] = attributeMap.get("Modifiers").split(":");
					for (String modifier : modifierArray) {
						classBuffer.append(modifier);
						classBuffer.append(" ");
					}
				}

				classBuffer.append(dataType).append(" ").append(attrName);
				if (null != attributeMap.get("Initializer") && !attributeMap.get("Initializer").equalsIgnoreCase(" ")) {
					classBuffer.append(" = ").append(attributeMap.get("Initializer"));
				} else {
					classBuffer.append(";");
				}
				classBuffer.append(System.lineSeparator()).append(System.lineSeparator());
				getter.append("public").append(" ").append(dataType).append(" ").append("get").append(attrName)
						.append("()  {");
				getter.append(System.lineSeparator());
				getter.append("return  ").append(attrName).append(";");
				getter.append(System.lineSeparator());
				getter.append("}");
				methodList.add(getter.toString());
				setter.append("public").append(" ").append("void").append(" ").append("set").append(attrName);
				setter.append("(").append(dataType).append(" ").append(attrName).append(")").append("{");
				setter.append(System.lineSeparator());
				setter.append("this.").append(attrName).append(" = ").append(attrName).append(";");
				setter.append(System.lineSeparator());
				setter.append("}");
				methodList.add(setter.toString());
			}
			Iterator<String> methodIterator = methodList.iterator();
			while (methodIterator.hasNext()) {
				classBuffer.append(methodIterator.next().toString());
				classBuffer.append(System.lineSeparator());
				classBuffer.append(System.lineSeparator());
			}
			classBuffer.append(System.lineSeparator()).append("}");
			createFile(classBuffer.toString(), erTables.getTableName(), path);
		}
	}

	public static void main(String[] args) {

		OrmGenerator generator = new OrmGenerator();
		DBProperties dbProperties = new DBProperties();
		dbProperties.setURL("jdbc:mysql://localhost:3306/UMLWEB");
		dbProperties.setUserName("root");
		dbProperties.setPassword("root");
		String path = "D:\\UMLEngineering\\workspace\\30thMay";
		dbProperties.setProjectPath(path);
		dbProperties.setProjectName("Hiber3");
		dbProperties.setPackageName("com.infy.vo");
		generator.generateORMHibernateCode(dbProperties, "");
	}

	public void createFile(String content, String fileName, String path) {
		try {
			String javaFile = path + File.separator + fileName + ".java";

			File file = new File(javaFile);
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();

			System.out.println("javaFile: " + javaFile + "  created.");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void generateORMHibernateCode(DBProperties dbProperties, String jsonData) {
		setTableList(jsonData);
		String projectName = dbProperties.getProjectName();
		String packageName = dbProperties.getPackageName();
		String path = dbProperties.getProjectPath()  + File.separator + projectName;
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
			System.out.println(path + " Dir Created.");
		}
		createPOM(projectName, path);
		path = path  + File.separator + "src" + File.separator + "main" +File.separator + "java";
		dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
			System.out.println(path + " Dir Created.");
		}
		createXML(dbProperties, path, packageName);
		if(null != packageName && !(" ".equalsIgnoreCase(packageName)))
		{
		String folderName = packageName.replace(".", File.separator);
		path = path + File.separator +folderName;
		}
		else
		{
			path = path + File.separator  + "com.xyz";
		}
		dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
			System.out.println(path + " Dir Created.");
		}
		
		createHibernateUtil(path, packageName);
		createMappingClasses(jsonData, path, packageName);
		createHibernateMain(path, packageName);
	}

}
