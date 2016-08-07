package gcc.po;

public class ClothesBean {
	private String id = "";         //主键ID
	private String brand = "";		//品牌
	private String name = "";		//衣服名
	private String describe = "";	//描述
	private String imgURL = "";		//图片来源URL
	private String saleNumber = "0";	//销量
	private String price = "";		//价格（积分）
	private String type = "";		//类别
	private String repertory = "100";	//库存
	
	public ClothesBean(){}
	
	public ClothesBean(String brand,String name,String describe,String imgURL,String saleNumber,String price){
		this.brand = brand;
		this.name = name;
		this.describe = describe;
		this.imgURL = imgURL;
		this.saleNumber = saleNumber;
		this.price = price;
	}
	
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public String getImgURL() {
		return imgURL;
	}
	public void setImgURL(String imgURL) {
		this.imgURL = imgURL;
	}
	public String getSaleNumber() {
		return saleNumber;
	}
	public void setSaleNumber(String saleNumber) {
		this.saleNumber = saleNumber;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRepertory() {
		return repertory;
	}

	public void setRepertory(String repertory) {
		this.repertory = repertory;
	}
	
	
}
