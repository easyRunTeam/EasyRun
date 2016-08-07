package gcc.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gcc.dao.ClothesDao;
import gcc.dao.DaoBase;
import gcc.dao.DeliverDataDao;
import gcc.dao.UserDao;
import gcc.po.ClothesBean;
import gcc.po.DeliverData;
import net.sf.json.JSONArray;

/**
 * Servlet implementation class GetDeliverData
 */
@WebServlet("/GetDeliverData")
public class GetDeliverData extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetDeliverData() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String account = request.getParameter("account");
		String userID = "";
		Connection conn = DaoBase.getConnection(true);
		UserDao userDao = new UserDao(conn);
		List<DeliverData> deliverDataList = new ArrayList<>();
		try {
			userID = userDao.findUserByAccount(account);//���UserID
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//����UserID��ѯ�ջ���Ϣ
		DeliverDataDao deliverDataDao = new DeliverDataDao(conn);
		deliverDataList = deliverDataDao.getDeliverData(userID);
		if(deliverDataList==null){
			response.getOutputStream().write("failed".getBytes());//δ֪����
		}
		else{
			JSONArray jsonArray = JSONArray.fromObject(deliverDataList);
			System.out.println(jsonArray.toString());
			response.getOutputStream().write(jsonArray.toString().getBytes());
		}
	}

	public static void main(String args[]){	//����һ��
		String account = "123";
		String userID = "";
		Connection conn = DaoBase.getConnection(true);
		UserDao userDao = new UserDao(conn);
		List<DeliverData> deliverDataList = new ArrayList<>();
		try {
			userID = userDao.findUserByAccount(account);//���UserID
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//����UserID��ѯ�ջ���Ϣ
		DeliverDataDao deliverDataDao = new DeliverDataDao(conn);
		deliverDataList = deliverDataDao.getDeliverData(userID);
		if(deliverDataList==null){
			System.out.println("failed");
		}
		else{
			JSONArray jsonArray = JSONArray.fromObject(deliverDataList);
			System.out.println("\n-----------<�ջ���Ϣ>-----------\n");
			System.out.println(jsonArray.toString());
		}
	}
}
