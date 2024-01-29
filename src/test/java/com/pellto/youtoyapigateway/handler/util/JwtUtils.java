package com.pellto.youtoyapigateway.handler.util;

public class JwtUtils {

  private static String createJwtSubject(String memberEmail, String memberId, String channelRoles,
      String memberRoles) {
    return String.format("^%s[%s]#%s#%s",
        memberEmail, memberId,
        channelRoles, memberRoles
    );
  }

  private static String createJwtSubject() {
    return createJwtSubject(
        MemberUtils.getMemberEmail(),
        MemberUtils.getMemberId(),
        ChannelUtils.getMemberChannelOwnerRoles(),
        MemberUtils.getMemberRoles()
    );
  }

  private static String createJwtSubjectWithChannelRoles(String channelRoles) {
    return createJwtSubject(
        MemberUtils.getMemberEmail(),
        MemberUtils.getMemberId(),
        channelRoles,
        MemberUtils.getMemberRoles()
    );
  }

  public static String getCoWorkerRoleSubject() {
    return createJwtSubjectWithChannelRoles(ChannelUtils.getMemberChannelCoWorkerRoles());
  }

  public static String getJwtSubject() {
    return createJwtSubject();
  }

  public static String getViewerRoleSubject() {
    return createJwtSubjectWithChannelRoles(ChannelUtils.getMemberChannelViewerRoles());
  }
}
