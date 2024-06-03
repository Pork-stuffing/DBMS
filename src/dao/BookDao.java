package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.dbutils.GenerousBeanProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.RowProcessor;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.sxt.utils.C3p0Tool;
import com.sxt.utils.MyException;
import com.sxt.utils.PageTool;

import entity.BookDB;

public class BookDao {
	QueryRunner queryRunner = new QueryRunner(C3p0Tool.getDataSource());
	// 开启驼峰自动转换
	BeanProcessor bean = new GenerousBeanProcessor();
	RowProcessor processor = new BasicRowProcessor(bean);

	// 图书列表分页
	public PageTool<BookDB> list(String currentPage, String pageSize, String word) {
		try {
			StringBuffer listSql = new StringBuffer("SELECT b.*,type_name ");
			StringBuffer countSql = new StringBuffer("SELECT COUNT(*) ");
			StringBuffer sql = new StringBuffer("FROM t_book b LEFT JOIN t_type t ON b.tid = t.tid ");
			if (word != null && !word.isEmpty()) {
				sql.append("where book_name like '%" + word + "%'");
				sql.append(" or type_name like '%" + word + "%'");
				sql.append(" or author like '%" + word + "%'");
				sql.append(" or press like '%" + word + "%'");
			}
			// 获取总记录数
			Long total = queryRunner.query(countSql.append(sql).toString(), new ScalarHandler<Long>());
			// 初始化分页工具
			PageTool<BookDB> pageTools = new PageTool<BookDB>(total.intValue(), currentPage, pageSize);
			sql.append("LIMIT ?, ?"); // 注意这里要有空格
			// 当前页的数据
			List<BookDB> list = queryRunner.query(listSql.append(sql).toString(),
					new BeanListHandler<BookDB>(BookDB.class, processor), pageTools.getStartIndex(),
					pageTools.getPageSize());
			pageTools.setRows(list);
			return pageTools;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new PageTool<BookDB>(0, currentPage, pageSize);
	}

	// 多条件查询
	public List<BookDB> list(String book_name,String bid) {
		StringBuffer sql = new StringBuffer("select * from t_book where 1 = 1 ");
		List<Object> params = new ArrayList<>();
		if (book_name != null && book_name != " ") {
			sql.append("and book_name = ? ");
			params.add(book_name);
		}
		if (bid != null && bid != " ") {
			sql.append("and bid = ? ");
			params.add(bid);
		}
		try {
			return queryRunner.query(sql.toString(), new BeanListHandler<BookDB>(BookDB.class, processor),
					params.toArray());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	// 添加图书
	public Integer addBook(BookDB bookDB) {
		String sql = "insert into t_book (book_name,author,num,press,tid,times) values(?,?,?,?,?,0)";
		Object[] params = { bookDB.getBookName(),bookDB.getAuthor(),bookDB.getNum(),bookDB.getPress(),
				bookDB.getTid()};
		try {
			return queryRunner.update(sql, params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	// 管理员修改图书信息
	public Integer updBook(BookDB bookDB) {
		String sql = "update t_book set tid = ?, press = ?, num = ? where bid = ?";
		Object[] params = {bookDB.getTid(),bookDB.getPress(),bookDB.getNum(),bookDB.getBid()};
		try {
			return queryRunner.update(sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	// 在有人借阅图书时改变图书库存
	public Integer changeNum(BookDB bookDB,Connection conn) throws SQLException {
		QueryRunner qr = new QueryRunner();
		String sql = "update t_book set times = ?, num = ? where bid = ?";
		Object[] params = {bookDB.getTimes(),bookDB.getNum(),bookDB.getBid()};
		return qr.update(conn,sql, params);
	
	}
	
	

	/*
	 * 删除图书
	 */
	public Integer delBook(String bid,Connection conn) throws SQLException {
		String sql = "delete from t_book where bid = ?";
		Object[] params = {bid};
		return  queryRunner.update(conn,sql, params);
	}
	
}
