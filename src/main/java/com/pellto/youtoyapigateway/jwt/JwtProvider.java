package com.pellto.youtoyapigateway.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import java.nio.charset.StandardCharsets;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtProvider {

  // TODO: setting env or another config
  private static final String SECRET_KEY = "SZPqwwAV8Wzf8Dc5gqduTbdu8Kdou26P"
      + "TXVwKJD1enFSXlwvdzIr5N9QDrx2iAAl"
      + "85KpDab3pjp8FjL9wDFv8xdtRDM2axUZ"
      + "gSApoWOPBXU2gNCEzp8uauRIfr4JFjnc"
      + "NUXUMU9iB7OHQQTFgDKH74tmGckYVA34"
      + "cBkPh6euPhdBWhzCIbCjK7czdmf99kte"
      + "wqB6KEYQnwWQPLjY0xwni9VH7N1bfhqW"
      + "us0MvkxdSmbuH613FpfazywvNdDbUEmI";

  private final String ALGORITHM = SIG.HS256.key().build().getAlgorithm();

  private final SecretKey key = new SecretKeySpec(
      SECRET_KEY.getBytes(StandardCharsets.UTF_8),
      ALGORITHM
  );

  public boolean isJwtValid(String jwt) {
    try {
      Jwts.parser().verifyWith(key).build().parseSignedClaims(jwt);
      return true;
    } catch (Exception e) {
      log.error("e = " + e);
      return false;
    }
  }

  public boolean isSubjectValid(String subject) {
    return subject.startsWith("^")
        && subject.endsWith("^")
        && subject.split("#").length == 3;
  }

  public Claims getPayload(String jwt) {
    return Jwts.parser().verifyWith(key).build().parseSignedClaims(jwt).getPayload();
  }
  
  public String getSubject(String jwt) {
    return getPayload(jwt).getSubject();
  }
}
