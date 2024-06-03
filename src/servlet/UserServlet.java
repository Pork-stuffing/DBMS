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

//用户
@WebServlet("/user")
public class UserServlet extends BaseServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private UserService userService = new UserService();
	
	//用户列表 分页
	public void list(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		String currentNum = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		PageTool<UserDB> pageTool = userService.list(currentNum,pageSize);
		//生成前端分页按钮
		String pagation = PaginationUtils.getPagation(pageTool.getTotalCount(), pageTool.getCurrentPage(), pageTool.getPageSize(), "user?method=list");
		request.setAttribute("pagation", pagation);
		request.setAttribute("uList", pageTool.getRows());
		request.getRequestDispatcher("admin/admin_user.jsp").forward(request, response);;
	}
	
	//添加用户
	public void addUser(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException, IllegalAccessException, InvocationTargetException {
		//使用BeanUtils减少代码量
		UserDB userDB = new UserDB();
		BeanUtils.populate(userDB,request.getParameterMap());
		userDB.setTimes(0);
		userDB.setPassword(MD5.valueOf(userDB.getPassword()));
		userService.addUser(userDB);
		response.sendRedirect("user?method=list");
	}
	
	//修改用户信息
	public void updUser(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException, IllegalAccessException, InvocationTargetException {
		//使用BeanUtils减少代码量
		UserDB userDB = new UserDB();
		BeanUtils.populate(userDB,request.getParameterMap());
		userService.updUser(userDB);
		response.sendRedirect("user?method=list");
	}

	//删除用户
	public void delUser(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException, IllegalAccessException, InvocationTargetException {
		String uid = request.getParameter("uid");
		userService.delUser(Integer.parseInt(uid));
		response.sendRedirect("user?method=list");
	}
	
	
	//校验账号是否已存在
	public void checkUser(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException, IllegalAccessException, InvocationTargetException {
		String account = request.getParameter("account");
		UserDB userDB = new UserDB();
		userDB.setAccount(account);
		List<UserDB> list = userService.getList(userDB);
		ResBean resBean = new ResBean();
		if(list != null && list.size() > 0 ) {
			resBean.setCode(400);
			resBean.setMsg("账号被占用");
		}else {
			resBean.setCode(200);
			resBean.setMsg("账号可以使用");
		}
		//将resBean转化为json字符串
		Gson gson = new Gson();
		String json = gson.toJson(resBean);
		response.getWriter().print(json);
	}
	
}
