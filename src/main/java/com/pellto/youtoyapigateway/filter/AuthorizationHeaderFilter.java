package com.pellto.youtoyapigateway.filter;

import com.pellto.youtoyapigateway.handler.ChannelAuthHandler;
import com.pellto.youtoyapigateway.handler.MemberAuthHandler;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
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

  private final MemberAuthHandler memberAuthHandler = new MemberAuthHandler();
  private final ChannelAuthHandler channelAuthHandler = new ChannelAuthHandler();

  // TODO: setting env or another config
  private static final String SECRET_KEY = "SZPqwwAV8Wzf8Dc5gqduTbdu8Kdou26P"
      + "TXVwKJD1enFSXlwvdzIr5N9QDrx2iAAl"
      + "85KpDab3pjp8FjL9wDFv8xdtRDM2axUZ"
      + "gSApoWOPBXU2gNCEzp8uauRIfr4JFjnc"
      + "NUXUMU9iB7OHQQTFgDKH74tmGckYVA34"
      + "cBkPh6euPhdBWhzCIbCjK7czdmf99kte"
      + "wqB6KEYQnwWQPLjY0xwni9VH7N1bfhqW"
      + "us0MvkxdSmbuH613FpfazywvNdDbUEmI";

  public AuthorizationHeaderFilter() {
    super(NameConfig.class);
  }

  private final SecretKey key = new SecretKeySpec(
      SECRET_KEY.getBytes(StandardCharsets.UTF_8),
      SIG.HS256.key().build().getAlgorithm()
  );

  @Override
  public GatewayFilter apply(NameConfig config) {
    return (exchange, chain) -> {
      ServerHttpRequest request = exchange.getRequest();

      if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
        return onError(exchange, "no authorization header", HttpStatus.UNAUTHORIZED);
      }

      String authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
      String jwt = authorizationHeader.replace("Bearer", "").replace(" ", "");
      if (!isJwtValid(jwt)) {
        return onError(exchange, "JWT token is not valid", HttpStatus.UNAUTHORIZED);
      }

      LinkedHashMap<String, Object> body = exchange.getAttribute(
          ServerWebExchangeUtils.CACHED_REQUEST_BODY_ATTR);
      var payload = getPayload(jwt);
      var subject = payload.getSubject();
      if (!isSubjectValid(subject)) {
        return onError(exchange, "JWT Subject is not valid", HttpStatus.UNAUTHORIZED);
      }

      var path = request.getPath();
      var uri = request.getURI();
      var method = request.getMethod();

      if (path.toString().contains("members") && !memberAuthHandler.checkValidAccess(path, method,
          body, subject)) {
        return onError(exchange, "Invalid Member Access.", HttpStatus.FORBIDDEN);
      }

      if (path.toString().contains("channels") && !channelAuthHandler.checkValidAccess(path, method,
          body, subject)) {
        return onError(exchange, "Invalid Channel Access.", HttpStatus.FORBIDDEN);
      }

      return chain.filter(exchange);
    };
  }

  private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
    log.error(String.format("err = %s", err));
    var response = exchange.getResponse();
    response.setStatusCode(httpStatus);
    return response.setComplete();
  }

  private boolean isJwtValid(String jwt) {
    try {
      Jwts.parser().verifyWith(key).build().parseSignedClaims(jwt);
      return true;
    } catch (Exception e) {
      log.error("e = " + e);
      return false;
    }
  }

  private boolean isSubjectValid(String subject) {
    return subject.startsWith("^")
        && subject.endsWith("^")
        && subject.split("#").length == 3;
  }

  private Claims getPayload(String jwt) {
    return Jwts.parser().verifyWith(key).build().parseSignedClaims(jwt).getPayload();
  }

  public static class Config {

  }
}
