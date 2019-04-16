package dlmu.mislab.tool.test;

import dlmu.mislab.common.Config;

public class ConfigTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(Config.IS_DEBUG);
		System.out.println(Config.DEFAULT_RESOURCE_FOLDER);
		System.out.println(Config.DEFAULT_CONFIG_FOLDER);
		System.out.println(Config.CHARSET_ON_CLIENT);
		System.out.println(Config.VERSION);
	}

}
