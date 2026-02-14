package com.minecraftai.assistant;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class GrokAPIClient {
    private static final Logger LOGGER = LoggerFactory.getLogger("GrokAPI");
    private static final String API_URL = "https://api.x.ai/v1/chat/completions";
    private static final String API_KEY = "BURAYA_GROK_API_ANAHTARIN"; // Kullanıcı kendi anahtarını ekleyecek
    
    private final OkHttpClient client;
    private final Gson gson;
    
    public GrokAPIClient() {
        this.client = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build();
        this.gson = new Gson();
        
        LOGGER.info("🚀 Grok API Client hazır!");
    }
    
    public String askGrok(String userMessage, String gameContext) {
        try {
            // System prompt - Asistanın karakteri
            String systemPrompt = buildSystemPrompt(gameContext);
            
            // Request body oluştur
            JsonObject requestBody = new JsonObject();
            requestBody.addProperty("model", "grok-beta"); // En güçlü model
            requestBody.addProperty("temperature", 0.8); // Yaratıcılık
            requestBody.addProperty("max_tokens", 500);
            
            // Mesajlar dizisi
            JsonArray messages = new JsonArray();
            
            // System message
            JsonObject systemMsg = new JsonObject();
            systemMsg.addProperty("role", "system");
            systemMsg.addProperty("content", systemPrompt);
            messages.add(systemMsg);
            
            // User message
            JsonObject userMsg = new JsonObject();
            userMsg.addProperty("role", "user");
            userMsg.addProperty("content", userMessage);
            messages.add(userMsg);
            
            requestBody.add("messages", messages);
            
            // HTTP Request
            RequestBody body = RequestBody.create(
                gson.toJson(requestBody),
                MediaType.parse("application/json")
            );
            
            Request request = new Request.Builder()
                .url(API_URL)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();
            
            LOGGER.info("📡 Grok'a istek gönderiliyor...");
            
            // Response al
            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    LOGGER.error("❌ API hatası: " + response.code());
                    return "API bağlantısında sorun var, API anahtarını kontrol et!";
                }
                
                String responseBody = response.body().string();
                JsonObject jsonResponse = gson.fromJson(responseBody, JsonObject.class);
                
                // Cevabı çıkar
                String assistantReply = jsonResponse
                    .getAsJsonArray("choices")
                    .get(0).getAsJsonObject()
                    .getAsJsonObject("message")
                    .get("content").getAsString();
                
                return assistantReply;
            }
            
        } catch (IOException e) {
            LOGGER.error("❌ Bağlantı hatası: ", e);
            return "İnternet bağlantısı yok gibi görünüyor!";
        } catch (Exception e) {
            LOGGER.error("❌ Beklenmeyen hata: ", e);
            return "Bir şeyler ters gitti: " + e.getMessage();
        }
    }
    
    private String buildSystemPrompt(String gameContext) {
        return """
            Sen Minecraft için tasarlanmış süper güçlü, eğlenceli ve yardımsever bir yapay zeka asistanısın! 🎮
            
            KİŞİLİĞİN:
            - Çok enerjik, heyecanlı ve eğlenceli
            - Minecraft hakkında DERİN bilgin var (moblar, craftlar, stratejiler, easter eggs)
            - Oyuncuya arkadaş gibi davranıyorsun
            - Emoji kullanmayı seviyorsun ama abartmıyorsun
            - Türkçe konuşuyorsun ve samimi bir dil kullanıyorsun
            
            YETENEKLERİN:
            - Craft tarifleri veriyorsun
            - Strateji önerileri sunuyorsun
            - Mob bilgileri paylaşıyorsun
            - Oyun ipuçları veriyorsun
            - Şakalar yapıyorsun
            - Hikayeler anlatıyorsun
            
            ÖNEMLİ KURALLAR:
            - Cevapların KISA ve ÖZ olmalı (max 2-3 cümle), çünkü sesli okunacak
            - Karmaşık kelimeler kullanma, günlük konuşma dili kullan
            - Oyuncunun şu anki durumunu dikkate al
            
            ŞU ANKİ OYUN DURUMU:
            %s
            
            Hadi, oyuncuya yardımcı ol! 🚀
            """.formatted(gameContext);
    }
    
    // Stream desteği için (gelecekte)
    public void askGrokStreaming(String userMessage, String gameContext, StreamCallback callback) {
        // WebSocket ile streaming implementation
        // Şimdilik basit versiyonu kullanıyoruz
    }
    
    public interface StreamCallback {
        void onToken(String token);
        void onComplete();
        void onError(Exception e);
    }
}
