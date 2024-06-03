package dao;

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
import com.sxt.utils.PageTool;

import entity.TypeDB;
import entity.UserDB;

/*
 * 图书种类
 */

public class TypeDao {
	QueryRunner queryRunner = new QueryRunner(C3p0Tool.getDataSource());
	// 开启驼峰自动转换
	BeanProcessor bean = new GenerousBeanProcessor();
	RowProcessor processor = new BasicRowProcessor(bean);

	// 图书列表分页
	public PageTool<TypeDB> listByPage(String currentPage, String pageSize) {
		try {
			StringBuffer listSql = new StringBuffer("SELECT * ");
			StringBuffer countSql = new StringBuffer("SELECT COUNT(*) ");
			StringBuffer sql = new StringBuffer("FROM t_type ");
			// 获取总记录数
			Long total = queryRunner.query(countSql.append(sql).toString(), new ScalarHandler<Long>());
			// 初始化分页工具
			PageTool<TypeDB> pageTools = new PageTool<TypeDB>(total.intValue(), currentPage, pageSize);
			sql.append("LIMIT ?, ?"); // 注意这里要有空格
			// 当前页的数据
			List<TypeDB> list = queryRunner.query(listSql.append(sql).toString(),
					new BeanListHandler<TypeDB>(TypeDB.class, processor), pageTools.getStartIndex(),
					pageTools.getPageSize());
			pageTools.setRows(list);
			return pageTools;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new PageTool<TypeDB>(0, currentPage, pageSize);
	}

	// 展示类别
	public List<TypeDB> list(String tid, String typeName) {
		StringBuffer sql = new StringBuffer("select * from t_type where 1 = 1 ");
		List<Object> params = new ArrayList<>();
		if (tid != null && tid != "") {
			sql.append("and tid = ? ");
			params.add(tid);
		}
		if (typeName != null && typeName != "") {
			sql.append("and type_name = ? ");
			params.add(typeName);
		}
		try {
			return queryRunner.query(sql.toString(), new BeanListHandler<TypeDB>(TypeDB.class, processor),
					params.toArray());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	// 添加图书类别
	public Integer addType(String typeName) {
		String sql = "insert into t_type (type_name) values(?)";
		Object[] params = { typeName };
		try {
			return queryRunner.update(sql, params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	// 管理员修改信息
	public Integer updType(TypeDB typeDB) {
		String sql = "update t_type set type_name = ? where tid = ?";
		Object[] params = {typeDB.getTypeName(),typeDB.getTid()};
		try {
			queryRunner.update(sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
	

	/*
	 * 删除图书类型
	 */
	public int delType(Integer tid) {
		String sql = "delete from t_type where tid = ?";
		Object[] params = {tid};
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
