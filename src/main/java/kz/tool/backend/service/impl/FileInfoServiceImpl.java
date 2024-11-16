package kz.tool.backend.service.impl;

import kz.tool.backend.dto.FileInfoContent;
import kz.tool.backend.entity.FileInfo;
import kz.tool.backend.enums.Bucket;
import kz.tool.backend.exception.EntityNotFoundException;
import kz.tool.backend.repository.FileInfoRepository;
import kz.tool.backend.service.FileInfoService;
import kz.tool.backend.service.MinioService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileInfoServiceImpl implements FileInfoService {

    private final FileInfoRepository fileInfoRepository;
    private final MinioService minioService;

    @Override
    public FileInfo findById(@NotNull UUID id) {
        return fileInfoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(FileInfo.class, id));
    }

    @Override
    @SneakyThrows
    public FileInfoContent findWithContentById(@NotNull UUID id) {
        var fileInfo = findById(id);
        var getObjectResponse = minioService.getObjectResponse(fileInfo.getBucket(), id.toString());
        return FileInfoContent.builder()
                .id(id)
                .bucket(fileInfo.getBucket())
                .dtCreate(fileInfo.getDtCreate())
                .content(getObjectResponse.readAllBytes())
                .build();
    }

    @Transactional
    public UUID create(@NotNull MultipartFile multipartFile, @NotNull Bucket bucket) {
        var id = UUID.randomUUID();
        var fileInfo = new FileInfo();
        fileInfo.setId(id);
        fileInfo.setBucket(bucket);
        fileInfo.setDtCreate(new Date());
        fileInfoRepository.save(fileInfo);
        minioService.uploadFile(multipartFile, bucket, id.toString());
        return id;
    }
}
