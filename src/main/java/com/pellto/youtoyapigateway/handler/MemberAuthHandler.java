package com.pellto.youtoyapigateway.handler;

import java.util.Arrays;
import java.util.LinkedHashMap;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.RequestPath;
import org.springframework.stereotype.Component;

@Component
public class MemberAuthHandler {

  private static final String ROOT_PATH = "/members";

  public MemberAuthHandler() {
  }

  public boolean checkValidAccess(
      RequestPath path,
      HttpMethod method,
      LinkedHashMap<String, Object> body,
      String subject
  ) {
//    var email = getMemberEmail(subject);
//    var role = getMemberRoles(subject);
//    var memberId = getMemberId(subject);
//    System.out.println("role = " + role);
//    System.out.println("email = " + email);
    System.out.println("body = " + body);

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

  private String getMemberEmail(String subject) {
    return subject.split("#")[0].substring(1).split("\\[")[0];
  }

  private String getMemberId(String subject) {
    return subject.split("#")[0].substring(1).split("\\[")[1].replace("]", "");
  }

  private String getMemberRoles(String subject) {
    var splitSubject = subject.split("#");
    return splitSubject[splitSubject.length - 1].split("\\^")[0];
  }

  // "/members/" -> signUp
  private boolean handlePostMethod() {
    System.out.println("POST METHOD");
    return true;
  }

  // "/members/{id}" -> delete
  private boolean handleDeleteMethod() {
    System.out.println("DELETE METHOD");
    return true;
  }

  private boolean handlePutMethod() {
    System.out.println("PUT METHOD");
    return true;
  }


  // "/members/name" -> changeName
  // "/members/pwd" -> changePwd
  private boolean handlePatchMethod(String subject,
      LinkedHashMap<String, Object> body) {
    var memberId = getMemberId(subject);
    var idClass = body.get("id").getClass();
    System.out.println("idClass = " + idClass);
    if (body.containsKey("id") && body.get("id").toString().equals(memberId)) {
      return true;
    }
    return false;
  }


  // "/members/details/{id}" -> getMemberDetail
  // "/members/info/{email}" -> getMemberInfo
  private boolean handleGetMethod(String subject, String path) {
    var email = getMemberEmail(subject);
    var memberId = getMemberId(subject);
    var _path = removeRootPath(path);
    var splitPath = Arrays.asList(_path.split("/"));
    var variable = splitPath.get(splitPath.size() - 1);

    System.out.println("email = " + email);
    System.out.println("memberId = " + memberId);
    System.out.println("_path = " + _path);
    System.out.println("splitPath = " + splitPath);
    System.out.println("variable = " + variable);

    if (splitPath.contains("details") && variable.equals(memberId)) {
      return true;
    }
    if (splitPath.contains("info") && variable.equals(email)) {
      return true;
    }
    return false;
  }

  private String removeRootPath(String path) {
    return path.replace(ROOT_PATH, "");
  }
}
