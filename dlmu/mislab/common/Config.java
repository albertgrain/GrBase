package dlmu.mislab.common;

import java.io.File;

public class Config {
	public static final String VERSION="1.0"; //2017-04-23
	public static final boolean IS_DEBUG = java.lang.management.ManagementFactory.getRuntimeMXBean().getInputArguments().toString().indexOf("jdwp") >= 0;
	//public static final String CHARSET_ON_SERVER = "utf-8"; // "ISO-8859-1";
	public static final String CHARSET_ON_CLIENT = "UTF-8";
	
	public static final char PATH_DELIMETER=File.separatorChar;
	public static final String DEFAULT_RESOURCE_FOLDER = PATH_DELIMETER +"docshare" + PATH_DELIMETER;
	public static final String DEFAULT_CONFIG_FOLDER = DEFAULT_RESOURCE_FOLDER + "conf" + PATH_DELIMETER;
	public static final String CONFIG_FILE_EXT=".cfg";
}
