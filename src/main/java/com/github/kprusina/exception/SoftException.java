package com.github.kprusina.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;

@Getter
public class SoftException extends I18nException {
  public static final HttpStatus DEFAULT_STATUS = HttpStatus.INTERNAL_SERVER_ERROR;
  private final HttpStatus status;

  public SoftException(HttpStatus status, String messageKey, @Nullable Object... args) {
    super(messageKey, args);
    this.status = status;
  }

  public SoftException(String messageKey, @Nullable Object... args) {
    this(DEFAULT_STATUS, messageKey, args);
  }
}
