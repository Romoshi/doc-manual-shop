package com.romoshi.bot.services.file;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Slf4j
public class FileService {

    @Value("${bot.file_path}")
    private String uploadDir;

    public Resource getLocalFile(String fileName) {
        try {
            Path filePath = Paths.get(uploadDir, fileName + ".pdf");
            return new FileSystemResource(filePath.toFile());
        } catch (NullPointerException e) {
            log.error("Файл не найден: " + fileName);
        }

        return null;
    }
}
