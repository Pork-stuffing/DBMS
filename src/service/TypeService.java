package service;

import java.util.List;

import com.sxt.utils.PageTool;

import dao.TypeDao;
import entity.TypeDB;

public class TypeService {
	
	private TypeDao typeDao = new TypeDao(); 
	public PageTool<TypeDB> listByPage(String currentPage, String pageSize) {
		return typeDao.listByPage(currentPage, pageSize);
	}
	public List<TypeDB> list(String tid, String typeNmae) {
		return typeDao.list(tid, typeNmae);
	}
	public Integer addType(String typeName) {
		return typeDao.addType(typeName);
	}
	public Integer updType(TypeDB typeDB) {
		return typeDao.updType(typeDB);
	}
	public int delType(Integer tid) {
		return typeDao.delType(tid);
	}
}
