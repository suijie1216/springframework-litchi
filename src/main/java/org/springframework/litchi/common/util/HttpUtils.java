package org.springframework.litchi.common.util;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * @author suijie
 */
public class HttpUtils {

    private static final String UNKNOWN = "unknown";

    private static final String LOCALHOST = "127.0.0.1";

    private static final String IP_SPLIT = ".";

    /**
     * 获取机器的ip地址
     * @return str
     */
    public static String getLocalIp() {
        Enumeration netInterfaces;
        try {
            netInterfaces = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            return "0.0.0.0";
        }
        while (netInterfaces.hasMoreElements()) {
            NetworkInterface networkInterface = (NetworkInterface) netInterfaces.nextElement();
            Enumeration<InetAddress> ip = networkInterface.getInetAddresses();
            while (ip.hasMoreElements()) {
                InetAddress inetAddress = ip.nextElement();
                if (!inetAddress.isSiteLocalAddress()) {
                    continue;
                }
                String hostAddress = inetAddress.getHostAddress();
                if (!hostAddress.contains(":")) {
                    return hostAddress;
                }
            }
        }
        return "0.0.0.0";
    }

    /**
     * 获取网络请求的ip地址
     * @param request
     * @return
     */
    public static String getRequestIp(HttpServletRequest request){
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if(LOCALHOST.equals(ip)){
                //根据网卡取本机配置的IP
                InetAddress inet;
                try {
                    inet = InetAddress.getLocalHost();
                    ip= inet.getHostAddress();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            }
        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if(ip != null && ip.length() > 15){
            if(ip.contains(IP_SPLIT)){
                ip = ip.substring(0,ip.indexOf(","));
            }
        }
        return ip;
    }
}
