package kz.tool.backend.service;

import kz.tool.backend.dto.FileInfoContent;
import kz.tool.backend.entity.FileInfo;
import kz.tool.backend.enums.Bucket;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface FileInfoService {

    /**
     * Получение информации по файлу
     * @param id Идентификатор файла. не может быть null
     * @return Информация по файлу
     * */
    FileInfo findById(@NotNull UUID id);

    /**
     * Получение информации по файлу и его содержимое
     * @param id Идентификатор файла. не может быть null
     * @return Информация по файлу с его содержимым в виде массива байт
     * */
    FileInfoContent findWithContentById(@NotNull UUID id);

    /**
     * Сохранение(создание) файла
     * @param multipartFile Файл. не может быть null
     * @param bucket Бакет. не может быть null
     * @return Идентификатор сохраненного файла
     * */
    UUID create(@NotNull MultipartFile multipartFile, @NotNull Bucket bucket);
}
