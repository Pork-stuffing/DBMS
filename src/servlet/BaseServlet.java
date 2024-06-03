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
 * ���е�servlet��Ҫ�̳д���
 */

public class BaseServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			//�����ַ�������
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=utf-8");
			//��ȡ������
			String m =request.getParameter("method");
			//��ȡ��
			Class <? extends BaseServlet> clazz = this.getClass();
			//��ȡҪִ�еķ���
			Method method = clazz.getDeclaredMethod(m, HttpServletRequest.class,HttpServletResponse.class);
			method.setAccessible(true);
			method.invoke(this, request,response);
		}catch (InvocationTargetException e) {
			if(e.getTargetException() instanceof MyException) {
				request.setAttribute("msg", e.getTargetException().getMessage());
			}else {
				request.setAttribute("msg", "�����쳣");
				e.printStackTrace();

			}
			request.getRequestDispatcher("error.jsp").forward(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("baseServlet�쳣����");
			request.setAttribute("msg", "���粨��");
			request.getRequestDispatcher("error.jsp").forward(request, response);
		}
	}

}
