package com.pellto.youtoyapigateway.handler.util;

import java.util.LinkedHashMap;
import org.springframework.http.server.RequestPath;

public class MemberUtil {

  private static final String MEMBER_EMAIL = "test@test.com";
  private static final String MEMBER_ID = "1";
  private static final String MEMBER_NAME = "testName";
  private static final String MEMBER_ROLES = String.format("[%s]", MEMBER_NAME);

  private static final String MEMBER_DETAIL_GET_PATH = "/members/details";
  private static final String MEMBER_INFO_GET_PATH = "/members/info";
  private static final String MEMBER_CHANGE_NAME_PATH = "/members/changeName";
  private static final String MEMBER_PWD = "pwd";
  private static final String MEMBER_CHANGE_PWD_PATH = "/members/changePwd";

  public static RequestPath getChangeNamePath() {
    return RequestPath.parse(MEMBER_CHANGE_NAME_PATH, null);
  }

  public static RequestPath getChangePwdPath() {
    return RequestPath.parse(MEMBER_CHANGE_PWD_PATH, null);
  }

  public static RequestPath getDetailPath(String id) {
    return RequestPath.parse(String.format("/%s/%s", MEMBER_DETAIL_GET_PATH, id), null);
  }

  public static RequestPath getDetailPath() {
    var path = getDetailPath(MEMBER_ID).toString();
    return RequestPath.parse(path, null);
  }

  public static RequestPath getInfoPath(String email) {
    return RequestPath.parse(String.format("/%s/%s", MEMBER_INFO_GET_PATH, email), null);
  }

  public static RequestPath getInfoPath() {
    var path = getInfoPath(MEMBER_EMAIL).toString();
    return RequestPath.parse(path, null);
  }

  public static String getMemberEmail() {
    return MEMBER_EMAIL;
  }

  public static String getMemberId() {
    return MEMBER_ID;
  }

  public static LinkedHashMap<String, Object> createChangeNameBody() {
    var body = new LinkedHashMap<String, Object>();
    body.put("id", MEMBER_ID);
    body.put("name", MEMBER_NAME);
    return body;
  }

  public static LinkedHashMap<String, Object> createChangeNameBody(String id) {
    var body = new LinkedHashMap<String, Object>();
    body.put("id", id);
    body.put("name", MEMBER_NAME);
    return body;
  }

  public static LinkedHashMap<String, Object> createChangePwdBody() {
    var body = new LinkedHashMap<String, Object>();
    body.put("id", MEMBER_ID);
    body.put("pwd", MEMBER_PWD);
    body.put("repeatPwd", MEMBER_PWD);
    return body;
  }

  public static LinkedHashMap<String, Object> createChangePwdBody(String id) {
    var body = new LinkedHashMap<String, Object>();
    body.put("id", id);
    body.put("pwd", MEMBER_PWD);
    body.put("repeatPwd", MEMBER_PWD);
    return body;
  }

  public static String getMemberRoles() {
    return MEMBER_ROLES;
  }
}
