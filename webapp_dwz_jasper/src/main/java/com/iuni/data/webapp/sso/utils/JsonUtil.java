package com.iuni.data.webapp.sso.utils;

import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.map.util.JSONPObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 对Jackson的封装，实现Json和JAVA的相互转换
 * @author CaiKe
 * @version dp-service-1.0.0
 */
public class JsonUtil {

	private ObjectMapper mapper;

	public JsonUtil() {
		mapper = new ObjectMapper();
		//只输出初始值被改变的属性,其它风格见Inclusion(Inclusion.ALWAYS,Inclusion.NON_NULL)
		mapper.getSerializationConfig().setSerializationInclusion(Inclusion.NON_DEFAULT);
		mapperInit();
	}

	/**
	 * 自定义风格
	 */
	public JsonUtil(Inclusion inclusion) {
		mapper = new ObjectMapper();
		mapper.getSerializationConfig().setSerializationInclusion(inclusion);
		mapperInit();
	}

	private void mapperInit() {
		//忽略在JSON中存在但Java对象没有的属性
		mapper.configure(org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		//禁用int代表Enum的order()來反序列化Enum
		mapper.configure(DeserializationConfig.Feature.FAIL_ON_NUMBERS_FOR_ENUMS, true);
	}

	/**
	 * Json转对象 (若JSON为Null或"null"字符串, 返回Null;若JSON字符串为"[]", 返回空集合)
	 */
	public <T> T fromJson(String jsonString, Class<T> clazz) {
		if (StringUtils.isEmpty(jsonString)) {
			return null;
		}

		try {
			return mapper.readValue(jsonString, clazz);
		} catch (IOException e) {
			return null;
		}
	}
	
	public <T> List<T> fromJson(List<String> jsonStringList, Class<T> clazz) {
		List<T> objList = new ArrayList<T>();
		if (jsonStringList != null && jsonStringList.size() > 0) {
			for (String jsonString : jsonStringList) {
				objList.add(fromJson(jsonString, clazz));
			}
		}
		return objList;
	}

	/**
	 * 对象转Json(对象为null, 返回"null".集合为空，返回"[]")
	 */
	public String toJson(Object object) {

		try {
			return mapper.writeValueAsString(object);
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * 输出JSONP格式
	 */
	public String toJsonP(String functionName, Object object) {
		return toJson(new JSONPObject(functionName, object));
	}
	
	/**
	 * @Description: 将json字符串对象转换为传入类型的java List对象列表
	 * @param Object jsonStrObj json字符串对象
	 * @param Class<T> objClass 转换后的java对象class类型
	 * @return <T> List<T>      转换后的List对象列表
	 */
    @SuppressWarnings({ "unchecked", "deprecation" })
	public static <T> List<T> toList(Object jsonStrObj, Class<T> objClass) throws net.sf.json.JSONException {
        JSONArray jsonArray = JSONArray.fromObject(jsonStrObj);
        return JSONArray.toList(jsonArray, objClass);
    }
    
}
