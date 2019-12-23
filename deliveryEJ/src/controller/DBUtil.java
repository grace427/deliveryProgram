package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

	private static String driver = "com.mysql.jdbc.Driver";
	private static String url = "jdbc:mysql://localhost/deliverydb";

	// 2.드라이버를 적재하고 데이터베이스를 연결하는 함수
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		// 1.드라이버를 적재
		Class.forName(driver);
		// 2.데이터베이스를 연결
		Connection con = DriverManager.getConnection(url, "root", "123456");
		System.out.println("db 연결되었음");
		return con;
	}
}
