package daggerok.app;

import lombok.RequiredArgsConstructor;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@RequiredArgsConstructor
public class MoveFilesCamelConfig {

  final CamelContext camelContext;
  //final ConnectionFactory connectionFactory;

  @PostConstruct
  public void initCamelJmsComponent() {
    //final JmsComponent jmsComponent = JmsComponent.jmsComponent(connectionFactory);
    //this.camelContext.addComponent("my-jms-component", jmsComponent);
  }

  @Bean
  public RouteBuilder copyDirectoryRoute() {
    return new RouteBuilder() {
      @Override
      public void configure() throws Exception {
        //from("my-jms-component:queue:my-queue")
        from("file:///tmp/camel-in")
            .to("file:///tmp/camel-out");
      }
    };
  }
}
