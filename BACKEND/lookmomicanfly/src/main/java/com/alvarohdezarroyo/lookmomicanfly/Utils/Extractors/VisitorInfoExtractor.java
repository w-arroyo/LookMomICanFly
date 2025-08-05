package com.alvarohdezarroyo.lookmomicanfly.Utils.Extractors;

import com.alvarohdezarroyo.lookmomicanfly.DTO.VisitorInfoDTO;
import eu.bitwalker.useragentutils.UserAgent;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class VisitorInfoExtractor {

    public VisitorInfoDTO extractVisitorInfo(HttpServletRequest request) {
        final UserAgent userAgent = getUserAgent(request);
        return new VisitorInfoDTO(
                getVisitorsIp(request),
                userAgent.getOperatingSystem().getDeviceType().getName(),
                userAgent.getBrowser().getName(),
                userAgent.getOperatingSystem().getName(),
                userAgent.getBrowser().getBrowserType().getName()
        );
    }

    private String getVisitorsIp(HttpServletRequest request) {

        //to get the IP you can just use request.getRemoteAddr(), the issue is that if the server's got a proxy, this method will return the proxy's IP instead of the client's
        String ip = request.getHeader("X-Forwarded-For"); // most proxies use this header
        if (ip == null || ip.trim().isEmpty() || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("Proxy-Client-IP"); // some old proxies use this header
        }
        if (ip == null || ip.trim().isEmpty() || ip.equalsIgnoreCase("unknown")) {
            ip = request.getRemoteAddr(); // explained above
        }
        if (ip == null || ip.trim().isEmpty()) {
            throw new RuntimeException("Couldn't retrieve visitor's IP address.");
        }
        if (ip.contains(",")) {
            ip = ip.split(",")[0].trim(); // if the header has multiple IP addresses you take the first one only
        }
        return ip;
    }

    private UserAgent getUserAgent(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        try {
            if (userAgent == null || userAgent.trim().isEmpty()) {
                throw new IllegalArgumentException("User-Agent header was not present inside the request.");
            }
            return UserAgent.parseUserAgentString(userAgent);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
