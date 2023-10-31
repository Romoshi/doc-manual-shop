package com.romoshi.bot.services.file;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.romoshi.bot.telegram.constant.BotStringConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.romoshi.bot.telegram.TelegramBot.sendMsg;

@Service
@Slf4j
public class FileDownloaderService {
    final RestTemplate restTemplate;

    @Value("${bot.token}")
    private String botToken;

    @Value("${bot.file_path}")
    private String savePath;

    public FileDownloaderService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void downloadTelegramFile(Message message) {
        try {
            Document document = message.getDocument();
            String fileId = document.getFileId();
            File file = getFile(fileId);

            if (file != null) {
                String filePath = file.getFilePath();
                String fileUrl = "https://api.telegram.org/file/bot" +
                        botToken + "/" + filePath;
                saveFileFromUrl(fileUrl, savePath + document.getFileId() + ".pdf");
            } else {
                log.error("File not found.");
            }
        } catch (Exception ex) {
            log.error("Can`t save file.");
        }
    }

    private File getFile(String fileId) throws IOException {
        String apiUrl = "https://api.telegram.org/bot" + botToken + "/getFile?file_id=" + fileId;
        URL url = new URL(apiUrl);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            InputStream inputStream = connection.getInputStream();
            InputStreamReader reader = new InputStreamReader(inputStream);

            Gson gson = new Gson();
            JsonObject json = gson.fromJson(reader, JsonObject.class);

            String fId = json.get("result").getAsJsonObject().get("file_id").getAsString();
            String filePath = json.get("result").getAsJsonObject().get("file_path").getAsString();

            File file = new File();
            file.setFileId(fId);
            file.setFilePath(filePath);

            return file;
        }

        return null;
    }

    private void saveFileFromUrl(String fileUrl, String savePath) throws IOException {
        URL url = new URL(fileUrl);
        try (InputStream inputStream = url.openStream();
             FileOutputStream outputStream = new FileOutputStream(savePath)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
    }
}
