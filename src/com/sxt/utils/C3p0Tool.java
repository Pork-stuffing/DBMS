package com.sxt.utils;
/*
 * C3p0����
 */

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class C3p0Tool {

	private static DataSource dataSource = new ComboPooledDataSource();
	
	//��ȡ����Դ
	public static DataSource getDataSource() {
		return dataSource;
	}
	
	//��ȡ��������
	public static Connection getConnection() {
			try {
				return dataSource.getConnection();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException();
			}
		
	}
	
}
