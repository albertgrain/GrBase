package dlmu.mislab.tool;

public class JsonParseBadFieldException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private String badFieldName=null;
	public JsonParseBadFieldException(String badFieldName){
		this.badFieldName=badFieldName;
	}
	public String getBadFieldName() {
		return badFieldName;
	}
	
	
}
