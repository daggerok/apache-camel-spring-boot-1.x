package daggerok.app;

import lombok.RequiredArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.Predicate;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//tag::choice[]
@Configuration
@RequiredArgsConstructor
public class ChoiceCamelConfig {

  @Bean
  public RouteBuilder moveFilesRoute() {
    return new RouteBuilder() {
      @Override
      public void configure() throws Exception {
        from("file:///tmp/camel-choice-in")
            .routeId("choice")
            .choice()
            .when(exchange -> {
              final String body = exchange.getMessage().getBody(String.class);
              return !body.toLowerCase().contains("err");
            })
            .to("file:///tmp/camel-choice-out")
            .otherwise()
            .to("file:///tmp/camel-choice-error")
            .endChoice()
        ;
      }
    };
  }
}
//end::choice[]
