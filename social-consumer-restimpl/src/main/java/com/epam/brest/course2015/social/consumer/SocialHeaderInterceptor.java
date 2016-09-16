package com.epam.brest.course2015.social.consumer;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.HttpRequestWrapper;

import java.io.IOException;

/**
 * Created by alexander_borohov on 2.8.16.
 */
public class SocialHeaderInterceptor implements ClientHttpRequestInterceptor {
    private static String role;
    private static String securityToken;

    public static void setSecurityToken(String securityToken) {
        SocialHeaderInterceptor.securityToken = securityToken;
    }

    public static void setRole(String role) {
        SocialHeaderInterceptor.role = role;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest req,
                                        byte[] body,
                                        ClientHttpRequestExecution execution)
    throws IOException {
        HttpRequestWrapper wrapper = new HttpRequestWrapper(req);
        wrapper.getHeaders().set("Role", role);
        wrapper.getHeaders().set("Security", securityToken);
        return execution.execute(req, body);
    }
}
