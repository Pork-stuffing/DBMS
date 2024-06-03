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
 * ͼ������
 */

public class TypeDao {
	QueryRunner queryRunner = new QueryRunner(C3p0Tool.getDataSource());
	// �����շ��Զ�ת��
	BeanProcessor bean = new GenerousBeanProcessor();
	RowProcessor processor = new BasicRowProcessor(bean);

	// ͼ���б��ҳ
	public PageTool<TypeDB> listByPage(String currentPage, String pageSize) {
		try {
			StringBuffer listSql = new StringBuffer("SELECT * ");
			StringBuffer countSql = new StringBuffer("SELECT COUNT(*) ");
			StringBuffer sql = new StringBuffer("FROM t_type ");
			// ��ȡ�ܼ�¼��
			Long total = queryRunner.query(countSql.append(sql).toString(), new ScalarHandler<Long>());
			// ��ʼ����ҳ����
			PageTool<TypeDB> pageTools = new PageTool<TypeDB>(total.intValue(), currentPage, pageSize);
			sql.append("LIMIT ?, ?"); // ע������Ҫ�пո�
			// ��ǰҳ������
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

	// չʾ���
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

	// ���ͼ�����
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

	// ����Ա�޸���Ϣ
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
	 * ɾ��ͼ������
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
