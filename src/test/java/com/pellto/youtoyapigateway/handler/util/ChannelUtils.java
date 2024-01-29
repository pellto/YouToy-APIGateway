package com.pellto.youtoyapigateway.handler.util;

import org.springframework.http.server.RequestPath;

public class ChannelUtils {

  private static final String CHANNEL_ID = "1";

  private static final String CHANNEL_OWNER_ROLES = "[1$OWNER]";
  private static final String CHANNEL_CO_WORKER_ROLES = "[1$CO_WORKER]";
  private static final String CHANNEL_VIEWER_ROLES = "[1$VIEWER]";
  private static final String GET_CHANNEL_PATH = "/channels";

  public static String getChannelId() {
    return CHANNEL_ID;
  }

  public static String getMemberChannelCoWorkerRoles() {
    return CHANNEL_CO_WORKER_ROLES;
  }

  public static String getMemberChannelOwnerRoles() {
    return CHANNEL_OWNER_ROLES;
  }

  public static String getMemberChannelViewerRoles() {
    return CHANNEL_VIEWER_ROLES;
  }

  public static RequestPath getGetChannelPath() {
    return getGetChannelPath(CHANNEL_ID);
  }

  public static RequestPath getGetChannelPath(String channelId) {
    return RequestPath.parse(String.format("%s/%s", GET_CHANNEL_PATH, channelId), null);
  }
}
