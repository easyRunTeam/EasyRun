package gcc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import gcc.po.ClothesBean;

public class ClothesDao {
	Connection conn = null;

	public ClothesDao(Connection c) {
		conn = c;
	}
	
	public boolean AddClothes(ClothesBean clothes) {
		final String sql = "insert into Clothes (clothesID,brand,name,describe,imgURL,saleNumber,price,type) values(?,?,?,?,?,?,?,?)";
		try (PreparedStatement ps1 = conn.prepareStatement(sql);) {
			String clothesID = UUID.randomUUID().toString();//长度为36位的字符串
			ps1.setString(1, clothesID);
			ps1.setString(2, clothes.getBrand());
			ps1.setString(3, clothes.getName());
			ps1.setString(4, clothes.getDescribe());
			ps1.setString(5, clothes.getImgURL());
			ps1.setString(6, clothes.getSaleNumber());
			ps1.setString(7, clothes.getPrice());
			ps1.setString(8, clothes.getType());
			ps1.executeUpdate();
			System.out.println("-----------< 插入clothes数据成功！>-----------");
			return true;
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public int AddtoCart(String clothesID,String userID) {//加入购物车
		final String sql1 = "insert into ClothesCart (clothesID,UserID) values(?,?)";
		try (PreparedStatement ps1 = conn.prepareStatement(sql1);) {
			ps1.setString(1,clothesID);
			ps1.setString(2,userID);
			ps1.executeUpdate();
			return 1;//加入购物车成功
		}catch (SQLException e) {
			//若该商品已加入到购物车，购物车已存在
			final String sql2 = "select * from ClothesCart where clothesID='"+clothesID+"'"
					+ " and UserID='"+userID+"'";
			try (PreparedStatement ps2 = conn.prepareStatement(sql2)){
				ResultSet rs1 = ps2.executeQuery();
				if(rs1.next()){
					return 2;//商品已存在购物车
				}
				else return 0;//未知错误
			}catch (SQLException ez) {//未知错误
				ez.printStackTrace();
				return 0;//未知错误
			}
		}
	}
	
	public boolean deleteClothes(String userID,String clothesID){//购物车中删除
		final String sql1 = "delete from ClothesCart where clothesID=? and UserID=?";
		try (PreparedStatement ps1 = conn.prepareStatement(sql1);) {
			ps1.setString(1,clothesID);
			ps1.setString(2,userID);
			ps1.executeUpdate();
			return true;//删除成功
		}catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	public boolean BuyClothes(String userID,String clothesId){//购买
		final String sql1 = "update Clothes set saleNumber=saleNumber+1 , repertory=repertory-1"
				+ " where clothesID ='"+clothesId+"'";
		try (PreparedStatement ps1 = conn.prepareStatement(sql1)){
			ps1.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
			return false;//购买失败
		}
		//首次购买
		final String sql2 = "insert into ClothesPurchaseInfo (clothesID,UserID,number) values(?,?,?)";
		try (PreparedStatement ps2 = conn.prepareStatement(sql2)){
			ps2.setString(1,clothesId);
			ps2.setString(2,userID);
			ps2.setString(3, "1");//一次只能购买一件
			ps2.executeUpdate();
		}catch (SQLException e) {//数据已存在（购买过），尝试更新
			final String sql3 = "update ClothesPurchaseInfo set number=number+1 "
					+ "where clothesID='"+clothesId+"' "+"and UserID='"+userID+"'";
			try (PreparedStatement ps3 = conn.prepareStatement(sql3)){
				ps3.executeUpdate();
			}catch (SQLException ez) {//更新失败，购买失败
				ez.printStackTrace();
				return false;//购买失败
			}
			return true;//购买成功
		}
		return true;//购买成功
	}
	
	public ClothesBean findByID(String id){ //查找指定项
		final String sql = "select * from Clothes where clothesID = '"+id+"'";
		ClothesBean clothes = new ClothesBean();
		try (PreparedStatement ps1 = conn.prepareStatement(sql))
		{
			ResultSet rs1 = ps1.executeQuery();
			if(rs1.next()){
				clothes.setId(rs1.getString("clothesID"));
				clothes.setBrand(rs1.getString("brand"));
				clothes.setName(rs1.getString("name"));
				clothes.setDescribe(rs1.getString("describe"));
				clothes.setImgURL(rs1.getString("imgURL"));
				clothes.setSaleNumber(rs1.getString("saleNumber"));
				clothes.setPrice(rs1.getString("price"));
				clothes.setType(rs1.getString("type"));
				clothes.setRepertory(rs1.getString("repertory"));
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return clothes;
	}
	
	public ArrayList<ClothesBean> findALL(){ //查找全部
		System.out.println("-----查找全部-----");
		final String sql1 = "select * from Clothes";
		try (PreparedStatement ps1 = conn.prepareStatement(sql1))
		{
			ResultSet rs1 = ps1.executeQuery();
			ArrayList<ClothesBean> clothesList = new ArrayList<>();
			while(rs1.next())
			{
				ClothesBean clothes = new ClothesBean();
				clothes.setId(rs1.getString("clothesID"));
				clothes.setBrand(rs1.getString("brand"));
				clothes.setName(rs1.getString("name"));
				clothes.setDescribe(rs1.getString("describe"));
				clothes.setImgURL(rs1.getString("imgURL"));
				clothes.setSaleNumber(rs1.getString("saleNumber"));
				clothes.setPrice(rs1.getString("price"));
				clothes.setType(rs1.getString("type"));
				clothes.setRepertory(rs1.getString("repertory"));
				clothesList.add(clothes);
			}
			return clothesList;
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public ArrayList<ClothesBean> findCartALLByUserID(String UserID){ //查找购物车全部
		System.out.println("-----查找购物车全部数据-----");
		final String sql1 = "select Clothes.clothesID,brand,name,describe,imgURL,price"
				+ " from Clothes , ClothesCart ,Users"
				+ " where ClothesCart.UserID=Users.UserID"
				+ " and ClothesCart.ClothesID=Clothes.ClothesID"
				+ " and ClothesCart.UserID='"+UserID+"'";
		try (PreparedStatement ps1 = conn.prepareStatement(sql1))
		{
			ResultSet rs1 = ps1.executeQuery();
			ArrayList<ClothesBean> clothesList = new ArrayList<>();
			while(rs1.next())
			{
				ClothesBean clothes = new ClothesBean();
				clothes.setId(rs1.getString("clothesID"));
				clothes.setBrand(rs1.getString("brand"));
				clothes.setName(rs1.getString("name"));
				clothes.setDescribe(rs1.getString("describe"));
				clothes.setImgURL(rs1.getString("imgURL"));
				clothes.setPrice(rs1.getString("price"));
				clothesList.add(clothes);
			}
			return clothesList;
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
