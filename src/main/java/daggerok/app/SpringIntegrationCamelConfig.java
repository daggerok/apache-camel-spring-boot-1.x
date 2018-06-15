package daggerok.app;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.spi.ComponentCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.dsl.support.GenericHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import javax.jms.ConnectionFactory;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;

//tag::jms-component-customizer[]
@Component
@RequiredArgsConstructor
class SpringIntegrationJmsComponentCustomizer implements ComponentCustomizer<JmsComponent> {

  final ConnectionFactory connectionFactory;

  @Override
  public void customize(JmsComponent component) {
    component.setConnectionFactory(connectionFactory);
  }
}
//end::jms-component-customizer[]

//tag::integration-flow-config[]

/**
 * Requires camel spring-integration-starter
 */
@Slf4j
@Configuration
class SpringIntegrationFlowConfig {

  @Bean
  public MessageChannel inputMessageChannel() {
    return MessageChannels.direct().get();
  }

  @Bean
  public IntegrationFlow inputMessageFlow() {
    return IntegrationFlows
        .from(inputMessageChannel())
        .handle(message -> {
          log.info("integration flow: {}", message.getPayload());
        })
        .get();
  }
}
//end::integration-flow-config[]

//tag::spring-integration[]
@Configuration
@RequiredArgsConstructor
public class SpringIntegrationCamelConfig {

  @Bean
  public RouteBuilder springIntegrationRoute() {
    return new RouteBuilder() {
      @Override
      public void configure() throws Exception {

        from("file:///tmp/camel-spring-integration-in")
            .routeId("to-spring-integration")
            //@formatter:off
            .transform()
              .body(String.class, (s, headers) -> s)
            //@formatter:on
            .to("jms:queue:flow")
        ;

        from("jms:queue:flow")
            .to("spring-integration:inputMessageChannel")
        ;
      }
    };
  }
}
//end::spring-integration[]
