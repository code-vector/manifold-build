package com.lanyine.manifold.web.util;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;

public class WebUtil extends WebUtils {
    private static String AJAX_HEADER = "x-requested-with";
    private static String XMLHTTPREQUEST = "XMLHttpRequest";

    public static boolean isAjaxRequest() {
        return isAjaxRequest(getHttpServletRequest());
    }

    public static boolean isAjaxRequest(HttpServletRequest request) {
        return XMLHTTPREQUEST.equalsIgnoreCase(request.getHeader(AJAX_HEADER));
    }

    public static String getRemoteIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if ((ip == null || ip.trim().isEmpty()) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if ((ip == null || ip.trim().isEmpty()) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static HttpSession getHttpSession() {
        return getHttpServletRequest().getSession();
    }

    public static HttpSession getHttpSession(HttpServletRequest request) {
        return request.getSession();
    }

    public static HttpServletResponse getHttpServletResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }

    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public static String[] getParameterValues(String parameterName) {
        return getHttpServletRequest().getParameterValues(parameterName);
    }

    public static String getParameterValue(String parameterName) {
        return getHttpServletRequest().getParameter(parameterName);
    }

    public static void setAttribute(String attributeName, Object obj) {
        getHttpServletRequest().setAttribute(attributeName, obj);
    }

    public static Object getAttribute(String attributeName) {
        return getHttpServletRequest().getAttribute(attributeName);
    }

    public static void setAttribute(HttpServletRequest request, String attributeName, Object obj) {
        request.setAttribute(attributeName, obj);
    }

    public static Object getAttribute(HttpServletRequest request, String attributeName) {
        return request.getAttribute(attributeName);
    }

    public static Object getObjectInSession(String key) {
        return getHttpSession().getAttribute(key);
    }

    public static Object getObjectInSession(HttpServletRequest request, String key) {
        return getHttpSession(request).getAttribute(key);
    }

    public static void jsonWrite(Object model, HttpServletResponse response) {
        jsonWrite(model, null, response);
    }

    public static void jsonWrite(Object model, MediaType contentType, HttpServletResponse response) {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        HttpOutputMessage outputMessage = new ServletServerHttpResponse(response);
        try {
            converter.write(model, contentType, outputMessage);
        } catch (HttpMessageNotWritableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
