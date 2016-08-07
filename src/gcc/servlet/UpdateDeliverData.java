package gcc.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gcc.dao.ClothesDao;
import gcc.dao.DaoBase;
import gcc.dao.DeliverDataDao;
import gcc.dao.UserDao;

/**
 * Servlet implementation class UpdateDeliverData
 */
@WebServlet("/UpdateDeliverData")
public class UpdateDeliverData extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateDeliverData() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String account = request.getParameter("account");
		String deliver_name = request.getParameter("deliver_name");
		String deliver_code = request.getParameter("deliver_code");
		String deliver_address = request.getParameter("deliver_address");
		String deliver_phone = request.getParameter("deliver_phone");
		Connection conn = DaoBase.getConnection(true);
		String userID = "";
		UserDao userDao = new UserDao(conn);
		try {
			userID = userDao.findUserByAccount(account);//根据account获得userID
			DeliverDataDao deliverDataDao = new DeliverDataDao(conn);
			Boolean result = false;
			result = deliverDataDao.update(deliver_name,userID,deliver_code,deliver_address,deliver_phone);
			conn.close();
			if(result==true){
				response.getOutputStream().write("succeed".getBytes());//更新成功
			}else response.getOutputStream().write("failed".getBytes());//未知错误
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]){	//测试一下
		String account = "123";
		String deliver_name = "葛丛丛";
		String deliver_code = "310023";
		String deliver_address = "浙江工业大学屏峰校区";
		String deliver_phone = "15700083580";
		Connection conn = DaoBase.getConnection(true);
		String userID = "";
		UserDao userDao = new UserDao(conn);
		try {
			userID = userDao.findUserByAccount(account);//根据account获得userID
			DeliverDataDao deliverDataDao = new DeliverDataDao(conn);
			Boolean result = false;
			result = deliverDataDao.update(deliver_name,userID,deliver_code,deliver_address,deliver_phone);
			conn.close();
			if(result==true){
				System.out.println("更新成功");//更新成功
			}else System.out.println("未知错误");//未知错误
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

}
