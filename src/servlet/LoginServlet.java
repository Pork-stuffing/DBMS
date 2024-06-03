package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sxt.utils.MD5;

import entity.UserDB;
import service.UserService;

@WebServlet("/login")
public class LoginServlet extends BaseServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private UserService userService = new UserService();
	
	/*
	 * �û���¼
	 */
	
	public void login(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		String account = request.getParameter("account");
		String password = request.getParameter("password");
		HttpSession session = request.getSession();
		UserDB userDB = userService.login(account, MD5.valueOf(password));
		if(userDB==null) {
			//�˺��������
			request.setAttribute("msg", "�˺��������");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}else {
			//��½�ɹ�
			session.setAttribute("userDB", userDB);
			request.getRequestDispatcher("index.jsp").forward(request, response);
		}
	}
	
}
