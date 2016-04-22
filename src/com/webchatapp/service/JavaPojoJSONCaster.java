package com.webchatapp.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
/**
 * Used To Cast JavaPojo to Json and Json to JavaPojo respectively...
 * @author rupeshsh
 * @version 1.0 Modified Date : 
 *
 */
public class JavaPojoJSONCaster {
	Object pojo = null;

	/**
	 * Used to convert the given List of Java POJO to JSONObject...
	 * 
	 * @param convert_ThisPojoList
	 * @param class1
	 * @return jsonObject
	 */
	public JSONObject convert_ThisPojoList_To_JSONObject(
			List<Object> convert_ThisPojoList, Class class1) {
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < convert_ThisPojoList.size(); i++) {
			jsonArray.put(convert_ThisPojo_To_JSONObject(
					convert_ThisPojoList.get(i), class1));
		}
		try {
			jsonObject.put(class1.getSimpleName() + "List", jsonArray);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;
	}

	/**
	 * Used to convert the given List of Java POJO to JSONArray...
	 * 
	 * @param convert_ThisPojoList
	 * @param class1
	 * @return jsonArray
	 */
	public JSONArray convert_ThisPojoList_To_JSONArray(
			List<Object> convert_ThisPojoList, Class class1) {
		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < convert_ThisPojoList.size(); i++) {
			jsonArray.put(convert_ThisPojo_To_JSONObject(
					convert_ThisPojoList.get(i), class1));
		}
		return jsonArray;
	}

	/**
	 * Used to convert the given Java POJO to JSONObject...
	 * 
	 * @param convert_ThisPojo
	 * @param class1
	 * @return jsonObject
	 */
	public JSONObject convert_ThisPojo_To_JSONObject(Object convert_ThisPojo,
			Class class1) {
		JSONObject jsonObject = new JSONObject();
		Field[] fields = class1.getDeclaredFields();

		try {
			for (int i = 0; i < fields.length; i++) {
				fields[i].setAccessible(true);
				try {
					String type=fields[i].getType().getSimpleName();
					if("String".equals(type)){
						jsonObject.put(fields[i].getName(),
								fields[i].get(convert_ThisPojo));
					}else if("Integer".equals(type)){
						jsonObject.put(fields[i].getName(),
								fields[i].get(convert_ThisPojo));	
					}else if("int".equals(type)){
						jsonObject.put(fields[i].getName(),
								fields[i].getInt(convert_ThisPojo));	
					}else if("Calendar".equals(type)){
						jsonObject.put(fields[i].getName(),
								fields[i].get(convert_ThisPojo));	
					}else{
						Object current = convert_ThisPojo_To_JSONObject(
								fields[i].get(convert_ThisPojo),
								fields[i].getType());
						jsonObject.put(fields[i].getName(), current);	
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {

		}
		return jsonObject;
	}

	/**
	 * Used to convert the given JSONObject to the given Java POJO ...
	 * 
	 * @param convert_ThisJSON
	 * @param convertTo_ThisPojo
	 * @param class1
	 * @return convertTo_ThisPojo
	 */
	public Object convert_ThisJSON_To_ThisPojo(String convert_ThisJSON,
			Object convertTo_ThisPojo, Class class1) {
		try {
			List<Object> objectList = new ArrayList<Object>();
			JSONArray jsonArray = new JSONArray(convert_ThisJSON);
			for (int i = 0; i < jsonArray.length(); i++) {
				objectList.add(convertClassToRespectedType(class1,
						convertTo_ThisPojo, jsonArray.getJSONObject(i)));
			}
			System.out.println("JSONArray");
			return objectList;
		} catch (JSONException e) {
			try {
				JSONObject jsonObject = new JSONObject(convert_ThisJSON);
				System.out.println("JSONObject : " + jsonObject.length());
				if (jsonObject.length() == 0) {
				} else if (jsonObject.length() == 1) {
					try {
						List<Object> objectList = new ArrayList<Object>();
						String key = class1.getSimpleName() + "List";
						JSONArray objects = jsonObject.getJSONArray(key);
						for (int i = 0; i < objects.length(); i++) {
							objectList.add(convertClassToRespectedType(class1,
									convertTo_ThisPojo,
									objects.getJSONObject(i)));
						}
						return objectList;
					} catch (JSONException e1) {
						convertTo_ThisPojo = convertClassToRespectedType(
								class1, convertTo_ThisPojo, jsonObject);
					}
				} else {
					convertTo_ThisPojo = convertClassToRespectedType(class1,
							convertTo_ThisPojo, jsonObject);
				}
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
		}
		return convertTo_ThisPojo;
	}

	/**
	 * Actual Conversion class used to convert given Java POJO to Given JSON
	 * 
	 * @param class1
	 * @param objectToCast
	 * @param jsonObject
	 * @return objectToCast
	 */
	public Object convertClassToRespectedType(Class class1,
			Object objectToCast, JSONObject jsonObject) {
		Field[] fields = class1.getDeclaredFields();
		if (jsonObject.length() == 0) {
			System.out.println("length empty");
		} else if (jsonObject.length() == fields.length) {
			for (int i = 0; i < fields.length; i++) {
				fields[i].setAccessible(true);
				try {
					String type=fields[i].getType().getSimpleName();
					if("String".equals(type)){
						String name = jsonObject.getString(fields[i].getName());
						fields[i].set(objectToCast, name);
					}else if("Integer".equals(type)){
						Integer s1 = new Integer(jsonObject.getInt(fields[i]
								.getName()));
						fields[i].set(objectToCast, s1);
					}else if("int".equals(type)){
						Integer s2 = new Integer(jsonObject.getInt(fields[i]
								.getName()));
						fields[i].setInt(objectToCast, s2);
					}else if("Calendar".equals(type)){
						Calendar cal = (Calendar) jsonObject.get(fields[i]
								.getName());
						fields[i].set(objectToCast, cal);	
					}else{
						Object current = convertClassToRespectedType(
								fields[i].getType(), fields[i].getType()
										.newInstance(),
								jsonObject.getJSONObject(fields[i].getName()));
						fields[i].set(objectToCast, current);	
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			objectToCast = class1.cast(objectToCast);
		} else {
			throw new Error(
					"Type Cast Exception... Unable To Cast... Wrong JSON String... ");
		}
		return objectToCast;
	}
}
