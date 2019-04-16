// By GuRui on 2017-10-17 下午5:45:41
package dlmu.mislab.tool;


/***
 * 将json字符串中的属性按照"yyyy-MM-dd HH:mm:ss"格式转化为java.util.Date对象
 * By GuRui on 2017-10-17 下午5:46:06
 *
 */
public class JsonDateTimeDeserializer extends JsonDateDeserializerBase
{
	public JsonDateTimeDeserializer(){
		super(DateTool.PATTERN_FULL_DATE_TIME);
	}
}
