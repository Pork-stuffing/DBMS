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
		//��ȡ����
		Connection conn = C3p0Tool.getConnection();
		try {
			//���ò��Զ��ύ
			conn.setAutoCommit(false);
			//����bidɾ�����ĵ���ʷ��¼
			historyDao.delHistory(bid, conn);
			//����bidɾ��ͼ����Ϣ
			bookDao.delBook(bid, conn);
			
			
			//���������ύ
			conn.commit();
		}catch (Exception e) {
			// TODO: handle exception
			conn.rollback();
			if(e instanceof MyException) {
				throw new MyException(e.getMessage());
			}else {
				e.printStackTrace();
				throw new MyException("ɾ��ʧ��");
			}
		}
		
	}
	
	//�û�����ͼ��
	public void borrowBook(UserDB userDB, String bid) throws SQLException {
		//��ȡ����
		Connection conn = C3p0Tool.getConnection();
		try {
			//�������ﲻ�Զ��ύ
			conn.setAutoCommit(false);
			//����bid��ȡͼ����Ϣ
			List<BookDB> list = bookDao.list(null, bid);
			BookDB bookDB = list.get(0);
			//��֤�û����������ݿ�����ͬ��
			userDB = userDao.getList(userDB).get(0);
			
			//t_history ����ͼ�������ʷ��¼
			HistoryDB historyDB = new HistoryDB();
			historyDB.setUid(userDB.getUid());
			historyDB.setName(userDB.getName());
			historyDB.setAccount(userDB.getAccount());
			historyDB.setBid(bookDB.getBid());
			historyDB.setBookName(bookDB.getBookName());
			Date d = new Date();
			historyDB.setBeginTime(d);
			historyDB.setEndTime(DateUtils.dateAdd(d,userDB.getLendNum()));//����ʱ��=����ʱ��+�û�����������
			historyDB.setStatus(1);
			historyDao.addHistory(historyDB,conn);
			
			//t_bookͼ������-- ͼ�鱻���Ĵ���++
			Integer num = bookDB.getNum();
			//��ͼ��������ж�
			if(num<=0) {
				throw new MyException("��治��");
			}
			bookDB.setNum(--num);
			Integer time = bookDB.getTimes();
			bookDB.setTimes(++time);
			bookDao.changeNum(bookDB,conn);
			
			//t_user�ı��û���Ϣ  user.times++ max_num--
			userDB.setTimes(userDB.getTimes()+1);
			//�ɽ������������ж�
			if(userDB.getMaxNum()<=0) {
				throw new MyException("������������");
			}
			userDB.setMaxNum(userDB.getMaxNum()-1);
			userDao.updNum(userDB,conn);
			//���������ύ
			conn.commit();
		} catch (Exception e) {
			// TODO: handle exception
			conn.rollback();
			if(e instanceof MyException) {
				throw new MyException(e.getMessage());
			}else {
				e.printStackTrace();
				throw new MyException("����ʧ��");
			}
		}
		
	}
	
	//�û�����
	public void backBook(String hid) throws SQLException {
		//��ȡ����
		Connection conn = C3p0Tool.getConnection();
		try {
			//�������ﲻ�Զ��ύ
			conn.setAutoCommit(false);
			//����hid��ȡhistoryDB, �޸�statusΪ2
			HistoryDB historyDB = historyDao.list(hid).get(0);
			historyDB.setStatus(2);
			historyDao.updHistory(historyDB, conn);
			//����historyDB��ȡͼ��bid
			Integer bid = historyDB.getBid();
			//����bid��ȡͼ����Ϣ���޸Ŀ�� + 1
			BookDB bookDB = bookDao.list(historyDB.getBookName(), bid+"").get(0);
			bookDB.setNum(bookDB.getNum() + 1);
			bookDB.setTimes(bookDB.getTimes() + 1);
			bookDao.changeNum(bookDB, conn);
			
			//����historyDB��ȡ�û�account
			String account = historyDB.getAccount();
			//����uid��ȡ�û���Ϣ,�޸�max_num +1
			UserDB userDB = new UserDB();
			userDB.setAccount(account);
			userDB = userDao.getList(userDB).get(0);
			userDB.setMaxNum(userDB.getMaxNum() + 1);
			userDao.updNum(userDB, conn);
			
			//�����ύ
			conn.commit();
		} catch (Exception e) {
			// TODO: handle exception
			if(e instanceof MyException) {
				throw new MyException(e.getMessage());
			}else {
				e.printStackTrace();
				throw new MyException("����ʧ��");
			}
		}
		
	}
}
