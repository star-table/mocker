package org.nico.mocker.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.cglib.beans.BeanCopier;
import org.springframework.util.CollectionUtils;

public class ModelUtils {

    static final Map<String, BeanCopier> BEAN_COPIERS = new HashMap<String, BeanCopier>();
    
    public static <T> List<T> convert(List<?> source, Class<T> c){
        if(CollectionUtils.isEmpty(source)) {
            return null;
        }
        List<T> target = new ArrayList<T>();
        copy(source, target);
        return target;
    }
    
    public static <T> T convert(Object source, Class<T> c){
        if(null == source) {
            return null;
        }
        T target = null;
        try {
            target = c.newInstance();
        } catch (Exception e) {
            return null;
        }
        copy(source, target);
        return target;
    }
    
    private static void copy(Object srcObj, Object destObj) {
        String key = genKey(srcObj.getClass(), destObj.getClass());
        BeanCopier copier = null;
        if (!BEAN_COPIERS.containsKey(key)) {
            copier = BeanCopier.create(srcObj.getClass(), destObj.getClass(), false);
            BEAN_COPIERS.put(key, copier);
        } else {
            copier = BEAN_COPIERS.get(key);
        }
        copier.copy(srcObj, destObj, null);
    }
    
    private static String genKey(Class<?> srcClazz, Class<?> destClazz) {
        return srcClazz.getName() + destClazz.getName();
    }
}
