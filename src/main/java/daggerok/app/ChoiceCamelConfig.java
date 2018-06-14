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
              final boolean isRandomError = Math.random() < .5;
              log.error("we've got an error? {}", isRandomError);
              return isRandomError;
            })
            .to("file:///tmp/camel-choice-error")
            .endChoice()
            .to("file:///tmp/camel-choice-out");
      }
    };
  }
}
//end::choice[]
