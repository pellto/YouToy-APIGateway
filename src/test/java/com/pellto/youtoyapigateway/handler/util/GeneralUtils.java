package com.pellto.youtoyapigateway.handler.util;

import org.springframework.http.HttpMethod;

public class GeneralUtil {

  private static final HttpMethod GET_METHOD = HttpMethod.GET;
  private static final HttpMethod POST_METHOD = HttpMethod.POST;
  private static final HttpMethod PATCH_METHOD = HttpMethod.PATCH;
  private static final HttpMethod DELETE_METHOD = HttpMethod.DELETE;

  public static HttpMethod getGetMethod() {
    return GET_METHOD;
  }

  public static HttpMethod getPostMethod() {
    return POST_METHOD;
  }

  public static HttpMethod getPatchMethod() {
    return PATCH_METHOD;
  }

  public static HttpMethod getDeleteMethod() {
    return DELETE_METHOD;
  }
}
