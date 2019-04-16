// By GuRui on 2017-10-17 下午1:43:47
package dlmu.mislab.tool.test;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import dlmu.mislab.tool.DateTool;
import dlmu.mislab.tool.JsonTimeOnlyDeserializer;

public class TestJackson {
	private static final ObjectMapper mapper = new ObjectMapper();
	static{
		mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);		
		mapper.configure(JsonParser.Feature.ALLOW_MISSING_VALUES, true);
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		mapper.configure(JsonParser.Feature.ALLOW_NUMERIC_LEADING_ZEROS, true);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		DateFormat df = new SimpleDateFormat(DateTool.PATTERN_FULL_DATE_TIME);
		mapper.setDateFormat(df);
	}
	
	
	/**
	 * By GuRui on 2017-10-17 下午1:43:47
	 * @param args
	 */
	public static void main(String[] args) throws JsonParseException {
		TestJackson.testRead();
		testCompare();
	}
	
	private static void testCompare(){
		JsonParent jp=new JsonParent();
		JsonTest jt=new JsonTest();
		jt.setAge(30);
		jt.setDob(new Date());
		jt.setName("张三");
		
		jp.setTest(jt);
		jp.setTodo("走");
		
		try {
			mapper.writer().forType(JsonParent.class).writeValue(System.out, jp);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private static void testRead(){
		try {
			
			ObjectReader reader=mapper.reader().forType(JsonParent.class);
			
			
			JsonParent jt= reader.readValue("{'yy':'yy', 'todo':'TODO', 'test':{'age':100, 'name':'Tom\\'s', 'dob':'12:33:33'}}");
			
			System.out.println(jt.getTest().getAge());
			System.out.println(jt.getTest().getName());
			System.out.println(jt.getTest().getDob());
			
		} catch (JsonMappingException e) {
			System.out.println(e.getLocalizedMessage());
			List<Reference> path=e.getPath();
			if(path!=null && path.size()>0){
				System.out.println(path.get(path.size()-1).getFieldName());	
			}
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

class CustomJsonDateDeserializer extends JsonDeserializer<Date>
{
	private String pattern=null;
	CustomJsonDateDeserializer(String pattern){
		this.pattern=pattern;
	}
	
    @Override
    public Date deserialize(JsonParser jsonparser,
            DeserializationContext deserializationcontext) throws IOException, JsonProcessingException {

        SimpleDateFormat format = new SimpleDateFormat(this.pattern);
        String date = jsonparser.getText();
        try {
            return format.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }

}



class JsonParent{
	String xx;
	public String getXx() {
		return xx;
	}
	public void setXx(String xx) {
		this.xx = xx;
	}
	String todo;
	JsonTest test;
	public String getTodo() {
		return todo;
	}
	public void setTodo(String todo) {
		this.todo = todo;
	}
	public JsonTest getTest() {
		return test;
	}
	public void setTest(JsonTest test) {
		this.test = test;
	}
	
}


class JsonTest{
	Integer age;
	String name;
	@JsonDeserialize(using=JsonTimeOnlyDeserializer.class)
	Date dob;
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	
}
