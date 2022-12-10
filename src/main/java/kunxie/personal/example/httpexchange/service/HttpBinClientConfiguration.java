package kunxie.personal.example.httpexchange.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import reactor.core.publisher.Mono;

@Slf4j
@Configuration
public class HttpBinClientConfiguration {

  private final String httpBinClientUrl;

  public HttpBinClientConfiguration(@Value("${http.bin.client.url}") String httpBinClientUrl) {
    this.httpBinClientUrl = httpBinClientUrl;
  }

  @Bean
  public HttpServiceProxyFactory httpServiceProxyFactory(WebClient.Builder builder) {
    WebClient webClient = builder
        .baseUrl(httpBinClientUrl)
        .filter(requestLogFilter())
        .filter(responseLogFilter())
        .build();
    return HttpServiceProxyFactory.builder(WebClientAdapter.forClient(webClient)).build();
  }

  @Bean
  public HttpBinClient httpBinClient(HttpServiceProxyFactory httpServiceProxyFactory) {
    return httpServiceProxyFactory.createClient(HttpBinClient.class);
  }

  private ExchangeFilterFunction requestLogFilter() {
    return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
      log.info("Request ==========>");
      log.info("HttpMethod: {} , Url: {}, Headers: {}", clientRequest.method(), clientRequest.url(), clientRequest.headers());
      // todo: find an elegant way to log the request body
      return Mono.just(clientRequest);
    });
  }

  private ExchangeFilterFunction responseLogFilter() {
    return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
      log.info("<========== Response");
      log.info("Response statusCode: {}", clientResponse.statusCode());
      log.info("Response Headers: {}", clientResponse.headers().asHttpHeaders());
      // todo: find an elegant way to log the response body
      return Mono.just(clientResponse);
    });
  }
}
