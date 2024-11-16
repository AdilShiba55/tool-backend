package kz.tool.backend.config;

import io.minio.MinioClient;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {

    @Value("${app.minio.url}")
    private String url;
    @Value("${app.minio.login}")
    private String login;
    @Value("${app.minio.password}")
    private String password;

    @Bean
    @SneakyThrows
    public MinioClient minioClient() {
        MinioClient.Builder builder = MinioClient.builder()
                .endpoint(url)
                .credentials(login, password);
        MinioClient minioClient = builder.build();
        minioClient.ignoreCertCheck();
        return minioClient;
    }
}
