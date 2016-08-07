package gcc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import gcc.po.FreePicBean;
import gcc.po.PicBean;


public class PictureDao
{
	Connection conn = null;

	public PictureDao(Connection c)
	{
		conn = c;
	}

	public ArrayList<PicBean> GetAllPics(int aID, int eventID)
			throws SQLException
	{
		final String sql1 = "select PicID,PicStatus,Author,Price from Pics where EventID=? AND AthleteID=?";
		try (PreparedStatement ps1 = conn.prepareStatement(sql1))
		{
			ps1.setInt(1, eventID);
			ps1.setInt(2, aID);
			ResultSet rs1 = ps1.executeQuery();
			ArrayList<PicBean> pics = new ArrayList<>();

			while (rs1.next())
			{
				PicBean pic = new PicBean();
				pic.setPicID(rs1.getString(1));
				pic.setPicStatus(rs1.getInt(2));
				pic.setAuthor(rs1.getString(3));
				pic.setPrice(rs1.getInt(4));
				pics.add(pic);
			}

			return pics;
		}
	}
	public ArrayList<PicBean> GetAllPics(String userID)
			throws SQLException
	{
		final String sql1 = "select EventName,upTime,UserName,HeadImgUrl, PicID,Pics.EventID,Price,PicStatus from Pics,Users,Events "
				+ "where Users.UserID=? "
				+ "and Pics.UserID = Users.UserID "
				+ "and Events.EventID = Pics.EventID "
				+ "order by upTime DESC";
		System.out.println(sql1);
		try (PreparedStatement ps1 = conn.prepareStatement(sql1))
		{
			ps1.setString(1, userID);
			ResultSet rs1 = ps1.executeQuery();

			ArrayList<PicBean> pics = new ArrayList<>();

			while (rs1.next())
			{
				PicBean fp = new PicBean();
				fp.setEventName(rs1.getString(1));
				fp.setUpTime(rs1.getLong(2));
				fp.setUserName(rs1.getString(3));
				fp.setHeadImgUrl(rs1.getString(4));
				fp.setPicID(rs1.getString(5));
				fp.setEventID(rs1.getString(6));
				fp.setUserID(userID);
				fp.setPrice(rs1.getInt(7));
				fp.setPicStatus(rs1.getInt(8));
				pics.add(fp);
			}
			return pics;
		}
	}

	public PicBean GetPicByPicID(String picID) throws SQLException
	{
		final String sql1 = "select PicStatus,Author,Price from Pics where PicID=?";
		try (PreparedStatement ps1 = conn.prepareStatement(sql1))
		{
			ps1.setString(1, picID);
			ResultSet rs1 = ps1.executeQuery();

			if (rs1.next())
			{
				PicBean pic = new PicBean();
				pic.setPicID(picID);
				pic.setPicStatus(rs1.getInt(1));
				pic.setAuthor(rs1.getString(2));
				pic.setPrice(rs1.getInt(3));
				return pic;
			}

			return null;
		}
	}

	public boolean AddPic(int eventID, int aID, PicBean pic) throws SQLException
	{
		final String sql1 = "insert into Pics (EventID,AthleteID,PicID,Price,Author,upTime,UserID,PicStatus) values(?,?,?,?,?,?,?,?)";
		try (PreparedStatement ps1 = conn.prepareStatement(sql1))
		{
			ps1.setInt(1, eventID);
			ps1.setInt(2, aID);
			ps1.setString(3, pic.getPicID());
			ps1.setInt(4, pic.getPrice());
			ps1.setString(5, pic.getAuthor());
			ps1.setLong(6,new Date().getTime());
			ps1.setString(7, pic.getUserID());
			ps1.setInt(8, 0);

			ps1.executeUpdate();
			return true;
		}
	}

	public PicBean UpdPic(PicBean pic) throws SQLException
	{
		final String sql1 = "update Pics set PicStatus=?,Price=?,Author=? where PicID=?";
		try (PreparedStatement ps1 = conn.prepareStatement(sql1))
		{
			ps1.setInt(1, pic.getPicStatus());
			ps1.setInt(2, pic.getPrice());
			ps1.setString(3, pic.getAuthor());
			ps1.setString(4, pic.getPicID());

			ps1.executeUpdate();
			return pic;
		}
	}
}
