package org.nico.mocker.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 *
 * @author xx
 * @date 2020-02-18 12:29
 */
public class MapUtils {

	private final static String SPLIT = ":";
	
	/**
	 * list转map
	 * 
	 * @param f
	 * @param list
	 * @return
	 */
	public static <K, V> Map<K, V> toMap(Function<V, K> f, Collection<V> list){
		Map<K, V> map = new HashMap<K, V>();
		if(list == null || list.size() == 0) {
			return map;
		}
		list.stream().forEach(o -> {
			map.put(f.apply(o), o);
		});
		return map;
	}
	
	public static <V> Map<String, V> toMap(Function<V, String> f1, Function<V, String> f2, Collection<V> list){
		Map<String, V> map = new HashMap<String, V>();
		if(list == null || list.size() == 0) {
			return map;
		}
		list.stream().forEach(o -> {
			map.put(assmblyKey(f1.apply(o), f2.apply(o)), o);
		});
		return map;
	}
	
	public static String assmblyKey(Object... args) {
		if(args == null || args.length == 0) {
			return "";
		}
		StringBuilder builder = new StringBuilder();
		for(Object o: args) {
			builder.append(o + SPLIT);
		}
		if(builder.length() > 0) {
			builder.deleteCharAt(builder.length() - 1);
		}
		return builder.toString();
	}
	

	public static boolean isEmpty(Map<?, ?> map) {
		if(map == null || map.isEmpty()) {
			return true;
		}
		for(Object o: map.values()) {
			if(o != null && ! StringUtils.isBlank(o.toString())) {
				return false;
			}
		}
		return true;
    }
}
