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

/**
 * @author ��Դ�
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
        
        System.out.println("account: " + account );
        System.out.println("password: " + password );
        Connection conn = DaoBase.getConnection(true);
        UserDao userDao = new UserDao(conn);
        try {
        	UserBean user = userDao.findUser(account,password);
			if(user!=null){
				//System.out.println("user account"+user.getAccount());
	        	//System.out.println("user password"+user.getPassword());
				session.setAttribute("user", user);
				response.getOutputStream().write("succeed".getBytes());
			}
			else{
				//System.out.println("failed");
				response.getOutputStream().write("failed".getBytes());
			}
		} catch (SQLException e) {
			e.printStackTrace();
			response.getOutputStream().write("failed".getBytes());
		}
    
    	
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
    	HttpSession session=request.getSession();
    	String account= request.getParameter("account");
        String password= request.getParameter("password");
        
        System.out.println("account: " + account );
        System.out.println("password: " + password );
        Connection conn = DaoBase.getConnection(true);
        UserDao userDao = new UserDao(conn);
        try {
        	UserBean user = userDao.findUser(account,password);
			if(user!=null){
				//System.out.println("user account"+user.getAccount());
	        	//System.out.println("user password"+user.getPassword());
				session.setAttribute("user", user);
				response.getOutputStream().write("succeed".getBytes());
			}
			else{
				//System.out.println("failed");
				response.getOutputStream().write("failed".getBytes());
			}
		} catch (SQLException e) {
			e.printStackTrace();
			response.getOutputStream().write("failed".getBytes());
		}
    }
}
