package com.minecraftai.assistant;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MinecraftVoiceAssistant implements ModInitializer {
    public static final String MOD_ID = "minecraft-voice-assistant";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    
    private static KeyBinding voiceActivationKey;
    private static VoiceRecorder voiceRecorder;
    private static GrokAPIClient grokClient;
    private static TextToSpeech tts;
    private static MinecraftContextProvider contextProvider;
    
    private static boolean isRecording = false;

    @Override
    public void onInitialize() {
        LOGGER.info("🎮 Minecraft Sesli Asistan başlatılıyor...");
        
        // Tuş ataması (V tuşu - Voice)
        voiceActivationKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.minecraftai.voice_activation",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_V,
            "category.minecraftai.main"
        ));
        
        // Componentleri başlat
        voiceRecorder = new VoiceRecorder();
        grokClient = new GrokAPIClient();
        tts = new TextToSpeech();
        contextProvider = new MinecraftContextProvider();
        
        // Tick eventi - her frame kontrol
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (voiceActivationKey.wasPressed()) {
                toggleVoiceRecording();
            }
        });
        
        LOGGER.info("✅ Sesli asistan hazır! V tuşuna bas ve konuş!");
    }
    
    private void toggleVoiceRecording() {
        if (!isRecording) {
            // Kaydı başlat
            LOGGER.info("🎤 Dinliyorum...");
            isRecording = true;
            voiceRecorder.startRecording();
            
            // Oyuncuya bilgi ver
            if (MinecraftClient.getInstance().player != null) {
                MinecraftClient.getInstance().player.sendMessage(
                    Text.literal("§a🎤 Dinliyorum... (V'yi bırak)"), false
                );
            }
        } else {
            // Kaydı durdur ve işle
            LOGGER.info("🛑 Kayıt durduruluyor...");
            isRecording = false;
            
            byte[] audioData = voiceRecorder.stopRecording();
            processVoiceInput(audioData);
        }
    }
    
    private void processVoiceInput(byte[] audioData) {
        new Thread(() -> {
            try {
                // Ses -> Text (Speech to Text)
                LOGGER.info("🔄 Ses metne çevriliyor...");
                String userInput = voiceRecorder.transcribeAudio(audioData);
                
                if (userInput == null || userInput.isEmpty()) {
                    sendChatMessage("§cAnlayamadım, tekrar söyler misin?");
                    return;
                }
                
                LOGGER.info("📝 Kullanıcı dedi: " + userInput);
                sendChatMessage("§7Dediğin: §f" + userInput);
                
                // Minecraft bağlam bilgisi topla
                String gameContext = contextProvider.getContext();
                
                // Grok'a sor
                LOGGER.info("🤖 Grok'a soruyorum...");
                sendChatMessage("§e🤔 Düşünüyorum...");
                
                String response = grokClient.askGrok(userInput, gameContext);
                
                if (response != null && !response.isEmpty()) {
                    LOGGER.info("💬 Grok cevap verdi: " + response);
                    sendChatMessage("§b🤖 Asistan: §f" + response);
                    
                    // Sesli yanıt ver
                    tts.speak(response);
                } else {
                    sendChatMessage("§cBir sorun oluştu, tekrar dener misin?");
                }
                
            } catch (Exception e) {
                LOGGER.error("❌ Hata oluştu: ", e);
                sendChatMessage("§cBir hata oluştu: " + e.getMessage());
            }
        }).start();
    }
    
    private void sendChatMessage(String message) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player != null) {
            client.player.sendMessage(Text.literal(message), false);
        }
    }
    
    public static GrokAPIClient getGrokClient() {
        return grokClient;
    }
    
    public static MinecraftContextProvider getContextProvider() {
        return contextProvider;
    }
}
