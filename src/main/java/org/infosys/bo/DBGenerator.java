package org.infosys.bo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.infosys.vo.common.ColumnMembers;
import org.infosys.vo.common.DBProperties;
import org.infosys.vo.common.ERTables;
import org.infosys.vo.common.TableMembers;

import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;
import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;

public class DBGenerator {
	public Connection conn = null;
	private Statement statement = null;
	PreparedStatement preparedStatement = null;
	OrmGenerator generator = new OrmGenerator();

	public String generateDB(DBProperties dbProperties, String jsonData) {
		JsonParserBO parser = new JsonParserBO();
		conn = getConnection(dbProperties);
		if (null != conn) {
			List<ERTables> tableList = parser.getTableList(jsonData);
			List<String> queryList = queryForCreateTable(tableList);
			for (String query : queryList) {
				try {
					runSql2(query);
					dbProperties.setStatusMessage("Table/s have been created successfully...");
					closeResources();
				} catch (MySQLSyntaxErrorException e) {
					e.printStackTrace();
					dbProperties.setStatusMessage(e.getMessage());
				} catch (SQLException e) {
					e.printStackTrace();
					dbProperties.setStatusMessage(e.getMessage());
				}
			}
		}
		return dbProperties.getStatusMessage();

	}

	private List<String> queryForCreateTable(List<ERTables> tableList) {
		List<String> queryList = new ArrayList<String>();
		List<String> primaryKeyList;
		List<String> foriegnKeyList;
		StringBuffer createQuery = null;
		String[] temp;
		String delimiter = " ";
		Iterator<ERTables> iterator = tableList.iterator();
		while (iterator.hasNext()) {
			createQuery = new StringBuffer();
			primaryKeyList = new ArrayList<String>();
			foriegnKeyList = new ArrayList<String>();
			ERTables erTables = iterator.next();
			createQuery.append("CREATE TABLE  ").append(erTables.getTableName()).append(" ( ");
			Iterator<String> attributeIterator = erTables.getAttributes().iterator();
			while (attributeIterator.hasNext()) {
				createQuery.append(" ");
				String column = attributeIterator.next();
				temp = column.split(delimiter);
				createQuery.append(temp[1]).append(" ").append(temp[0]);
			}

			if (!erTables.getKeys().isEmpty()) {
				Iterator<String> keyIterator = erTables.getKeys().iterator();
				while (keyIterator.hasNext()) {
					String key = keyIterator.next();
					temp = key.split(delimiter);
					if (temp[0].trim().equalsIgnoreCase("Primary")) {
						primaryKeyList.add(temp[1]);
					} else if (temp[0].trim().equalsIgnoreCase("Foriegn")) {
						foriegnKeyList.add(temp[1]);
					}
				}
				int pKey = 0;
				int fKey = 0;
				Iterator<String> pKeyIterator = primaryKeyList.iterator();
				while (pKeyIterator.hasNext()) {
					createQuery.append(" , ");
					if (pKey == 0) {
						createQuery.append("PRIMARY KEY (").append(pKeyIterator.next());
					} else {
						createQuery.append(pKeyIterator.next());
					}

					pKey++;
				}
				if (0 != primaryKeyList.size()) {
					createQuery.append(" ) ");
				}
				Iterator<String> fKeyIterator = foriegnKeyList.iterator();
				while (fKeyIterator.hasNext()) {
					createQuery.append("  , ");
					if (fKey == 0) {
						createQuery.append("Foriegn KEY (").append(fKeyIterator.next());
					} else {
						createQuery.append(fKeyIterator.next());
					}

					fKey++;
				}
				if (0 != foriegnKeyList.size()) {
					createQuery.append(" ) ");
				}
			}
			createQuery.append(" ); ");
			System.out.println("createQuery : " + createQuery);
			queryList.add(createQuery.toString());
		}

		System.out.println("createQueryList : " + queryList);
		return queryList;

	}

	public List<String> getTableList(DBProperties dbProperties) {
		String schemaName = "UMLWEB";
		List<String> tableList = new ArrayList<String>();
		String getTables = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE = 'BASE TABLE' AND TABLE_SCHEMA='"
				+ schemaName + "'";
		String all = "SELECT table_name, column_name, data_type, data_length FROM USER_TAB_COLUMNS WHERE table_name = 'MYTABLE'";
		try {
			conn = getConnection(dbProperties);
			statement = conn.createStatement();
			ResultSet rs = runSql(getTables);
			while (rs.next()) {
				tableList.add(rs.getString("TABLE_NAME"));
			}
			if (!tableList.isEmpty()) {
				dbProperties.setStatusMessage("Tables are there..");
			}
		} catch (SQLException e) {
			dbProperties.setStatusMessage(e.getMessage());
			e.printStackTrace();
		} finally {
			closeResources();
		}
		return tableList;
	}

	public List<TableMembers> getTables(DBProperties dbProperties) {
		List<TableMembers> tableMemberList = new ArrayList<TableMembers>();
		List<ColumnMembers> columnMemberList = null;
		TableMembers tableMembers = null;
		ColumnMembers columnMembers;
		String schemaName = "UMLWEB";
		String tableName = "";
		String getTableDetails = "SELECT TABLE_NAME, COLUMN_NAME, COLUMN_TYPE, COLUMN_KEY FROM  INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = ? ";
		try {
			conn = getConnection(dbProperties);
			preparedStatement = conn.prepareStatement(getTableDetails);
			preparedStatement.setString(1, schemaName);
			ResultSet rs = preparedStatement.executeQuery(); 
			
			while (rs.next()) {
				columnMembers = new ColumnMembers();
				columnMembers.setName(rs.getString("COLUMN_NAME"));
				columnMembers.setType(rs.getString("COLUMN_TYPE"));
				columnMembers.setKey(rs.getString("COLUMN_KEY"));
				tableName = rs.getString("TABLE_NAME");
				if(null == tableMembers || !(tableName.equalsIgnoreCase(tableMembers.getName())))
				{
				tableMembers = new TableMembers();
				columnMemberList = new ArrayList<ColumnMembers>();
				tableMembers.setName(tableName);
				}
				columnMemberList.add(columnMembers);
				tableMembers.setColumnList(columnMemberList);
				if(!tableMemberList.contains(tableMembers))
				{
				tableMemberList.add(tableMembers);
				}
			}
		} catch (SQLException e) {
			dbProperties.setStatusMessage(e.getMessage());
			e.printStackTrace();
		} finally {
			closeResources();
		}
		return tableMemberList;
	}

	public Connection getConnection(DBProperties dbProperties) {
		try {
			if (null == conn || conn.isClosed()) {
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection(dbProperties.getURL(), dbProperties.getUserName(),
						dbProperties.getPassword());
				System.out.println("connection built");
			}
		} catch (CommunicationsException e) {
			e.printStackTrace();
			dbProperties.setStatusMessage(e.getMessage());
		} catch (SQLException e) {
			dbProperties.setStatusMessage(e.getMessage());
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			dbProperties.setStatusMessage(e.getMessage());
			e.printStackTrace();
		}

		return conn;
	}

	public ResultSet runSql(String sql) throws SQLException {
		statement = conn.createStatement();
		return statement.executeQuery(sql);
	}

	public ResultSet runSql1(String sql) throws SQLException {
		preparedStatement = conn.prepareStatement(sql);
		return preparedStatement.executeQuery();
	}

	public boolean runSql2(String sql) throws SQLException {
		statement = conn.createStatement();
		return statement.execute(sql);
	}

	public void closeResources() {
		try {
			if (null != conn) {
				conn.close();
			}
			if (statement != null) {
				statement.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		DBGenerator dbGenerator = new DBGenerator();
		DBProperties dbProperties = new DBProperties();
		dbProperties.setURL("jdbc:mysql://localhost:3306/UMLWEB");
		dbProperties.setUserName("root");
		dbProperties.setPassword("root");
		// testTableList(dbGenerator, dbProperties);
		List<TableMembers> tabDetailsList = dbGenerator.getTables(dbProperties);
		Iterator tableDetailsIterator = tabDetailsList.iterator();
		while (tableDetailsIterator.hasNext()) {
			TableMembers tableMembers = (TableMembers) tableDetailsIterator.next();
			System.out.println("Table: " + tableMembers.getName());
			Iterator columnIterator = tableMembers.getColumnList().iterator();
			while (columnIterator.hasNext()) {
				ColumnMembers columnMembers = (ColumnMembers) columnIterator.next();
				System.out.println("column: " + columnMembers.getName());
				System.out.println("type: " + columnMembers.getType());
				System.out.println("Key: " + columnMembers.getKey());
			}
		}
	}

	private List<String> testTableList(DBGenerator dbGenerator, DBProperties dbProperties) {
		List<String> tabList = dbGenerator.getTableList(dbProperties);
		System.out.println("Size : " + tabList.size());
		Iterator tableIterator = tabList.iterator();
		while (tableIterator.hasNext()) {
			System.out.println("Table: " + tableIterator.next());
		}
		return tabList;
	}
}
