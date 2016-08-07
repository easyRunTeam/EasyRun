package gcc.servlet;


import java.io.IOException;

import java.sql.Connection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import gcc.dao.DaoBase;
import gcc.dao.PictureDao;
import gcc.dao.UserDao;

import gcc.po.PicBean;
import gcc.po.UserBean;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class MoneyEnough extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private List<String> filenames=new ArrayList<String>();
	
    public MoneyEnough() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("--------< MoneyEnough >--------");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		Connection conn = DaoBase.getConnection(true);
		PictureDao picturedao=new PictureDao(conn);
		UserDao userdao=new UserDao(conn);
		String userID=request.getParameter("userID");
		String picID=request.getParameter("picID");
		String eventID=request.getParameter("eventID");
		try
		{
			PicBean pic=picturedao.GetPicByPicID(picID);
			UserBean user=userdao.GetUser(userID);
			System.out.println(user.getMoney());
			System.out.println(pic.getPrice());

			if(user.getMoney()-pic.getPrice()>=0)
			{
				
				Map<String,String> map=new HashMap<String,String>();
				map.put("userID", userID);
				map.put("username", user.getUserName());
				map.put("price",String.valueOf(pic.getPrice()));
				map.put("money", String.valueOf(user.getMoney()));
				map.put("eventID", eventID);
				map.put("picID", picID);
				JSONObject json = JSONObject.fromObject(map);
				System.out.println(json.toString());
				response.getOutputStream().write(json.toString().getBytes());
			}
			else
			{
				response.getOutputStream().write("failed".getBytes());
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally{
			DaoBase.close(conn, null, null);
		}
		//¥Ú”°«Î«ÛÕ∑
		/*Enumeration names = request.getHeaderNames();
		System.out.println("==========================================================");
		while(names.hasMoreElements()){
	        String name = (String) names.nextElement();
	        System.out.println(name + ":" + request.getHeader(name));
	    }*/
		
	}

	
}
