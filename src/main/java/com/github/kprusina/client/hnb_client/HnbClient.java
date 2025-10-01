package com.github.kprusina.client.hnb_client;

import com.github.kprusina.client.hnb_client.response.HnbRateDto;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "hnbClient", url = "https://api.hnb.hr/tecajn-eur/v3")
public interface HnbClient {

  @GetMapping
  List<HnbRateDto> getRate(@RequestParam("valuta") String valuta);
}
