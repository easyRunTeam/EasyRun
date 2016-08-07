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
import gcc.dao.UserDao;

/**
 * Servlet implementation class DeleteClothes
 */
@WebServlet("/DeleteClothes")
public class DeleteClothes extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public DeleteClothes() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String clothesID = request.getParameter("itemID");
		String account = request.getParameter("account");
		String userID = "";
		Connection conn = DaoBase.getConnection(true);
		UserDao userDao = new UserDao(conn);
		try {
			userID = userDao.findUserByAccount(account);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ClothesDao clothesDao = new ClothesDao(conn);
		boolean result = clothesDao.deleteClothes(userID, clothesID);
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(result){
			response.getOutputStream().write("succeed".getBytes());//¹ºÂò³É¹¦
		}
		else{
			response.getOutputStream().write("failed".getBytes());//¹ºÂòÊ§°Ü
		}
	}

	public static void main(String args[]){	//²âÊÔ¹ºÂò
		String clothesID= "d3f9cad2-5106-4a18-b399-bebe8ee214f3";
		String account = "123";
		Connection conn = DaoBase.getConnection(true);
		String userID = "";
		UserDao userDao = new UserDao(conn);
		try {
			userID = userDao.findUserByAccount(account);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ClothesDao clothesDao = new ClothesDao(conn);
		boolean result = clothesDao.deleteClothes(userID, clothesID);
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(result){
			System.out.println("É¾³ý³É¹¦");//É¾³ý³É¹¦
		}
		else{
			System.out.println("É¾³ýÊ§°Ü");//É¾³ýÊ§°Ü
		}
    }
}
