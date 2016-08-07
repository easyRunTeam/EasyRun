package gcc.servlet;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import gcc.dao.DaoBase;
import gcc.dao.UserDao;
import gcc.po.UserBean;
import gcc.util.FaceAlignment;
import gcc.util.MD5;

public class MarathonRegister extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private List<String> filenames=new ArrayList<String>();
	
    public MarathonRegister() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("--------< uploadforUser >--------");
		System.out.println();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		//��ӡ����ͷ
		/*Enumeration names = request.getHeaderNames();
		System.out.println("==========================================================");
		while(names.hasMoreElements()){
	        String name = (String) names.nextElement();
	        System.out.println(name + ":" + request.getHeader(name));
	    }*/
		try {
			getPictureUrl(request, response);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	private void getPictureUrl(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		UserBean user = new UserBean();
		String filename = null;
		String address = "";
		String path1="";
		//��ô����ļ���Ŀ����  
        DiskFileItemFactory factory = new DiskFileItemFactory(); 

        String basePath = "C:\\TomcatProject\\";
        System.out.println("��Ŀ·��= "+basePath);
        String path = basePath+"Athlete\\pic\\";
        
        factory.setRepository(new File(path));  
        factory.setSizeThreshold(1024*1024); 
        //��ˮƽ��API�ļ��ϴ�����  
        ServletFileUpload upload = new ServletFileUpload(factory); 
        upload.setHeaderEncoding("UTF-8");
        
        try {  
            //�����ϴ�����ļ�  
        	List<FileItem> list = (List<FileItem>)upload.parseRequest(request);
        	
        	for(FileItem item : list){  
            	//��ȡ������������  
            	String name = item.getFieldName();  
            	//�����ȡ�� ����Ϣ����ͨ�� �ı� ��Ϣ  
                if(item.isFormField()){                     
                    //��ȡ�û�����������ַ��� ����Ϊ���ύ�������� �ַ������͵�  
                    String value = item.getString("UTF-8");//�������ó�UTF-8��������������
                    if(name.equals("user.userName")){
                    	System.out.println("item.name:"+name);
                        System.out.println("item.value:"+value);
                        user.setUserName(value);
                    }
                    else if(name.equals("user.email")){
                    	System.out.println("item.name:"+name);
                        System.out.println("item.value:"+value);
                        user.setEmail(value);
                    }
                    else if(name.equals("user.celphone")){
                    	System.out.println("item.name:"+name);
                        System.out.println("item.value:"+value);
                        user.setCelphone(value);
                    }
                    else if(name.equals("user.identityCard")){
                    	System.out.println("item.name:"+name);
                        System.out.println("item.value:"+value);
                        user.setIdentityCard(value);
                    }
                    else if(name.equals("user.bloodType")){
                    	System.out.println("item.name:"+name);
                        System.out.println("item.value:"+value);
                        user.setBloodType(value);
                    }
                    else if(name.equals("user.height")){
                    	System.out.println("item.name:"+name);
                        System.out.println("item.value:"+Float.parseFloat(value));
                        user.setHeight(Float.parseFloat(value));
                    }
                    else if(name.equals("user.weight")){
                    	System.out.println("item.name:"+name);
                        System.out.println("item.value:"+Float.parseFloat(value));
                        user.setWeight(Float.parseFloat(value));
                    }
                    else if(name.equals("country")){
                    	System.out.println("item.name:"+name);
                        System.out.println("item.value:"+value);
                        address += value+" ";
                    }
                    else if(name.equals("province")){
                    	System.out.println("item.name:"+name);
                        System.out.println("item.value:"+value);
                        address += value+" ";
                    }
                    else if(name.equals("city")){
                    	System.out.println("item.name:"+name);
                        System.out.println("item.value:"+value);
                        address += value;
                    }
                    else if(name.equals("user.urgencyContact")){
                    	System.out.println("item.name:"+name);
                        System.out.println("item.value:"+value);
                        user.setUrgencyContact(value);
                    }
                    else if(name.equals("user.urgencyPhone")){
                    	System.out.println("item.name:"+name);
                        System.out.println("item.value:"+value);
                        user.setUrgencyPhone(value);
                    }
                    else if(name.equals("user.account")){
                    	System.out.println("item.name:"+name);
                        System.out.println("item.value:"+value);
                        user.setAccount(value);
                    }
                }
                else{
                	//��ȡ·����  
                    String value = item.getName();
                    System.out.println("item.name:"+name);
                    System.out.println("item.value:"+value);
                    int start = value.lastIndexOf("\\");
                    //��ȡ �ϴ��ļ��� �ַ������֣���1�� ȥ����б��
                    filename = value.substring(start+1);
                    System.out.println("filename:"+filename);//filename������׺
                    System.out.println("path: "+path);
                    File fileParent = new File(path);
                    if  (!fileParent .exists()  && !fileParent.isDirectory())      
                    {       
                        fileParent.mkdirs();
                    }
                    //���ļ�������MD5
                    MD5 md5 = new MD5();
                    String time = Long.toString(System.currentTimeMillis());
                    String JPG=md5.md5Encode(filename+time);
                    filename=JPG+".jpg";
                    System.out.println("New filename:"+filename);//filename������׺
                    File fileChild = new File(path,filename);
                    OutputStream out = new FileOutputStream(fileChild);  
                    InputStream in = item.getInputStream();  
                    int length = 0 ;  
                    byte [] buf = new byte[1024] ;  
                    System.out.println("��ȡ�ϴ��ļ����ܹ���������"+item.getSize());  
  
                    while( (length = in.read(buf) ) != -1){  
                        out.write(buf, 0, length);  
                    }
                    in.close();  
                    out.close();
                    path1=path+filename;
                    String path2=basePath+"Athlete\\cutface\\"+JPG;
                    System.out.println(path1);
                    System.out.println(path2);
                    FaceAlignment ni = new FaceAlignment();
            		System.out.println("run dll");
            		try{
            			System.out.println(1);
            			int ans = ni.cutface(path1, path2);
            			System.out.println("ans:"+ans);
            		}
            		catch(Exception e)
            		{
            			e.printStackTrace();
            		}
                }
            }
        	System.out.println("filename:"+filename);
        	user.setIdentityPic("http://120.27.106.188:8088//Athlete//pic//"+filename);
        	user.setAddress(address);
        	System.out.println("address: "+address);
        	HttpSession session=request.getSession();
        	session.setAttribute("user", user);//�˶�Աע��ʱ����Ϣ
        }catch (FileUploadException e) {  
        	e.printStackTrace();  
        	HttpSession session=request.getSession();
        	session.setAttribute("user", user);//�˶�Աע��ʱ����Ϣ
        	request.getRequestDispatcher("addInfoFailed.jsp").forward(request, response);
	    }  
	    catch (Exception e) {           
	        e.printStackTrace();  
	        HttpSession session=request.getSession();
        	session.setAttribute("user", user);//�˶�Աע��ʱ����Ϣ
	        request.getRequestDispatcher("addInfoFailed.jsp").forward(request, response);
	    }
        //д�����ݿ�
        System.out.println("Beforedatabase");
        Connection conn = DaoBase.getConnection(true);
        UserDao userDao = new UserDao(conn);
        try {
        	userDao.UpdUser(user);
		} catch (SQLException e) {
			HttpSession session=request.getSession();
        	session.setAttribute("user", user);//�˶�Աע��ʱ����Ϣ
			request.getRequestDispatcher("addInfoFailed.jsp").forward(request, response);
			e.printStackTrace();
		}

        catch (NumberFormatException e) {
        	HttpSession session=request.getSession();
        	session.setAttribute("user", user);//�˶�Աע��ʱ����Ϣ
			request.getRequestDispatcher("addInfoFailed.jsp").forward(request, response);
			e.printStackTrace();
		}
        finally{
        	DaoBase.close(conn, null, null);
        }
        System.out.println("Afterdatabase");
        System.out.println("��Ϣ���Ƴɹ���");
		
        request.getRequestDispatcher("addInfoSuccess.jsp").forward(request, response);
	}
}
