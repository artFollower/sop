package com.skycloud.oa.utils;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * 
 * 删除
* @ClassName: JsonUtil 
* @Description: TODO
* @author xie
* @date 2014骞�1鏈�5鏃�涓嬪崍7:37:39 
*
 */
public class JsonUtil {
	private static ObjectMapper mapper = new ObjectMapper();
	static {
		mapper.setSerializationInclusion(Inclusion.NON_NULL);
	}

	public static String Object2Json(Object o) {
		if (o == null)
			return null;

		String s = null;
		try {
			s = mapper.writeValueAsString(o);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return s;
	}
}
