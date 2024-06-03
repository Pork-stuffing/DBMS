package servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.sxt.utils.PageTool;
import com.sxt.utils.PaginationUtils;
import com.sxt.utils.ResBean;

import entity.TypeDB;
import entity.UserDB;
import service.TypeService;

/*
 * ͼ�����
 */
@WebServlet("/type")
public class TypeServlet extends BaseServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private TypeService typeService = new TypeService();

	// ͼ�������б� ��ҳ
	public void listByPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String currentNum = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		PageTool<TypeDB> pageTool = typeService.listByPage(currentNum, pageSize);
		// ����ǰ�˷�ҳ��ť
		String pagation = PaginationUtils.getPagation(pageTool.getTotalCount(), pageTool.getCurrentPage(),
				pageTool.getPageSize(), "type?method=listByPage");
		request.setAttribute("pagation", pagation);
		request.setAttribute("tList", pageTool.getRows());
		request.getRequestDispatcher("admin/admin_booktype.jsp").forward(request, response);
		;
	}

	// У���˺��Ƿ��Ѵ���
	public void checkType(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, IllegalAccessException, InvocationTargetException {
		String typeName = request.getParameter("typeName");
		List<TypeDB> list = typeService.list(null, typeName);
		ResBean resBean = new ResBean();
		if (list != null && list.size() > 0) {
			resBean.setCode(400);
			resBean.setMsg("��������Ѿ�����");
		} else {
			resBean.setCode(200);
			resBean.setMsg("������ƿ���ʹ��");
		}
		// ��resBeanת��Ϊjson�ַ���
		Gson gson = new Gson();
		String json = gson.toJson(resBean);
		response.getWriter().print(json);
	}

	// �������
	public void addType(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String typeName = request.getParameter("typeName");
		typeService.addType(typeName);
		request.getRequestDispatcher("type?method=listByPage").forward(request, response);
		;
	}

	// �޸�����
	public void updType(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String typeName = request.getParameter("typeName");
		String tid = request.getParameter("tid");
		TypeDB typeDB = new TypeDB();
		typeDB.setTid(Integer.parseInt(tid));
		typeDB.setTypeName(typeName);
		typeService.updType(typeDB);
		request.getRequestDispatcher("type?method=listByPage").forward(request, response);
		;
	}
	
	// ɾ������
	public void delType(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String tid = request.getParameter("tid");
		typeService.delType(Integer.parseInt(tid));
		request.getRequestDispatcher("type?method=listByPage").forward(request, response);
		;
	}
}
