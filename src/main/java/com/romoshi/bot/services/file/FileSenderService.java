package com.romoshi.bot.services.file;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class FileSenderService {

    @Value("${bot.token}")
    private String botToken;

    private final RestTemplate restTemplate;

    private final FileService fileService;

    public FileSenderService(FileService fileService, RestTemplate restTemplate) {
        this.fileService = fileService;
        this.restTemplate = restTemplate;
    }

    public void sendFileToChat(String chatId, String fileName) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("chat_id", chatId);

            Resource fileResource = fileService.getLocalFile(fileName);

            body.add("document", fileResource);

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            String url = "https://api.telegram.org/bot" + botToken + "/sendDocument";

            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    String.class);

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                log.info("Файл успешно отправлен в чат.");
            } else {
                log.error("Ошибка при отправке файла: " + responseEntity.getBody());
            }
        } catch (Exception e) {
            log.error("Ошибка при отправке файла: " + e.getMessage());
        }
    }
}


