package com.lc.manager.shiro;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.util.Map;

public class FilterUtil {
    private static final Logger log= LoggerFactory.getLogger(FilterUtil.class);
    /**
     * 是否是Ajax请求
     * @param servletRequest
     * @return
     */
    public static boolean isAjax(ServletRequest servletRequest) {
        String header = ((HttpServletRequest) servletRequest).getHeader("X-Requested-With");
        if("XMLHttpRequest".equalsIgnoreCase(header)){
            return true;
        }
        return false;
    }

    /**
     *  使用response输出JSON
     * @param servletResponse
     * @param resultMap
     */
    public static void out(ServletResponse servletResponse, Map<String, Object> resultMap) {
        PrintWriter out = null;
        try {
            servletResponse.setCharacterEncoding("UTF-8");
            servletResponse.setContentType("application/json");
            out = servletResponse.getWriter();
            out.println(new Gson().toJson(resultMap));
        } catch (Exception e) {
            log.error(e + "输出JSON出错");
        }finally{
            if(out!=null){
                out.flush();
                out.close();
            }
        }
    }
}
