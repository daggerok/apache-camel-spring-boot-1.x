package daggerok.app;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.file.GenericFile;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.spi.ComponentCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.jms.ConnectionFactory;
import java.io.*;

import static java.util.stream.Collectors.joining;

//tag::jms-component-customizer[]
@Component
@RequiredArgsConstructor
class MoveFilesViaJmsComponentCustomizer implements ComponentCustomizer<JmsComponent> {

  final ConnectionFactory connectionFactory;

  @Override
  public void customize(JmsComponent component) {
    component.setConnectionFactory(connectionFactory);
  }
}
//end::jms-component-customizer[]

//tag::move-files-via-jms[]
@Slf4j
@Configuration
@RequiredArgsConstructor
public class MoveFilesViaJmsCamelConfig {

  @Bean
  public RouteBuilder moveFilesViaJmsRoute() {
    return new RouteBuilder() {
      @Override
      public void configure() throws Exception {

        from("file:///tmp/camel-jms-in")
            .routeId("in-dir-to-jms")
            .transform()
            .body(GenericFile.class, genericFile -> {
              final File file = File.class.cast(genericFile.getFile());
              try (final FileInputStream inputStream = new FileInputStream(file)) {
                final InputStreamReader streamReader = new InputStreamReader(inputStream);
                final BufferedReader in = new BufferedReader(streamReader);
                return in.lines().collect(joining());
              } catch (IOException e) {
                log.error(e.getLocalizedMessage());
                throw new RuntimeException(e);
              }
            })
            .to("jms:queue:files")
        ;

        from("jms:queue:files")
            .routeId("jms-to-out-dir")
            .to("file:///tmp/camel-jms-out")
        ;
      }
    };
  }
}
//end::move-files-via-jms[]
