package shop.s5g.bookApi.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageUploader {
    public static byte[] downloadImageAsByte(String imageUrl) throws IOException {
        URL url = new URL(imageUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        try (InputStream inputStream = connection.getInputStream();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[4096];
            int bytesReard;
            while ((bytesReard = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesReard);
            }

            return outputStream.toByteArray();
        }
    }

}
