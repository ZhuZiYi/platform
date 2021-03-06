package com.lbw.platform.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieSpecProvider;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.impl.cookie.BestMatchSpecFactory;
import org.apache.http.impl.cookie.BrowserCompatSpecFactory;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class APIMthod {
    // 创建CookieStore实例
    private static CookieStore cookieStore = null;
    private static HttpClientContext context = null;
    private static String response = "";

    public static String sendGetByHttpCient(String url){

        HttpResponse httpResponse = null;
        CloseableHttpClient client = HttpClients.custom().setDefaultCookieStore(cookieStore).build();

        try {
            HttpGet httpGet = new HttpGet(url);
            httpResponse = client.execute(httpGet);
            response = printResponse(httpResponse);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                // 关闭流并释放资源
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return response;
    }

    public static String sendPostByHttpCient(String url, Map<String, String> parameterMap){
        HttpResponse httpResponse = null;
        UrlEncodedFormEntity postEntity = null;
        CloseableHttpClient client = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
        try {
            HttpPost httpPost = new HttpPost(url);
            
            postEntity = new UrlEncodedFormEntity(getParam(parameterMap), "UTF-8");
            //httpPost.setEntity(postEntity);
            httpPost.addHeader("Cookie","MEIQIA_VISIT_ID=1EI5cpNHTlugNgRi4KNxHG5Hcba; MEIQIA_EXTRA_TRACK_ID=1EI5cqPILY05rJx6I917uqabPCd; sc_jwb=EjRWeJCrze8SNFZ4kKvN7yMyCaEJs3F1SYW1uJxEwVo%3D");
            httpPost.addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36");

            httpResponse = client.execute(httpPost);
            response = printResponse(httpResponse);
            if (cookieStore == null || "".equals(cookieStore)) {
                String host = httpPost.getURI().getHost();
                setCookieStore(httpResponse, host);
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                // 关闭流并释放资源
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return response;
    }

    public static String printResponse(HttpResponse httpResponse) throws ParseException, IOException {
        String responseString = "";
        // 获取响应消息实体
        HttpEntity entity = httpResponse.getEntity();
        // 判断响应实体是否为空
        if (entity != null) {
            responseString = EntityUtils.toString(entity);
        }
        return responseString;
    }

    public static void setContext() {
        context = HttpClientContext.create();
        Registry<CookieSpecProvider> registry = RegistryBuilder
                .<CookieSpecProvider> create()
                .register(CookieSpecs.BEST_MATCH, new BestMatchSpecFactory())
                .register(CookieSpecs.BROWSER_COMPATIBILITY,
                        new BrowserCompatSpecFactory()).build();
        context.setCookieSpecRegistry(registry);
        context.setCookieStore(cookieStore);
    }

    public static void setCookieStore(HttpResponse httpResponse,String host) {
        cookieStore = new BasicCookieStore();
        // JSESSIONID
        if (null == httpResponse.getFirstHeader("Set-Cookie")) {
            cookieStore = null;
        }else {
            String setCookie = httpResponse.getFirstHeader("Set-Cookie").getValue();
            String JSESSIONID = setCookie.substring("JSESSIONID=".length(),setCookie.indexOf(";"));
            // 新建一个Cookie
            BasicClientCookie cookie = new BasicClientCookie("JSESSIONID",JSESSIONID);
            cookie.setVersion(0);
            cookie.setDomain(host);
            cookie.setPath("/");
            cookieStore.addCookie(cookie);
        }
    }

    public static List<Cookie> getCookies(){
        if (null == cookieStore) {
            return null;
        }
        return cookieStore.getCookies();
    }

    public static void deleteCookies(){
        if (cookieStore != null) {
            cookieStore.clear();
            cookieStore = null;
        }
    }

    public static List<NameValuePair> getParam(Map<String, String> parameterMap) {
        List<NameValuePair> param = new ArrayList<NameValuePair>();
        Iterator it = parameterMap.entrySet().iterator();
        while (it.hasNext()) {
            Entry parmEntry = (Entry) it.next();
            param.add(new BasicNameValuePair((String) parmEntry.getKey(),(String) parmEntry.getValue()));
        }
        return param;
    }
    
    public static void main(String[] args) throws Exception {
    	Map<String, String> parameterMap = new HashMap();
    	//parameterMap.put("mobile", "13755181631");
    	//parameterMap.put("auth", "0962");
    	
    	//String rsp = sendPostByHttpCient("https://jwb.sc-edu.com/api/user/login/",parameterMap);
    	//System.out.println(rsp);
    	
    	
    	
    	parameterMap.clear();
    	
    	
    	String rsp=sendPostByHttpCient("https://jwb.sc-edu.com/api/branch/list/",parameterMap);
    	
    	System.out.println(rsp);
    }
}