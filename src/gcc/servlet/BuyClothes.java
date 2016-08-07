package gcc.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gcc.dao.ClothesDao;
import gcc.dao.DaoBase;
import gcc.dao.UserDao;
import gcc.po.ClothesBean;
import net.sf.json.JSONArray;

/**
 * Servlet implementation class BuyClothes
 */
@WebServlet("/BuyClothes")
public class BuyClothes extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BuyClothes() {
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
		String itemID = request.getParameter("itemID");
		String account = request.getParameter("account");
		String userID = "";
		String jifen = "";
		Connection conn = DaoBase.getConnection(true);
		UserDao userDao = new UserDao(conn);
		try {
			userID = userDao.findUserByAccount(account);
			jifen = userDao.findJifenByAccount(account);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ClothesDao clothesDao = new ClothesDao(conn);
		int result0 = userDao.updateJifen(account, jifen, itemID);
		if(result0==0){
			response.getOutputStream().write("jifenNotEnough".getBytes());//»ý·Ö²»×ã
			return;
		}
		boolean result = clothesDao.BuyClothes(userID, itemID);
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(result && (result0==1)){
			response.getOutputStream().write("succeed".getBytes());//¹ºÂò³É¹¦
		}
		else{
			response.getOutputStream().write("failed".getBytes());//¹ºÂòÊ§°Ü
		}
	}

	public static void main(String args[]){	//²âÊÔ¹ºÂò
		String itemID= "9b9d7c32-7ea3-453f-965e-090a47479f88";
		String account = "123";
		String userID = "";
		String jifen = "";
		Connection conn = DaoBase.getConnection(true);
		UserDao userDao = new UserDao(conn);
		try {
			userID = userDao.findUserByAccount(account);
			jifen = userDao.findJifenByAccount(account);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ClothesDao clothesDao = new ClothesDao(conn);
		int result0 = userDao.updateJifen(account, jifen, itemID);
		if(result0==0){
			System.out.println("»ý·Ö²»×ã");//»ý·Ö²»×ã
			return;
		}
		boolean result = clothesDao.BuyClothes(userID, itemID);

		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(result && (result0==1)){
			System.out.println("¹ºÂò³É¹¦");//¹ºÂò³É¹¦
		}
		else{
			System.out.println("¹ºÂòÊ§°Ü");//¹ºÂòÊ§°Ü
		}
    }
}
