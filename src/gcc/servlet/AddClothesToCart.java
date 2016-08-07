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
 * Servlet implementation class AddClothesToCart
 */
@WebServlet("/AddClothesToCart")
public class AddClothesToCart extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddClothesToCart() {
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
		String clothesID = request.getParameter("itemID");
		String userID = "";
		int result = 0;
		Connection conn = DaoBase.getConnection(true);
		UserDao userDao = new UserDao(conn);
		try {
			userID = userDao.findUserByAccount(account);//����account���userID
			ClothesDao clothesDao = new ClothesDao(conn);
			result = clothesDao.AddtoCart(clothesID, userID);
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		switch(result){
			case 1:
				response.getOutputStream().write("succeed".getBytes());//���빺�ﳵ
				break;
			case 2:
				response.getOutputStream().write("exist".getBytes());//��Ʒ�Ѵ��ڹ��ﳵ
				break;
			case 0:
				response.getOutputStream().write("failed".getBytes());//δ֪����
				break;
		}
	}
	
	public static void main(String args[]){	//����һ��
		String account = "123";
		String clothesID = "31d2f835-cdde-436d-a2fb-a9125c787bd1";
		String userID = "";
		int result = 0;
		Connection conn = DaoBase.getConnection(true);
		UserDao userDao = new UserDao(conn);
		try {
			userID = userDao.findUserByAccount(account);//����account���userID
			ClothesDao clothesDao = new ClothesDao(conn);
			result = clothesDao.AddtoCart(clothesID, userID);
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		switch(result){
			case 1:
				System.out.println("��ӵ����ﳵ�ɹ�");
				break;
			case 2:
				System.out.println("���ﳵ�и���Ʒ�Ѵ���");
				break;
			case 0:
				System.out.println("δ֪����");
				break;
		}
    }

}
