package gcc.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import gcc.dao.DaoBase;
import gcc.dao.UserDao;
import gcc.po.UserBean;
import gcc.util.MD5;

/**
 * @author ��Դ�
 * @version 1.0*/
public class RegisterBase extends HttpServlet{
	private static final long serialVersionUID = 1L;       
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub      
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        String filename = null;
		//��ô����ļ���Ŀ����  
        DiskFileItemFactory factory = new DiskFileItemFactory(); 

        String basePath = "C:\\TomcatProject\\";
        System.out.println("��Ŀ·��= "+basePath);
        String path = basePath+"UserIcon\\";
        
        factory.setRepository(new File(path));  
        factory.setSizeThreshold(1024*1024); 
        //��ˮƽ��API�ļ��ϴ�����  
        ServletFileUpload upload = new ServletFileUpload(factory); 
        upload.setHeaderEncoding("UTF-8");
        
        UserBean user = new UserBean();
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
                    if(name.equals("account")){
                    	System.out.println("item.name:"+name);
                        System.out.println("item.value:"+value);
                        user.setAccount(value);
                        System.out.println("account"+value);
                    }
                    else if(name.equals("password")){
                    	System.out.println("item.name:"+name);
                        System.out.println("item.value:"+value);
                        user.setPassword(value);
                    }
                    else if(name.equals("who"))
                    {
                    	System.out.println("item.name:"+name);
                        System.out.println("item.value:"+value);
                        user.setWhose(Integer.parseInt(value));
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
                    File fileParent = new File(filename);
                    if  (!fileParent .exists()  && !fileParent.isDirectory())      
                    {       
                        fileParent.mkdirs();
                    }
                    String UserID = UUID.randomUUID().toString();//����Ϊ36λ���ַ���
                    filename=UserID+".jpg";
                    user.setUserID(UserID);
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
        	user.setHeadImgUrl("http://120.27.106.188:8088/UserIcon"+filename);
            
        }catch (FileUploadException e) {  
        	e.printStackTrace();  
	    }  
	    catch (Exception e) {           
	        e.printStackTrace();  
	    }
        //д�����ݿ� 
        Connection conn = DaoBase.getConnection(true);
        UserDao userDao = new UserDao(conn);
        
        String userID=null;
        try {
			userID=userDao.findUserByAccount(user.getAccount());
			 if(userID!=null)
		        {
		        	response.getOutputStream().write("failed".getBytes());
		        }
		        boolean result = userDao.AddBaseUser(user);
				if(result){	//���û���ӳɹ�
					response.getOutputStream().write("succeed".getBytes());
				}
				else{	//���û����ʧ��
					response.getOutputStream().write("failed".getBytes());
				}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        finally{
        	DaoBase.close(conn, null, null);
        }
       
    }
}
