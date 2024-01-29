package com.pellto.youtoyapigateway.handler;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.RequestPath;

public class ChannelManagementAuthHandler implements AuthHandler {

  @Override
  public boolean checkValidAccess(RequestPath path, HttpMethod method,
      LinkedHashMap<String, Object> body, String subject) {

    if (HttpMethod.GET.matches(method.name())) {
      return handleGetMethod(subject, path.toString());
    }
    if (HttpMethod.PATCH.matches(method.name())) {
      return handlePatchMethod(subject, body);
    }
    if (HttpMethod.PUT.matches(method.name())) {
      return handlePutMethod();
    }
    if (HttpMethod.DELETE.matches(method.name())) {
      return handleDeleteMethod();
    }
    if (HttpMethod.POST.matches(method.name())) {
      return handlePostMethod(subject, body);
    }
    return false;
  }

  // "/channelManagements/invite" -> inviteMember
  private boolean handlePostMethod(String subject, LinkedHashMap<String, Object> body) {
    var channelRoles = getChannelRoleHashMap(subject);
    var targetChannel = body.get("channelId").toString();
    return hasOwnerRoles(channelRoles, targetChannel);
  }

  private boolean handleDeleteMethod() {
    System.out.println("DELETE METHOD");
    return true;
  }

  private boolean handlePutMethod() {
    System.out.println("PUT METHOD");
    return true;
  }

  // "/channelManagements" -> changeLevel
  private boolean handlePatchMethod(String subject, LinkedHashMap<String, Object> body) {
    var channelRoles = getChannelRoleHashMap(subject);
    var targetChannelId = body.get("channelId").toString();
    var targetLevel = body.get("level").toString();
    switch (targetLevel) {
      case ("VIEWER") -> {
        return hasCoWorkerRoles(channelRoles, targetChannelId);
      }
      case ("CO_WORKER"), ("OWNER") -> {
        return hasOwnerRoles(channelRoles, targetChannelId);
      }
      default -> {
        return false;
      }
    }
  }

  // "/channelManagements/member/{memberId}" -> getChannelManagement
  private boolean handleGetMethod(String subject, String path) {
    var memberId = getMemberId(subject);
    var splitPath = Arrays.asList(path.split("/"));
    var targetMemberId = splitPath.get(splitPath.size() - 1);
    return memberId.equals(targetMemberId);
  }

  private String getChannelIdFromPath(String path) {
    return path.split("/")[2];
  }

  private HashMap<String, String> getChannelRoleHashMap(String subject) {
    return convertToHashmap(getChannelRoles(subject));
  }

  private HashMap<String, String> convertToHashmap(List<String> listChannelRoles) {
    var channelRoles = new HashMap<String, String>();
    for (String role : listChannelRoles) {
      var _split = role.split("\\$");
      channelRoles.put(_split[0].strip(), _split[1].strip());
    }
    return channelRoles;
  }

  private List<String> getChannelRoles(String subject) {
    var stringRoles = subject.split("#")[1].replace("[", "").replace("]", "");
    return Arrays.asList(stringRoles.split(","));
  }

  private boolean hasOwnerRoles(HashMap<String, String> channelRoles,
      String targetChannelId) {
    if (!channelRoles.containsKey(targetChannelId)) {
      return false;
    }
    var role = channelRoles.get(targetChannelId);
    return role.equals("OWNER");
  }

  private boolean hasCoWorkerRoles(HashMap<String, String> channelRoles,
      String targetChannelId) {
    if (!channelRoles.containsKey(targetChannelId)) {
      return false;
    }
    var role = channelRoles.get(targetChannelId);
    return role.equals("OWNER") || role.equals("CO_WORKER");
  }

  private boolean hasViewerRoles(HashMap<String, String> channelRoles,
      String targetChannelId) {
    return channelRoles.containsKey(targetChannelId);
  }

  private String getMemberId(String subject) {
    return subject.split("#")[0].substring(1).split("\\[")[1].replace("]", "");
  }
}
