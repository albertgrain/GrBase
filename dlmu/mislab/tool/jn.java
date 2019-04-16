package dlmu.mislab.tool;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dlmu.mislab.common.Config;
import dlmu.mislab.common.IJson;

/**
 * Google的Gson的辅助类
 * @author GuRui
 *
 */
public class jn {
	private static Logger logger=LoggerFactory.getLogger(jn.class);
	
	private static final ObjectMapper mapper = new ObjectMapper();
	static{
		mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);		
		mapper.configure(JsonParser.Feature.ALLOW_MISSING_VALUES, true);
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		mapper.configure(JsonParser.Feature.ALLOW_NUMERIC_LEADING_ZEROS, true);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.setDateFormat(new SimpleDateFormat(DateTool.PATTERN_DEFAULT));
	}
	
	/***
	 * 将IJson类型对象转化为Json字符串并从指定Writer输出
	 * By GuRui on 2017-10-17 下午5:36:23
	 * @param obj 待转化为Json的对象
	 * @param write 输出
	 * @return 失败返回false，writer中被写入空字符串
	 */
	public static boolean toJson(Object obj, Writer writer){
		try {
			mapper.writer().forType(obj.getClass()).writeValue(writer, obj);
		} catch(IOException e){
			logger.error(e.getMessage());
			try {
				writer.close();
			} catch (IOException e1) {}
			return false;
		}
		return true;
	}
	
	/***
	 * 将IJson类型对象转化为Json字符串并返回字符串
	 * @param obj 待转化为Json的对象
	 * @return 失败返回空字符串
	 */
	public static String toJson(Object obj) {
		Writer writer=new StringWriter();
		return toJson(obj,writer)? writer.toString(): "";
	}
	
	public static <T> String toJsonList(List<T> list){
		Writer writer=new StringWriter();
		return toJsonList(list,writer)? writer.toString(): "";
	}
	
	public static <T> boolean toJsonList(List<T> list, Writer writer){
		try {
			mapper.writer().forType(list.getClass()).writeValue(writer, list);
		} catch(IOException e){
			logger.error(e.getMessage());
			try {
				writer.close();
			} catch (IOException e1) {}
			return false;
		}
		return true;
	}

	@Deprecated
	/***
	 * 将对象转化成Json字符串
	 * By GuRui on 2015-1-18 下午2:01:09
	 * @param pattern 时间日期转换格式
	 * @param obj 待转换对象
	 * @return
	 */
	public static String toJson(String pattern, Object obj) {
		return toJson((IJson)obj);
	}

	/***
	 * 从字符串中解析并装配java对象
	 * @param json json字符串
	 * @param clazz 待返回对象类型。对于日期字段，需要在字段上加"@JsonDeserialize(using = JsonDateTimeDeserializer.class /JsonDateOnlyDeserializer.class /JsonTimeOnlyDeserializer.class)"以指定具体的转换方式。
	 * @return json为null或空或格式有问题返回null
	 */
	public static <T> T fromJson(String json,Class<T> clazz) throws JsonParseBadFieldException{
		if(Str.isNullOrEmpty(json)){
			return null;
		}
		if(Config.IS_DEBUG){
			logger.debug(json);
		}
		try {
			return mapper.reader().forType(clazz).readValue(json);
		} catch (JsonMappingException e) {
			logger.error(e.getLocalizedMessage());
			List<Reference> path=e.getPath();
			if(path!=null && path.size()>0){
				throw new JsonParseBadFieldException(path.get(path.size()-1).getFieldName());	
			}
			return null;
		} catch (IOException e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@Deprecated
	/***
	 * 从字符串中解析并装配java对象。注意，不同于默认情况，transient类型的字段也会被解析。
	 * By GuRui on 2015-1-18 下午2:07:46
	 * @param pattern 日期/时间样式。入"yyyy-MM-dd hh:mm:ss"
	 * @param json json字符串
	 * @param clazz 待返回对象类型
	 * @return json为null或格式有问题返回null，否则返回一个非空对象，但其属性全为null
	 */
	public static <T> T fromJson(final String pattern, String json, Class<T> clazz) {
		return fromJson(json,clazz);	
	}
	
	
	/***
	 * 从字符串中解析并装配java对象列表。注意，不同于默认情况，transient类型的字段也会被解析。
	 * @param json json字符串
	 * @param clazz 希望解析成的对象的类型
	 * @return 非空列表对象.若json并非数组，或是数组但其单项解析失败，都会返回0长数组对象。
	 */
	public static <T> List<T> fromJsonToList(String json, Class<T> clazz){
		throw new RuntimeException("TODO");
	}

	@Deprecated
	/***
	 * 从字符串中解析并装配java对象列表。注意，不同于默认情况，transient类型的字段也会被解析。
	 * @param pattern 解析日期的模式,如"yyyy-MM-dd HH:mm:ss"
	 * @param json json字符串
	 * @param clazz 希望解析成的对象的类型
	 * @return 非空列表对象.若json并非数组，或是数组但其单项解析失败，都会返回0长数组对象。
	 */
	public static <T> List<T> fromJsonToList(String pattern, String json, Class<T> clazz){
		return fromJsonToList(json, clazz);
	}
}




