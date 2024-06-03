package servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.google.gson.Gson;
import com.sxt.utils.MD5;
import com.sxt.utils.PageTool;
import com.sxt.utils.PaginationUtils;
import com.sxt.utils.ResBean;

import entity.UserDB;
import service.UserService;

//�û�
@WebServlet("/user")
public class UserServlet extends BaseServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private UserService userService = new UserService();
	
	//�û��б� ��ҳ
	public void list(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		String currentNum = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		PageTool<UserDB> pageTool = userService.list(currentNum,pageSize);
		//����ǰ�˷�ҳ��ť
		String pagation = PaginationUtils.getPagation(pageTool.getTotalCount(), pageTool.getCurrentPage(), pageTool.getPageSize(), "user?method=list");
		request.setAttribute("pagation", pagation);
		request.setAttribute("uList", pageTool.getRows());
		request.getRequestDispatcher("admin/admin_user.jsp").forward(request, response);;
	}
	
	//����û�
	public void addUser(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException, IllegalAccessException, InvocationTargetException {
		//ʹ��BeanUtils���ٴ�����
		UserDB userDB = new UserDB();
		BeanUtils.populate(userDB,request.getParameterMap());
		userDB.setTimes(0);
		userDB.setPassword(MD5.valueOf(userDB.getPassword()));
		userService.addUser(userDB);
		response.sendRedirect("user?method=list");
	}
	
	//�޸��û���Ϣ
	public void updUser(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException, IllegalAccessException, InvocationTargetException {
		//ʹ��BeanUtils���ٴ�����
		UserDB userDB = new UserDB();
		BeanUtils.populate(userDB,request.getParameterMap());
		userService.updUser(userDB);
		response.sendRedirect("user?method=list");
	}

	//ɾ���û�
	public void delUser(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException, IllegalAccessException, InvocationTargetException {
		String uid = request.getParameter("uid");
		userService.delUser(Integer.parseInt(uid));
		response.sendRedirect("user?method=list");
	}
	
	
	//У���˺��Ƿ��Ѵ���
	public void checkUser(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException, IllegalAccessException, InvocationTargetException {
		String account = request.getParameter("account");
		UserDB userDB = new UserDB();
		userDB.setAccount(account);
		List<UserDB> list = userService.getList(userDB);
		ResBean resBean = new ResBean();
		if(list != null && list.size() > 0 ) {
			resBean.setCode(400);
			resBean.setMsg("�˺ű�ռ��");
		}else {
			resBean.setCode(200);
			resBean.setMsg("�˺ſ���ʹ��");
		}
		//��resBeanת��Ϊjson�ַ���
		Gson gson = new Gson();
		String json = gson.toJson(resBean);
		response.getWriter().print(json);
	}
	
}
