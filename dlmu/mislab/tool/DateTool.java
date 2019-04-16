// By GuRui on 2014-12-4 上午1:04:26
package dlmu.mislab.tool;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTool {
	public static final String PATTERN_FULL_DATE_TIME="yyyy-MM-dd HH:mm:ss";
	public static final String PATTERN_DATE="yyyy-MM-dd";
	public static final String PATTERN_TIME="HH:mm:ss";
	public static String PATTERN_DEFAULT=PATTERN_FULL_DATE_TIME;
	
	private static final long MILLIS_PER_DAY = 24 * 60 * 60 * 1000;
	public static  long getTimePortion(Date date){
		return date.getTime() % MILLIS_PER_DAY;
	}
	
	public static long getDatePortion(Date date){
		return date.getTime() / MILLIS_PER_DAY;
	}
	
	/***
	 * 比较两个Date的时间部分的年、月、日、时、分、秒是否相等（不比较毫秒）
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean compareDateTime(Date d1, Date d2){
		return compareDate(d1,d2) & compareTime(d1,d2);
	}
	
	/***
	 * 比较两个Date的时间部分的时、分、秒是否相等（不比较毫秒，不考虑日期部分）
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean compareTime(Date d1, Date d2){
		long l1=getTimePortion(d1);
		long l2=getTimePortion(d2);
		return l1/1000 == l2 /1000; //don't consider the mil-second part
	}
	
	/***
	 * 比较两个Date的时间部分的年、月、日是否相等（不考虑时间部分）
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean compareDate(Date d1, Date d2){
		long l1=getDatePortion(d1);
		long l2=getDatePortion(d2);
		return l1==l2;
	}
	
	/***
	 * 将整数日期转换为Date
	 * By GuRui on 2015-1-14 下午10:27:48
	 * @param year
	 * @param month 从1月开始
	 * @param day
	 * @param hour
	 * @param minute
	 * @param second
	 * @return
	 */
	public static Date createDate(int year, int month, int day, int hour, int minute, int second){
		Calendar cal = Calendar.getInstance();
		cal.set(year, month-1, day, hour, minute, second);
		return cal.getTime();
	}
	
	private static Date getSafeDate(Date date){
		if(date==null){
			return createDate(1970, 1, 1, 0, 0, 0);
		}else{
			return date;
		}
	}
	/***
	 * 字符串转换为Date，不抛出异常。默认格式"yyyy-MM-dd"。如果转换失败返回一个日期：1970年1月1日0点0分0秒
	 * By GuRui on 2015-1-14 下午10:18:00
	 * @param 日期字符串
	 * @return
	 */
	public static Date strToDateSafe(String strDate){
		return getSafeDate(strToDate(strDate));
	}
	/***
	 * 字符串转换为Date，不抛出异常。如果转换失败返回一个日期：1970年1月1日0点0分0秒
	 * By GuRui on 2015-1-14 下午10:26:17
	 * @param 日期字符串
	 * @param pattern
	 * @return
	 */
	public static Date strToDateSafe(String strDate, String pattern){
		return getSafeDate(strToDate(strDate));
	}
	
	/***
	 * 字符串转换为Date，不抛出异常。如果转换失败返回null
	 * @param strDate 日期字符串
	 * @param pattern 日期字符串格式
	 * @return 转换失败返回null
	 */
	public static Date strToDate(String strDate, String pattern){
		try{
			return createDate(strDate,pattern);
		}catch(Exception e){ 
			return null;
		}
	}
	
	/***
	 * 字符串转换为Date，不抛出异常。如果转换失败返回null
	 * @param strDate 时间字符串。默认格式 "yyyy-MM-dd"
	 * @return 转换失败返回null
	 */
	public static Date strToDate(String strDate){
		return strToDate(strDate,"yyyy-MM-dd");
	}
	
	
	/***
	 * 字符串转换为Date，不抛出异常。如果转换失败返回一个日期：1970年1月1日0点0分0秒
	 * @param strTime 时间字符串。默认格式 "HH:mm:ss"
	 * @return 如果转换失败返回一个日期：1970年1月1日0点0分0秒
	 */
	public static Date strToTimeSafe(String strTime){
		return getSafeDate(strToTime(strTime));
	}
	
	/***
	 * 字符串转换为Date，不抛出异常。如果转换失败返回null
	 * @param strTime 时间字符串。默认格式 HH:mm:ss
	 * @return 转换失败返回null
	 */
	public static Date strToTime(String strTime){
		try{
			return createDate(strTime,"HH:mm:ss");
		}catch(Exception e){
			return null;
		}
	}
	
	/***
	 * 将字符串转化为Date，不抛出异常。如果转换失败返回null
	 * @param str 任意包含日期、时间 或 日期+时间 的字符串
	 * @return 一个日期对象，转换失败返回null
	 */
	public static Date strToDateOrTime(String str){
		Date date=null;
		if(Str.isNullOrEmpty(str)){
			return null;
		}
		try{
			date=createDate(str, "yyyy-MM-dd HH:mm:ss");
		}catch(ParseException e){
			try{
				date=createDate(str, "yyyy-MM-dd");
			}catch(ParseException e2){
				try{
					date=createDate(str, "HH:mm:ss");
				}catch(ParseException e3){
					return null;
				}
			}
		}
		
		return date;
	}
	
	/***
	 * 将日期转换成字符串
	 * By GuRui on 2016-6-16 下午1:27:52
	 * @param date 日期
	 * @param pattern 如"yyyy-MM-dd" "yyyy-MM-dd hh:mm:ss"
	 * @return
	 */
	public static String dateToStr(Date date, String pattern){
		SimpleDateFormat sdf=new SimpleDateFormat(pattern);
		return sdf.format(date); 
	}

	/***
	 * 将日期转换成"yyyy-MM-dd hh:mm:ss"格式字符串
	 * @param date
	 * @return
	 */
	public static String dateToDateTimeStr(Date date){
		return DateTool.dateToStr(date, PATTERN_FULL_DATE_TIME);
	}
	
	/***
	 * 将日期转换成"yyyy-MM-dd"格式字符串
	 * @param date
	 * @return
	 */
	public static String dateToDateStr(Date date){
		return DateTool.dateToStr(date, PATTERN_DATE);
	}
	
	/***
	 * 将日期转换成"hh:mm:ss"格式字符串
	 * @param date
	 * @return
	 */
	public static String dateToTimeStr(Date date){
		return DateTool.dateToStr(date, PATTERN_TIME);
	}
	
	/***
	 * 字符串转化为日期，默认为"yyyy-MM-dd"格式
	 * By GuRui on 2015-1-14 下午10:19:11
	 * @param strDate
	 * @return
	 * @throws ParseException
	 */
	public static Date createDate(String strDate) throws ParseException{
		return createDate(strDate,PATTERN_DATE);
	}
	
	public static Date createDate(String strDate, String pattern) throws ParseException{
		DateFormat formatter = new SimpleDateFormat(pattern);
		return formatter.parse(strDate); 
	}
	
	/***
	 * 取得公元1900年1月1日0点0分0秒的日期
	 * By GuRui on 2016-5-29 下午12:29:19
	 * @return
	 */
	public static Date getDateYear1900(){
		return createDate(1900,1,1,0,0,0);
	}
	
	/***
	 * 取得从当前日期开始之后（正）或之前（负）若干天的日期
	 * By GuRui on 2016-5-29 下午12:27:54
	 * @param offsetDays 举例今天的天数，可正可负
	 * @return
	 */
	public static Date getDateFromNow(int offsetDays){
		Calendar cal=Calendar.getInstance();//当前日期   
	    cal.add(Calendar.DATE,offsetDays);//减一天，变为上月最后一天
	    return cal.getTime();
	}
}
