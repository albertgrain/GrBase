package dlmu.mislab.tool;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MapTools {

	public static Map<String, String> beanToSimpleMap(Object bean){
		HashMap<String, String> rtn = new HashMap<String, String>();
		if(bean == null){  
            return null;  
        }
        try {  
            BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());  
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();  
            for (PropertyDescriptor property : propertyDescriptors) {  
                String key = property.getName();  
  
                // 过滤class属性  
                if (!key.equals("class")) {  
                    // 得到property对应的getter方法  
                    Method getter = property.getReadMethod();  
                    Object value = getter.invoke(bean);  
                    if(value==null){
                    	rtn.put(key, null);
                    }else{
                    	if(value instanceof Date){
                    		rtn.put(key, DateTool.dateToDateTimeStr((Date)value));
                    	}else{
                    		rtn.put(key, value.toString());
                    	}
                    }
                }  
  
            }  
        } catch (Exception e) {  
            throw new ToolException(e);  
        }  
  
        return rtn;  
	}

	
	public static Map<String, String[]> beanToMap(Object bean){
		HashMap<String, String[]> rtn = new HashMap<String, String[]>();
		if(bean == null){  
            return null;  
        }
        try {  
            BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());  
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();  
            for (PropertyDescriptor property : propertyDescriptors) {  
                String key = property.getName();  
  
                // 过滤class属性  
                if (!key.equals("class")) {  
                    // 得到property对应的getter方法  
                    Method getter = property.getReadMethod();  
                    Object value = getter.invoke(bean);  
                    if(value==null){
                    	rtn.put(key, new String[]{null});
                    }else{
                    	if(value instanceof Date){
                    		rtn.put(key, new String[]{DateTool.dateToDateTimeStr((Date)value)});
                    	}else{
                    		rtn.put(key, new String[]{value.toString()});
                    	}
                    }
                }  
  
            }  
        } catch (Exception e) {  
            throw new ToolException(e);  
        }  
  
        return rtn;  
	}
	
    public static void mapToBean(Map<String, Object> map, Object bean) {
        try {  
            BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());  
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();  
  
            for (PropertyDescriptor property : propertyDescriptors) {  
                String key = property.getName();  
  
                if (map.containsKey(key)) {  
                    Object value = map.get(key);  
                    // 得到property对应的setter方法  
                    Method setter = property.getWriteMethod();  
                    setter.invoke(bean, value);  
                }  
            }  
        } catch (Exception e) {  
            throw new ToolException(e.getMessage(), e) ;
        }  
        return;  
    } 
}
