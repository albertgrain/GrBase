// By GuRui on 2018-5-3 上午9:22:49
package dlmu.mislab.tool;

import java.io.File;

/***
 * File tools
 * By GuRui on 2018-5-3 上午9:22:53
 *
 */
public class Ft {
	public static File createFile(String... paths){
		File rtn=new File("/");
		for(String p: paths){
			rtn=new File(rtn, p);
		}
		return rtn;
	}
	
	public static void main(String[] args){
		File f=Ft.createFile("/","docshare","bin","aaa.dat");
		System.out.println(f.toString());
		
	}

}
