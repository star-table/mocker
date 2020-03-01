package org.nico.mocker.utils;

import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang3.StringUtils;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtils {

    private static final OkHttpClient httpClient = new OkHttpClient.Builder()
            .readTimeout(3, TimeUnit.SECONDS)          // 设置读取超时时间
            .writeTimeout(3,TimeUnit.SECONDS)          // 设置写的超时时间
            .connectTimeout(1,TimeUnit.SECONDS)        // 设置连接超时时间
            .hostnameVerifier(getHostnameVerifier())          //  跳过中证书验证
            .sslSocketFactory(getSSLSocketFactory(),getTrustManager())
            .build();

    public static final MediaType JSON_MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");

    private static final String GET = "GET";
    private static final String POST = "POST";
    private static final String PUT = "PUT";
    private static final String DELETE = "DELETE";

    public static String sendGet(String url) {
        return sendGet(url, null, null);
    }

    public static String sendGet(String url, Map<String, String> params) {
        return sendGet(url, params, null);
    }

    public static String sendGet(String url, Map<String, String> params, Map<String, String> headers) {
        return http(GET, url, params, null,null, headers);
    }

    public static String sendPost(String url, Map<String, String> datas){
        return http(POST, url, null, datas, null, null);
    }

    public static String sendPost(String url, Map<String, String> params, Map<String, String> datas){
        return http(POST, url, params, datas, null, null);
    }

    public static String sendPost(String url, Map<String, String> params, String jsonBody){
        return http(POST, url, params, null, jsonBody, null);
    }

    public static String sendPost(String url, Map<String, String> params, Map<String, String> datas, String jsonBody, Map<String, String> headers){
        return http(POST, url, params, datas, jsonBody, headers);
    }

    public static String sendPut(String url, Map<String, String> datas){
        return http(PUT, url, null, datas, null,  null);
    }

    public static String sendPut(String url, Map<String, String> params, Map<String, String> datas){
        return http(PUT, url, params, datas, null, null);
    }

    public static String sendPut(String url, Map<String, String> params, String jsonBody){
        return http(PUT, url, params, null, jsonBody, null);
    }

    public static String sendPut(String url, Map<String, String> params, Map<String, String> datas, String jsonBody, Map<String, String> headers){
        return http(PUT, url, params, datas, jsonBody, headers);
    }

    public static String sendDelete(String url, Map<String, String> datas){
        return http(DELETE, url, null, datas, null, null);
    }

    public static String sendDelete(String url, Map<String, String> params, Map<String, String> datas){
        return http(DELETE, url, params, datas, null, null);
    }

    public static String sendDelete(String url, Map<String, String> params, String jsonBody){
        return http(DELETE, url, params, null, jsonBody, null);
    }

    public static String sendDelete(String url, Map<String, String> params, Map<String, String> datas, String jsonBody, Map<String, String> headers){
        return http(DELETE, url, params, datas, jsonBody, headers);
    }

    private static String http(String method, String url, Map<String, String> params, Map<String, String> datas, String jsonBody, Map<String, String> headers) {
        Request.Builder requestBuilder = null;
        
        if(null != params && params.size() > 0){
            HttpUrl.Builder httpUrlBuilder = HttpUrl.parse(url).newBuilder();
            for(String str : params.keySet()){
                httpUrlBuilder.addEncodedQueryParameter(str, params.get(str));
            }
            requestBuilder = new Request.Builder().url(httpUrlBuilder.build());
        }else{
            requestBuilder = new Request.Builder().url(url);
        }

        FormBody.Builder formBody = new FormBody.Builder();
        if(null != datas && datas.size() > 0){
            for(String key : datas.keySet()){
                formBody.add(key, datas.get(key));
            }
        }
        RequestBody body = formBody.build();
        if(!StringUtils.isEmpty(jsonBody)){
            body = RequestBody.create(JSON_MEDIA_TYPE, jsonBody);
        }

        if(POST.equals(method)){
            requestBuilder.post(body);
        }else if(PUT.equals(method)){
            requestBuilder.put(body);
        }else if(DELETE.equals(method)){
            requestBuilder.delete(body);
        }

        if(null != headers && headers.size() > 0){
            for(String key : headers.keySet()){
                requestBuilder.addHeader(key, headers.get(key));
            }
        }
        try {
            Response response = httpClient.newCall(requestBuilder.build()).execute();
            if (response.isSuccessful()) {
                String result = response.body().string();
                return result;
            } else {
                return null;
            }
        } catch (IOException e) {
        	e.printStackTrace();
            return null;
        }
    }

    public static HostnameVerifier getHostnameVerifier() {
        HostnameVerifier hostnameVerifier = new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                return true;
            }
        };
        return hostnameVerifier;
    }


    public static SSLSocketFactory getSSLSocketFactory() {
        try {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, new TrustManager[]{getTrustManager()}, new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static X509TrustManager getTrustManager() {
        X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException { }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
        return trustManager;
    }
}
