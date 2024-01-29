package com.pellto.youtoyapigateway.handler;

import com.pellto.youtoyapigateway.handler.util.ChannelManagementUtils;
import com.pellto.youtoyapigateway.handler.util.GeneralUtils;
import com.pellto.youtoyapigateway.handler.util.JwtUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@Tag("handler")
class ChannelManagementAuthHandlerTest {

  private static final String HANDLER_NAME = "ChannelManagementAuthHandler";

  @InjectMocks
  private ChannelManagementAuthHandler channelManagementAuthHandler;

  @DisplayName("[" + HANDLER_NAME + "/handlePostMethod/invite] invite path 성공 테스트")
  @Test
  void handlePostMethodDetailPathSuccessTest() {
    var method = GeneralUtils.getPostMethod();
    var subject = JwtUtils.getJwtSubject();
    var path = ChannelManagementUtils.getInvitePath();
    var body = ChannelManagementUtils.createInviteBody();

    var result = channelManagementAuthHandler.checkValidAccess(path, method, body, subject);

    Assertions.assertThat(result).isTrue();
  }

  @DisplayName("[" + HANDLER_NAME + "/handlePostMethod/invite] invite path viewer가 초대 실패 테스트")
  @Test
  void handlePostMethodInvitePathFailByViewerTest() {
    var method = GeneralUtils.getPostMethod();
    var subject = JwtUtils.getViewerRoleSubject();
    var path = ChannelManagementUtils.getInvitePath();
    var body = ChannelManagementUtils.createInviteBody();

    var result = channelManagementAuthHandler.checkValidAccess(path, method, body, subject);

    Assertions.assertThat(result).isFalse();
  }

  @DisplayName("[" + HANDLER_NAME + "/handlePostMethod/invite] invite path co_worker가 초대 실패 테스트")
  @Test
  void handlePostMethodInvitePathFailByCoWorkerTest() {
    var method = GeneralUtils.getPostMethod();
    var subject = JwtUtils.getCoWorkerRoleSubject();
    var path = ChannelManagementUtils.getInvitePath();
    var body = ChannelManagementUtils.createInviteBody();

    var result = channelManagementAuthHandler.checkValidAccess(path, method, body, subject);

    Assertions.assertThat(result).isFalse();
  }

  @DisplayName("[" + HANDLER_NAME
      + "/handlePatchMethod/changeLevel] changeLevel path 소유자가 co-worker변경 성공 테스트")
  @Test
  void handlePatchMethodChangeLevelPathSuccessTest() {
    var method = GeneralUtils.getPatchMethod();
    var subject = JwtUtils.getJwtSubject();
    var path = ChannelManagementUtils.getChangeLevelPath();
    var body = ChannelManagementUtils.createChangeLevelBodyWithLevel("CO_WORKER");

    var result = channelManagementAuthHandler.checkValidAccess(path, method, body, subject);

    Assertions.assertThat(result).isTrue();
  }

  @DisplayName("[" + HANDLER_NAME
      + "/handlePatchMethod/changeLevel] changeLevel path co-worker가 viewer 성공 테스트")
  @Test
  void handlePatchMethodChangeLevelPathSuccessToViewerTest() {
    var method = GeneralUtils.getPatchMethod();
    var subject = JwtUtils.getCoWorkerRoleSubject();
    var path = ChannelManagementUtils.getChangeLevelPath();
    var body = ChannelManagementUtils.createChangeLevelBodyWithLevel("VIEWER");

    var result = channelManagementAuthHandler.checkValidAccess(path, method, body, subject);

    Assertions.assertThat(result).isTrue();
  }

  @DisplayName("[" + HANDLER_NAME
      + "/handlePatchMethod/changeLevel] changeLevel path co-worker가 co-worker 실패 테스트")
  @Test
  void handlePatchMethodChangeLevelPathFailTest() {
    var method = GeneralUtils.getPatchMethod();
    var subject = JwtUtils.getCoWorkerRoleSubject();
    var path = ChannelManagementUtils.getChangeLevelPath();
    var body = ChannelManagementUtils.createChangeLevelBodyWithLevel("CO_WORKER");

    var result = channelManagementAuthHandler.checkValidAccess(path, method, body, subject);

    Assertions.assertThat(result).isFalse();
  }

  @DisplayName(
      "[" + HANDLER_NAME + "/handlePatchMethod/changeLevel] changeLevel path viewer가 viewer 실패 테스트")
  @Test
  void handlePatchMethodChangeLevelPathFailWithViewerTest() {
    var method = GeneralUtils.getPatchMethod();
    var subject = JwtUtils.getViewerRoleSubject();
    var path = ChannelManagementUtils.getChangeLevelPath();
    var body = ChannelManagementUtils.createChangeLevelBodyWithLevel("VIEWER");

    var result = channelManagementAuthHandler.checkValidAccess(path, method, body, subject);

    Assertions.assertThat(result).isFalse();
  }

  @DisplayName(
      "[" + HANDLER_NAME + "/handlePatchMethod/changeLevel] changeLevel path 이상한 요청 실패 테스트")
  @Test
  void handlePatchMethodChangeLevelPathFailWithAnotherLevelTest() {
    var method = GeneralUtils.getPatchMethod();
    var subject = JwtUtils.getJwtSubject();
    var path = ChannelManagementUtils.getChangeLevelPath();
    var body = ChannelManagementUtils.createChangeLevelBodyWithLevel("WRONG_LEVEL");

    var result = channelManagementAuthHandler.checkValidAccess(path, method, body, subject);

    Assertions.assertThat(result).isFalse();
  }

  @DisplayName(
      "[" + HANDLER_NAME
          + "/handleGetMethod/getChannelManagement] getChannelManagement path 성공 테스트")
  @Test
  void handleGetMethodGetChannelManagementPathSuccessTest() {
    var method = GeneralUtils.getGetMethod();
    var subject = JwtUtils.getJwtSubject();
    var path = ChannelManagementUtils.getGetChannelManagementPath();

    var result = channelManagementAuthHandler.checkValidAccess(path, method, null, subject);

    Assertions.assertThat(result).isTrue();
  }

  @DisplayName(
      "[" + HANDLER_NAME
          + "/handleGetMethod/getChannelManagement] getChannelManagement path 실패 테스트")
  @Test
  void handleGetMethodGetChannelManagementPathFailTest() {
    var method = GeneralUtils.getGetMethod();
    var subject = JwtUtils.getJwtSubject();
    var path = ChannelManagementUtils.getGetChannelManagementPath("2");

    var result = channelManagementAuthHandler.checkValidAccess(path, method, null, subject);

    Assertions.assertThat(result).isFalse();
  }
}