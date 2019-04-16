package dlmu.mislab.tool;

/***
 * 将json字符串中的属性按照"yyyy-MM-dd"格式转化为java.util.Date对象
 * By GuRui on 2017-10-17 下午5:46:06
 *
 */
public class JsonDateOnlyDeserializer extends JsonDateDeserializerBase
{
	public JsonDateOnlyDeserializer(){
		super(DateTool.PATTERN_DATE);
	}
}
