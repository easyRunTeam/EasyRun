package gcc.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import gcc.po.EventBean;
import gcc.dao.DaoBase;
import gcc.dao.EventDao;
@WebServlet(urlPatterns = "/MarathonRegister2", initParams =
{
	@WebInitParam(name="retrytime", value="60"),
	@WebInitParam(name="posibility", value="200"),
	@WebInitParam(name="total", value="2")
})
public class MarathonRegister2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection conn;
	
    public MarathonRegister2() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		String userID=(String)request.getAttribute("userID");
		conn = DaoBase.getConnection(true);
		EventDao eventdao=new EventDao(conn);
		Map<Integer, String> event=new HashMap<Integer, String>();
		try
		{
			
			ArrayList<EventBean> events = eventdao
					.GetEventByStatus(EventBean.Status.ongoing);

			for (EventBean eb : events)
			{
				
				event.put(eb.getEventID(), eb.getEventName());
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("System Error:\n" + e.getMessage());
		}
		finally
		{
			DaoBase.close(conn, null, null);
		}

		request.setAttribute("event", event);
		request.setAttribute("userID", userID);
		request.getRequestDispatcher("marathonRegister2.jsp").forward(request,response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
