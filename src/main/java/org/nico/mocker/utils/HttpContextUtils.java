package org.nico.mocker.utils;

import java.util.Random;

public class HttpContextUtils {

    private final static ThreadLocal<HttpContext> TL = new ThreadLocal<HttpContext>();
    
    private static Random random = new Random();
    
    public static int getListSize() {
    	return getContext().getListSize();
	}

	public static void setListSize(Integer listSize) {
		getContext().setListSize(listSize);
	}
	
	public static Integer getMapSize() {
		return getContext().getMapSize();
	}

	public static void setMapSize(Integer mapSize) {
		getContext().setMapSize(mapSize);
	}
	
	public static String getDateFormat() {
		return getContext().getDateFormat();
	}

	public static void setDateFormat(String dateFormat) {
		getContext().setDateFormat(dateFormat);
	}
	
	public synchronized static HttpContext getContext() {
		HttpContext context = TL.get();
		if(context == null) {
			context = new HttpContext();
			TL.set(context);
		}
		return context;
	}
    
    public static void clear() {
    	TL.remove();
    }
    
    public static class HttpContext{
    	
    	private Integer listSize;
    	
    	private Integer mapSize;
    	
    	private String dateFormat;

		public Integer getListSize() {
			if(listSize == null) {
				listSize = random.nextInt(20);
			}
			return listSize;
		}

		public void setListSize(Integer listSize) {
			this.listSize = listSize;
		}

		public Integer getMapSize() {
			if(mapSize == null) {
				mapSize = random.nextInt(20);
			}
			return mapSize;
		}

		public void setMapSize(Integer mapSize) {
			this.mapSize = mapSize;
		}

		public String getDateFormat() {
			if(dateFormat == null) {
				dateFormat = "yyyy-MM-dd HH:mm:ss";
			}
			return dateFormat;
		}

		public void setDateFormat(String dateFormat) {
			this.dateFormat = dateFormat;
		}

    }
    
}
