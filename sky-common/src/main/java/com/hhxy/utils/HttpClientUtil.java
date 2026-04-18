package com.hhxy.utils;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.util.Map;

public class HttpClientUtil {
    public static String doGet(String url, Map<String, String> paramMap) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String resultString = "";
        try {
            StringBuilder builder = new StringBuilder(url);
            if (paramMap != null) {
                builder.append("?");
                for (String key : paramMap.keySet()) {
                    builder.append(key).append("=").append(paramMap.get(key)).append("&");
                }
            }
            HttpGet httpGet = new HttpGet(builder.toString());
            CloseableHttpResponse response = httpClient.execute(httpGet);
            resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (Exception e) { e.printStackTrace(); }
        return resultString;
    }
}
