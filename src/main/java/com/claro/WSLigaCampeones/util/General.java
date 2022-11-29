package com.claro.WSLigaCampeones.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class General {

	public static List<String> getValuesByFields(Object object)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		List<String> listResults = new ArrayList<>();

		Class<?> objectClass = object.getClass();
		Field[] fields = objectClass.getDeclaredFields();

		for (Field f : fields) {
			try {
				String name = ("" + f.getName().charAt(0)).toUpperCase() + f.getName().substring(1);
				Method m = objectClass.getMethod("get" + name);
				if (!Objects.equals("void", m.getReturnType().getCanonicalName())) {
					listResults.add(convert(m.invoke(object)));
				}
			} catch (NoSuchMethodException e) {
			}
		}
		return listResults;
	}
	
	public static List<String> getNombresAtributos(Class<?> objectClass){
		List<String> listResults = new ArrayList<>();
		Field[] fields = objectClass.getDeclaredFields();
		for (Field f : fields) {
			String name = ("" + f.getName().charAt(0)).toUpperCase() + f.getName().substring(1);
			listResults.add(name);
		}
		return listResults;
	}
	public static List<String> getNombresAtributosUsuarios(Class<?> objectClass){
		List<String> listResults = new ArrayList<>();
		Field[] fields = objectClass.getDeclaredFields();
		for (Field f : fields) {
			String name = ("" + f.getName().charAt(0)).toUpperCase() + f.getName().substring(1);
			if(!name.equalsIgnoreCase("Clave")) {
			listResults.add(name);
			}
		}
		return listResults;
	}
	
	public static String convert(Object object) {
		if(Objects.isNull(object)){
			return null;
		}
		if(object instanceof Date) {
			return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format((Date) object);
		}else {
			return object.toString();
		}
	}
}
