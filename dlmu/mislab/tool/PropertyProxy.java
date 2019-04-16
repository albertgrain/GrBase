package dlmu.mislab.tool;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Properties;

import org.slf4j.Logger;

/**
 * 用于从配置文件中读取培新的辅助类。配置信息以Properties对象返回或存入
 * By GuRui on 2015-7-3 上午12:13:29
 *
 */
public class PropertyProxy {
	private Logger logger;
	
	public PropertyProxy (){}
	
	public PropertyProxy(Logger logger){
		this.logger=logger;
	}
	
	/**
	 * 根据指定文件名加载Properties
	 * By GuRui on 2015-7-3 上午12:14:26
	 * @param propFileName
	 * @return 一定不会返回null
	 */
	public Properties load(String propFileName){
		Properties prop=new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream(propFileName);
			prop.load(input);
		} catch (IOException ex) {
			this.logError(ex);
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					this.logError(e);
				}
			}
		}
		return prop==null?new Properties():prop;
	}
	
	/**
	 * 利用InputStream加载信息
	 * 当用InputStream input = servletContext.getResourceAsStream("/index.jsp");
	 * 获得InputStream时使用此方法
	 * By GuRui on 2015-7-2 下午5:20:46
	 * @param ins
	 * @return 一定不会返回null
	 */
	public Properties load(InputStream ins){
		Properties prop=new Properties();
		try {
			prop.load(ins);
		} catch (IOException e) {
			if(ins!=null){
				try{ins.close();}catch(Exception ex){}
			}
			this.logError(e);
		}
		return prop==null?new Properties():prop;
	}
	
	/**
	 * 保存Properties
	 * By GuRui on 2015-7-3 上午12:15:46
	 * @param propFileName
	 * @param prop
	 * @return true为成功
	 */
	public boolean save(String propFileName, Properties prop){
		if(prop==null || Str.isNullOrEmpty(propFileName)){
			return false;
		}
		
		boolean rtn=false;
		OutputStream output = null;
		try {
			output = new FileOutputStream(propFileName);
			prop.store(output, null);
			rtn=true;
		} catch (IOException io) {
			this.logError(io);
			rtn=false;
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					this.logError(e);
					rtn=false;
				}
			}
		}
		return rtn;
	}
	
	private void logError(Exception ex){
		if(this.logger!=null){
			this.logger.error(ex.getMessage());
		}else{
			ex.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		PropertyProxy fup=new PropertyProxy();
		Properties props = fup.load("\\test.cfg");
		String test=props.getProperty("test","");
		String date=props.getProperty("time","");
		System.out.println(test.toString()+"  "+date.toString());
		props.setProperty("time", new Date().toString());
		props.setProperty("aNull", null); //ERROR
		props.setProperty("aEmpty", "");
		fup.save("\\test.cfg", props);
		
	}
}
