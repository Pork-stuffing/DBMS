package dao;
/*
 * �û��������Ӳ�
 */

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.dbutils.GenerousBeanProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.RowProcessor;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.Test;

import com.sxt.utils.C3p0Tool;
import com.sxt.utils.MyException;
import com.sxt.utils.PageTool;

import entity.UserDB;

public class UserDao {
	QueryRunner queryRunner = new QueryRunner(C3p0Tool.getDataSource());
	//�����շ��Զ�ת��
	BeanProcessor bean = new GenerousBeanProcessor();
	RowProcessor processor = new BasicRowProcessor(bean);
	
	
	//��¼
	public UserDB login(String account,String password) {
		String sql = "select * from t_user where account = ? and password = ?";
		Object[] params = {account,password};
		try {
			return queryRunner.query(sql, new BeanHandler<UserDB>(UserDB.class,processor),params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	//��ѯ�û�
	public List<UserDB> list(){
		String sql = "select * from t_user";
		try {
			return queryRunner.query(sql, new BeanListHandler<UserDB>(UserDB.class,processor));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	// �û��б��ҳ
	public PageTool<UserDB> list(String currentPage, String pageSize) {
	    try {
	        StringBuffer listSql = new StringBuffer("SELECT * ");
	        StringBuffer countSql = new StringBuffer("SELECT COUNT(*) ");
	        StringBuffer sql = new StringBuffer("FROM t_user ");
	        // ��ȡ�ܼ�¼��
	        Long total = queryRunner.query(countSql.append(sql).toString(), new ScalarHandler<Long>());
	        // ��ʼ����ҳ����
	        PageTool<UserDB> pageTools = new PageTool<UserDB>(total.intValue(), currentPage, pageSize);
	        sql.append("LIMIT ?, ?"); // ע������Ҫ�пո�
	        // ��ǰҳ������
	        List<UserDB> list = queryRunner.query(
	            listSql.append(sql).toString(),
	            new BeanListHandler<UserDB>(UserDB.class, processor),
	            pageTools.getStartIndex(),
	            pageTools.getPageSize()
	        );
	        pageTools.setRows(list);
	        return pageTools;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return new PageTool<UserDB>(0, currentPage, pageSize);
	}

	
	//����û�
	public Integer addUser(UserDB userDB) {
		String sql = "insert into t_user (account,password,name,phone,times,lend_num,max_num,role) values(?,?,?,?,?,?,?,?)";
		Object[] params = {userDB.getAccount(),userDB.getPassword(),userDB.getName(),userDB.getPhone(),userDB.getTimes(),
				userDB.getLendNum(),userDB.getMaxNum(),userDB.getRole()};
		try {
			return queryRunner.update(sql,params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new MyException("�������������ܳ���40");
		}
	}
	
	//
	public List<UserDB> getList(UserDB userDB){
		String sql = "select * from t_user where account = ?";
		Object[] params = {userDB.getAccount()};
		try {
			return queryRunner.query(sql, new BeanListHandler<UserDB>(UserDB.class,processor),params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	//����Ա�޸���Ϣ
	public Integer updUser(UserDB userDB){
		String sql = "update t_user set phone = ?, lend_num = ?, max_num = ? where uid = ?";
		Object[] params = {userDB.getPhone(),userDB.getLendNum(),userDB.getMaxNum(),userDB.getUid()};
		try {
			queryRunner.update(sql,params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	//ͼ�����ʱ�ı���Ϣ
	public Integer updNum(UserDB userDB,Connection conn) throws SQLException{
		QueryRunner qr = new QueryRunner();
		String sql = "update t_user set times = ?, max_num = ? where uid = ?";
		Object[] params = {userDB.getTimes(),userDB.getMaxNum(),userDB.getUid()};
		return qr.update(conn,sql,params);
		
	}
	
	/*
	 * ɾ���û�
	 */
	public int delUser(Integer uid) {
		String sql = "delete from t_user where uid = ?";
		Object[] params = {uid};
		try {
			int update = queryRunner.update(sql, params);
			return update;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
}
