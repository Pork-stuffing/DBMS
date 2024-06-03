package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.dbutils.GenerousBeanProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.RowProcessor;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.sxt.utils.C3p0Tool;
import com.sxt.utils.HistoryDBHandler;
import com.sxt.utils.PageTool;

import entity.BookDB;
import entity.HistoryDB;

public class HistoryDao {

	QueryRunner queryRunner = new QueryRunner(C3p0Tool.getDataSource());
	// �����շ��Զ�ת��
	BeanProcessor bean = new GenerousBeanProcessor();
	RowProcessor processor = new BasicRowProcessor(bean);

	/*
	 * ���ͼ����ļ�¼
	 */
	public Integer addHistory(HistoryDB historyDB, Connection conn) throws SQLException {
		QueryRunner queryRunner = new QueryRunner();
		String sql = "insert into t_history (uid,name,account,bid,book_name,begin_time,end_time,status) values(?,?,?,?,?,?,?,?)";
		
		 // �� java.util.Date ת��Ϊ java.sql.Timestamp
	    Timestamp beginTime = new Timestamp(historyDB.getBeginTime().getTime());
	    Timestamp endTime = new Timestamp(historyDB.getEndTime().getTime());
	    
	    Object[] params = { 
	        historyDB.getUid(), 
	        historyDB.getName(), 
	        historyDB.getAccount(), 
	        historyDB.getBid(),
	        historyDB.getBookName(), 
	        beginTime, // ʹ�� Timestamp ����
	        endTime,   // ʹ�� Timestamp ����
	        historyDB.getStatus() 
	    };
	    return queryRunner.update(conn, sql, params);
	}

	// ��ѯ��ʷ��¼
	public PageTool<HistoryDB> listByPage(String currentPage, String pageSize, Integer uid, Integer status) {
	    try {
	        StringBuffer listSql = new StringBuffer("SELECT * ");
	        StringBuffer countSql = new StringBuffer("SELECT COUNT(*) ");
	        StringBuffer sql = new StringBuffer("FROM user_borrow_history where 1 = 1 ");
	        List<Object> params = new ArrayList<>();
	        if (uid != null) {
	            sql.append("and uid = ? ");
	            params.add(uid);
	        }
	        if (status != null) {
	            sql.append("and status = ? ");
	            params.add(status);
	        }
	        // ��ȡ�ܼ�¼��
	        Long total = queryRunner.query(countSql.append(sql).toString(), new ScalarHandler<Long>(), params.toArray());
	        // ��ʼ����ҳ����
	        PageTool<HistoryDB> pageTools = new PageTool<HistoryDB>(total.intValue(), currentPage, pageSize);
	        sql.append(" order by begin_time desc limit ?, ? "); // ע������Ҫ�пո�
	        params.add(pageTools.getStartIndex());
	        params.add(pageTools.getPageSize());

	        // ��ǰҳ������
	        List<HistoryDB> list = queryRunner.query(listSql.append(sql).toString(), new HistoryDBHandler(), params.toArray());
	        
	        pageTools.setRows(list);
	        return pageTools;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return new PageTool<HistoryDB>(0, currentPage, pageSize);
	}
	
	/**
	 * �޷�ҳ����ѯ
	 * @param hid
	 * @return
	 */
	public List<HistoryDB> list(String hid){
		StringBuffer sql = new StringBuffer("select * from t_history where 1 = 1 ");
		List<Object> params = new ArrayList<>();
		if (hid != null && hid != "") {
			sql.append("and hid = ? ");
			params.add(hid);
		}
		try {
			return queryRunner.query(sql.toString(), new HistoryDBHandler(), params.toArray());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//����ͼ��id��ѯ��ʷ
	public List<HistoryDB> list1(String bid){
		StringBuffer sql = new StringBuffer("select * from t_history where 1 = 1 ");
		List<Object> params = new ArrayList<>();
		if (bid != null && bid != "") {
			sql.append("and bid = ? ");
			params.add(bid);
		}
		try {
			return queryRunner.query(sql.toString(), new HistoryDBHandler(), params.toArray());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/*
	 * �޸�ͼ�������Ϣ
	 */
	public Integer updHistory(HistoryDB historyDB,Connection conn) throws SQLException {
		QueryRunner qr = new QueryRunner();
		String sql = "update t_history set status = ? where hid = ?";
		Object[] params = {historyDB.getStatus(), historyDB.getHid()};
		System.out.println(params);
		return qr.update(conn,sql, params);
	}
	
	public Integer delHistory(String bid,Connection conn) throws SQLException {
		QueryRunner qr = new QueryRunner();
		String sql = "delete from t_history where bid = ?";
		Object[] params = {bid};
		return qr.update(conn,sql,params);
	}

}
