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
import gcc.po.FreePicBean;
import net.sf.json.JSONArray;

/**
 * Servlet implementation class GetClothesPageResource
 * 
 * 获取商城中服饰的所有数据资源
 */
public class GetClothesPageResource extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetClothesPageResource() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		Connection conn = DaoBase.getConnection(true);
        ClothesDao clothesDao = new ClothesDao(conn);
        ArrayList<ClothesBean> clothesList = new ArrayList<>();
        clothesList = clothesDao.findALL();
        try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if(clothesList!=null){
			JSONArray jsonArray = JSONArray.fromObject(clothesList);
			System.out.println(jsonArray.toString());
			response.getOutputStream().write(jsonArray.toString().getBytes());
		}
		else{
			response.getOutputStream().write("failed".getBytes());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	public static void main(String args[]){	//测试一下
    	Connection conn = DaoBase.getConnection(true);
    	ClothesDao clothesDao = new ClothesDao(conn);
        ArrayList<ClothesBean> clothesList = new ArrayList<>();
        clothesList = clothesDao.findALL();
        if(clothesList!=null){
			JSONArray jsonArray = JSONArray.fromObject(clothesList);
			System.out.println(jsonArray.toString());
		}
    }

}
