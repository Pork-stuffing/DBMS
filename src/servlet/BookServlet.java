package servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.google.gson.Gson;
import com.sxt.utils.PageTool;
import com.sxt.utils.PaginationUtils;
import com.sxt.utils.ResBean;

import entity.BookDB;
import entity.TypeDB;
import entity.UserDB;
import service.BookService;
import service.TypeService;

@WebServlet("/book")
public class BookServlet extends BaseServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BookService bookService = new BookService();
	
	private TypeService typeService = new TypeService();
	
	//ͼ���б� ��ҳ
	public void listByPage(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		UserDB userDB =(UserDB) request.getSession().getAttribute("userDB");
		//����userDB��ȡ�û�����
		Integer role = userDB.getRole();
		String word = request.getParameter("word");
		String currentNum = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		PageTool<BookDB> pageTool = bookService.listByPage(currentNum,pageSize,word);
		String path = "book?method=listByPage";
		if(word!=null && word!=" ") {
			path+="&word="+word;
		}
		//����ǰ�˷�ҳ��ť
		String pagation = PaginationUtils.getPagation(pageTool.getTotalCount(), pageTool.getCurrentPage(), pageTool.getPageSize(), path);
		List<TypeDB> typeList = typeService.list(null, null);
		request.setAttribute("pagation", pagation);
		request.setAttribute("typeList", typeList);
		request.setAttribute("word", word);
		request.setAttribute("bList", pageTool.getRows());
		//����role�ж���ת���ĸ�ҳ��
		if(role == 1 ) {
			//��ͨ�û�
			request.getRequestDispatcher("user/select.jsp").forward(request, response);;

		}else {
			//����Ա
			request.getRequestDispatcher("admin/admin_book.jsp").forward(request, response);;

		}
	}
	
	// У��ͼ���Ƿ��Ѵ���
	public void checkBook(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, IllegalAccessException, InvocationTargetException {
		String bookName = request.getParameter("bookName");
		List<BookDB> list = bookService.list(bookName);
		ResBean resBean = new ResBean();
		if (list != null && list.size() > 0) { 
			resBean.setCode(400);
			resBean.setMsg("ͼ�������Ѿ�����");
		} else {
			resBean.setCode(200);
			resBean.setMsg("ͼ�����ƿ���ʹ��");
		}
		// ��resBeanת��Ϊjson�ַ���
		Gson gson = new Gson();
		String json = gson.toJson(resBean);
		response.getWriter().print(json);
	}
	
	public void addBook(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException, IllegalAccessException, InvocationTargetException {
		//ʹ��BeanUtils���ٴ�����
		BookDB bookDB = new BookDB();
		BeanUtils.populate(bookDB,request.getParameterMap());
		bookDB.setTimes(0);
		bookService.addBook(bookDB);
		response.sendRedirect("book?method=listByPage");
	}
	
	//�޸�ͼ����Ϣ
	public void updBook(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException, IllegalAccessException, InvocationTargetException {
		//ʹ��BeanUtils���ٴ�����
		BookDB bookDB = new BookDB();
		BeanUtils.populate(bookDB,request.getParameterMap());
		bookService.updBook(bookDB);
		response.sendRedirect("book?method=listByPage");
	}
	
	// ɾ��ͼ��
	public void delBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		String bid = request.getParameter("bid");
		bookService.delBook(bid);
		request.getRequestDispatcher("book?method=listByPage").forward(request, response);
		
	}
	
	// ����ͼ��
	public void borrowBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		String bid = request.getParameter("bid");
		//��ȡ��ǰ�û���Ϣ
		UserDB userDB =(UserDB) request.getSession().getAttribute("userDB");
		bookService.borrowBook(userDB,bid);
		request.getRequestDispatcher("history?method=list").forward(request, response);
	}
	
	// ����
	public void backBook(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		// ��ȡ���ļ�¼��id
		String hid = request.getParameter("hid");
		bookService.backBook(hid);
		request.getRequestDispatcher("history?method=backList").forward(request,response);
	}
	
}
