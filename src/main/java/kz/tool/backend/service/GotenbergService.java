package kz.tool.backend.service;

import kz.tool.backend.dto.FileConvertResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.multipart.MultipartFile;

public interface GotenbergService {

    FileConvertResponse convertToPdf(@NotNull MultipartFile multipartFile);
}
