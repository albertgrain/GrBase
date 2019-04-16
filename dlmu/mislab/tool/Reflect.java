// By GuRui on 2015-8-8 上午2:36:32
package dlmu.mislab.tool;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Reflect {
	public static List<Field> getFieldsUpTo(Class<?> startClass) {
		List<Field> rtn = new LinkedList<Field>();
		rtn.addAll(Arrays.asList(startClass.getDeclaredFields()));

		Class<?> parentClass = startClass.getSuperclass();
		if (parentClass != null) {
			List<Field> parentClassFields = getFieldsUpTo(parentClass); //This does not work if the field of parent class is private
			rtn.addAll(parentClassFields);
		}

		return rtn;
	}
	
	/***
	 * If a field of bean is defined as:
	 * 		List<String> stringList = new ArrayList<String>();
	 * then, this methond can get the type of String by relection
	 * By GuRui on 2016-9-2 上午10:49:10
	 * @param f
	 * @return
	 */
	public static Type getGernericTypeOfAList(Field f){
		ParameterizedType childType = (ParameterizedType) f.getGenericType();
		return childType.getActualTypeArguments()[0];
	}
}
