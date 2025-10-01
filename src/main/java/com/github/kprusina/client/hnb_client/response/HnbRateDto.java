package com.github.kprusina.client.hnb_client.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class HnbRateDto {
  @JsonProperty("broj_tecajnice")
  private String exchangeRateNumber;

  @JsonProperty("datum_primjene")
  private String effectiveDate;

  @JsonProperty("drzava")
  private String country;

  @JsonProperty("srednji_tecaj")
  private String averageRate;
}
