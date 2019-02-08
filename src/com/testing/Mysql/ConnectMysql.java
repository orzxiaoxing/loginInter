package com.testing.Mysql;

import java.sql.DriverManager;

import com.mysql.jdbc.Connection;

public class ConnectMysql {
	public Connection conn;
	// 数据库连接的地址、用户名和密码
	// ？之前是数据库的地址端口和数据库名，后面的是连接的参数
	public static final String DBURL = "jdbc:mysql://192.168.220.130:3306/test_project?useUnicode=true&autoReconnect=true&characterEncoding=utf-8";
	public static final String DBUSER = "root";
	public static final String DBPWD = "qwert123";

	public ConnectMysql() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = (Connection) DriverManager.getConnection(DBURL, DBUSER, DBPWD);
			// 设置超时时间
			DriverManager.setLoginTimeout(10);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
