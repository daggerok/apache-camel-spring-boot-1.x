package daggerok.app;

import lombok.RequiredArgsConstructor;
import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

import static java.lang.String.format;

//tag::process-exchange[]
@Configuration
@RequiredArgsConstructor
public class ExchangeProcessorCamelConfig {

  @Bean
  public RouteBuilder processExchangeRoute() {
    return new RouteBuilder() {
      @Override
      public void configure() throws Exception {
        from("file:///tmp/camel-exchange-in")
            .routeId("exchange-processor")
            .process().exchange(exchange -> {
              final Message inputMessage = exchange.getIn();
              final Object body = inputMessage.getBody(String.class);
              log.info("handling message body in exchange consumer: {}", body);
            })
            .to("file:///tmp/camel-exchange-out");
      }
    };
  }
}
//end::process-exchange[]
