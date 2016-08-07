package gcc.po;


public class EventBean
{
	private int eventID = -1;
	private String eventName = "";
	private int eventStatus;
	private String eventPicUrl;
	private String eventStartTime;
	private String eventEndTime;
	private String eventDescribe;
	private String eventPlace;

	public String getEventPlace() {
		return eventPlace;
	}

	public void setEventPlace(String eventPlace) {
		this.eventPlace = eventPlace;
	}

	public int getEventID()
	{
		return eventID;
	}

	public void setEventID(int eventID)
	{
		this.eventID = eventID;
	}

	public String getEventName()
	{
		return eventName;
	}

	public void setEventName(String eventName)
	{
		this.eventName = eventName;
	}

	public int getEventStatus()
	{
		return eventStatus;
	}

	public void setEventStatus(int eventStatus)
	{
		this.eventStatus = eventStatus;
	}

	public String getEventPicUrl() {
		return eventPicUrl;
	}

	public void setEventPicUrl(String eventPicUrl) {
		this.eventPicUrl = eventPicUrl;
	}

	public String getEventStartTime() {
		return eventStartTime;
	}

	public void setEventStartTime(String eventStartTime) {
		this.eventStartTime = eventStartTime;
	}

	public String getEventEndTime() {
		return eventEndTime;
	}

	public void setEventEndTime(String eventEndTime) {
		this.eventEndTime = eventEndTime;
	}

	public String getEventDescribe() {
		return eventDescribe;
	}

	public void setEventDescribe(String eventDescribe) {
		this.eventDescribe = eventDescribe;
	}




	
}

