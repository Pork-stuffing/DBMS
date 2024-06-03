package servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.jdi.InvocationException;
import com.sxt.utils.MyException;


/*
 * 所有的servlet都要继承此类
 */

public class BaseServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			//设置字符集编码
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=utf-8");
			//获取方法名
			String m =request.getParameter("method");
			//获取类
			Class <? extends BaseServlet> clazz = this.getClass();
			//获取要执行的方法
			Method method = clazz.getDeclaredMethod(m, HttpServletRequest.class,HttpServletResponse.class);
			method.setAccessible(true);
			method.invoke(this, request,response);
		}catch (InvocationTargetException e) {
			if(e.getTargetException() instanceof MyException) {
				request.setAttribute("msg", e.getTargetException().getMessage());
			}else {
				request.setAttribute("msg", "网络异常");
				e.printStackTrace();

			}
			request.getRequestDispatcher("error.jsp").forward(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("baseServlet异常处理");
			request.setAttribute("msg", "网络波动");
			request.getRequestDispatcher("error.jsp").forward(request, response);
		}
	}

}
