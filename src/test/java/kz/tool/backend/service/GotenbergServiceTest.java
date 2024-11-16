package kz.tool.backend.service;

import kz.tool.backend.BaseTest;
import kz.tool.backend.dto.FileConvertResponse;
import kz.tool.backend.util.UtFile;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.file.Files;
import java.util.Arrays;

public class GotenbergServiceTest extends BaseTest {

    @Autowired
    private GotenbergService gotenbergService;

    @Test
    @SneakyThrows
    public void should_convert_docx_file_to_pdf() {
        // given
        var file = UtFile.getFile("files/mydocument.docx");
        var multipartFile = UtFile.getMultipartFile(file);

        // when
        FileConvertResponse fileConvertResponse = gotenbergService.convertToPdf(multipartFile);

        // then
        Assertions.assertFalse(Arrays.equals(Files.readAllBytes(file.toPath()), fileConvertResponse.content()));
    }
}