package gcc.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import gcc.dao.DaoBase;
import gcc.dao.UserDao;
import gcc.po.UserBean;
import net.sf.json.JSONArray;

/**
 * @author ธ๐ดิดิ
 * @version 1.0*/
public class LoginServer extends HttpServlet{
	private static final long serialVersionUID = 1L;       
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub     
    	request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
    	HttpSession session=request.getSession();
    	String account= request.getParameter("account");
        String password= request.getParameter("password");
        String who=request.getParameter("who");
        int identity=Integer.parseInt(who);
        System.out.println("account: " + account );
        System.out.println("password: " + password );
        System.out.println("who: "+who);
        Connection conn = DaoBase.getConnection(true);
        UserDao userDao = new UserDao(conn);
        try {
        	UserBean user = userDao.findUser(account,password,identity);
			if(user!=null){
				//System.out.println("user account"+user.getAccount());
	        	//System.out.println("user password"+user.getPassword());
				JSONArray jsonArray = JSONArray.fromObject(user);
				session.setAttribute("user", user);
				System.out.println(jsonArray);
				response.getOutputStream().write(jsonArray.toString().getBytes());
				
			}
			else{
				//System.out.println("failed");
				response.getOutputStream().write("failed".getBytes());
			}
		} catch (SQLException e) {
			e.printStackTrace();
			response.getOutputStream().write("failed".getBytes());
		}
        finally
        {
        	DaoBase.close(conn, null, null);
        }
    
    	
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
    	HttpSession session=request.getSession();
    	String account= request.getParameter("account");
        String password= request.getParameter("password");
        String who=request.getParameter("who");
        int whose=Integer.parseInt(who);
        System.out.println("account: " + account );
        System.out.println("password: " + password );
        System.out.println("who:"+whose);
        Connection conn = DaoBase.getConnection(true);
        UserDao userDao = new UserDao(conn);
        try {
        	UserBean user = userDao.findUser(account,password,whose);
			if(user!=null){
				JSONArray jsonArray = JSONArray.fromObject(user);
				session.setAttribute("user", user);
				System.out.println(jsonArray);
				response.getOutputStream().write(jsonArray.toString().getBytes());
				
			}
			else{
				response.getOutputStream().write("failed".getBytes());
			}
		} catch (SQLException e) {
			e.printStackTrace();
			response.getOutputStream().write("failed".getBytes());
		}
        finally{
        	DaoBase.close(conn, null, null);
        }
    }
}
