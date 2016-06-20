package gcc.po;

import java.sql.Date;

import gcc.po.MasterBean.Gender;

public class MasterBean {
	public static enum Gender
	{
		male,female;
	}
	private String userID = "";
	private String headImgUrl =""; 	// 用户头像链接
	private String userName = "";	//昵称
	private String account = "";	//用户名
	private String password = "";	//密码
	private String realName = "";	//真实姓名
	private String celphone = "";
	private String email = "";
	private Date birth;
	private Gender gender;
	private String identityCard = "";	//身份证号
	private String identityPic = "";	//身份证上传图的存储路径
	private String bloodType = "";		//血型
	private String address = "";		//住址：省+市+区/县
	private float height;				//身高
	private float weight;				//体重
	private String urgencyContact = ""; //紧急联系人姓名
	private String urgencyPhone = "";	//紧急联系人电话
	
	
	public String getEmail()
	{
		return email;
	}
	public void setEmail(String email)
	{
		this.email = email;
	}
	public Date getBirth()
	{
		return birth;
	}
	public void setBirth(Date birth)
	{
		this.birth = birth;
	}
	public Gender getGender()
	{
		return gender;
	}
	public void setGender(int gender)
	{
		this.gender = Gender.values()[gender];
	}
	public String getUserName()
	{
		return userName;
	}
	public void setUserName(String userName)
	{
		this.userName = userName;
	}
	public String getCelphone()
	{
		return celphone;
	}
	public void setCelphone(String celphone)
	{
		this.celphone = celphone;
	}
	public String getIdentityCard() {
		return identityCard;
	}
	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}
	public String getIdentityPic() {
		return identityPic;
	}
	public void setIdentityPic(String identityPic) {
		this.identityPic = identityPic;
	}
	public String getBloodType() {
		return bloodType;
	}
	public void setBloodType(String bloodType) {
		this.bloodType = bloodType;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public float getHeight() {
		return height;
	}
	public void setHeight(float height) {
		this.height = height;
	}
	public float getWeight() {
		return weight;
	}
	public void setWeight(float weight) {
		this.weight = weight;
	}
	public String getUrgencyContact() {
		return urgencyContact;
	}
	public void setUrgencyContact(String urgencyContact) {
		this.urgencyContact = urgencyContact;
	}
	public String getUrgencyPhone() {
		return urgencyPhone;
	}
	public void setUrgencyPhone(String urgencyPhone) {
		this.urgencyPhone = urgencyPhone;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public String getHeadImgUrl() {
		return headImgUrl;
	}
	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
}
