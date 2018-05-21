package edu.byui.cit.dateapp.data;

import android.provider.BaseColumns;


public class DaterCreatorContract {
	public static final class UserEntry implements BaseColumns {
		public static final String TABLE_NAME = "user";
		public static final String COLUMN_NAME = "name";
	}

	public static final class DatesDone implements BaseColumns {
		public static final String TABLE_NAME = "dates_done";
		public static final String USER_ID_COLUMN = "user_id";
		public static final String DATE_INFO_ID = "date_info_id";
	}

	public static final class DateInfo implements BaseColumns {
		public static final String TABLE_NAME = "DateInfo";
		public static final String DATE_NAME_COLUMN = "name";
		public static final String DATE_GROUP_SIZE_COLUMN = "group_size";
		public static final String DATE_TEMP_COLUMN = "temp";
		public static final String DATE_VENUE_COLUMN = "venue";
		public static final String DATE_DESCRIPTION_COLUMN = "description";
		public static final String DATE_ACTIVITY_LEVEL_COLUMN = "activity_level";
		public static final String DATE_COST_COLUMN_ = "cost";
	}
}
