package kz.tool.backend.service.impl;

import kz.tool.backend.dto.FileConvertResponse;
import kz.tool.backend.service.GotenbergService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class GotenbergServiceImpl implements GotenbergService {

    private final WebClient gotenbergWebClient;

    @Override
    public FileConvertResponse convertToPdf(@NotNull MultipartFile multipartFile) {
        MultiValueMap<String, Object> formData = getFormData(multipartFile);
        byte[] content = gotenbergWebClient.post()
                .uri("/forms/libreoffice/convert")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(formData))
                .accept(MediaType.APPLICATION_PDF)
                .retrieve()
                .bodyToMono(byte[].class)
                .block();

        return FileConvertResponse.builder()
                .content(content)
                .build();
    }

    @SneakyThrows
    private MultiValueMap<String, Object> getFormData(MultipartFile multipartFile) {
        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
        formData.add("files", new ByteArrayResource(multipartFile.getBytes()) {
            @Override
            public String getFilename() {
                return multipartFile.getOriginalFilename();
            }
        });
        return formData;
    }
}
