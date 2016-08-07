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

public class BuyPicture extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private List<String> filenames=new ArrayList<String>();
	
    public BuyPicture() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("--------< BuyPicture >--------");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		Connection conn = DaoBase.getConnection(true);
		PictureDao picturedao=new PictureDao(conn);
		UserDao userdao=new UserDao(conn);
		String userID=request.getParameter("userID");
		String picID=request.getParameter("picID");
		try
		{
			PicBean pic=picturedao.GetPicByPicID(picID);
			UserBean user=userdao.GetUser(userID);
			System.out.println(user.getAccount());
			System.out.println(user.getMoney());
			System.out.println(pic.getPrice());
			user.setMoney(user.getMoney()-pic.getPrice());
			System.out.println(user.getMoney());
			pic.setPicStatus(1);
			user=userdao.UpdUser(user);
			pic=picturedao.UpdPic(pic);
			
			response.getOutputStream().write("success".toString().getBytes());
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			response.getOutputStream().write("failed".toString().getBytes());
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
