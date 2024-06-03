package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter("/*")
public class LoginFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		//ǿ��ת������
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		//��ȡ����·��
		String uri = req.getRequestURI();
		if(uri.contains("/login")||uri.contains("/static")) {
			//����
			chain.doFilter(req, res);
		}else {
			//��֤�Ƿ��¼
			Object userDB = req.getSession().getAttribute("userDB");
			if(userDB!= null) {
				chain.doFilter(req, res);
			}else {
				res.sendRedirect("login.jsp");
			}
		}
		
	}

}
