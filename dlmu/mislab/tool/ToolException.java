// By GuRui on 2014-12-6 下午12:22:53
package dlmu.mislab.tool;


public class ToolException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public ToolException(String msg){
		super(msg);
	}
	public ToolException(Throwable e){
		super(e);
	}
	
	public ToolException(String msg, Throwable e){
		super(msg, e);
	}
}