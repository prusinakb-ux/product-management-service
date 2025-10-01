package com.github.kprusina.feature.currency;

import com.github.kprusina.client.hnb_client.HnbClient;
import com.github.kprusina.client.hnb_client.response.HnbRateDto;
import com.github.kprusina.exception.SoftException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CurrencyService {

  private final HnbClient hnbClient;

  public BigDecimal convertEurToCurrency(BigDecimal priceEur, String targetCurrency) {

    HnbRateDto latestRate = fetchLatestRate(targetCurrency);
    BigDecimal averageRate = parseAndValidateRate(latestRate.getAverageRate(), targetCurrency);

    return priceEur.multiply(averageRate).setScale(2, RoundingMode.HALF_UP);
  }

  //  private HnbRateDto fetchLatestRate(String targetCurrency) {
  //    List<HnbRateDto> rates =
  //        Optional.ofNullable(hnbClient.getRate(targetCurrency))
  //            .filter(list -> !list.isEmpty())
  //            .orElseThrow(() -> new SoftException("hnbRate.notFound", targetCurrency));
  //
  //    return rates.stream()
  //        .max(Comparator.comparing(HnbRateDto::getEffectiveDate))
  //        .orElseThrow(() -> new SoftException("hnbRate.notFound", targetCurrency));
  //  }

  private HnbRateDto fetchLatestRate(String targetCurrency) {
    return Optional.ofNullable(hnbClient.getRate(targetCurrency))
        .flatMap(list -> list.stream().max(Comparator.comparing(HnbRateDto::getEffectiveDate)))
        .orElseThrow(() -> new SoftException("hnbRate.notFound", targetCurrency));
  }

  private BigDecimal parseAndValidateRate(String rateStr, String currency) {
    try {
      BigDecimal rate = new BigDecimal(rateStr.trim().replace(",", "."));
      if (rate.compareTo(BigDecimal.ZERO) <= 0) {
        throw new SoftException("error.invalidAverageRate", rateStr);
      }
      return rate;
    } catch (NumberFormatException e) {
      log.warn("Failed to parse {} rate: {}", currency, rateStr, e);
      throw new SoftException("error.invalidAverageRate", rateStr, e);
    }
  }
}
