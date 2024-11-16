package kz.tool.backend.service;

import io.minio.GetObjectResponse;
import jakarta.validation.constraints.NotNull;
import kz.tool.backend.enums.Bucket;
import org.springframework.web.multipart.MultipartFile;

public interface MinioService {

    /**
     * Проверка, что бакет уже создан
     * @param bucket Бакет
     * */
    boolean isBucketExists(@NotNull Bucket bucket);

    /**
     * Загрузка файла
     * @param file Файл
     * @param bucket Бакет
     * @param fileName Наименование файла
     * */
    void uploadFile(MultipartFile file, Bucket bucket, String fileName);

    /**
     * Получение данных по файлу
     * @param bucket Бакет
     * @param fileName Наименование файла
     * @return данные по файлу
     * */
    GetObjectResponse getObjectResponse(Bucket bucket, String fileName);
}
