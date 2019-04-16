// By GuRui on 2017-10-17 下午5:43:13
package dlmu.mislab.tool;

/***
 * 将json字符串中的属性按照"HH:mm:ss"格式转化为java.util.Date对象
 * By GuRui on 2017-10-17 下午5:46:06
 *
 */
public class JsonTimeOnlyDeserializer extends JsonDateDeserializerBase
{
	public JsonTimeOnlyDeserializer(){
		super(DateTool.PATTERN_TIME);
	}
}
