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
  public RouteBuilder choiceRoute() {
    return new RouteBuilder() {
      @Override
      public void configure() throws Exception {
        from("file:///tmp/camel-choice-in")
            .routeId("choice")
            //@formatter:off
            .choice()
              .when(exchange -> !exchange.getMessage().getBody(String.class).toLowerCase().contains("err"))
                .to("file:///tmp/camel-choice-out")
              .otherwise()
                .to("file:///tmp/camel-choice-error")
            .endChoice()
            //@formatter:on
        ;
      }
    };
  }
}
//end::choice[]
