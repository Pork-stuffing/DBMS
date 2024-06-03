package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sxt.utils.DateUtils;
import com.sxt.utils.PageTool;
import com.sxt.utils.PaginationUtils;

import entity.HistoryDB;
import entity.UserDB;
import service.HistoryService;

//ͼ�������ʷ��¼
@WebServlet("/history")
public class HistoryServlet extends BaseServlet{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private HistoryService historyService = new HistoryService();
	
	// ��ѯ���ڽ��ĵ�ͼ��
	public void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserDB userDB = (UserDB) request.getSession().getAttribute("userDB");
		//���ݵ�ǰ��½���û���ȡ��ɫ
		Integer role = userDB.getRole();
		String currentPage = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		//��ͨ�û�ֻ�ܲ�ѯ�Լ���  ����Ա��ѯ����  
		PageTool<HistoryDB> pageTool = null;
		if (role == 1) {
			//��ͨ�û�
			pageTool = historyService.listByPage(currentPage, pageSize, userDB.getUid(), 1);
		} else {
			//����Ա
			pageTool = historyService.listByPage(currentPage, pageSize, null, 1);
		}		
		String path = "history?method=list";
		//����ǰ�˷�ҳ��ť
		String pagation = PaginationUtils.getPagation(pageTool.getTotalCount(), pageTool.getCurrentPage(), pageTool.getPageSize(), path);
		request.setAttribute("pagation", pagation);
		request.setAttribute("hList", pageTool.getRows());
		//����role�ж���ת��ҳ��
		if (role == 1) {
			//��ͨ�û�
			request.getRequestDispatcher("user/borrow.jsp").forward(request, response);
		} else {
			//����Ա
			request.getRequestDispatcher("admin/admin_borrow.jsp").forward(request, response);
		}
		
	}
	
	
	// ��ѯ�Ѿ����黹��ͼ��
	public void backList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserDB userDB = (UserDB) request.getSession().getAttribute("userDB");
		//���ݵ�ǰ��½���û���ȡ��ɫ
		Integer role = userDB.getRole();
		String currentPage = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		//��ͨ�û�ֻ�ܲ�ѯ�Լ���  ����Ա��ѯ����  
		PageTool<HistoryDB> pageTool = null;
		if (role == 1) {
			//��ͨ�û�
			pageTool = historyService.listByPage(currentPage, pageSize, userDB.getUid(), 2);
		} else {
			//����Ա
			pageTool = historyService.listByPage(currentPage, pageSize, null, 2);
		}		
		String path = "history?method=backList";
		//����ǰ�˷�ҳ��ť
		String pagation = PaginationUtils.getPagation(pageTool.getTotalCount(), pageTool.getCurrentPage(), pageTool.getPageSize(), path);
		request.setAttribute("pagation", pagation);
		request.setAttribute("hList", pageTool.getRows());
		//����role�ж���ת��ҳ��
		if (role == 1) {
			//��ͨ�û�
			request.getRequestDispatcher("user/history.jsp").forward(request, response);
		} else {
			//����Ա
			request.getRequestDispatcher("admin/admin_history.jsp").forward(request, response);
		}
		
	}
	
}
