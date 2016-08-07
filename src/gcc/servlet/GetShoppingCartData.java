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
 * Servlet implementation class GetShoppingCartData
 */
@WebServlet("/GetShoppingCartData")
public class GetShoppingCartData extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetShoppingCartData() {
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
		
		String userID = "";
		ArrayList<ClothesBean> clothesList = new ArrayList<ClothesBean>();
		Connection conn = DaoBase.getConnection(true);
		UserDao userDao = new UserDao(conn);
		try {
			userID = userDao.findUserByAccount(account);
			ClothesDao clothesDao = new ClothesDao(conn);
			clothesList = clothesDao.findCartALLByUserID(userID);
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(clothesList==null){
			response.getOutputStream().write("error".getBytes());//购买失败
		}
		else{
			if(clothesList.size()==0){
				response.getOutputStream().write("nothing".getBytes());//购物车为空
			}
			else{
				JSONArray jsonArray = JSONArray.fromObject(clothesList);
				System.out.println("\n--购物车数据--\n"+jsonArray.toString());
				response.getOutputStream().write(jsonArray.toString().getBytes());
			}
		}
	}

	public static void main(String args[]){	//测试一下
		String account = "123";
		String userID = "";
		ArrayList<ClothesBean> clothesList = new ArrayList<ClothesBean>();
		Connection conn = DaoBase.getConnection(true);
		UserDao userDao = new UserDao(conn);
		try {
			userID = userDao.findUserByAccount(account);
			ClothesDao clothesDao = new ClothesDao(conn);
			clothesList = clothesDao.findCartALLByUserID(userID);
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(clothesList==null){
			System.out.println("error");//购买失败
		}
		else{
			if(clothesList.size()==0){
				System.out.println("购物车为空");//购物车为空
			}
			else{
				JSONArray jsonArray = JSONArray.fromObject(clothesList);
				System.out.println("\n--购物车数据--\n"+jsonArray.toString());
			}
		}
    }
}
