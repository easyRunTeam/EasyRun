package gcc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import gcc.po.UserBean;


public class UserDao
{
	Connection conn = null;

	public UserDao(Connection c)
	{
		conn = c;
	}
	
	public UserBean GetUser(String userID) throws SQLException
	{
		final String sql1 = "select * from Users where UserID=?";
		try (PreparedStatement ps1 = conn.prepareStatement(sql1))
		{
			ps1.setString(1, userID);
			ResultSet rs1 = ps1.executeQuery();
			if (!rs1.next())// no match
				return null;

			// get user
			UserBean user = new UserBean();
			user.setUserID(userID);
			user.setUserName(rs1.getString("UserName"));
			user.setCelphone(rs1.getString("Celphone"));
			user.setEmail(rs1.getString("Email"));
			user.setGender(rs1.getInt("Gender"));
			user.setIdentityCard(rs1.getString("IdentityCard"));
			user.setIdentityPic(rs1.getString("IdentityPic"));
			user.setBloodType(rs1.getString("BloodType"));
			user.setAddress(rs1.getString("Address"));
			user.setHeight(rs1.getFloat("Height"));
			user.setWeight(rs1.getFloat("Weight"));
			user.setUrgencyContact(rs1.getString("UrgencyContact"));
			user.setUrgencyPhone(rs1.getString("UrgencyPhone"));
			user.setMoney(rs1.getInt("Money"));
			user.setAccount(rs1.getString("Account"));
			user.setWhose(rs1.getInt("whose"));
			user.setJifen(rs1.getString("jifen"));
			user.setPassword(rs1.getString("Password"));
			user.setRealName(rs1.getString("RealName"));
			return user;
		}
	}
	
	public UserBean GetUserbyAccount(String account) throws SQLException
	{
		final String sql1 = "select * from Users where Account=?";
		try (PreparedStatement ps1 = conn.prepareStatement(sql1))
		{
			ps1.setString(1, account);
			ResultSet rs1 = ps1.executeQuery();
			if (!rs1.next())// no match
				return null;

			// get user
			UserBean user = new UserBean();
			user.setUserID(rs1.getString("UserID"));
			user.setUserName(rs1.getString("UserName"));
			user.setCelphone(rs1.getString("Celphone"));
			user.setEmail(rs1.getString("Email"));
			user.setGender(rs1.getInt("Gender"));
			user.setIdentityCard(rs1.getString("IdentityCard"));
			user.setIdentityPic(rs1.getString("IdentityPic"));
			user.setBloodType(rs1.getString("BloodType"));
			user.setAddress(rs1.getString("Address"));
			user.setHeight(rs1.getFloat("Height"));
			user.setWeight(rs1.getFloat("Weight"));
			user.setUrgencyContact(rs1.getString("UrgencyContact"));
			user.setUrgencyPhone(rs1.getString("UrgencyPhone"));
			user.setMoney(rs1.getInt("Money"));
			user.setAccount(rs1.getString("Account"));
			user.setWhose(rs1.getInt("whose"));
			user.setJifen(rs1.getString("jifen"));
			
			return user;
		}
	}
	public UserBean findUser(String account, String password,int Whose ) throws SQLException
	{
		final String sql1 = "select * from Users where Account=? and Password=? and Whose=?";
		System.out.println(sql1);
		try (PreparedStatement ps1 = conn.prepareStatement(sql1))
		{
			ps1.setString(1, account);
			ps1.setString(2, password);
			ps1.setInt(3, Whose);
			ResultSet rs1 = ps1.executeQuery();
			if (!rs1.next())// no match
				return null;

			// get user
			UserBean user = new UserBean();
			user.setUserID(rs1.getString("UserID"));
			user.setUserName(rs1.getString("UserName"));
			user.setCelphone(rs1.getString("Celphone"));
			user.setEmail(rs1.getString("Email"));
			user.setGender(rs1.getInt("Gender"));
			user.setIdentityCard(rs1.getString("IdentityCard"));
			user.setIdentityPic(rs1.getString("IdentityPic"));
			user.setBloodType(rs1.getString("BloodType"));
			user.setAddress(rs1.getString("Address"));
			user.setHeight(rs1.getFloat("Height"));
			user.setWeight(rs1.getFloat("Weight"));
			user.setUrgencyContact(rs1.getString("UrgencyContact"));
			user.setUrgencyPhone(rs1.getString("UrgencyPhone"));
			user.setJifen(rs1.getString("jifen"));
			user.setWhose(rs1.getInt("whose"));
			user.setMoney(rs1.getInt("Money"));
			user.setHeadImgUrl(rs1.getString("HeadImgUrl"));
			user.setAccount(rs1.getString("Account"));
			return user;
		}
	}
	public String findUserByAccount(String account) throws SQLException
	{
		final String sql1 = "select * from Users where Account=?";
		try (PreparedStatement ps1 = conn.prepareStatement(sql1))
		{
			ps1.setString(1, account);
			ResultSet rs1 = ps1.executeQuery();
			if (!rs1.next())// no match
				return null;
			return rs1.getString("UserID");
		}
	}

	public boolean AddBaseUser(UserBean user)
	{
		final String sql = "insert into Users (UserID,Account,Password,HeadImgUrl,Whose,Money) values(?,?,?,?,?,?)";
		try (PreparedStatement ps1 = conn.prepareStatement(sql))
		{
			
			ps1.setString(1, user.getUserID());
			ps1.setString(2, user.getAccount());
			ps1.setString(3, user.getPassword());
			ps1.setString(4, user.getHeadImgUrl());
			ps1.setInt(5, user.getWhose());
			ps1.setFloat(6, 0);
			ps1.executeUpdate();
			return true;
		}catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	
	public UserBean UpdUser(UserBean user) throws SQLException
	{
		final String sql1 = "update Users set UserName=?,Celphone=?,Email=?,IdentityCard=?,"
				+ "IdentityPic=?,BloodType=?,Address=?,Height=?,Weight=?,UrgencyContact=?,UrgencyPhone=?,"
				+"whose=?,Money=?,jifen=?"
				+ " where Account=?";
		try (PreparedStatement ps1 = conn.prepareStatement(sql1))
		{
			ps1.setString(1, user.getUserName());
			ps1.setString(2, user.getCelphone());
			ps1.setString(3, user.getEmail());
			ps1.setString(4, user.getIdentityCard());
			ps1.setString(5, user.getIdentityPic());
			ps1.setString(6, user.getBloodType());
			ps1.setString(7, user.getAddress());
			ps1.setFloat(8, user.getHeight());
			ps1.setFloat(9, user.getWeight());
			ps1.setString(10, user.getUrgencyContact());
			ps1.setString(11, user.getUrgencyPhone());
			ps1.setInt(12,user.getWhose());
			ps1.setInt(13, user.getMoney());
			ps1.setString(14, user.getJifen());
			System.out.println(user.getMoney());
			System.out.println(user.getAccount());
			ps1.setString(15, user.getAccount());

			//ps1.executeUpdate();
			int a=ps1.executeUpdate();;
			System.out.println(a);
			return user;
		}
	}
	
	public boolean registerUpdate(UserBean user)
	{
		//注册时详细信息写入数据库
		final String sql1 = "update Users set UserName=?,Email=?"
						  + " where Account=? and Password=?";
		try (PreparedStatement ps1 = conn.prepareStatement(sql1))
		{
			ps1.setString(1, user.getUserName());
			ps1.setString(2, user.getEmail());
			ps1.setString(3, user.getAccount());
			ps1.setString(4, user.getPassword());
			ps1.executeUpdate();
			return true;
		}catch(SQLException e){
			return false;
		}
	}



public UserBean GetUserForShop(String account){//获取用户在商城中的相关信息
		UserBean user = new UserBean();
		final String sql1 = "select * from Users where Account=?";
		try (PreparedStatement ps1 = conn.prepareStatement(sql1))
		{
			ps1.setString(1, account);
			ResultSet rs1 = ps1.executeQuery();
			if (rs1.next()){
				user.setUserID(rs1.getString("UserID"));
				user.setUserName(rs1.getString("UserName"));
				user.setCelphone(rs1.getString("Celphone"));
				user.setEmail(rs1.getString("Email"));
				user.setGender(rs1.getInt("Gender"));
				user.setIdentityCard(rs1.getString("IdentityCard"));
				user.setIdentityPic(rs1.getString("IdentityPic"));
				user.setBloodType(rs1.getString("BloodType"));
				user.setAddress(rs1.getString("Address"));
				user.setHeight(rs1.getFloat("Height"));
				user.setWeight(rs1.getFloat("Weight"));
				user.setUrgencyContact(rs1.getString("UrgencyContact"));
				user.setUrgencyPhone(rs1.getString("UrgencyPhone"));
				user.setMoney(rs1.getInt("Money"));
				user.setAccount(rs1.getString("Account"));
				user.setWhose(rs1.getInt("whose"));
				user.setJifen(rs1.getString("jifen"));
				user.setPassword(rs1.getString("Password"));
				user.setRealName(rs1.getString("RealName"));
				user.setHeadImgUrl(rs1.getString("HeadImgUrl"));
				return user;
			}else return null;//找不到该用户
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
public int updateJifen(String account,String jifen,String clothesID)
{
	final String sql0 = "select price from Clothes  where clothesID='"+clothesID+"'";
	String price = "";
	try (PreparedStatement ps0 = conn.prepareStatement(sql0))
	{
		ResultSet rs1 = ps0.executeQuery();
		if (!rs1.next())// no match
			return -1;
		else{
			price = rs1.getString("price");
			System.out.println("price="+price);
			if(Integer.parseInt(jifen)<Integer.parseInt(price)){
				System.out.println("积分不足");
				return 0;
			}
		}	
	}catch (SQLException e) {
		e.printStackTrace();
		return -1;
	}
	final String sql1 = "update Users set jifen=jifen-"+price+" where Account=?";
	try (PreparedStatement ps1 = conn.prepareStatement(sql1))
	{
		ps1.setString(1, account);
		ps1.executeUpdate();
		return 1;
	}catch (SQLException e) {
		e.printStackTrace();
		return -1;
	}
}



public String findJifenByAccount(String account)
{
	final String sql1 = "select * from Users where Account=?";
	try (PreparedStatement ps1 = conn.prepareStatement(sql1))
	{
		ps1.setString(1, account);
		ResultSet rs1 = ps1.executeQuery();
		if (!rs1.next())// no match
			return null;
		return rs1.getString("jifen");
	}catch (SQLException e) {
		e.printStackTrace();
		return null;
	}
}

}
