package com.pellto.youtoyapigateway.handler.util;

import java.util.LinkedHashMap;
import org.springframework.http.server.RequestPath;

public class ChannelManagementUtils {

  private static final String CHANNEL_MANAGEMENT_LEVEL = "VIEWER";

  private static final String CHANNEL_MANAGEMENT_INVITE_PATH = "/channelManagements/invite";
  private static final String CHANNEL_MANAGEMENT_CHANGE_LEVEL_PATH = "/channelManagements";
  private static final String CHANNEL_MANAGEMENT_GET_MANAGEMENT_PATH = "/channelManagements/member";

  public static RequestPath getChangeLevelPath() {
    return RequestPath.parse(CHANNEL_MANAGEMENT_CHANGE_LEVEL_PATH, null);
  }

  public static RequestPath getGetChannelManagementPath(String memberId) {
    return RequestPath.parse(
        String.format("%s/%s", CHANNEL_MANAGEMENT_GET_MANAGEMENT_PATH, memberId), null);
  }

  public static RequestPath getGetChannelManagementPath() {
    return getGetChannelManagementPath(MemberUtils.getMemberId());
  }

  public static RequestPath getInvitePath() {
    return RequestPath.parse(String.format("%s", CHANNEL_MANAGEMENT_INVITE_PATH), null);
  }

  public static LinkedHashMap<String, Object> createInviteBody(String channelId) {
    var body = new LinkedHashMap<String, Object>();
    body.put("channelId", channelId);
    body.put("memberId", MemberUtils.getMemberId());
    return body;
  }

  public static LinkedHashMap<String, Object> createInviteBody() {
    return createInviteBody(ChannelUtils.getChannelId());
  }

  public static LinkedHashMap<String, Object> createChangeLevelBody() {
    return createChangeLevelBody(ChannelUtils.getChannelId(), CHANNEL_MANAGEMENT_LEVEL);
  }

  private static LinkedHashMap<String, Object> createChangeLevelBody(String channelId,
      String level) {
    var body = new LinkedHashMap<String, Object>();
    body.put("channelId", channelId);
    body.put("memberId", MemberUtils.getMemberId());
    body.put("level", level);
    return body;
  }

  public static LinkedHashMap<String, Object> createChangeLevelBodyWithLevel(String level) {
    return createChangeLevelBody(ChannelUtils.getChannelId(), level);
  }
}
