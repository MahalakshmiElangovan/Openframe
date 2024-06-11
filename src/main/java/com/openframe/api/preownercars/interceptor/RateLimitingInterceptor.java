package com.openframe.api.preownercars.interceptor;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RateLimitingInterceptor implements HandlerInterceptor {
	private final Map<String, RequestInfo> requestCounts = new ConcurrentHashMap<>();
    private final Map<String, Integer> violations = new ConcurrentHashMap<>();
    private final int RATE_LIMIT = 2; // 2 requests per second
    private final int THROTTLED_RATE = 1; // 1 request per second if throttled
    private final int BLACKLIST_THRESHOLD = 5; // 5 violations lead to blacklisting

    Logger logger = LoggerFactory.getLogger(RateLimitingInterceptor.class);
    
    private static class RequestInfo {
        long timestamp;
        int count;
        boolean throttled;

        RequestInfo(long timestamp, int count, boolean throttled) {
            this.timestamp = timestamp;
            this.count = count;
            this.throttled = throttled;
        }
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ipAddress = request.getRemoteAddr();
        if (isBlacklisted(ipAddress)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            logger.info("IP Address is blocklisted");
            sendErrorResponse(response, HttpServletResponse.SC_FORBIDDEN, "Too many requests. IP blacklisted.");
            return false;
        }

        long currentTime = System.currentTimeMillis() / 1000; 
        RequestInfo requestInfo = requestCounts.getOrDefault(ipAddress, new RequestInfo(currentTime, 0, false));
        if (currentTime == requestInfo.timestamp) {
            requestInfo.count++;
            
        } else {
            requestInfo.timestamp = currentTime;
            requestInfo.count = 1;
            
        }

        if (requestInfo.throttled) {
            if (requestInfo.count > THROTTLED_RATE) {
                violations.merge(ipAddress, 1, Integer::sum);
                if (violations.get(ipAddress) >= BLACKLIST_THRESHOLD) {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    return false;
                }
                response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
                return false;
            }
        } else {
            if (requestInfo.count > RATE_LIMIT) {
                requestInfo.throttled = true;
                violations.merge(ipAddress, 1, Integer::sum);
                sendErrorResponse(response, HttpServletResponse.SC_FORBIDDEN, "IP Throtteld.");
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return false;
            }
        }
        
        requestCounts.put(ipAddress, requestInfo);
        return true;
    }

    private boolean isBlacklisted(String ipAddress) {
        return violations.getOrDefault(ipAddress, 0) >= BLACKLIST_THRESHOLD;
    }
    
    private void sendErrorResponse(HttpServletResponse response, int status, String message) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\": \"" + message + "\"}");
    }
}
