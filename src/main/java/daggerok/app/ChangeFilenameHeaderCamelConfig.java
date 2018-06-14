package daggerok.app;

import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

import static java.lang.String.format;

//tag::change-filename-header[]
@Configuration
@RequiredArgsConstructor
public class ChangeFilenameHeaderCamelConfig {

  @Bean
  public RouteBuilder filenameHeaderRoute() {
    return new RouteBuilder() {
      @Override
      public void configure() throws Exception {
        from("file:///tmp/camel-filename-in")
            .routeId("change-filename-header")
            .setHeader("CamelFileName", () -> format("%s-changed.txt", UUID.randomUUID().toString()))
            .to("file:///tmp/camel-filename-out");
      }
    };
  }
}
//end::change-filename-header[]
