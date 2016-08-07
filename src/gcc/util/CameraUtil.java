package gcc.util;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.swing.ImageIcon;
import com.EasyMarathon.util.NumIdentify;

import gcc.dao.AthleteDao;
import gcc.dao.DaoBase;
import gcc.dao.EventDao;
import gcc.dao.FreePicDao;
import gcc.dao.PictureDao;
import gcc.dao.UserDao;
import gcc.po.ConfirmData;
import gcc.po.PicBean;


public class CameraUtil {
	Connection conn;

	public static String bytes2Hex(byte[] src) {
		char[] res = new char[src.length * 2];
		final char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		for (int i = 0, j = 0; i < src.length; i++) {
			res[j++] = hexDigits[src[i] >>> 4 & 0x0f];
			res[j++] = hexDigits[src[i] & 0x0f];
		}

		return new String(res);
	}

	/* ����Ƭ��ˮӡ */
	public static void watermark(String iconPath, String srcImgPath, String targerPath) {
		OutputStream os = null;

		try {
			Image srcImg = ImageIO.read(new File(srcImgPath));

			BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null), srcImg.getHeight(null),
					BufferedImage.TYPE_INT_RGB);
			BufferedImage result = null;
			// ˮӡͼ���·�� ˮӡһ��Ϊgif����png�ģ�����������͸����
			ImageIcon imgIcon = new ImageIcon(iconPath);
			double height = imgIcon.getIconHeight();
			double width = imgIcon.getIconWidth();
			double height1 = buffImg.getHeight();
			double width1 = buffImg.getWidth();
			if (height / width > height1 / width1) {
				height1 = height1 / width1 * width;
				width1 = width;
			} else {
				width1 = width1 / height1 * height;
				height1 = height;
			}
			result = new BufferedImage((int) width1, (int) height1, BufferedImage.TYPE_INT_RGB);
			// �õ����ʶ���
			Graphics2D g = result.createGraphics();
			g.drawImage(srcImg, 0, 0, (int) width1, (int) height1, null);
			// �õ�Image����
			Image img = imgIcon.getImage();
			// ��ʾˮӡͼƬ��λ��
			g.drawImage(img, 0, 0, null);
			g.dispose();
			os = new FileOutputStream(targerPath);
			// ����ͼƬ
			srcImg.flush();
			ImageIO.write(result, "JPG", os);
			os.flush();
			System.out.println("ͼƬ������Iconӡ�¡�����������");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != os)
					os.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/* ����ԭͼ */
	public static void Original(String srcImgPath, String targerPath) {
		OutputStream os = null;

		try {
			Image srcImg = ImageIO.read(new File(srcImgPath));

			BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null), srcImg.getHeight(null),
					BufferedImage.TYPE_INT_RGB);
			Graphics2D g = buffImg.createGraphics();

			g.drawImage(srcImg, 0, 0, srcImg.getWidth(null), srcImg.getHeight(null), null);

			g.dispose();
			os = new FileOutputStream(targerPath);
			// ����ͼƬ
			srcImg.flush();

			ImageIO.write(buffImg, "JPG", os);
			os.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != os)
					os.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


	public ConfirmData uploadPicService(File picture,String event,int price,String account)
	{
		System.out.println("��Ӱʦ�ϴ���Ƭ��...");
		conn = DaoBase.getConnection(true);
		PictureDao picturedao = new PictureDao(conn);	
		UserDao userdao=new UserDao(conn);
		EventDao eventdao=new EventDao(conn);
		int eventID=0;
		try{
			eventID=eventdao.findEventByName(event);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		String srcImgPath = picture.getAbsolutePath();// �Ѿ�������Ƭ��
		// System.out.println(srcImgPath);
		NumIdentify numidentify = new NumIdentify();
		int aID = numidentify.GetID(srcImgPath);
		System.out.println(aID);
		int ans=0;
		String userID=null;
		ConfirmData Athlet=new ConfirmData();
		AthleteDao athletedao = new AthleteDao(conn);
		if (aID == -1)
		{
			ArrayList<ConfirmData> athlets=new ArrayList<ConfirmData>();
			System.out.println("�޷����к�����ʶ�𣬼�����������ʶ��");
	        String path = "C:\\TomcatProject\\Athlete\\cutface\\tmp";
			FaceAlignment ni = new FaceAlignment();
    		System.out.println("run dll");
    		try{
    			System.out.println(1);
    			ans = ni.cutface(srcImgPath, path);
    			System.out.println("ans:"+ans);
    		}
    		catch(Exception e)
    		{
    			e.printStackTrace();
    		}
			System.out.println("ͼƬ�������");		
			try
			{
				athlets = athletedao.GetsomeAthletes();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			if(athlets.size()==0)
			{
				System.out.println("�Ҳ�����Ӧ����");
				return Athlet;
			}
			System.out.println("�˶�Ա��Ϊ��"+athlets.size());
			int i=0;
			File file =new File("C:\\TomcatProject\\Athlete\\cutface\\libface_lfw.txt");
			try{
			  if(!file.exists()){
			       file.createNewFile();
			      }
			  FileWriter fileWritter = new FileWriter(file);

			  for(i=0;i<athlets.size();i++)
			  {
				  String pname = athlets.get(i).getIdentityPic();
				  int size=pname.length();
				  String data=pname.substring(0, size-4)+"-0.jpg "+i+"\r\n";
				  System.out.println("��Ƭ����"+athlets.get(i).getIdentityPic());
				  System.out.println("��Ƭ�ţ�"+String.valueOf(i));
				  System.out.println(data);
				  fileWritter.write(data);			 
			  }
			  String data="tmp-0.jpg"+" "+i;
			  fileWritter.write(data);
			  fileWritter.flush();
			  fileWritter.close();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			System.out.println("�ɹ�д�����ݣ�");
			Facerecognition face=new Facerecognition();
			ans=face.recFace(i, "test", "C:\\TomcatProject\\Athlete\\config\\netv8_2_deploy_lfw_0107.prototxt", "C:\\TomcatProject\\Athlete\\config\\recog0107_iter_2840000.caffemodel", i+1);
			//ans=0;
			Athlet.setName(athlets.get(ans).getName());
			Athlet.setIdentityPic(athlets.get(ans).getIdentityPic());;
			Athlet.setAthleteID(athlets.get(ans).getAthleteID());
			userID=athlets.get(ans).getUserID();
			aID=athlets.get(ans).getAthleteID();
			//file.delete();
		}
		else{
			System.out.println("������ʶ��ɹ���");
			Athlet=athletedao.GetAthlete(aID, eventID);
			userID=Athlet.getUserID();
		}
		
		
		String iconPath = "C:/TomcatProject/Camera/"+eventID+"/watermark/EasyMarathon.png";
		String path = "C:/TomcatProject/Camera/" + eventID + "/";
		System.out.println("��ӰʦĿ¼��" + path);
		System.out.println("ˮӡĿ¼��" + iconPath);

		String picID = null;
		MD5 md5 = new MD5();
		String imgStr = null;
		try
		{
			FileInputStream fis = new FileInputStream(picture);
			System.out.println(1);
			byte[] bytes = new byte[fis.available()];
			System.out.println(2);
			imgStr = bytes2Hex(bytes);
			System.out.println(3);
			System.out.println("ת���ɹ��ַ����ɹ���");
			fis.read(bytes);
			fis.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		// ���������MD5����
		try
		{
			String time = Long.toString(System.currentTimeMillis());
			picID = md5.md5Encode(imgStr+time);
			//picID=path+picID;
			PicBean pic = new PicBean();
			pic.setPicID("http://120.27.106.188:8088//Camera//"+eventID+"//"+picID+".jpg");
			pic.setPrice(price);
			pic.setAuthor(account);
			pic.setUserID(userID);
			pic.setEventName(event);
			pic.setUserName(userdao.GetUser(userID).getUserName());
			System.out.println("PicID:"+picID);
			System.out.println("Price:"+price);
			System.out.println("Account"+account);
			System.out.println("AID"+aID);
			picturedao.AddPic(eventID, aID, pic);
			System.out.println("������Ƭ���ɹ���");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		String pathInitial = path + "initial" + "/";
		String pathwater = path + "watermark" + "/";
		try
		{
			File fileLocation = new File(pathInitial);
			if (!fileLocation.exists())
			{
				fileLocation.mkdirs();
				System.out.println("����Ŀ¼�ɹ�");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			DaoBase.close(conn, null, null);
		}
		try
		{
			File fileLocation = new File(pathwater);
			if (!fileLocation.exists())
				fileLocation.mkdirs();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		pathInitial = pathInitial + picID + ".jpg";
		pathwater = pathwater + picID + ".jpg";
		Original(srcImgPath, pathInitial);
		watermark(iconPath, srcImgPath, pathwater);
		return Athlet;

	}
}
