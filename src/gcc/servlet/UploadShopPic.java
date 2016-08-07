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
 * ����̳��ز�ͼƬ
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
		//��ô����ļ���Ŀ����  
        DiskFileItemFactory factory = new DiskFileItemFactory(); 

        //String basePath = request.getSession().getServletContext().getRealPath("/");
        String basePath = "C:\\TomcatProject\\";
        System.out.println("ͼƬ�ϴ�·��12345= "+basePath);
        String path = basePath;
        String tomcatPath = ServerData.RootURL;
        factory.setRepository(new File(path));  
        factory.setSizeThreshold(1024*1024); 
        //��ˮƽ��API�ļ��ϴ�����  
        ServletFileUpload upload = new ServletFileUpload(factory); 
        upload.setHeaderEncoding("UTF-8");

        String ShopType="";
        String ClothesType="";
        ClothesBean clothes = new ClothesBean();
        try {  
        	List<FileItem> list = (List<FileItem>)upload.parseRequest(request);
        	//��ȡ������
        	for(FileItem item : list){  
            	//��ȡ������������  
            	String name = item.getFieldName();  
            	//�����ȡ�� ����Ϣ����ͨ�� �ı� ��Ϣ  
                if(item.isFormField()){                     
                    //��ȡ�û�����������ַ��� ����Ϊ���ύ�������� �ַ������͵�  
                    String value = item.getString("UTF-8");//�������ó�UTF-8��������������
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
        	//��ȡ�ļ�����
        	for(FileItem item : list){  
            	String name = item.getFieldName();  
                if(!item.isFormField()){                     
                	//��ȡ·����  
                    String value = item.getName();
                    System.out.println("item.name:"+name);
                    System.out.println("item.value:"+value);
                    int start = value.lastIndexOf("\\");
                    //��ȡ �ϴ��ļ��� �ַ������֣���1�� ȥ����б��
                    filename = value.substring(start+1);
                    System.out.println("filename:"+filename);//filename������׺
                    path+="Shopping/"+ShopType+"/"+ClothesType+"/";
                    tomcatPath +="Shopping/"+ShopType+"/"+ClothesType+"/";
                    System.out.println("path: "+path);
                    File fileParent = new File(path);
                    if  (!fileParent.exists())      
                    {    
                    	System.out.println("--------------<mkdir>-----------");
                        fileParent.mkdirs();
                    }
                    //���ļ�������MD5
                    MD5 md5 = new MD5();
                    filename=md5.md5Encode(filename)+".jpg";
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
        	System.out.println("file URL:"+path+filename);
        	clothes.setImgURL(tomcatPath+filename);
            
        }catch (FileUploadException e) {  
        	e.printStackTrace();  
	    }  
	    catch (Exception e) {           
	        e.printStackTrace();  
	    }
        //д�����ݿ�
        Connection conn = DaoBase.getConnection(true);
        ClothesDao clothesDao = new ClothesDao(conn);
        clothesDao.AddClothes(clothes);
	}

}
