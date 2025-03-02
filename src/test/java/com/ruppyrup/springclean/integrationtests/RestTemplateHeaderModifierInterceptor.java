package com.ruppyrup.springclean.integrationtests;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

public class RestTemplateHeaderModifierInterceptor implements HandlerInterceptor, ClientHttpRequestInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    response.setHeader(HttpHeaders.AUTHORIZATION, "Bearer test-token");
    return true;
  }

  @Override
  public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
      return execution.execute(request, body);

    }
}
