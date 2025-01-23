package com.kadai.aws.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * サンプルコード<br>
 * データベースの操作をする
 */
public class SampleRepository {

	private static final String JDBC_URL = "jdbc:hsqldb:mem:testdb";
	private static final String JDBC_USER = "SA";
	private static final String JDBC_PASSWORD = "";

	public void createTable() {
		try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
				Statement stmt = conn.createStatement()) {
			stmt.execute("CREATE TABLE sample (id INTEGER IDENTITY, name VARCHAR(50))");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insertData(String name) {
		try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
				Statement stmt = conn.createStatement()) {
			stmt.execute("INSERT INTO sample (name) VALUES ('" + name + "')");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void queryData() {
		try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM sample")) {
			while (rs.next()) {
				System.out.println("ID: " + rs.getInt("id") + ", Name: " + rs.getString("name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
