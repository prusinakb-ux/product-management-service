package com.github.kprusina.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class I18nMessageResolver {

  private final MessageSource messageSource;

  public I18nMessage translate(String message, @Nullable Object... args) {
    return new I18nMessage(
        messageSource.getMessage(message, args, LocaleContextHolder.getLocale()));
  }
}
