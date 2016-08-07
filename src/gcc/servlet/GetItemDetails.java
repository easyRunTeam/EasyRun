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
import gcc.po.ClothesBean;
import net.sf.json.JSONArray;

/**
 * Servlet implementation class GetItemDetails
 */
@WebServlet("/GetItemDetails")
public class GetItemDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public GetItemDetails() {
        super();
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
		String itemID= request.getParameter("itemID");
		Connection conn = DaoBase.getConnection(true);
		ClothesDao clothesDao = new ClothesDao(conn);
		ClothesBean clothes = clothesDao.findByID(itemID);
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(clothes!=null){
			JSONArray jsonArray = JSONArray.fromObject(clothes);
			System.out.println(jsonArray.toString());
			response.getOutputStream().write(jsonArray.toString().getBytes());
		}
		else{
			response.getOutputStream().write("failed".getBytes());
		}
	}

	public static void main(String args[]){	//≤‚ ‘“ªœ¬
		Connection conn = DaoBase.getConnection(true);
		ClothesDao clothesDao = new ClothesDao(conn);
		String itemID = "9b9d7c32-7ea3-453f-965e-090a47479f88";
		ClothesBean clothes = clothesDao.findByID(itemID);
        if(clothes!=null){
			JSONArray jsonArray = JSONArray.fromObject(clothes);
			System.out.println(jsonArray.toString());
		}
    }
}
