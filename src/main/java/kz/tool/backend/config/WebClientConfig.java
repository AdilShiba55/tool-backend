package kz.tool.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${app.gotenberg.url}")
    private String gotenbergUrl;

    @Bean
    public WebClient gotenbergWebClient() {
        return WebClient
                .builder()
                .baseUrl(gotenbergUrl)
                .build();
    }
}
