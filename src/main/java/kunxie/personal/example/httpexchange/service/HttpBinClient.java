package kunxie.personal.example.httpexchange.service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

public interface HttpBinClient {

  @GetExchange("/get")
  String get(@RequestHeader HttpHeaders headers);

  @GetExchange(value = "/get")
  String getWithRequestParams(@RequestParam("param1") String param1, @RequestParam("param2") int param2, @RequestParam("param3") boolean param3);

  @GetExchange("/get")
  ResponseEntity<String> getWithQueryMap(@RequestParam MultiValueMap<String, String> paramsMap, @RequestHeader HttpHeaders headers);

  @GetExchange("/status/{code}")
  ResponseEntity<Void> statusCode(@PathVariable("code") int statusCode);

}
