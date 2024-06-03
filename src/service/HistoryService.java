package service;


import com.sxt.utils.PageTool;

import dao.HistoryDao;
import entity.HistoryDB;

public class HistoryService {
	
	private HistoryDao historyDao = new HistoryDao();
	
	public PageTool<HistoryDB> listByPage(String currentPage, String pageSize,Integer uid,Integer status) {
		return historyDao.listByPage(currentPage, pageSize, uid, status);
	}
}
