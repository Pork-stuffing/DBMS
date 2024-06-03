package service;
/*
 * �û�ҵ���
 */

import java.util.List;

import com.sxt.utils.PageTool;

import dao.UserDao;
import entity.UserDB;

public class UserService {
	
	private UserDao userDao = new UserDao();
	
	//��¼
	public UserDB login(String account,String password) {
		return userDao.login(account,password);
	}
	
	//����û�
	public Integer addUser(UserDB userDB) {
		return userDao.addUser(userDB);
	}
	
	//��ѯ�û�
	public PageTool<UserDB> list(String currentPage,String pageSize){
		return userDao.list(currentPage,pageSize);
	}
	
	//�����û���Ϣ
	public Integer updUser(UserDB userDB){
		return userDao.updUser(userDB);
	}
	
	//�����û���Ϣ
	public Integer delUser(Integer uid){
		return userDao.delUser(uid);
	}
	
	//
	public List<UserDB> getList(UserDB userDB){
		return userDao.getList(userDB);
	}
}
