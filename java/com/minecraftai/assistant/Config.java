package com.minecraftai.assistant;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Config {
    private static final Logger LOGGER = LoggerFactory.getLogger("Config");
    private static final File CONFIG_FILE = new File("config/minecraft-voice-assistant.json");
    
    // API Anahtarları
    public String grokApiKey = "BURAYA_GROK_API_KEY";
    public String openaiApiKey = "BURAYA_OPENAI_API_KEY"; // STT ve TTS için
    
    // Ayarlar
    public boolean enableVoiceInput = true;
    public boolean enableVoiceOutput = true;
    public float voiceVolume = 1.0f;
    public float voiceSpeed = 1.0f;
    public String voiceModel = "nova"; // alloy, echo, fable, onyx, shimmer, nova
    
    // Grok ayarları
    public String grokModel = "grok-beta";
    public float grokTemperature = 0.8f;
    public int grokMaxTokens = 500;
    
    public static Config load() {
        CONFIG_FILE.getParentFile().mkdirs();
        
        if (CONFIG_FILE.exists()) {
            try (FileReader reader = new FileReader(CONFIG_FILE)) {
                Gson gson = new Gson();
                Config config = gson.fromJson(reader, Config.class);
                LOGGER.info("✅ Config yüklendi");
                return config;
            } catch (IOException e) {
                LOGGER.error("❌ Config okuma hatası: ", e);
            }
        }
        
        // Varsayılan config oluştur
        Config config = new Config();
        config.save();
        LOGGER.info("📝 Yeni config oluşturuldu: " + CONFIG_FILE.getAbsolutePath());
        LOGGER.warn("⚠️ API anahtarlarını config dosyasına eklemeyi unutma!");
        return config;
    }
    
    public void save() {
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(this, writer);
            LOGGER.info("💾 Config kaydedildi");
        } catch (IOException e) {
            LOGGER.error("❌ Config yazma hatası: ", e);
        }
    }
}
