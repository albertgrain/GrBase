package dlmu.mislab.tool;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Str {
	/***
	 * 从target中寻找findStr出现的次数。如果target或findStr为空则返回-1
	 * @param target 目标字符串
	 * @param findStr 待查找的字符串
	 * @return -1表示查找错误
	 */
	public static int countMatches(String target, String findStr){
		if(Str.isNullOrEmpty(target) || Str.isNullOrEmpty(findStr)){
			return -1;
		}
		int lastIndex = 0;
		int count = 0;
		while(lastIndex != -1){
		    lastIndex = target.indexOf(findStr,lastIndex);
		    if(lastIndex != -1){
		        count ++;
		        lastIndex += findStr.length();
		    }
		}
		return count;
	}
	
	/***
	 * 将字符串中的非法字符转义成符合SQL规范的字符串
	 * By GuRui on 2017-11-14 上午10:54:09
	 * @param s
	 * @return
	 */
	public static String escapeSQL(String s) {
		return SQLEscaper.escapeString(s, true);
	}
	
	/***
	 * 将字符串中的非法字符转义成符合SQL规范的字符串
	 * By GuRui on 2017-11-14 上午10:55:05
	 * @param x 目标字符串
	 * @param escapeDoubleQuotes 是否
	 * @return
	 */
    public static String escapeSQL(String x, boolean escapeDoubleQuotes) {
        StringBuilder sBuilder = new StringBuilder(x.length() * 11/10);

        int stringLength = x.length();

        for (int i = 0; i < stringLength; ++i) {
            char c = x.charAt(i);

            switch (c) {
            case 0: /* Must be escaped for 'mysql' */
                sBuilder.append('\\');
                sBuilder.append('0');

                break;

            case '\n': /* Must be escaped for logs */
                sBuilder.append('\\');
                sBuilder.append('n');

                break;

            case '\r':
                sBuilder.append('\\');
                sBuilder.append('r');

                break;

            case '\\':
                sBuilder.append('\\');
                sBuilder.append('\\');

                break;

            case '\'':
                sBuilder.append('\\');
                sBuilder.append('\'');

                break;

            case '"': /* Better safe than sorry */
                if (escapeDoubleQuotes) {
                    sBuilder.append('\\');
                }

                sBuilder.append('"');

                break;

            case '\032': /* This gives problems on Win32 */
                sBuilder.append('\\');
                sBuilder.append('Z');

                break;

            case '\u00a5':
            case '\u20a9':
                // escape characters interpreted as backslash by mysql
                // fall through

            default:
                sBuilder.append(c);
            }
        }

        return sBuilder.toString();
    }
    
	/***
	 * 将字符串转化为安全字符串：如果字符串为空，则转化为默认字符串
	 * By GuRui on 2017-11-14 上午10:48:32
	 * @param str
	 * @param defaultStr
	 * @return
	 */
	public static String toSafeString(String str, String defaultStr){
		if(str==null){
			return defaultStr;
		}else{
			return str;
		}
	}

	/***
	 * 将任何对象（包括字符串）转化为toString()字符串。如果为空，则转化为空字符串
	 * By GuRui on 2017-11-14 上午10:49:39
	 * @param obj
	 * @return
	 */
	public static String toSafeString(Object obj){
		if(obj==null){
			return "";
		}else{
			return obj.toString();
		}
	}

	public static boolean isNullOrEmpty(String s) {
		return s == null || s.isEmpty();
	}

	/***
	 * Date类型格式化为字符串
	 * By GuRui on 2015-1-14 下午10:15:41
	 * @param date 日期变量
	 * @param pattern 格式字符串
	 * @return
	 */
	public static String dateToString(final Date date,String pattern){
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}

	public static String getChineseWeekDay(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int dw=c.get(Calendar.DAY_OF_WEEK);
		if(dw == 1){
			return "日";
		}else{
			dw = dw - 1;
		}
		return Str.toChinese(dw);
	}

	/***
	 * 一位数字转大写汉字
	 * By GuRui on 2015-1-12 下午3:20:00
	 * @param num
	 * @return
	 */
	public static String toChinese(String num){
		if(num==null || num.isEmpty()){
			return "空";
		}else{
			if(num.length()>1){
				throw new IllegalArgumentException("只能解析一位数字");
			}
			return toChinese(num);
		}
	}

	/***
	 * 一位数字转大写汉字
	 * By GuRui on 2015-1-12 下午3:20:22
	 * @param num
	 * @return
	 */
	public static String toChinese(int num) {
		String Cnum = null;
		switch (num) {
		case 0:
			Cnum = "零";
			break;
		case 1:
			Cnum = "一";
			break;
		case 2:
			Cnum = "二";
			break;
		case 3:
			Cnum = "三";
			break;
		case 4:
			Cnum = "四";
			break;
		case 5:
			Cnum = "五";
			break;
		case 6:
			Cnum = "六";
			break;
		case 7:
			Cnum = "七";
			break;
		case 8:
			Cnum = "八";
			break;
		case 9:
			Cnum = "九";
			break;
		default:
			throw new IllegalArgumentException("只能解析一位数字");
		}
		return Cnum;
	}
	/***
	 * 将当前日期转换为周几
	 * By Yhq on 2015-1-12 下午3:20:22
	 * @param num
	 * @return
	 */
	public static String getWeekDay(int i)
	{
		if (Calendar.MONDAY == i)
		{
			return "周一";
		}
		if (Calendar.TUESDAY == i)
		{
			return "周二";
		}
		if (Calendar.WEDNESDAY == i)
		{
			return "周三";
		}
		if (Calendar.THURSDAY == i)
		{
			return "周四";
		}
		if (Calendar.FRIDAY == i)
		{
			return "周五";
		}
		if (Calendar.SATURDAY == i)
		{
			return "周六";
		}
		if (Calendar.SUNDAY == i)
		{
			return "周日";
		}
		return null;
	}

	public static String getFileNameShort(File f){
		return (f==null)?"":f.getName();
	}

	public static String getFileNameShort(String filename){
		return getFileNameShort(new File(filename));
	}

	public static String getFileNameShortWithoutExt(File f){
		String shortname=getFileNameShort(f);
		int idx=shortname.lastIndexOf('.');
		return idx<0?shortname:shortname.substring(0,idx);
	}

	public static String getFileNameShortWithoutExt(String filename){
		return getFileNameShortWithoutExt(new File(filename));
	}

	public static String getFileExt(File f){
		String shortname=getFileNameShort(f);
		return shortname.substring(shortname.lastIndexOf('.')+1);
	}

	public static String getFileExt(String filename){
		return getFileNameShortWithoutExt(new File(filename));
	}

	public static String getFileExtension(File f){
		return f.getParent();
	}
	
	/***
	 * Unicode字符串还原为字符串
	 * 如："\u652f\u4ed8\u5fc5\u8981\u914d\u7f6e\u672a\u914d\u7f6e\u4e14\u6ca1\u6709\u4f20\u5165"
	 * 解码为：支付必要配置未配置且没有传入
	 * By GuRui on 2018-1-17 下午12:56:14
	 * @param unicode
	 * @return 输入为空则返回null
	 */
	public static String unicodeToString(String unicode){
		if(Str.isNullOrEmpty(unicode)){
			return null;
		}
		unicode = unicode.replace("\\","");
		String[] arr = unicode.split("u");
		return arr[0];
	}
	
	/***
	 * 将字符串编码为Unicode字符串
	 * By GuRui on 2018-1-17 下午12:56:56
	 * @param s
	 * @return 出错返回	 */
	public static String getUnicode(String s) {
        try {
            StringBuffer out = new StringBuffer("");
            byte[] bytes = s.getBytes("unicode");
            for (int i = 0; i < bytes.length - 1; i += 2) {
                out.append("\\u");
                String str = Integer.toHexString(bytes[i + 1] & 0xff);
                for (int j = str.length(); j < 2; j++) {
                    out.append("0");
                }
                String str1 = Integer.toHexString(bytes[i] & 0xff);
                out.append(str1);
                out.append(str);
                 
            }
            return out.toString();
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }
		
	public static String moneyToChinese(double money){
        return moneyToChinese(new BigDecimal(money));
	}
	
	public static String moneyToChinese(BigDecimal money){
        return NumberToChinese.number2CNMontrayUnit(money);
	}
	
	/***
	 * 内部辅助类：数字转换为汉语中人民币的大写
	 */
	static class NumberToChinese {
		/**
	     * 汉语中数字大写
	     */
	    private static final String[] CN_UPPER_NUMBER = { "零", "壹", "贰", "叁", "肆",
	            "伍", "陆", "柒", "捌", "玖" };
	    /**
	     * 汉语中货币单位大写，这样的设计类似于占位符
	     */
	    private static final String[] CN_UPPER_MONETRAY_UNIT = { "分", "角", "元",
	            "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟", "兆", "拾",
	            "佰", "仟" };
	    /**
	     * 特殊字符：整
	     */
	    private static final String CN_FULL = "整";
	    /**
	     * 特殊字符：负
	     */
	    private static final String CN_NEGATIVE = "负";
	    /**
	     * 金额的精度，默认值为2
	     */
	    private static final int MONEY_PRECISION = 2;
	    /**
	     * 特殊字符：零元整
	     */
	    private static final String CN_ZEOR_FULL = "零元" + CN_FULL;

	    /**
	     * 把输入的金额转换为汉语中人民币的大写
	     * 
	     * @param numberOfMoney
	     *            输入的金额
	     * @return 对应的汉语大写
	     */
	    public static String number2CNMontrayUnit(BigDecimal numberOfMoney) {
	        StringBuffer sb = new StringBuffer();
	        // -1, 0, or 1 as the value of this BigDecimal is negative, zero, or
	        // positive.
	        int signum = numberOfMoney.signum();
	        // 零元整的情况
	        if (signum == 0) {
	            return CN_ZEOR_FULL;
	        }
	        //这里会进行金额的四舍五入
	        long number = numberOfMoney.movePointRight(MONEY_PRECISION)
	                .setScale(0, 4).abs().longValue();
	        // 得到小数点后两位值
	        long scale = number % 100;
	        int numUnit = 0;
	        int numIndex = 0;
	        boolean getZero = false;
	        // 判断最后两位数，一共有四中情况：00 = 0, 01 = 1, 10, 11
	        if (!(scale > 0)) {
	            numIndex = 2;
	            number = number / 100;
	            getZero = true;
	        }
	        if ((scale > 0) && (!(scale % 10 > 0))) {
	            numIndex = 1;
	            number = number / 10;
	            getZero = true;
	        }
	        int zeroSize = 0;
	        while (true) {
	            if (number <= 0) {
	                break;
	            }
	            // 每次获取到最后一个数
	            numUnit = (int) (number % 10);
	            if (numUnit > 0) {
	                if ((numIndex == 9) && (zeroSize >= 3)) {
	                    sb.insert(0, CN_UPPER_MONETRAY_UNIT[6]);
	                }
	                if ((numIndex == 13) && (zeroSize >= 3)) {
	                    sb.insert(0, CN_UPPER_MONETRAY_UNIT[10]);
	                }
	                sb.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
	                sb.insert(0, CN_UPPER_NUMBER[numUnit]);
	                getZero = false;
	                zeroSize = 0;
	            } else {
	                ++zeroSize;
	                if (!(getZero)) {
	                    sb.insert(0, CN_UPPER_NUMBER[numUnit]);
	                }
	                if (numIndex == 2) {
	                    if (number > 0) {
	                        sb.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
	                    }
	                } else if (((numIndex - 2) % 4 == 0) && (number % 1000 > 0)) {
	                    sb.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
	                }
	                getZero = true;
	            }
	            // 让number每次都去掉最后一个数
	            number = number / 10;
	            ++numIndex;
	        }
	        // 如果signum == -1，则说明输入的数字为负数，就在最前面追加特殊字符：负
	        if (signum == -1) {
	            sb.insert(0, CN_NEGATIVE);
	        }
	        // 输入的数字小数点后两位为"00"的情况，则要在最后追加特殊字符：整
	        if (!(scale > 0)) {
	            sb.append(CN_FULL);
	        }
	        return sb.toString();
	    }
	}
	
	
	public static void main(String[] args){
		//System.out.print(getFileExtension(new File("d:\\test\\test1.doc")));
		//System.out.print(getFileNameShortWithoutExt(""));
        System.out.println("2020004.01 读作：" + moneyToChinese(2020004.01));
	}
	
} // End of Class Str


