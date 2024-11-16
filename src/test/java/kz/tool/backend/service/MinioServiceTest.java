package kz.tool.backend.service;

import kz.tool.backend.BaseTest;
import kz.tool.backend.enums.Bucket;
import kz.tool.backend.util.UtFile;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class MinioServiceTest extends BaseTest {

    @Autowired
    private MinioService minioService;

    @Test
    @SneakyThrows
    public void should_upload_file_and_give_it() {
        // given
        var file = UtFile.getFile("files/cat.jpg");
        var multipartFile = UtFile.getMultipartFile(file);
        var fileName = UUID.randomUUID().toString();
        var bucket = Bucket.PUBLIC;

        // when
        minioService.uploadFile(multipartFile, bucket, fileName);
        var getObjectResponse = minioService.getObjectResponse(bucket, fileName);

        // then
        Assertions.assertArrayEquals(multipartFile.getBytes(), getObjectResponse.readAllBytes());
    }

}