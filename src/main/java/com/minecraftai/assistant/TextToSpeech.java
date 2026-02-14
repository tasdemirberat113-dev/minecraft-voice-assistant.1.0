package com.minecraftai.assistant;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

public class TextToSpeech {
    private static final Logger LOGGER = LoggerFactory.getLogger("TTS");
    
    private final OkHttpClient client;
    private final Gson gson;
    
    public TextToSpeech() {
        this.client = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build();
        this.gson = new Gson();
    }
    
    public void speak(String text) {
        new Thread(() -> {
            try {
                LOGGER.info("🔊 Konuşuyor: " + text);
                
                // OpenAI TTS API kullan (en kaliteli)
                byte[] audioData = generateSpeech(text);
                
                if (audioData != null) {
                    playAudio(audioData);
                }
                
            } catch (Exception e) {
                LOGGER.error("❌ TTS hatası: ", e);
            }
        }).start();
    }
    
    private byte[] generateSpeech(String text) {
        try {
            // OpenAI TTS API
            JsonObject requestBody = new JsonObject();
            requestBody.addProperty("model", "tts-1"); // veya tts-1-hd daha kaliteli
            requestBody.addProperty("input", text);
            requestBody.addProperty("voice", "nova"); // nova, alloy, echo, fable, onyx, shimmer
            requestBody.addProperty("speed", 1.0); // Konuşma hızı
            
            RequestBody body = RequestBody.create(
                gson.toJson(requestBody),
                MediaType.parse("application/json")
            );
            
            Request request = new Request.Builder()
                .url("https://api.openai.com/v1/audio/speech")
                .addHeader("Authorization", "Bearer OPENAI_API_KEY_BURAYA")
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();
            
            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    return response.body().bytes();
                } else {
                    LOGGER.error("TTS API hatası: " + response.code());
                    return useFallbackTTS(text);
                }
            }
            
        } catch (Exception e) {
            LOGGER.error("TTS oluşturma hatası: ", e);
            return useFallbackTTS(text);
        }
    }
    
    private byte[] useFallbackTTS(String text) {
        // Fallback: Java'nın yerleşik TTS'i (kalitesiz ama çalışır)
        LOGGER.warn("⚠️ Fallback TTS kullanılıyor");
        
        // FreeTTS veya MaryTTS gibi offline kütüphaneler kullanılabilir
        // Şimdilik null dönelim, kullanıcı API anahtarı eklemelidir
        return null;
    }
    
    private void playAudio(byte[] audioData) {
        try {
            // MP3 audio decode et ve oynat
            ByteArrayInputStream bais = new ByteArrayInputStream(audioData);
            
            // MP3 için javazoom kullanılabilir
            // Şimdilik basit WAV formatı varsayalım
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(bais);
            AudioFormat format = audioStream.getFormat();
            
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
            SourceDataLine speakers = (SourceDataLine) AudioSystem.getLine(info);
            speakers.open(format);
            speakers.start();
            
            byte[] buffer = new byte[4096];
            int bytesRead;
            
            while ((bytesRead = audioStream.read(buffer, 0, buffer.length)) != -1) {
                speakers.write(buffer, 0, bytesRead);
            }
            
            speakers.drain();
            speakers.close();
            audioStream.close();
            
            LOGGER.info("✅ Ses çalındı!");
            
        } catch (Exception e) {
            LOGGER.error("❌ Ses çalma hatası: ", e);
        }
    }
    
    public void stop() {
        // Konuşmayı durdur (gelecekte implement edilecek)
    }
}
