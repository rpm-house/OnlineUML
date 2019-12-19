package org.infosys.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.infosys.vo.common.User;
import org.springframework.stereotype.Repository;

@Repository
public class BaseDAO {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/UMLWEB";
	static final String USER = "root";
	static final String PASS = "root";
	Connection conn = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet;

	public User saveUser(User user) {
		int count = 0;
		String oneTimePassowrd = getRandomNumber();
		String saveUser = "INSERT INTO UML_USER"
				+ "(USER_NAME,PASSWORD, MOBILE, EMAIL, WORKSPACE, ONE_TIME_PASSWORD, CREATED_BY, CREATED_DATE, EXPIRY_DATE) VALUES"
				+ "(?,?,?,?,?,?,?,?,?)";

		try {
			conn = getConnection();
			preparedStatement = conn.prepareStatement(saveUser);
			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getPassword());
			preparedStatement.setString(3, user.getMobile());
			preparedStatement.setString(4, user.getEmail());
			preparedStatement.setString(5, user.getWorkspace());
			preparedStatement.setString(6, oneTimePassowrd);
			preparedStatement.setString(7, "SYSTEM");
			preparedStatement.setString(8, getCurrentDate());
			preparedStatement.setString(9, getExpiryDate(1));
			System.out.println(preparedStatement);
			count = preparedStatement.executeUpdate();
			if (count > 0) {
				user.setOneTimePassword(oneTimePassowrd);
				user.setStatusCode(1);
			} else {
				user.setStatusCode(0);
				user.setStatusMessage("Something Went Wrong! Sorry!");
			}
		} catch (SQLException e) {
			user.setStatusCode(0);
			user.setStatusMessage(e.getMessage());
			e.printStackTrace();
		} finally {
			closeResources();
		}
		return user;
	}

	public User updatePassword(User user) {
		int count = 0;
		String saveUser = "UPDATE UML_USER SET PASSWORD = ?, ONE_TIME_PASSWORD = ? WHERE PASSWORD = ? AND EMAIL =?";
		String oneTimePassowrd = getRandomNumber();
		try {
			conn = getConnection();
			preparedStatement = conn.prepareStatement(saveUser);
			preparedStatement.setString(1, user.getNewPassword());
			preparedStatement.setString(2, oneTimePassowrd);
			preparedStatement.setString(3, user.getOldPassword());
			preparedStatement.setString(4, user.getRecoveryMail());
			count = preparedStatement.executeUpdate();
			if (count > 0) {
				user.setOneTimePassword(oneTimePassowrd);
				user.setStatusCode(1);
			} else {
				user.setStatusCode(0);
				user.setStatusMessage("Wrong data given");
			}

		} catch (SQLException e) {
			user.setStatusCode(0);
			user.setStatusMessage(e.getMessage());
			e.printStackTrace();
		} finally {
			closeResources();
		}
		return user;
	}

	public User savePassword(User user) {
		int count = 0;
		String saveUser = "UPDATE UML_USER SET PASSWORD = ?, ONE_TIME_PASSWORD = ? WHERE MOBILE = ? AND EMAIL =?";
		String oneTimePassowrd = getRandomNumber();
		try {
			conn = getConnection();
			preparedStatement = conn.prepareStatement(saveUser);
			preparedStatement.setString(1, user.getNewPassword());
			preparedStatement.setString(2, oneTimePassowrd);
			preparedStatement.setString(3, user.getRecoveryMobile());
			preparedStatement.setString(4, user.getRecoveryMail());
			count = preparedStatement.executeUpdate();
			if (count > 0) {
				user.setOneTimePassword(oneTimePassowrd);
				user.setStatusCode(1);
			} else {
				user.setStatusCode(0);
				user.setStatusMessage("Wrong data given");
			}
		} catch (SQLException e) {
			user.setStatusCode(0);
			user.setStatusMessage(e.getMessage());
			e.printStackTrace();
		} finally {
			closeResources();
		}
		return user;
	}

	public User getUserDetails(User user) {
		String getUser = null;
		String cond = null;
		String expDate = null;
		boolean userExist = false;
		if (null != user.getLoginMail() && !(user.getLoginMail().equalsIgnoreCase(""))) {
			getUser = "SELECT * FROM UML_USER WHERE EMAIL = ? AND PASSWORD = ?";
			cond = user.getLoginMail();
		} else {
			getUser = "SELECT * FROM UML_USER WHERE MOBILE = ? AND PASSWORD = ?";
			cond = user.getLoginMobile();
		}
		try {
			conn = getConnection();
			preparedStatement = conn.prepareStatement(getUser);
			preparedStatement.setString(1, cond);
			preparedStatement.setString(2, user.getLoginPassword());
			System.out.println("prepared : " + preparedStatement.toString());
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				user.setName(resultSet.getString("USER_NAME"));
				user.setMobile(resultSet.getString("MOBILE"));
				user.setEmail(resultSet.getString("EMAIL"));
				user.setWorkspace(resultSet.getString("WORKSPACE"));
				expDate = resultSet.getString("EXPIRY_DATE");
				userExist = true;
			}
			if (userExist && isValidPeriod(expDate)) {
				user.setStatusCode(1);
			} else if (userExist && !isValidPeriod(expDate)) {
				user.setStatusCode(2);
				user.setStatusMessage("User's Trail Period Over!");
			} else if (!userExist) {
				user.setStatusCode(0);
				user.setStatusMessage("Not Valid User!");
			}
		} catch (SQLException e) {
			user.setStatusCode(0);
			user.setStatusMessage(e.getMessage());
			e.printStackTrace();
		} finally {
			closeResources();
		}
		return user;
	}

	public User verifyOneTimePassword(User user) {
		String getUser = null;
		int count = 0;
		getUser = "SELECT COUNT(1) FROM UML_USER WHERE EMAIL = ? AND ONE_TIME_PASSWORD = ?";
		try {
			conn = getConnection();
			preparedStatement = conn.prepareStatement(getUser);
			preparedStatement.setString(1, user.getEmail());
			preparedStatement.setString(2, user.getOneTimePassword());
			System.out.println("prepared : " + preparedStatement.toString());
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				count = resultSet.getInt(1);
			}
			if (count == 1) {
				user.setStatusCode(1);
			} else {
				user.setStatusCode(0);
				user.setStatusMessage("One Time Password is InCorrect!");
			}
		} catch (SQLException e) {
			user.setStatusCode(0);
			user.setStatusMessage(e.getMessage());
			e.printStackTrace();
		} finally {
			closeResources();
		}
		return user;
	}

	private Connection getConnection() {
		try {
			if (null == conn || conn.isClosed()) {
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection(DB_URL, USER, PASS);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return conn;
	}

	private void closeResources() {
		try {
			if (null != preparedStatement)
				preparedStatement.close();
			if (null != resultSet)
				resultSet.close();
			if (null != conn)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * private static java.sql.Timestamp getCurrentTimeStamp() {
	 * 
	 * java.util.Date today = new java.util.Date(); return new
	 * java.sql.Timestamp(today.getTime());
	 * 
	 * }
	 * 
	 * private Long dayToMiliseconds(int days) { Long result = Long.valueOf(days
	 * * 24 * 60 * 60 * 1000); return result; }
	 */

	public String getRandomNumber() {
		int randomPIN = (int) (Math.random() * 9000) + 1000;
		return String.valueOf(randomPIN);
	}

	public static void main(String[] args) {
		BaseDAO baseDAO = new BaseDAO();
		// System.out.println(baseDAO.getRandomNumber());
		// Long miliseconds = baseDAO.dayToMiliseconds(30);

		/*
		 * System.out.println(baseDAO.getCurrentTimeStamp());
		 * System.out.println(new
		 * Timestamp(baseDAO.getCurrentTimeStamp().getTime() + miliseconds));
		 */
		String currentDate = baseDAO.getCurrentDate();
		String expiryDate = baseDAO.getExpiryDate(1);
		System.out.println(currentDate);
		System.out.println(expiryDate);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
		try {
			Date start = new SimpleDateFormat("dd-M-yyyy", Locale.ENGLISH)
                    .parse(currentDate);
            Date end = new SimpleDateFormat("dd-M-yyyy", Locale.ENGLISH)
                    .parse(expiryDate);

            System.out.println(start);
            System.out.println(end);
		}
		 catch (ParseException e) {
				e.printStackTrace();
			}
		System.out.println(baseDAO.isValidPeriod("20-5-2016"));

	}

	private String getCurrentDate() {
		Calendar now = Calendar.getInstance();
		String currentDate = now.get(Calendar.DATE) + "-" + (now.get(Calendar.MONTH) + 1) + "-"
				+ now.get(Calendar.YEAR);
		return currentDate;
	}

	private String getExpiryDate(int month) {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.MONTH, month);
		String expiryDate = (now.get(Calendar.DATE)-1) + "-" + (now.get(Calendar.MONTH) + 1) + "-" + now.get(Calendar.YEAR);
		return expiryDate;
	}

	private boolean isValidPeriod(String expDate) {
		boolean isValidPeriod = false;
		Calendar now = Calendar.getInstance();
		String currentDate = now.get(Calendar.DATE) + "-" + (now.get(Calendar.MONTH) + 1) + "-"
				+ now.get(Calendar.YEAR);
		SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");
		try {
			Date date1 = sdf.parse(currentDate);
			Date date2 = sdf.parse(expDate);
			if (date1.compareTo(date2) <= 0) {
				System.out.println("Date1 is before Date2");
				isValidPeriod = true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return isValidPeriod;
	}
}
