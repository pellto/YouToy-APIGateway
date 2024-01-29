package com.pellto.youtoyapigateway.filter;

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
public class AuthorizationHeaderFilter extends
    AbstractGatewayFilterFactory<AbstractGatewayFilterFactory.NameConfig> {

  private static final String FILTER_NAME = "AuthorizationHeaderFilter";
  private final JwtProvider jwtProvider;

  public AuthorizationHeaderFilter(JwtProvider jwtProvider) {
    super(NameConfig.class);
    this.jwtProvider = jwtProvider;
  }

  @Override
  public GatewayFilter apply(NameConfig config) {
    return (exchange, chain) -> {
      ServerHttpRequest request = exchange.getRequest();
      if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
        return onError(exchange, "no authorization header", HttpStatus.UNAUTHORIZED);
      }

      String authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
      String jwt = authorizationHeader.replace("Bearer", "").replace(" ", "");
      if (!jwtProvider.isJwtValid(jwt)) {
        return onError(exchange, "JWT token is not valid", HttpStatus.UNAUTHORIZED);
      }

      LinkedHashMap<String, Object> body = exchange.getAttribute(
          ServerWebExchangeUtils.CACHED_REQUEST_BODY_ATTR);
      var subject = jwtProvider.getSubject(jwt);
      if (!jwtProvider.isSubjectValid(subject)) {
        return onError(exchange, "JWT Subject is not valid", HttpStatus.UNAUTHORIZED);
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

  public static class Config {

  }
}
