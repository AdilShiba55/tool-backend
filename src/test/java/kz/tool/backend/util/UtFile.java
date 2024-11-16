package kz.tool.backend.util;

import lombok.SneakyThrows;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.util.Objects;

public class UtFile {

    public static File getFile(String path) {
        return new File(Objects.requireNonNull(UtFile.class.getClassLoader().getResource(path)).getFile());
    }

    @SneakyThrows
    public static MultipartFile getMultipartFile(File file) {
        String fileName = file.getName();
        String contentType = Files.probeContentType(file.toPath());
        try(var inputStream = new FileInputStream(file)) {
            return  new MockMultipartFile(
                    fileName,
                    fileName,
                    contentType,
                    FileCopyUtils.copyToByteArray(inputStream)
            );
        }
    }
}
