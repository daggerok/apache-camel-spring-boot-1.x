package daggerok.app;

import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//tag::content[]
@Configuration
@RequiredArgsConstructor
public class MoveFilesCamelConfig {

  @Bean
  public RouteBuilder copyDirectoryRoute() {
    return new RouteBuilder() {
      @Override
      public void configure() throws Exception {
        from("file:///tmp/camel-in")
            .to("file:///tmp/camel-out");
      }
    };
  }
}
//end::content[]
