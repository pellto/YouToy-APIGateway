package com.pellto.youtoyapigateway.filter;

import com.pellto.youtoyapigateway.handler.MemberAuthHandler;
import com.pellto.youtoyapigateway.jwt.JwtProvider;
import java.util.LinkedHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class MemberAuthorizationFilter extends
    AbstractGatewayFilterFactory<AbstractGatewayFilterFactory.NameConfig> {

  private static final String FILTER_NAME = "MemberAuthorizationFilter";
  private final JwtProvider jwtProvider;
  private final MemberAuthHandler memberAuthHandler;

  public MemberAuthorizationFilter(JwtProvider jwtProvider, MemberAuthHandler memberAuthHandler) {
    super(NameConfig.class);
    this.jwtProvider = jwtProvider;
    this.memberAuthHandler = memberAuthHandler;
  }

  @Override
  public GatewayFilter apply(NameConfig config) {
    return (exchange, chain) -> {
      ServerHttpRequest request = exchange.getRequest();
      LinkedHashMap<String, Object> body = exchange.getAttribute(
          ServerWebExchangeUtils.CACHED_REQUEST_BODY_ATTR);
      String authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
      String jwt = authorizationHeader.replace("Bearer", "").replace(" ", "");

      var path = request.getPath();
      var method = request.getMethod();
      var subject = jwtProvider.getSubject(jwt);

      if (path.toString().contains("members") && !memberAuthHandler.checkValidAccess(path, method,
          body, subject)) {
        return onError(exchange, "Invalid Member Access.", HttpStatus.FORBIDDEN);
      }

      return chain.filter(exchange);
    };
  }

  private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
    log.error(String.format("[%s]: %s", FILTER_NAME, err));
    var response = exchange.getResponse();
    response.setStatusCode(httpStatus);
    return response.setComplete();
  }
}
