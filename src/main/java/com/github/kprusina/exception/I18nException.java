package com.github.kprusina.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;

@Slf4j
@Getter
public abstract class I18nException extends RuntimeException {
  String messageKey;
  Object[] args;

  public I18nException(String messageKey, @Nullable Object... args) {
    super();
    this.messageKey = messageKey;
    this.args = args;
  }

  public I18nException(Throwable cause, String messageKey, @Nullable Object... args) {
    this(messageKey, args);
    log.error("An exception occured, messageKey: " + messageKey, cause);
  }
}
