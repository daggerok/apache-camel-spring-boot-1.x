package daggerok.app;

import lombok.RequiredArgsConstructor;
import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static java.lang.String.format;

//tag::easier-exchange[]
@Configuration
@RequiredArgsConstructor
public class EasierExchangeProcessorCamelConfig {

  @Bean
  public RouteBuilder easierExchangeRoute() {
    return new RouteBuilder() {
      @Override
      public void configure() throws Exception {
        from("file:///tmp/camel-easier-exchange-in")
            .routeId("easier-exchange")
            .process()
            .message(message -> {
              final String body = message.getBody(String.class);
              final Map<String, Object> headers = message.getHeaders();
              log.info("easy body: {}", body);
              headers.entrySet().parallelStream()
                  .filter(e -> "CamelFileLength".contains(e.getKey()))
                  .forEach(e -> log.info("header({}): {}", e.getKey(), e.getValue()));
            })
            /* // absolutely same, even more easier!
            .body(String.class, (body, headers) -> {
              log.info("easiest: {}", body);
              headers.entrySet().parallelStream()
                  .filter(e -> "CamelFileLength".contains(e.getKey()))
                  .forEach(e -> log.info("header({}): {}", e.getKey(), e.getValue()));
            })
            */
            .to("file:///tmp/camel-easier-exchange-out");
      }
    };
  }
}
//end::easier-exchange[]
