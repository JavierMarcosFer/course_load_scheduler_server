package org.dselent.scheduling.server.model;

import java.sql.JDBCType;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseSchedule extends Model{
	//table name
	public static final String TABLE_NAME = "course_schedule";

	//column names
	public static enum Columns
	{
		ID,
		SECTION_ID,
		LECTURE_TYPE,
		MEETING_DAYS,
		TIME_START,
		TIME_END,
		CREATED_AT,
		UPDATED_AT
	}

	// enum list
	private static final List<Columns> COLUMN_LIST = new ArrayList<>();

	// type mapping
	private static final Map<Columns, JDBCType> COLUMN_TYPE_MAP = new HashMap<>();

	static
	{
		for(Columns key : Columns.values())
		{
			COLUMN_LIST.add(key);
		}

		COLUMN_TYPE_MAP.put(Columns.ID, JDBCType.INTEGER);
		COLUMN_TYPE_MAP.put(Columns.SECTION_ID, JDBCType.INTEGER);
		COLUMN_TYPE_MAP.put(Columns.LECTURE_TYPE, JDBCType.VARCHAR);
		COLUMN_TYPE_MAP.put(Columns.MEETING_DAYS, JDBCType.VARCHAR);
		COLUMN_TYPE_MAP.put(Columns.TIME_START, JDBCType.INTEGER);
		COLUMN_TYPE_MAP.put(Columns.TIME_END, JDBCType.INTEGER);
		COLUMN_TYPE_MAP.put(Columns.CREATED_AT, JDBCType.TIMESTAMP_WITH_TIMEZONE);
		COLUMN_TYPE_MAP.put(Columns.UPDATED_AT, JDBCType.TIMESTAMP_WITH_TIMEZONE);
	};

	// attributes
	private String lectureType;
	private String meetingDays;
	private Integer timeStart;
	private Integer timeEnd;
	private Timestamp createdAt;
	private Timestamp updatedAt;
	private Integer id;
	private Integer sectionId;
	
	public static JDBCType getColumnType(Columns column)
	{
		return COLUMN_TYPE_MAP.get(column);
	}
	
	public static String getColumnName(Columns column)
	{
		return column.toString().toLowerCase();
	}
	
	public static List<String> getColumnNameList()
	{
		List<String> columnNameList = new ArrayList<>();
		
		for(Columns column : COLUMN_LIST)
		{
			columnNameList.add(getColumnName(column));
		}
		
		return columnNameList;
	}


	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getSectionId() {
		return sectionId;
	}
	public void setSectionId(Integer sectionId) {
		this.sectionId = sectionId;
	}
	public String getType() {
		return lectureType;
	}
	public void setType(String type) {
		this.lectureType = type;
	}
	public String getMeetingDays() {
		return meetingDays;
	}
	public void setMeetingDays(String meetingDays) {
		this.meetingDays = meetingDays;
	}
	public Integer getTimeStart() {
		return timeStart;
	}
	public void setTimeStart(Integer timeStart) {
		this.timeStart = timeStart;
	}
	public Integer getTimeEnd() {
		return timeEnd;
	}
	public void setTimeEnd(Integer timeEnd) {
		this.timeEnd = timeEnd;
	}
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	public Timestamp getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}
}