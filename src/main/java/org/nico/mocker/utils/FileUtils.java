package org.nico.mocker.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;

public class FileUtils {

    public static final String separator = "/";
    
    public static String read(String filePath) throws IOException {
        FileInputStream inputStream = null;
        BufferedReader reader = null;
        try {
            File file = new File(filePath);
            reader = new BufferedReader(new FileReader(file));
            StringBuilder builder = new StringBuilder();
            String line = null;
            while((line = reader.readLine()) != null) {
                builder.append(line + System.lineSeparator());
            }
            return builder.toString();
        }finally {
            if(inputStream != null) inputStream.close();
            if(reader != null) reader.close();
        }
    }
    
    
    public static String combination(String pre, String after) {
        if(after.startsWith(separator)) {
            after = after.substring(separator.length());
        }
        if(! pre.endsWith(separator)) {
            pre += separator;
        }
        return pre + after;
    }
    
}
