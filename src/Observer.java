
public class Observer {
	public static int cur_min_of_hour;
	public static int cur_hour_of_day;
	public static int cur_day_of_week;
	public static int cur_day_number;
	public static int min_so_far;
	
	public static void updateTime(int cur_min) {
		min_so_far = cur_min;
		cur_min_of_hour = cur_min % 60;
		cur_hour_of_day = (cur_min/60) % 24;
		cur_day_number = cur_min / (60*24);
		cur_day_of_week = cur_day_number % 7;
	}
	
	public static int getCurrentTimeInMin() {
		return cur_hour_of_day*60+cur_min_of_hour;
	}
	
	public static int getMinSoFar() {
		return min_so_far;
	}
	
	public static String getCurrentTimeHeader() {
		StringBuilder sb = new StringBuilder();
		sb.append("cur_day_number");
		sb.append(",");
		sb.append("cur_hour_of_day");
		sb.append(",");
		sb.append("cur_min_of_hour");
		sb.append(",");
		sb.append("cur_day_of_week");
		return sb.toString();
	}
	
	public static String getCurrentTime() {
		StringBuilder sb = new StringBuilder();
		sb.append(cur_day_number);
		sb.append(",");
		sb.append(cur_hour_of_day);
		sb.append(",");
		sb.append(cur_min_of_hour);
		sb.append(",");
		sb.append(cur_day_of_week);
		return sb.toString();
	}
}
