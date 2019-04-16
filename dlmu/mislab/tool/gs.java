package dlmu.mislab.tool;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import dlmu.mislab.common.Config;

@Deprecated
/**
 * Google的Gson的辅助类
 * @author GuRui
 *
 */
public class gs {
	private static Logger logger=LoggerFactory.getLogger(gs.class);
	public static String toJson(Object obj) {
		return gs.toJson(DateTool.PATTERN_DEFAULT, obj);
	}

	/***
	 * 将对象转化成Json字符串
	 * By GuRui on 2015-1-18 下午2:01:09
	 * @param pattern 时间日期转换格式
	 * @param obj 待转换对象
	 * @return
	 */
	public static String toJson(String pattern, Object obj) {
		Gson gson = new GsonBuilder().setDateFormat(pattern).create();

		String rtn=gson.toJson(obj);
		if(Config.IS_DEBUG){
			System.out.println(rtn);
		}
		return rtn;
	}

	/***
	 * 从字符串中解析并装配java对象
	 * @param json json字符串
	 * @param clazz 待返回对象类型
	 * @return json为null或格式有问题返回null，否则返回一个非空对象，但其属性全为null
	 */
	public static <T> T fromJson(String json,Class<T> clazz) {
		return fromJson(DateTool.PATTERN_DEFAULT, json, clazz);
	}

	/***
	 * 从字符串中解析并装配java对象。注意，不同于默认情况，transient类型的字段也会被解析。
	 * By GuRui on 2015-1-18 下午2:07:46
	 * @param pattern 日期/时间样式。入"yyyy-MM-dd hh:mm:ss"
	 * @param json json字符串
	 * @param clazz 待返回对象类型
	 * @return json为null或格式有问题返回null，否则返回一个非空对象，但其属性全为null
	 */
	public static <T> T fromJson(final String pattern, String json, Class<T> clazz) {
		if(Config.IS_DEBUG){
			logger.debug(json);
		}
		T rtn= null;
		try{
			rtn =gs.createGson(pattern).fromJson(json,clazz);
		}catch(Exception e){
			logger.error("Bad formatted json:\n" + json);
		}
		return rtn;		
	}
	
	private static Gson gson=null;
	public static Gson createGson(final String datePattern){
		if(gson!=null){
			return gson;
		}
		GsonBuilder gb = new GsonBuilder();	
		//The adapter is used to parse "" to null without error;
		gb.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
	        DateFormat df = new SimpleDateFormat(datePattern);
			@Override
			public Date deserialize(JsonElement je, Type arg1,
					JsonDeserializationContext jc) throws JsonParseException {
				try {
	                return df.parse(je.getAsString());
	            } catch (ParseException e) {
	            	logger.error(e.getMessage());
	                return null;
	            }
			}
	    });
		gb.registerTypeAdapter(Integer.class, new JsonDeserializer<Integer>(){
			@Override
			public Integer deserialize(JsonElement je, Type arg1,
					JsonDeserializationContext arg2) throws JsonParseException {
				try{
					return Integer.parseInt(je.getAsString());
				}catch(Exception e){
					logger.error(e.getMessage());
					return null;
				}
			}
			
		});
		
		gb.registerTypeAdapter(Double.class, new JsonDeserializer<Double>(){
			@Override
			public Double deserialize(JsonElement je, Type arg1,
					JsonDeserializationContext arg2) throws JsonParseException {
				try{
					return Double.parseDouble(je.getAsString());
				}catch(Exception e){
					logger.error(e.getMessage());
					return null;
				}
			}
			
		});
		
		
		gb.registerTypeAdapter(Float.class, new JsonDeserializer<Float>(){
			@Override
			public Float deserialize(JsonElement je, Type arg1,
					JsonDeserializationContext arg2) throws JsonParseException {
				try{
					return Float.parseFloat(je.getAsString());
				}catch(Exception e){
					logger.error(e.getMessage());
					return null;
				}
			}
			
		});
		
		
		gb.registerTypeAdapter(Long.class, new JsonDeserializer<Long>(){
			@Override
			public Long deserialize(JsonElement je, Type arg1,
					JsonDeserializationContext arg2) throws JsonParseException {
				try{
					return Long.parseLong(je.getAsString());
				}catch(Exception e){
					logger.error(e.getMessage());
					return null;
				}
			}
			
		});
		gb.registerTypeAdapter(Short.class, new JsonDeserializer<Short>(){
			@Override
			public Short deserialize(JsonElement je, Type arg1,
					JsonDeserializationContext arg2) throws JsonParseException {
				try{
					return Short.parseShort(je.getAsString());
				}catch(Exception e){
					logger.error(e.getMessage());
					return null;
				}
			}
			
		});
	    
		//gb.setDateFormat(pattern);
		gb.excludeFieldsWithModifiers(Modifier.STATIC); //excludestaticfields from Gson serialization, but nottransientandvolatileones.
		gson=gb.create();
		return gson;
		
	}
	
	/***
	 * 从字符串中解析并装配java对象列表。注意，不同于默认情况，transient类型的字段也会被解析。
	 * @param json json字符串
	 * @param clazz 希望解析成的对象的类型
	 * @return 非空列表对象.若json并非数组，或是数组但其单项解析失败，都会返回0长数组对象。
	 */
	public static <T> List<T> fromJsonToList(String json, Class<T> clazz){
		return gs.fromJsonToList(DateTool.PATTERN_DEFAULT, json, clazz);
	}

	/***
	 * 从字符串中解析并装配java对象列表。注意，不同于默认情况，transient类型的字段也会被解析。
	 * @param pattern 解析日期的模式,如"yyyy-MM-dd HH:mm:ss"
	 * @param json json字符串
	 * @param clazz 希望解析成的对象的类型
	 * @return 非空列表对象.若json并非数组，或是数组但其单项解析失败，都会返回0长数组对象。
	 */
	public static <T> List<T> fromJsonToList(String pattern, String json, Class<T> clazz){
		//		Type listType = new TypeToken<List<T>>(){}.getType();
		//		return new Gson().fromJson(json, listType);
		List<T> rtn= new LinkedList<T>();
		JsonParser parser = new JsonParser();
		JsonElement el = null;
		try{
			el=parser.parse(json);
		}catch(Exception e){
			logger.error(e.getMessage());
			return null;
		}
		if(el.isJsonArray()){
			JsonArray array=el.getAsJsonArray();
			for(JsonElement item:array){
				T t=null;
				try{
					t=gs.createGson(pattern).fromJson(item, clazz );
				}catch(JsonSyntaxException e){
					logger.error("Bad formatted json:\n" + item.toString());
				}
				if(t!=null){
					rtn.add(t);
				}
			}
		}
		return rtn;
	}
}
