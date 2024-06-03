package com.sxt.utils;

import java.sql.SQLException;

import dao.BookDao;
import dao.HistoryDao;
import dao.UserDao;
import entity.BookDB;
import entity.UserDB;

public class TestDemo {
	
	public static void main(String[] args) throws SQLException {
		HistoryDao historyDao  = new HistoryDao();
		System.out.println(historyDao.list(2+""));
	}
}
