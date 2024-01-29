package com.pellto.youtoyapigateway.handler;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.RequestPath;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChannelAuthHandler implements AuthHandler {

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
      return handlePostMethod();
    }
    return false;
  }

  private boolean handlePostMethod() {
    System.out.println("POST METHOD");
    return true;
  }

  private boolean handleDeleteMethod() {
    System.out.println("DELETE METHOD");
    return true;
  }

  private boolean handlePutMethod() {
    System.out.println("PUT METHOD");
    return true;
  }

  private boolean handlePatchMethod(String subject, LinkedHashMap<String, Object> body) {
    return false;
  }

  // "/channels/{channelId}" -> getChannel
  private boolean handleGetMethod(String subject, String path) {
    var listChannelRoles = getChannelRoles(subject);
    var hashMapChannelRoles = convertToHashmap(listChannelRoles);
    var pathChannelId = getChannelIdFromPath(path);
    return hashMapChannelRoles.containsKey(pathChannelId);
  }

  private String getChannelIdFromPath(String path) {
    return path.split("/")[2];
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
}
