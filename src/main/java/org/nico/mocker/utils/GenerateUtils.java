package org.nico.mocker.utils;

import java.util.Random;
import java.util.UUID;

public class GenerateUtils {

	public static String createCode(int len){
		String sources = "0123456789ABCDEFGHIGKLMNOPQRSTUVWXYZabcdefghigklmnopqrstuvwxyz"; 
		Random rand = new Random();
		StringBuffer flag = new StringBuffer();
		for (int j = 0; j < len; j++) 
		{
			flag.append(sources.charAt(rand.nextInt(sources.length() - 1)) + "");
		}
		return flag.toString();
	}
	
	public static String createToken() {
	    return UUID.randomUUID().toString().replaceAll("-", "").toLowerCase();
	}
	
	public static String createOssKey(Long id, String suffix) {
	    return System.currentTimeMillis() + "$" + id + "$" + createToken() + suffix; 
	}
	
	public static String createFileId() {
        return UUID.randomUUID().toString().replaceAll("-", "").toLowerCase().substring(0, 10) + System.currentTimeMillis();
    }
	
}
