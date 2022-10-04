package utils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson2.JSON;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

public class RequestUtil {
    public static <T> T parseRequestBody(HttpServletRequest req, Class<T> clazz) throws IOException {
        BufferedReader reader = req.getReader();
        String data = reader.readLine();
        return JSON.parseObject(data, clazz);
    }

    public static <T> T parseRequestParams(HttpServletRequest req, Class<T> clazz) {
        Map<String, String[]> parameterMap = req.getParameterMap();
        JSONObject jsonObject = new JSONObject();
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            String key = entry.getKey();
            String[] value = entry.getValue();
            if (value != null && value.length == 1) {
                jsonObject.put(key, value[0]);
            } else if (value.length > 1) {
                jsonObject.put(key, value);
            }
        }
        return jsonObject.toJavaObject(clazz);
    }

    public static String getRequestUrl(HttpServletRequest req) {
        String requestURI = req.getRequestURI();
        String contextPath = req.getContextPath();
        if (!"".equals(contextPath)) {
            requestURI = requestURI.replace(contextPath, "");
        }
        return requestURI;
    }
}
