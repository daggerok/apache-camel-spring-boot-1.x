package daggerok.app;

import lombok.RequiredArgsConstructor;
import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
            .body(String.class, (body, headers) -> {
              log.info("easier body handling: {}", body);
              headers.entrySet().parallelStream()
                  .filter(e -> "CamelFileLength".contains(e.getKey()))
                  .forEach(e -> log.info("header({}): {}", e.getKey(), e.getValue()));
            })
            .to("file:///tmp/camel-easier-exchange-out");
      }
    };
  }
}
//end::easier-exchange[]
