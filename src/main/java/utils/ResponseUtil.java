package utils;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ResponseUtil {
    public static <T> void DoResponse(HttpServletResponse resp, T data) throws IOException {
        Class<?> clazz = data.getClass();
        Object result;
        if (clazz.isPrimitive()
                || Number.class.isAssignableFrom(clazz)
                || Boolean.class == clazz
                || Character.class == clazz
                || String.class == clazz) {
            result = data;
            resp.setContentType("text/html;charset=utf-8");
        } else {
            result = JSONObject.toJSONStringWithDateFormat(data,"yyyy-MM-dd hh:mm:ss");
            resp.setContentType("application/json;charset=utf-8");
        }
        PrintWriter writer = resp.getWriter();
        writer.print(result);
        writer.flush();
        writer.close();
    }
}
