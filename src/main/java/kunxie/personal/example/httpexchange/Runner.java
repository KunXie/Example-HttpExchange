package kunxie.personal.example.httpexchange;

import kunxie.personal.example.httpexchange.service.HttpBinClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Slf4j
@Service
@RequiredArgsConstructor
public class Runner implements ApplicationRunner {

  private final HttpBinClient httpBinClient;

  @Override
  public void run(ApplicationArguments args) {
    var headers = new HttpHeaders();
    headers.add("Test-Header-Name1", "Test-Header-Value1");
    log.info("Application Runner: ----");
    log.info("/get: " + httpBinClient.get(headers));
    log.info("/get withRequestParams: " + httpBinClient.getWithRequestParams("param1", 1, false));

    headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
    headers.add(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE);

    var paramsMap = new LinkedMultiValueMap<String, String>();
    paramsMap.add("param1", "DEFAULT_VALUE");
    paramsMap.add("PARAM2", "1000");
    paramsMap.add("param3", "false");
    paramsMap.add("param3", "true");
    log.info("/get withQueryMap: " + httpBinClient.getWithQueryMap(paramsMap, headers));

    log.info("/status/200: " + httpBinClient.statusCode(200));
    httpClient404();
    httpClient500();
  }

  private void httpClient404() {
    try {
      log.info("/status/404: " + httpBinClient.statusCode(404)); // WebClientResponseException
    } catch (WebClientResponseException ex) {
      log.error("Exception:", ex);
    }
  }

  private void httpClient500() {
    try {
      log.info("/status/404: " + httpBinClient.statusCode(500)); // WebClientResponseException
    } catch (WebClientResponseException ex) {
      log.error("Exception:", ex);
    }
  }
}
