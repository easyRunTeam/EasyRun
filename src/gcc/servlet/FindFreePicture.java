package gcc.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gcc.dao.DaoBase;
import gcc.dao.FreePicDao;
import gcc.dao.UserDao;
import gcc.po.FreePicBean;
import gcc.po.UserBean;
import net.sf.json.JSONArray;

/**
 * @author ¸ð´Ô´Ô
 * @version 1.0*/
public class FindFreePicture extends HttpServlet{
	private static final long serialVersionUID = 1L;       
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub      
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String account= request.getParameter("account");
        String password= request.getParameter("password");
        
        UserBean user = new UserBean();
        user.setAccount(account);
        user.setPassword(password);
        Connection conn = DaoBase.getConnection(true);
        FreePicDao freePicDao = new FreePicDao(conn);
        try {
        	ArrayList<FreePicBean> freePicList= freePicDao.GetFreePicsByUser(user);
			if(freePicList!=null){
				JSONArray jsonArray = JSONArray.fromObject(freePicList);
				response.getOutputStream().write(jsonArray.toString().getBytes());
			}
			else{
				response.getOutputStream().write("failed".getBytes());
			}
		} catch (SQLException e) {
			e.printStackTrace();
			response.getOutputStream().write("failed".getBytes());
		}
    }
    
    public static void main(String args[]){
    	ArrayList<FreePicBean> freePicList = new ArrayList<FreePicBean>();
    	FreePicBean f = new FreePicBean();
    	f.setDownloadCnt(1);
    	f.setEventName("º¼ÖÝÂíÀ­ËÉ");
    	f.setHeadImgUrl("UserIcon/o124UJFD&fwiS.jpg");
    	f.setUpTime(360000);
    	f.setUserName("¸ð´Ô´Ô");
    	for(int i=0;i<2;i++){
    		freePicList.add(f);
    	}
    	JSONArray jsonArray = JSONArray.fromObject(freePicList);
    	System.out.println(jsonArray.toString());
    }
}
