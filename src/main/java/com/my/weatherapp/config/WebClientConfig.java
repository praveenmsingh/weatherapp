package com.my.weatherapp.config;

import static io.netty.channel.ChannelOption.CONNECT_TIMEOUT_MILLIS;

import java.time.Duration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Configuration
public class WebClientConfig {

  @Bean
  public WebClient webClient() {
    // Configure HttpClient with timeouts
    HttpClient httpClient = HttpClient.create()
        .responseTimeout(Duration.ofSeconds(20))  // Response timeout
        .option(CONNECT_TIMEOUT_MILLIS, 5000);  // Connection timeout

    return WebClient.builder()
        .clientConnector(new ReactorClientHttpConnector(httpClient))
        .build();
  }
}
