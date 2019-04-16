// By GuRui on 2015-3-30 下午2:22:53
package dlmu.mislab.tool;

public class Int {
	/**
	 * Parse Integer safely from String
	 * By GuRui on 2015-3-30 下午2:24:55
	 * @param strInt String to be parsed
	 * @param defaultValue value returned if parse failed or is empty
	 * @return
	 */
	public static int parse(String strInt, int defaultValue){
		if(Str.isNullOrEmpty(strInt)){
			return defaultValue;
		}else{
			int rtn=defaultValue;
			try{
				rtn=Integer.parseInt(strInt);
			}catch(Exception e){}
			return rtn;
		}
	}
}
