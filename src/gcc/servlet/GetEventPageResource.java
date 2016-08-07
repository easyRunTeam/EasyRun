package gcc.servlet;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gcc.dao.DaoBase;
import gcc.dao.EventDao;
import gcc.po.EventBean;
import gcc.po.UserBean;
import net.sf.json.JSONArray;

public class GetEventPageResource extends HttpServlet{
	private static final long serialVersionUID = 1L;       
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub      
    	request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        Connection conn = DaoBase.getConnection(true);
        EventDao eventDao = new EventDao(conn);
        try {
        	System.out.println("GetEventPageResource");
        	ArrayList<EventBean> eventList= eventDao.GetAllEvents();
			if(eventList!=null){
				JSONArray jsonArray = JSONArray.fromObject(eventList);
				System.out.println(jsonArray.toString());
				 URLEncoder.encode(jsonArray.toString(), "utf-8");
				response.getOutputStream().write(jsonArray.toString().getBytes());
			}
			else{
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
    
    
    @SuppressWarnings("null")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        Connection conn = DaoBase.getConnection(true);
        EventDao eventDao = new EventDao(conn);
        try {
        	ArrayList<EventBean> eventList= eventDao.GetAllEvents();
			if(eventList!=null){
				JSONArray jsonArray = JSONArray.fromObject(eventList);
				System.out.println(jsonArray.toString());
				 URLEncoder.encode(jsonArray.toString(), "utf-8");
				response.getOutputStream().write(jsonArray.toString().getBytes());
			}
			else{
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
}
