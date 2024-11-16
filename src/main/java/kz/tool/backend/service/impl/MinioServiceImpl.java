package kz.tool.backend.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.minio.BucketExistsArgs;
import io.minio.GetObjectArgs;
import io.minio.GetObjectResponse;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.SetBucketPolicyArgs;
import jakarta.annotation.PostConstruct;
import kz.tool.backend.dto.MinioPolicySettings;
import kz.tool.backend.dto.MinioPolicyStatement;
import kz.tool.backend.enums.Bucket;
import kz.tool.backend.service.MinioService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MinioServiceImpl implements MinioService {

    private final MinioClient minioClient;
    private final ObjectMapper objectMapper;

    @PostConstruct
    private void initBuckets() {
        for(Bucket bucket : Bucket.values()) {
            createBucket(bucket);
        }
    }

    @SneakyThrows
    private void createBucket(Bucket bucket) {
        if(!isBucketExists(bucket)) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket.getName()).build());
            if(bucket.getOpen()) {
                var bucketName = bucket.getName();
                var minioPolicyStatement = MinioPolicyStatement.builder()
                        .effect("Allow")
                        .principal("*")
                        .action(List.of("s3:GetObject"))
                        .resource(List.of("arn:aws:s3:::" + bucketName + "/*"))
                        .build();
                var minioPolicySettings = MinioPolicySettings.builder()
                        .version("2012-10-17")
                        .statement(List.of(minioPolicyStatement))
                        .build();

                String minioPolicySettingsJson = objectMapper.writeValueAsString(minioPolicySettings);

                minioClient.setBucketPolicy(SetBucketPolicyArgs.builder()
                        .bucket(bucketName)
                        .config(minioPolicySettingsJson)
                        .build());
            }
        }
    }

    @SneakyThrows
    public boolean isBucketExists(Bucket bucket) {
        return minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket.getName()).build());
    }

    @SneakyThrows
    public void uploadFile(MultipartFile file, Bucket bucket, String fileName) {
        try (var inputStream = file.getInputStream()) {
            var args = PutObjectArgs.builder()
                    .bucket(bucket.getName())
                    .object(fileName)
                    .stream(inputStream, file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build();
            minioClient.putObject(args);
        }
    }

    @SneakyThrows
    public GetObjectResponse getObjectResponse(Bucket bucket, String fileName) {
        return minioClient.getObject(GetObjectArgs
                .builder()
                .bucket(bucket.getName())
                .object(fileName)
                .build());
    }
}
