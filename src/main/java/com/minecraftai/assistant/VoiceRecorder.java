package com.minecraftai.assistant;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

public class VoiceRecorder {
    private static final Logger LOGGER = LoggerFactory.getLogger("VoiceRecorder");
    
    // Ses formatı
    private static final float SAMPLE_RATE = 16000.0f;
    private static final int SAMPLE_SIZE = 16;
    private static final int CHANNELS = 1; // Mono
    private static final boolean SIGNED = true;
    private static final boolean BIG_ENDIAN = false;
    
    private TargetDataLine microphone;
    private ByteArrayOutputStream audioBuffer;
    private Thread recordingThread;
    private boolean isRecording = false;
    
    private final Gson gson;
    private final OkHttpClient client;
    
    public VoiceRecorder() {
        this.gson = new Gson();
        this.client = new OkHttpClient.Builder().build();
    }
    
    public void startRecording() {
        try {
            AudioFormat format = new AudioFormat(
                SAMPLE_RATE, SAMPLE_SIZE, CHANNELS, SIGNED, BIG_ENDIAN
            );
            
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            
            if (!AudioSystem.isLineSupported(info)) {
                LOGGER.error("❌ Mikrofon desteklenmiyor!");
                return;
            }
            
            microphone = (TargetDataLine) AudioSystem.getLine(info);
            microphone.open(format);
            microphone.start();
            
            audioBuffer = new ByteArrayOutputStream();
            isRecording = true;
            
            // Kayıt thread'i
            recordingThread = new Thread(() -> {
                byte[] buffer = new byte[4096];
                while (isRecording) {
                    int bytesRead = microphone.read(buffer, 0, buffer.length);
                    if (bytesRead > 0) {
                        audioBuffer.write(buffer, 0, bytesRead);
                    }
                }
            });
            recordingThread.start();
            
            LOGGER.info("🎤 Kayıt başladı!");
            
        } catch (LineUnavailableException e) {
            LOGGER.error("❌ Mikrofon açılamadı: ", e);
        }
    }
    
    public byte[] stopRecording() {
        isRecording = false;
        
        if (recordingThread != null) {
            try {
                recordingThread.join();
            } catch (InterruptedException e) {
                LOGGER.error("Thread hatası: ", e);
            }
        }
        
        if (microphone != null) {
            microphone.stop();
            microphone.close();
        }
        
        byte[] audioData = audioBuffer.toByteArray();
        LOGGER.info("✅ Kayıt tamamlandı: " + audioData.length + " bytes");
        
        return audioData;
    }
    
    public String transcribeAudio(byte[] audioData) {
        try {
            // OpenAI Whisper API kullanıyoruz (en iyi speech-to-text)
            // Alternatif: Google Speech-to-Text, Azure Speech
            
            // Geçici WAV dosyası oluştur
            File tempWav = File.createTempFile("voice", ".wav");
            AudioFormat format = new AudioFormat(
                SAMPLE_RATE, SAMPLE_SIZE, CHANNELS, SIGNED, BIG_ENDIAN
            );
            
            byte[] audioBytes = audioData;
            ByteArrayInputStream bais = new ByteArrayInputStream(audioBytes);
            AudioInputStream audioStream = new AudioInputStream(
                bais, format, audioBytes.length / format.getFrameSize()
            );
            
            AudioSystem.write(audioStream, AudioFileFormat.Type.WAVE, tempWav);
            
            // Whisper API'ye gönder
            RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", tempWav.getName(),
                    RequestBody.create(tempWav, MediaType.parse("audio/wav")))
                .addFormDataPart("model", "whisper-1")
                .addFormDataPart("language", "tr") // Türkçe
                .build();
            
            Request request = new Request.Builder()
                .url("https://api.openai.com/v1/audio/transcriptions")
                .addHeader("Authorization", "Bearer OPENAI_API_KEY_BURAYA")
                .post(requestBody)
                .build();
            
            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    JsonObject json = gson.fromJson(responseBody, JsonObject.class);
                    String transcription = json.get("text").getAsString();
                    
                    tempWav.delete(); // Temizle
                    return transcription;
                } else {
                    LOGGER.error("Whisper API hatası: " + response.code());
                    
                    // Fallback: Basit ses tanıma (Google Web Speech API alternatifi)
                    tempWav.delete();
                    return useFallbackSTT(audioData);
                }
            }
            
        } catch (Exception e) {
            LOGGER.error("❌ Transkripsiyon hatası: ", e);
            return null;
        }
    }
    
    private String useFallbackSTT(byte[] audioData) {
        // Basit fallback - gerçek projede daha iyi bir çözüm kullan
        LOGGER.warn("⚠️ Fallback STT kullanılıyor");
        return "[Ses tanıma servisi yapılandırılmamış - API anahtarı gerekli]";
    }
}
