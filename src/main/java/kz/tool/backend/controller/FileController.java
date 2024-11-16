package kz.tool.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import kz.tool.backend.dto.FileInfoContent;
import kz.tool.backend.enums.Bucket;
import kz.tool.backend.service.FileInfoService;
import kz.tool.backend.service.MinioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/files")
public class FileController {

    private final FileInfoService fileInfoService;

    @PostMapping
    @Operation(summary = "Добавление файла")
    public UUID create(@RequestPart MultipartFile file) {
        return fileInfoService.create(file, Bucket.PUBLIC);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение данных файла")
    public FileInfoContent findWithContentById(@PathVariable UUID id) {
        return fileInfoService.findWithContentById(id);
    }
}
