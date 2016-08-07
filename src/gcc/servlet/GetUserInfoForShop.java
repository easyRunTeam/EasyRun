package gcc.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gcc.dao.DaoBase;
import gcc.dao.UserDao;
import gcc.po.UserBean;
import net.sf.json.JSONArray;

/**
 * Servlet implementation class GetUserInfoForShop
 */
@WebServlet("/GetUserInfoForShop")
public class GetUserInfoForShop extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetUserInfoForShop() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		UserBean user = null;
		String account = request.getParameter("account");
		try {
			Connection conn = DaoBase.getConnection(true);
			UserDao userDao = new UserDao(conn);
			user = userDao.GetUserForShop(account);
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(user!=null){
			JSONArray jsonArray = JSONArray.fromObject(user);
			System.out.println("\n----商城个人中心----\n"+jsonArray.toString());
			response.getOutputStream().write(jsonArray.toString().getBytes());
		}else{
			response.getOutputStream().write("failed".getBytes());
		}
	}

	public static void main(String args[]){	//测试一下
		String account = "123";
		UserBean user = null;
		try {
			Connection conn = DaoBase.getConnection(true);
			UserDao userDao = new UserDao(conn);
			user = userDao.GetUserForShop(account);
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(user!=null){
			JSONArray jsonArray = JSONArray.fromObject(user);
			System.out.println("\n----商城个人中心----\n"+jsonArray.toString());
		}else{
			System.out.println("failed");
		}
	}
}
