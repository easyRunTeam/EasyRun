package gcc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import gcc.po.EventBean;

public class EventDao
{
	Connection conn = null;

	public EventDao(Connection c)
	{
		conn = c;
	}

	public EventBean GetEventByID(int eventID) throws SQLException
	{
		final String sql1 = "select * from Events where EventID=?";
		try (PreparedStatement ps1 = conn.prepareStatement(sql1))
		{
			ps1.setInt(1, eventID);
			ResultSet rs1 = ps1.executeQuery();
			if (!rs1.next())// no match
				return null;

			// get event
			EventBean event = new EventBean();
			event.setEventID(eventID);
			event.setEventName(rs1.getString("EventName"));
			event.setEventDescribe(rs1.getString("EventDescribe"));
			 SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
				event.setEventStartTime(sf.format(rs1.getLong("EventStartTime")));
				event.setEventEndTime(sf.format(rs1.getLong("EventEndTime")));
			event.setEventStatus(rs1.getInt("EventStatus"));
			event.setEventPicUrl(rs1.getString("EventPicUrl"));
			return event;
		}
	}
	public Integer findEventByName(String eventName) throws SQLException
	{
		final String sql1 = "select * from Events where eventName=?";
		try (PreparedStatement ps1 = conn.prepareStatement(sql1))
		{
			ps1.setString(1, eventName);
			ResultSet rs1 = ps1.executeQuery();
			if (!rs1.next())// no match
				return null;
			return rs1.getInt("EventID");
		}
	}

	public ArrayList<EventBean> GetEventByStatus(int eventStatus) throws SQLException
	{
		final String sql1 = "select * from Events where EventStatus=?";
		try (PreparedStatement ps1 = conn.prepareStatement(sql1))
		{
			ps1.setInt(1, eventStatus);
			ResultSet rs1 = ps1.executeQuery();
			ArrayList<EventBean> events = new ArrayList<>();
			while(rs1.next())
			{
				EventBean event = new EventBean();
				event.setEventID(rs1.getInt("EventID"));
				event.setEventName(rs1.getString("EventName"));
				event.setEventDescribe(rs1.getString("EventDescribe"));
				 SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
					event.setEventStartTime(sf.format(rs1.getLong("EventStartTime")));
					event.setEventEndTime(sf.format(rs1.getLong("EventEndTime")));
				event.setEventStatus(rs1.getInt("EventStatus"));
				event.setEventPicUrl(rs1.getString("EventPicUrl"));
				event.setEventPlace(rs1.getString("EventPlace"));
				events.add(event);
			}
			return events;
		}
	}
	
	public Integer AddEvent(EventBean event)
			throws SQLException, Throwable
	{
		final String sql1 = "insert into Events (EventName,EventStatus,EventStartTime,EventEndTime,EventDescribe,EventPicUrl) values(?,0,?,?,?,?)";
		try (PreparedStatement ps1 = conn.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS))
		{
			ps1.setString(1, event.getEventName());
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			ps1.setLong(2, sdf.parse(event.getEventStartTime()).getTime());
			ps1.setLong(3, sdf.parse(event.getEventEndTime()).getTime());
			ps1.setString(4, event.getEventDescribe());
			ps1.setString(5, event.getEventPicUrl());
			ps1.executeUpdate();
			ResultSet rs1=ps1.getGeneratedKeys();
			if(rs1.next())
				return rs1.getInt(1);
			else
				return null;
		}
	}
	public ArrayList<EventBean> GetAllEvents() throws SQLException
	{
		final String sql1 = "select * from Events";
		try (PreparedStatement ps1 = conn.prepareStatement(sql1))
		{
			ResultSet rs1 = ps1.executeQuery();
			ArrayList<EventBean> events = new ArrayList<>();
			while(rs1.next())
			{
				EventBean event = new EventBean();
				event.setEventID(rs1.getInt("EventID"));
				event.setEventName(rs1.getString("EventName"));
				event.setEventDescribe(rs1.getString("EventDescribe"));
			    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
				event.setEventStartTime(sf.format(rs1.getLong("EventStartTime")));
				event.setEventEndTime(sf.format(rs1.getLong("EventEndTime")));
				event.setEventStatus(rs1.getInt("EventStatus"));
				event.setEventPicUrl(rs1.getString("EventPicUrl"));
				event.setEventPlace(rs1.getString("EventPlace"));
				events.add(event);
			}
			return events;
		}
	}
	public ArrayList<EventBean> GetAllEventsRegistered() throws SQLException
	{
		final String sql1 = "select * from Events Where ";
		try (PreparedStatement ps1 = conn.prepareStatement(sql1))
		{
			ResultSet rs1 = ps1.executeQuery();
			ArrayList<EventBean> events = new ArrayList<>();
			while(rs1.next())
			{
				EventBean event = new EventBean();
				event.setEventID(rs1.getInt("EventID"));
				event.setEventName(rs1.getString("EventName"));
				event.setEventDescribe(rs1.getString("EventDescribe"));
				 SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
					event.setEventStartTime(sf.format(rs1.getLong("EventStartTime")));
					event.setEventEndTime(sf.format(rs1.getLong("EventEndTime")));
				event.setEventStatus(rs1.getInt("EventStatus"));
				event.setEventPicUrl(rs1.getString("EventPicUrl"));
				event.setEventPlace(rs1.getString("EventPlace"));
				events.add(event);
			}
			return events;
		}
	}
	public boolean updateEvent(EventBean event)
			throws SQLException, Throwable
	{
		final String sql1 = "update Events set EventName=? , EventStatus=? , EventDescribe=? , EventEndTime=? , EventStartTime=? , EventPicUrl=? where EventID=?";
		try (PreparedStatement ps1 = conn.prepareStatement(sql1))
		{
			ps1.setString(1, event.getEventName());
			ps1.setInt(2, event.getEventStatus());
			ps1.setString(3, event.getEventDescribe());
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			ps1.setLong(4, sdf.parse(event.getEventEndTime()).getTime());
			ps1.setLong(5, sdf.parse(event.getEventStartTime()).getTime());
			ps1.setString(6, event.getEventPicUrl());
			ps1.setInt(7, event.getEventID());
			ps1.executeUpdate();
			return true;
		}
	}
}
