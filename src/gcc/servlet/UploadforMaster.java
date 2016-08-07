package gcc.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import gcc.dao.DaoBase;
import gcc.dao.EventDao;
import gcc.po.ConfirmData;
import gcc.util.CameraUtil;
import gcc.util.MD5;
import net.sf.json.JSONArray;


public class UploadforMaster extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
    public UploadforMaster() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("--------< uploadforMaster >--------");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		try {
			String event = null;
			String account = "";
			String filename = null;
			int price = 0;
			
			//获得磁盘文件条目工厂  
	        DiskFileItemFactory factory = new DiskFileItemFactory(); 
	        
	        String basePath = "C:\\TomcatProject\\";
	        System.out.println("项目路径= "+basePath);
	        String path = basePath+"Camera\\temp\\";
	        factory.setRepository(new File(path));  
	        factory.setSizeThreshold(1024*1024); 
	        //高水平的API文件上传处理  
	        ServletFileUpload upload = new ServletFileUpload(factory); 
	        upload.setHeaderEncoding("UTF-8");
	        try {  
	            //可以上传多个文件  
	        	List<FileItem> list = (List<FileItem>)upload.parseRequest(request);
	        	
	        	for(FileItem item : list){  
	            	//获取表单的属性名字  
	            	String name = item.getFieldName();  
	            	//如果获取的 表单信息是普通的 文本 信息  
	                if(item.isFormField()){                     
	                    //获取用户具体输入的字符串 ，因为表单提交过来的是 字符串类型的  
	                    String value = item.getString("UTF-8");
	                    if(name.equals("account")){
	                    	System.out.println("item.name:"+name);
	                        System.out.println("item.value:"+value);
	                        account = value;
	                    }
	                    else if(name.equals("event")){
	                    	System.out.println("item.name:"+name);
	                        System.out.println("item.value:"+value);
	                        event = value;
	                    }else if(name.equals("price")){
	                    	System.out.println("item.name:"+name);
	                        System.out.println("item.value:"+value);
	                        price = Integer.parseInt(value);
	                    }
	                }  
	            }
	        	
	            for(FileItem item : list){  
	            	//获取表单的属性名字  
	            	System.out.println("发现照片！");
	            	String name = item.getFieldName(); 
	                if(!item.isFormField()){ //是图片          
	                	//获取路径名  
	                    String value = item.getName();
//	                    System.out.println("item.name:"+name);
//	                    System.out.println("item.value:"+value);
	                    int start = value.lastIndexOf("\\");
	                    //截取 上传文件的 字符串名字，加1是 去掉反斜杠
	                    filename = value.substring(start+1);
//	                    System.out.println("filename:"+filename);//filename包含后缀
//	                    System.out.println("path: "+path);
	                    File fileParent = new File(path);
	                    if  (!fileParent .exists()  && !fileParent.isDirectory())      
	                    {       
	                        fileParent.mkdirs();
	                    }
	                    //给文件命名加MD5
	                    MD5 md5 = new MD5();
	                    String time = Long.toString(System.currentTimeMillis());
	                    filename=md5.md5Encode(filename+time)+".jpg";
//	                    System.out.println("New filename:"+filename);//filename包含后缀
	                    File fileChild = new File(path,filename);
	                    OutputStream out = new FileOutputStream(fileChild);  
	                    InputStream in = item.getInputStream();  
	                    int length = 0 ;  
	                    byte [] buf = new byte[1024] ;  
	                    System.out.println("获取上传文件的总共的容量："+item.getSize());  
	  
	                    while( (length = in.read(buf) ) != -1){  
	                        out.write(buf, 0, length);  
	                    }
	                    in.close();  
	                    CameraUtil camerautil=new CameraUtil();
	                    System.out.println(event);
	                    ConfirmData athlet=camerautil.uploadPicService(fileChild,event, price, account);
	                    System.out.println(athlet.getIdentityPic());
	                    JSONArray jsonArray = JSONArray.fromObject(athlet);
	    				response.getOutputStream().write(jsonArray.toString().getBytes());
	    		        
	    		        
	    				
	                }  
	            }
	        }catch (FileUploadException e) {  
	        	e.printStackTrace();  
	        	response.getOutputStream().write("failed".getBytes());
		    }  
	        
		    catch (Exception e) {           
		        e.printStackTrace();  
		        response.getOutputStream().write("failed".getBytes());
		    }
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}


}
