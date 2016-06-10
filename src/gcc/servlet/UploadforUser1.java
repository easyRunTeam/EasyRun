package gcc.servlet;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gcc.util.MD5;

import java.io.IOException;
import java.net.URLEncoder;

import gcc.dao.DaoBase;
import gcc.dao.EventDao;
import gcc.dao.FreePicDao;
import gcc.dao.UserDao;
import gcc.po.EventBean;
import gcc.po.EventBean.Status;
import gcc.po.FreePicBean;
import gcc.po.UserBean;
import net.sf.json.JSONArray;

/**
 * @author ΩØΩı≈Ù
 * @version 1.0*/
public class UploadforUser1 extends HttpServlet {

	

		private static final long serialVersionUID = 1L;       
	    
	    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        // TODO Auto-generated method stub      
	    	request.setCharacterEncoding("UTF-8");
	        response.setCharacterEncoding("UTF-8");
	       

	        String account= request.getParameter("account");
	        //UserBean user = new UserBean();
	    
	        Connection conn = DaoBase.getConnection(true);
	        EventDao eventDao = new EventDao(conn);
	        try {
	        	ArrayList<EventBean> eventList= eventDao.GetAllEvents();
	        	String [] events=new String[eventList.size()];
	        	for(int i=0;i<eventList.size();i++)
	        		events[i]=eventList.get(i).getEventName();
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
	    }
	    
	    
	    @SuppressWarnings("null")
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    	request.setCharacterEncoding("UTF-8");
	        response.setCharacterEncoding("UTF-8");

	        String account= request.getParameter("account");
	        UserBean user = new UserBean();
	        user.setAccount(account);
	        Connection conn = DaoBase.getConnection(true);
	        EventDao eventDao = new EventDao(conn);
	        try {
	        	ArrayList<EventBean> eventList= eventDao.GetAllEvents();
	        	String [] events=new String[eventList.size()];
	        	for(int i=0;i<eventList.size();i++)
	        		events[i]=eventList.get(i).getEventName();
	        		
	        	
				if(eventList!=null){
					JSONArray jsonArray = JSONArray.fromObject(eventList);
					System.out.println(jsonArray.toString());
					
					response.getOutputStream().write(URLEncoder.encode(jsonArray.toString(), "UTF-8").getBytes());
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
	    	ArrayList<EventBean> freePicList = new ArrayList<EventBean>();
	    	EventBean f = new EventBean();
	    	f.setEventID(1);
	    	f.setEventName("gcc");
	    	f.setEventStatus(Status.ongoing.ordinal());
	    	
	    	for(int i=0;i<2;i++){
	    		freePicList.add(f);
	    		
	    	}
	    	String []events =new String[freePicList.size()];
	    	for(int i=0;i<2;i++){
	    	    events[i]=(freePicList.get(i).getEventName());
	    	}
	    	JSONArray jsonArray = JSONArray.fromObject(freePicList);
	    	System.out.println(jsonArray.toString());
	    }
	    
	}


