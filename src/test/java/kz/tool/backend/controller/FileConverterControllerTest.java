package kz.tool.backend.controller;

import io.restassured.RestAssured;
import kz.tool.backend.BaseTest;
import kz.tool.backend.dto.FileConvertResponse;
import kz.tool.backend.util.UtFile;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.nio.file.Files;
import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class FileConverterControllerTest extends BaseTest {

    @Test
    @SneakyThrows
    public void should_convert_to_pdf() {
        // given
        var file = UtFile.getFile("files/mydocument.docx");

        // when
        var fileConvertResponse = RestAssured.given()
                .header("Content-type", "multipart/form-data")
                .multiPart("file", file, "multipart/form-data")
                .when()
                .post("/api/v1/files/converter/pdf")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(FileConvertResponse.class);

        // then
        Assertions.assertFalse(Arrays.equals(Files.readAllBytes(file.toPath()), fileConvertResponse.content()));
    }
}