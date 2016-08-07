package gcc.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import gcc.dao.ClothesDao;
import gcc.dao.DaoBase;
import gcc.po.ClothesBean;
import gcc.util.MD5;
import gcc.util.ServerData;

/**
 * Servlet implementation class UploadShopPic
 * 添加商城素材图片
 */
public class UploadShopPic extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadShopPic() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String filename = null;
		//获得磁盘文件条目工厂  
        DiskFileItemFactory factory = new DiskFileItemFactory(); 

        //String basePath = request.getSession().getServletContext().getRealPath("/");
        String basePath = "C:\\TomcatProject\\";
        System.out.println("图片上传路径12345= "+basePath);
        String path = basePath;
        String tomcatPath = ServerData.RootURL;
        factory.setRepository(new File(path));  
        factory.setSizeThreshold(1024*1024); 
        //高水平的API文件上传处理  
        ServletFileUpload upload = new ServletFileUpload(factory); 
        upload.setHeaderEncoding("UTF-8");

        String ShopType="";
        String ClothesType="";
        ClothesBean clothes = new ClothesBean();
        try {  
        	List<FileItem> list = (List<FileItem>)upload.parseRequest(request);
        	//获取表单数据
        	for(FileItem item : list){  
            	//获取表单的属性名字  
            	String name = item.getFieldName();  
            	//如果获取的 表单信息是普通的 文本 信息  
                if(item.isFormField()){                     
                    //获取用户具体输入的字符串 ，因为表单提交过来的是 字符串类型的  
                    String value = item.getString("UTF-8");//必须设置成UTF-8，否则中文乱码
                    if(name.equals("ShopType")){
                    	System.out.println("ShopType.name:"+name);
                        System.out.println("ShopType.value:"+value);
                        ShopType=value.toString();
                    }
                    if(name.equals("ClothesType")){
                    	System.out.println("ClothesType.name:"+name);
                        System.out.println("ClothesType.value:"+value);
                        ClothesType=value.toString();
                        clothes.setType(ClothesType);
                    }
                    if(name.equals("brand")){
                    	System.out.println("ClothesType.name:"+name);
                        System.out.println("ClothesType.value:"+value);
                        clothes.setBrand(value.toString());
                    }
                    if(name.equals("name")){
                    	System.out.println("ClothesType.name:"+name);
                        System.out.println("ClothesType.value:"+value);
                        clothes.setName(value.toString());
                    }
                    if(name.equals("describe")){
                    	System.out.println("ClothesType.name:"+name);
                        System.out.println("ClothesType.value:"+value);
                        clothes.setDescribe(value.toString());
                    }
                    if(name.equals("price")){
                    	System.out.println("ClothesType.name:"+name);
                        System.out.println("ClothesType.value:"+value);
                        clothes.setPrice(value.toString());
                    }
                }
            }
        	//获取文件数据
        	for(FileItem item : list){  
            	String name = item.getFieldName();  
                if(!item.isFormField()){                     
                	//获取路径名  
                    String value = item.getName();
                    System.out.println("item.name:"+name);
                    System.out.println("item.value:"+value);
                    int start = value.lastIndexOf("\\");
                    //截取 上传文件的 字符串名字，加1是 去掉反斜杠
                    filename = value.substring(start+1);
                    System.out.println("filename:"+filename);//filename包含后缀
                    path+="Shopping/"+ShopType+"/"+ClothesType+"/";
                    tomcatPath +="Shopping/"+ShopType+"/"+ClothesType+"/";
                    System.out.println("path: "+path);
                    File fileParent = new File(path);
                    if  (!fileParent.exists())      
                    {    
                    	System.out.println("--------------<mkdir>-----------");
                        fileParent.mkdirs();
                    }
                    //给文件命名加MD5
                    MD5 md5 = new MD5();
                    filename=md5.md5Encode(filename)+".jpg";
                    System.out.println("New filename:"+filename);//filename包含后缀
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
                    out.close();
                }
            }
        	System.out.println("file URL:"+path+filename);
        	clothes.setImgURL(tomcatPath+filename);
            
        }catch (FileUploadException e) {  
        	e.printStackTrace();  
	    }  
	    catch (Exception e) {           
	        e.printStackTrace();  
	    }
        //写入数据库
        Connection conn = DaoBase.getConnection(true);
        ClothesDao clothesDao = new ClothesDao(conn);
        clothesDao.AddClothes(clothes);
	}

}
