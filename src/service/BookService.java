package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.sxt.utils.C3p0Tool;
import com.sxt.utils.DateUtils;
import com.sxt.utils.MyException;
import com.sxt.utils.PageTool;

import dao.BookDao;
import dao.HistoryDao;
import dao.UserDao;
import entity.BookDB;
import entity.HistoryDB;
import entity.UserDB;

public class BookService {

	private HistoryDao historyDao = new HistoryDao();
	
	private UserDao userDao = new UserDao();
	
	private BookDao bookDao = new BookDao();
		
	public PageTool<BookDB> listByPage(String currentPage, String pageSize,String word) {
		return bookDao.list(currentPage, pageSize,word);
	}
	
	public List<BookDB> list(String book_name) {
		return bookDao.list(book_name,null);
	}
	public Integer addBook(BookDB bookDB) {
		return bookDao.addBook(bookDB);
	}
	public Integer updBook(BookDB bookDB) {
		return bookDao.updBook(bookDB);
	}
	public void delBook(String bid) throws SQLException {
		//获取连接
		Connection conn = C3p0Tool.getConnection();
		try {
			//设置不自动提交
			conn.setAutoCommit(false);
			//根据bid删除借阅的历史记录
			historyDao.delHistory(bid, conn);
			//根据bid删除图书信息
			bookDao.delBook(bid, conn);
			
			
			//事物正常提交
			conn.commit();
		}catch (Exception e) {
			// TODO: handle exception
			conn.rollback();
			if(e instanceof MyException) {
				throw new MyException(e.getMessage());
			}else {
				e.printStackTrace();
				throw new MyException("删除失败");
			}
		}
		
	}
	
	//用户借阅图书
	public void borrowBook(UserDB userDB, String bid) throws SQLException {
		//获取连接
		Connection conn = C3p0Tool.getConnection();
		try {
			//设置事物不自动提交
			conn.setAutoCommit(false);
			//根据bid获取图书信息
			List<BookDB> list = bookDao.list(null, bid);
			BookDB bookDB = list.get(0);
			//保证用户数据与数据库数据同步
			userDB = userDao.getList(userDB).get(0);
			
			//t_history 创建图书借阅历史记录
			HistoryDB historyDB = new HistoryDB();
			historyDB.setUid(userDB.getUid());
			historyDB.setName(userDB.getName());
			historyDB.setAccount(userDB.getAccount());
			historyDB.setBid(bookDB.getBid());
			historyDB.setBookName(bookDB.getBookName());
			Date d = new Date();
			historyDB.setBeginTime(d);
			historyDB.setEndTime(DateUtils.dateAdd(d,userDB.getLendNum()));//还书时间=借书时间+用户最大借书天数
			historyDB.setStatus(1);
			historyDao.addHistory(historyDB,conn);
			
			//t_book图书数量-- 图书被借阅次数++
			Integer num = bookDB.getNum();
			//对图书库存进行判断
			if(num<=0) {
				throw new MyException("库存不足");
			}
			bookDB.setNum(--num);
			Integer time = bookDB.getTimes();
			bookDB.setTimes(++time);
			bookDao.changeNum(bookDB,conn);
			
			//t_user改变用户信息  user.times++ max_num--
			userDB.setTimes(userDB.getTimes()+1);
			//可借阅数量进行判断
			if(userDB.getMaxNum()<=0) {
				throw new MyException("借阅数量已满");
			}
			userDB.setMaxNum(userDB.getMaxNum()-1);
			userDao.updNum(userDB,conn);
			//事物正常提交
			conn.commit();
		} catch (Exception e) {
			// TODO: handle exception
			conn.rollback();
			if(e instanceof MyException) {
				throw new MyException(e.getMessage());
			}else {
				e.printStackTrace();
				throw new MyException("借阅失败");
			}
		}
		
	}
	
	//用户还书
	public void backBook(String hid) throws SQLException {
		//获取连接
		Connection conn = C3p0Tool.getConnection();
		try {
			//设置事物不自动提交
			conn.setAutoCommit(false);
			//根据hid获取historyDB, 修改status为2
			HistoryDB historyDB = historyDao.list(hid).get(0);
			historyDB.setStatus(2);
			historyDao.updHistory(historyDB, conn);
			//根据historyDB获取图书bid
			Integer bid = historyDB.getBid();
			//根据bid获取图书信息，修改库存 + 1
			BookDB bookDB = bookDao.list(historyDB.getBookName(), bid+"").get(0);
			bookDB.setNum(bookDB.getNum() + 1);
			bookDB.setTimes(bookDB.getTimes() + 1);
			bookDao.changeNum(bookDB, conn);
			
			//根据historyDB获取用户account
			String account = historyDB.getAccount();
			//根据uid获取用户信息,修改max_num +1
			UserDB userDB = new UserDB();
			userDB.setAccount(account);
			userDB = userDao.getList(userDB).get(0);
			userDB.setMaxNum(userDB.getMaxNum() + 1);
			userDao.updNum(userDB, conn);
			
			//事物提交
			conn.commit();
		} catch (Exception e) {
			// TODO: handle exception
			if(e instanceof MyException) {
				throw new MyException(e.getMessage());
			}else {
				e.printStackTrace();
				throw new MyException("还书失败");
			}
		}
		
	}
}
