package gcc.po;

import java.io.File;

public class PicBean
{
	private File file;
	private String picID;
	private String author = "";
	private int Price = 0;
	private int picStatus;
	private String userID;
	private long upTime;
	private String eventID;
	private String userName = "";
	private String eventName = "";
	private String headImgUrl =""; // 用户头像链接
	public long getUpTime() {
		return upTime;
	}

	public void setUpTime(long upTime) {
		this.upTime = upTime;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	

	public File getFile()
	{
		return file;
	}

	public void setFile(File file)
	{
		this.file = file;
	}

	public String getPicID()
	{
		return picID;
	}

	public void setPicID(String picID)
	{
		this.picID = picID;
	}

	public String getAuthor()
	{
		return author;
	}

	public void setAuthor(String author)
	{
		this.author = author;
	}

	public int getPrice()
	{
		return Price;
	}

	public void setPrice(int price)
	{
		Price = price;
	}

	public int getPicStatus()
	{
		return picStatus;
	}

	public void setPicStatus(int picStatus)
	{
		this.picStatus = picStatus;
	}

	public String getEventID() {
		return eventID;
	}

	public void setEventID(String eventID) {
		this.eventID = eventID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getHeadImgUrl() {
		return headImgUrl;
	}

	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}


}
