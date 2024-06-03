package service;
/*
 * 用户业务层
 */

import java.util.List;

import com.sxt.utils.PageTool;

import dao.UserDao;
import entity.UserDB;

public class UserService {
	
	private UserDao userDao = new UserDao();
	
	//登录
	public UserDB login(String account,String password) {
		return userDao.login(account,password);
	}
	
	//添加用户
	public Integer addUser(UserDB userDB) {
		return userDao.addUser(userDB);
	}
	
	//查询用户
	public PageTool<UserDB> list(String currentPage,String pageSize){
		return userDao.list(currentPage,pageSize);
	}
	
	//更新用户信息
	public Integer updUser(UserDB userDB){
		return userDao.updUser(userDB);
	}
	
	//更新用户信息
	public Integer delUser(Integer uid){
		return userDao.delUser(uid);
	}
	
	//
	public List<UserDB> getList(UserDB userDB){
		return userDao.getList(userDB);
	}
}
