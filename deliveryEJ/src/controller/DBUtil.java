package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

	private static String driver = "com.mysql.jdbc.Driver";
	private static String url = "jdbc:mysql://localhost/deliverydb";

	// 2.����̹��� �����ϰ� �����ͺ��̽��� �����ϴ� �Լ�
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		// 1.����̹��� ����
		Class.forName(driver);
		// 2.�����ͺ��̽��� ����
		Connection con = DriverManager.getConnection(url, "root", "123456");
		System.out.println("db ����Ǿ���");
		return con;
	}
}
