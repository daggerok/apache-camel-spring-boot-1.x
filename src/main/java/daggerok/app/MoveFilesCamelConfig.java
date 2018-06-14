package daggerok.app;

import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//tag::move-files[]
@Configuration
@RequiredArgsConstructor
public class MoveFilesCamelConfig {

  @Bean
  public RouteBuilder moveFilesRoute() {
    return new RouteBuilder() {
      @Override
      public void configure() throws Exception {
        from("file:///tmp/camel-in")
            .routeId("dir-to-dir")
            .to("file:///tmp/camel-out");
      }
    };
  }
}
//end::move-files[]
