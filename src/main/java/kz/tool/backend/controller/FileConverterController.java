package kz.tool.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import kz.tool.backend.dto.FileConvertResponse;
import kz.tool.backend.service.GotenbergService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/files/converter")
@RequiredArgsConstructor
public class FileConverterController {

    private final GotenbergService gotenbergService;

    @PostMapping("/pdf")
    @Operation(summary = "Конвертация файла в pdf формат")
    public FileConvertResponse convertToPdf(@RequestPart MultipartFile file) {
        return gotenbergService.convertToPdf(file);
    }
}
