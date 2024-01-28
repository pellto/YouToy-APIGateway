package com.pellto.youtoyapigateway.handler;

import java.util.LinkedHashMap;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.RequestPath;

public interface AuthHandler {

  boolean checkValidAccess(
      RequestPath path,
      HttpMethod method,
      LinkedHashMap<String, Object> body,
      String subject
  );
}
