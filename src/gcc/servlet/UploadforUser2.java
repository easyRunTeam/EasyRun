package gcc.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.struts2.ServletActionContext;


import gcc.dao.DaoBase;
import gcc.dao.EventDao;
import gcc.dao.FreePicDao;
import gcc.dao.UserDao;
import gcc.util.MD5;

import javax.servlet.ServletException;  
import javax.servlet.http.HttpServlet;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;  
import org.apache.commons.fileupload.FileUploadException;  
import org.apache.commons.fileupload.disk.DiskFileItemFactory;  
import org.apache.commons.fileupload.servlet.ServletFileUpload; 

public class UploadforUser2 extends HttpServlet{

	private List<String> filenames=new ArrayList<String>();
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {}
	{
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{
		System.out.println("--------< uploadforUser >--------");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		try {
			getPictureUrl(request, response);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	private void getPictureUrl(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String eventName = null;
		String account = null;
		String filename = null;
		boolean result=false;
		int eventID;
		//��ô����ļ���Ŀ����  
        DiskFileItemFactory factory = new DiskFileItemFactory(); 
        
        String basePath = request.getSession().getServletContext().getRealPath("/");
        System.out.println("��Ŀ·��= "+basePath);
        String path = basePath+"UserIcon\\";
        //String path = "C:\\easyrun\\UserPicture\\";
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
                    String value = item.getString("UTF-8");
                    if(name.equals("account")){
                    	System.out.println("item.name:"+name);
                        System.out.println("item.value:"+value);
                        account = value;
                    	//request.setAttribute("wechatID", value);
                    }
                    else if(name.equals("event")){
                    	System.out.println("item.name:"+name);
                        System.out.println("item.value:"+value);
                        eventName = value;
                    	//request.setAttribute("eventID", value);
                    	path += value+"\\";
                    }
                }  
            }
        	
            for(FileItem item : list){  
            	//��ȡ������������  
            	String name = item.getFieldName(); 
                if(!item.isFormField()){ //��ͼƬ          
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
                    filename=md5.md5Encode(filename+time)+".jpg";
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
                }  
            }
        }catch (FileUploadException e) {  
        	e.printStackTrace();  
	    }  
	    catch (Exception e) {           
	        e.printStackTrace();  
	    }
        //д�����ݿ�
        Connection conn = DaoBase.getConnection(true);
        EventDao edao=new EventDao(conn);
        FreePicDao fpdao = new FreePicDao(conn);
        UserDao uDao=new UserDao(conn);
        try {
        	String UserID=uDao.findUserByAccount(account);
        	eventID=edao.findEventByName(eventName);
			result=fpdao.AddPic(eventID, UserID,filename);
		} catch (NumberFormatException e) {
			
			e.printStackTrace();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
        System.out.println("�ϴ��ɹ���");
		DaoBase.close(conn, null, null);
		if(result)
		{
			response.getOutputStream().write("succeed".getBytes());
		}
		else
		{
			response.getOutputStream().write("failed".getBytes());
		}
	}
}
