package kz.tool.backend.controller;

import io.restassured.RestAssured;
import kz.tool.backend.BaseTest;
import kz.tool.backend.dto.FileInfoContent;
import kz.tool.backend.util.UtFile;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.nio.file.Files;
import java.util.UUID;

class FileControllerTest extends BaseTest {

    @Test
    @SneakyThrows
    public void should_create_file_and_give_it() {
        // given
        var file = UtFile.getFile("files/cat.jpg");

        // when
        var id = RestAssured.given()
                .header("Content-type", "multipart/form-data")
                .multiPart("file", file, "multipart/form-data")
                .when()
                .post("/api/v1/files")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(UUID.class);

        var fileInfoContent = RestAssured.given()
                .when()
                .get("/api/v1/files/" + id)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(FileInfoContent.class);

        // then
        Assertions.assertNotNull(fileInfoContent);
        Assertions.assertArrayEquals(Files.readAllBytes(file.toPath()), fileInfoContent.content());
    }

}