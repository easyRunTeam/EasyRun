package gcc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import gcc.po.DeliverData;

public class DeliverDataDao {
	Connection conn = null;

	public DeliverDataDao(Connection c) {
		conn = c;
	}
	
	public boolean update(String deliver_name,String userID,String deliver_code,String deliver_address,String deliver_phone){//更新
		final String sql1 = "insert into DeliverData (UserID,deliverName,deliverCode,deliverAddress,deliverPhone) values(?,?,?,?,?)";
		try (PreparedStatement ps1 = conn.prepareStatement(sql1))
		{
			ps1.setString(1, userID);
			ps1.setString(2, deliver_name);
			ps1.setString(3, deliver_code);
			ps1.setString(4, deliver_address);
			ps1.setString(5, deliver_phone);
			ps1.executeUpdate();
			return true;
		}catch (SQLException e) {
			//若收货信息已存在，则更新
			final String sql2 = "update DeliverData set deliverName=? , deliverCode=? ,"
					+ " deliverAddress=?, deliverPhone=?"
					+ " where UserID=?";
			try (PreparedStatement ps1 = conn.prepareStatement(sql2))
			{
				ps1.setString(1, deliver_name);
				ps1.setString(2, deliver_code);
				ps1.setString(3, deliver_address);
				ps1.setString(4, deliver_phone);
				ps1.setString(5, userID);
				ps1.executeUpdate();
				return true;
			}catch (SQLException ex) {//未知错误
				ex.printStackTrace();
				return false;
			}
		}
	}
	
	public List<DeliverData> getDeliverData(String userID){
		final String sql1 = "select * from DeliverData where UserID=?";
		DeliverData deliverData = new DeliverData();
		List<DeliverData> deliverDataList = new ArrayList<>();
		try (PreparedStatement ps1 = conn.prepareStatement(sql1))
		{
			ps1.setString(1, userID);
			ResultSet rs1 = ps1.executeQuery();
			while (rs1.next()){
				deliverData.setDeliverName(rs1.getString("deliverName"));
				deliverData.setDeliverCode(rs1.getString("deliverCode"));
				deliverData.setDeliverAddress(rs1.getString("deliverAddress"));
				deliverData.setDeliverPhone(rs1.getString("deliverPhone"));
				deliverDataList.add(deliverData);
			}
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return deliverDataList;
	}
}