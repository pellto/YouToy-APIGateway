package com.pellto.youtoyapigateway.handler;

import com.pellto.youtoyapigateway.handler.util.ChannelUtils;
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
class ChannelAuthHandlerTest {

  private static final String HANDLER_NAME = "ChannelAuthHandler";

  @InjectMocks
  private ChannelAuthHandler channelAuthHandler;

  @DisplayName("[" + HANDLER_NAME + "/handleGetMethod/channel] channel path 성공 테스트")
  @Test
  void handleGetMethodChannelPathSuccessTest() {
    var method = GeneralUtils.getGetMethod();
    var subject = JwtUtils.getJwtSubject();
    var path = ChannelUtils.getGetChannelPath();

    var result = channelAuthHandler.checkValidAccess(path, method, null, subject);

    Assertions.assertThat(result).isTrue();
  }

  @DisplayName("[" + HANDLER_NAME + "/handleGetMethod/channel] channel path 실패 테스트")
  @Test
  void handleGetMethodChannelPathFailTest() {
    var method = GeneralUtils.getGetMethod();
    var subject = JwtUtils.getJwtSubject();
    var path = ChannelUtils.getGetChannelPath("2");

    var result = channelAuthHandler.checkValidAccess(path, method, null, subject);

    Assertions.assertThat(result).isFalse();
  }
}