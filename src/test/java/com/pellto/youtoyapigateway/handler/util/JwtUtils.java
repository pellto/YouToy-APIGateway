package com.pellto.youtoyapigateway.handler.util;

public class JwtUtil {

  private static final String JWT_SUBJECT = String.format("^%s[%s]#%s#%s",
      MemberUtil.getMemberEmail(),
      MemberUtil.getMemberId(),
      ChannelUtils.getMemberChannelRoles(),
      MemberUtil.getMemberRoles());

  public static String getJwtSubject() {
    return JWT_SUBJECT;
  }
}
