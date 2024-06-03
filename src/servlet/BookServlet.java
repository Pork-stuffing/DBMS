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
	
	//图书列表 分页
	public void listByPage(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		UserDB userDB =(UserDB) request.getSession().getAttribute("userDB");
		//根据userDB获取用户类型
		Integer role = userDB.getRole();
		String word = request.getParameter("word");
		String currentNum = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		PageTool<BookDB> pageTool = bookService.listByPage(currentNum,pageSize,word);
		String path = "book?method=listByPage";
		if(word!=null && word!=" ") {
			path+="&word="+word;
		}
		//生成前端分页按钮
		String pagation = PaginationUtils.getPagation(pageTool.getTotalCount(), pageTool.getCurrentPage(), pageTool.getPageSize(), path);
		List<TypeDB> typeList = typeService.list(null, null);
		request.setAttribute("pagation", pagation);
		request.setAttribute("typeList", typeList);
		request.setAttribute("word", word);
		request.setAttribute("bList", pageTool.getRows());
		//根据role判断跳转到哪个页面
		if(role == 1 ) {
			//普通用户
			request.getRequestDispatcher("user/select.jsp").forward(request, response);;

		}else {
			//管理员
			request.getRequestDispatcher("admin/admin_book.jsp").forward(request, response);;

		}
	}
	
	// 校验图书是否已存在
	public void checkBook(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, IllegalAccessException, InvocationTargetException {
		String bookName = request.getParameter("bookName");
		List<BookDB> list = bookService.list(bookName);
		ResBean resBean = new ResBean();
		if (list != null && list.size() > 0) { 
			resBean.setCode(400);
			resBean.setMsg("图书名称已经存在");
		} else {
			resBean.setCode(200);
			resBean.setMsg("图书名称可以使用");
		}
		// 将resBean转化为json字符串
		Gson gson = new Gson();
		String json = gson.toJson(resBean);
		response.getWriter().print(json);
	}
	
	public void addBook(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException, IllegalAccessException, InvocationTargetException {
		//使用BeanUtils减少代码量
		BookDB bookDB = new BookDB();
		BeanUtils.populate(bookDB,request.getParameterMap());
		bookDB.setTimes(0);
		bookService.addBook(bookDB);
		response.sendRedirect("book?method=listByPage");
	}
	
	//修改图书信息
	public void updBook(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException, IllegalAccessException, InvocationTargetException {
		//使用BeanUtils减少代码量
		BookDB bookDB = new BookDB();
		BeanUtils.populate(bookDB,request.getParameterMap());
		bookService.updBook(bookDB);
		response.sendRedirect("book?method=listByPage");
	}
	
	// 删除图书
	public void delBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		String bid = request.getParameter("bid");
		bookService.delBook(bid);
		request.getRequestDispatcher("book?method=listByPage").forward(request, response);
		
	}
	
	// 借阅图书
	public void borrowBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		String bid = request.getParameter("bid");
		//获取当前用户信息
		UserDB userDB =(UserDB) request.getSession().getAttribute("userDB");
		bookService.borrowBook(userDB,bid);
		request.getRequestDispatcher("history?method=list").forward(request, response);
	}
	
	// 还书
	public void backBook(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		// 获取借阅记录的id
		String hid = request.getParameter("hid");
		bookService.backBook(hid);
		request.getRequestDispatcher("history?method=backList").forward(request,response);
	}
	
}
