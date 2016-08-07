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
			String clothesID = UUID.randomUUID().toString();//����Ϊ36λ���ַ���
			ps1.setString(1, clothesID);
			ps1.setString(2, clothes.getBrand());
			ps1.setString(3, clothes.getName());
			ps1.setString(4, clothes.getDescribe());
			ps1.setString(5, clothes.getImgURL());
			ps1.setString(6, clothes.getSaleNumber());
			ps1.setString(7, clothes.getPrice());
			ps1.setString(8, clothes.getType());
			ps1.executeUpdate();
			System.out.println("-----------< ����clothes���ݳɹ���>-----------");
			return true;
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public int AddtoCart(String clothesID,String userID) {//���빺�ﳵ
		final String sql1 = "insert into ClothesCart (clothesID,UserID) values(?,?)";
		try (PreparedStatement ps1 = conn.prepareStatement(sql1);) {
			ps1.setString(1,clothesID);
			ps1.setString(2,userID);
			ps1.executeUpdate();
			return 1;//���빺�ﳵ�ɹ�
		}catch (SQLException e) {
			//������Ʒ�Ѽ��뵽���ﳵ�����ﳵ�Ѵ���
			final String sql2 = "select * from ClothesCart where clothesID='"+clothesID+"'"
					+ " and UserID='"+userID+"'";
			try (PreparedStatement ps2 = conn.prepareStatement(sql2)){
				ResultSet rs1 = ps2.executeQuery();
				if(rs1.next()){
					return 2;//��Ʒ�Ѵ��ڹ��ﳵ
				}
				else return 0;//δ֪����
			}catch (SQLException ez) {//δ֪����
				ez.printStackTrace();
				return 0;//δ֪����
			}
		}
	}
	
	public boolean deleteClothes(String userID,String clothesID){//���ﳵ��ɾ��
		final String sql1 = "delete from ClothesCart where clothesID=? and UserID=?";
		try (PreparedStatement ps1 = conn.prepareStatement(sql1);) {
			ps1.setString(1,clothesID);
			ps1.setString(2,userID);
			ps1.executeUpdate();
			return true;//ɾ���ɹ�
		}catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	public boolean BuyClothes(String userID,String clothesId){//����
		final String sql1 = "update Clothes set saleNumber=saleNumber+1 , repertory=repertory-1"
				+ " where clothesID ='"+clothesId+"'";
		try (PreparedStatement ps1 = conn.prepareStatement(sql1)){
			ps1.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
			return false;//����ʧ��
		}
		//�״ι���
		final String sql2 = "insert into ClothesPurchaseInfo (clothesID,UserID,number) values(?,?,?)";
		try (PreparedStatement ps2 = conn.prepareStatement(sql2)){
			ps2.setString(1,clothesId);
			ps2.setString(2,userID);
			ps2.setString(3, "1");//һ��ֻ�ܹ���һ��
			ps2.executeUpdate();
		}catch (SQLException e) {//�����Ѵ��ڣ�������������Ը���
			final String sql3 = "update ClothesPurchaseInfo set number=number+1 "
					+ "where clothesID='"+clothesId+"' "+"and UserID='"+userID+"'";
			try (PreparedStatement ps3 = conn.prepareStatement(sql3)){
				ps3.executeUpdate();
			}catch (SQLException ez) {//����ʧ�ܣ�����ʧ��
				ez.printStackTrace();
				return false;//����ʧ��
			}
			return true;//����ɹ�
		}
		return true;//����ɹ�
	}
	
	public ClothesBean findByID(String id){ //����ָ����
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
	
	public ArrayList<ClothesBean> findALL(){ //����ȫ��
		System.out.println("-----����ȫ��-----");
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
	
	public ArrayList<ClothesBean> findCartALLByUserID(String UserID){ //���ҹ��ﳵȫ��
		System.out.println("-----���ҹ��ﳵȫ������-----");
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
