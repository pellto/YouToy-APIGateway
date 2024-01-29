package com.pellto.youtoyapigateway.handler;

import com.pellto.youtoyapigateway.handler.util.GeneralUtils;
import com.pellto.youtoyapigateway.handler.util.JwtUtils;
import com.pellto.youtoyapigateway.handler.util.MemberUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@Tag("handler")
class MemberAuthHandlerTest {

  private static final String HANDLER_NAME = "MemberAuthHandler";

  @InjectMocks
  private MemberAuthHandler memberAuthHandler;

  @DisplayName("[" + HANDLER_NAME + "/handleGetMethod/detail] detail path 성공 테스트")
  @Test
  void handleGetMethodDetailPathSuccessTest() {
    var method = GeneralUtils.getGetMethod();
    var subject = JwtUtils.getJwtSubject();
    var path = MemberUtils.getDetailPath();

    var result = memberAuthHandler.checkValidAccess(path, method, null, subject);

    Assertions.assertThat(result).isTrue();
  }

  @DisplayName("[" + HANDLER_NAME + "/handleGetMethod/detail] detail path 실패 테스트")
  @Test
  void handleGetMethodDetailPathFailTest() {
    var method = GeneralUtils.getGetMethod();
    var subject = JwtUtils.getJwtSubject();
    var path = MemberUtils.getDetailPath("2");

    var result = memberAuthHandler.checkValidAccess(path, method, null, subject);

    Assertions.assertThat(result).isFalse();
  }

  @DisplayName("[" + HANDLER_NAME + "/handleGetMethod/info] info path 성공 테스트")
  @Test
  void handleGetMethodInfoPathSuccessTest() {
    var method = GeneralUtils.getGetMethod();
    var subject = JwtUtils.getJwtSubject();
    var path = MemberUtils.getInfoPath();

    var result = memberAuthHandler.checkValidAccess(path, method, null, subject);

    Assertions.assertThat(result).isTrue();
  }

  @DisplayName("[" + HANDLER_NAME + "/handleGetMethod/info] info path 실패 테스트")
  @Test
  void handleGetMethodInfoPathFailTest() {
    var method = GeneralUtils.getGetMethod();
    var subject = JwtUtils.getJwtSubject();
    var path = MemberUtils.getInfoPath("wrong@email.com");

    var result = memberAuthHandler.checkValidAccess(path, method, null, subject);

    Assertions.assertThat(result).isFalse();
  }


  @DisplayName("[" + HANDLER_NAME + "/handlePatchMethod/changeName] change name 성공 테스트")
  @Test
  void handlePatchMethodChangeNameSuccessTest() {
    var method = GeneralUtils.getPatchMethod();
    var subject = JwtUtils.getJwtSubject();
    var path = MemberUtils.getChangeNamePath();
    var body = MemberUtils.createChangeNameBody();

    var result = memberAuthHandler.checkValidAccess(path, method, body, subject);

    Assertions.assertThat(result).isTrue();
  }

  @DisplayName("[" + HANDLER_NAME + "/handlePatchMethod/changeName] change name 실패 테스트")
  @Test
  void handlePatchMethodChangeNameFailTest() {
    var method = GeneralUtils.getPatchMethod();
    var subject = JwtUtils.getJwtSubject();
    var path = MemberUtils.getChangeNamePath();
    var body = MemberUtils.createChangeNameBody("2");

    var result = memberAuthHandler.checkValidAccess(path, method, body, subject);

    Assertions.assertThat(result).isFalse();
  }


  @DisplayName("[" + HANDLER_NAME + "/handlePatchMethod/changePwd] change pwd 성공 테스트")
  @Test
  void handlePatchMethodChangePwdSuccessTest() {
    var method = GeneralUtils.getPatchMethod();
    var subject = JwtUtils.getJwtSubject();
    var path = MemberUtils.getChangePwdPath();
    var body = MemberUtils.createChangePwdBody();

    var result = memberAuthHandler.checkValidAccess(path, method, body, subject);

    Assertions.assertThat(result).isTrue();
  }

  @DisplayName("[" + HANDLER_NAME + "/handlePatchMethod/changePwd] change pwd 실패 테스트")
  @Test
  void handlePatchMethodChangePwdFailTest() {
    var method = GeneralUtils.getPatchMethod();
    var subject = JwtUtils.getJwtSubject();
    var path = MemberUtils.getChangePwdPath();
    var body = MemberUtils.createChangePwdBody("2");

    var result = memberAuthHandler.checkValidAccess(path, method, body, subject);

    Assertions.assertThat(result).isFalse();
  }
}