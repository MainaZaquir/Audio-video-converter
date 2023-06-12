import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class Server {
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create();
        server.bind(null, 8000, 0);

        server.createContext("/convert/audio_to_video", new AudioToVideoHandler());
        server.createContext("/convert/video_to_audio", new VideoToAudioHandler());

        server.start();
        System.out.println("Server started on port 8000");
    }

    static class AudioToVideoHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            try {
                InputStream audioStream = exchange.getRequestBody();
                String outputVideoFile = exchange.getRequestHeaders().getFirst("outputVideoFile");
                File output = new File(outputVideoFile);

                Files.copy(audioStream, output.toPath());
                audioStream.close();

                String response = "Audio to video conversion completed. Output: " + output.getAbsolutePath();
                sendResponse(exchange, response);
            } catch (Exception e) {
                sendErrorResponse(exchange, e.getMessage());
            }
        }
    }

    static class VideoToAudioHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            try {
                InputStream videoStream = exchange.getRequestBody();
                String outputAudioFile = exchange.getRequestHeaders().getFirst("outputAudioFile");
                File output = new File(outputAudioFile);

                FileOutputStream outputStream = new FileOutputStream(output);
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = videoStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                videoStream.close();
                outputStream.close();

                String response = "Video to audio conversion completed. Output: " + output.getAbsolutePath();
                sendResponse(exchange, response);
            } catch (Exception e) {
                sendErrorResponse(exchange, e.getMessage());
            }
        }
    }

    private static void sendResponse(HttpExchange exchange, String response) throws IOException {
        exchange.sendResponseHeaders(200, response.getBytes().length);
        exchange.getResponseBody().write(response.getBytes());
        exchange.close();
    }

    private static void sendErrorResponse(HttpExchange exchange, String error) throws IOException {
        exchange.sendResponseHeaders(500, error.getBytes().length);
        exchange.getResponseBody().write(error.getBytes());
        exchange.close();
    }
}
