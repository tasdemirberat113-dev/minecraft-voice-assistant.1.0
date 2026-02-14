package com.minecraftassistant;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

import javax.sound.sampled.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class MinecraftVoiceAssistant implements ModInitializer {
    
    private static KeyBinding voiceKey;
    private boolean isRecording = false;
    private boolean isProcessing = false;
    private AudioFormat audioFormat;
    private TargetDataLine microphone;
    private ByteArrayOutputStream audioStream;
    
    // Minecraft bilgi veritabanı
    private final String[] GREETINGS = {
        "Selam dostum! Minecraft'ta ne yapmak istiyorsun?",
        "Hey! Sana nasıl yardım edebilirim?",
        "Merhaba madenci! Bugün ne keşfedelim?",
        "Yo! Ben senin Minecraft asistanınım, sor bana!"
    };
    
    @Override
    public void onInitialize() {
        System.out.println("Minecraft Sesli Asistan başlatılıyor!");
        
        // V tuşuna basılınca ses kaydı başlat
        voiceKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "Sesli Asistan",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_V,
            "Minecraft Asistan"
        ));
        
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (voiceKey.wasPressed()) {
                if (!isRecording && !isProcessing) {
                    startListening(client);
                } else if (isRecording) {
                    stopListening(client);
                }
            }
        });
        
        // Ses formatını ayarla
        audioFormat = new AudioFormat(16000.0f, 16, 1, true, false);
    }
    
    private void startListening(MinecraftClient client) {
        isRecording = true;
        audioStream = new ByteArrayOutputStream();
        
        if (client.player != null) {
            client.player.sendMessage(Text.literal("§a🎤 Dinliyorum..."), true);
        }
        
        try {
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, audioFormat);
            microphone = (TargetDataLine) AudioSystem.getLine(info);
            microphone.open(audioFormat);
            microphone.start();
            
            // Ses kaydı thread'i
            new Thread(() -> {
                byte[] buffer = new byte[4096];
                while (isRecording) {
                    int bytesRead = microphone.read(buffer, 0, buffer.length);
                    audioStream.write(buffer, 0, bytesRead);
                }
            }).start();
            
        } catch (LineUnavailableException e) {
            if (client.player != null) {
                client.player.sendMessage(Text.literal("§cMikrofon hatası!"), false);
            }
            e.printStackTrace();
        }
    }
    
    private void stopListening(MinecraftClient client) {
        isRecording = false;
        
        if (microphone != null) {
            microphone.stop();
            microphone.close();
        }
        
        if (client.player != null) {
            client.player.sendMessage(Text.literal("§e🔄 İşleniyor..."), true);
        }
        
        isProcessing = true;
        
        // Ses işleme ve yanıt verme
        new Thread(() -> {
            try {
                byte[] audioData = audioStream.toByteArray();
                String userText = speechToText(audioData);
                
                if (client.player != null) {
                    client.player.sendMessage(Text.literal("§7Sen: " + userText), false);
                }
                
                String response = generateResponse(userText);
                
                if (client.player != null) {
                    client.player.sendMessage(Text.literal("§b🤖 Asistan: " + response), false);
                }
                
                textToSpeech(response);
                
            } catch (Exception e) {
                if (client.player != null) {
                    client.player.sendMessage(Text.literal("§cBir hata oluştu!"), false);
                }
                e.printStackTrace();
            } finally {
                isProcessing = false;
            }
        }).start();
    }
    
    private String speechToText(byte[] audioData) {
        // Basit simülasyon - gerçek projede Web Speech API veya Whisper kullanılabilir
        return "elmas nasıl bulunur";
    }
    
    private String generateResponse(String userInput) {
        userInput = userInput.toLowerCase().trim();
        
        // Selamlama
        if (userInput.contains("merhaba") || userInput.contains("selam") || userInput.contains("hey")) {
            Random rand = new Random();
            return GREETINGS[rand.nextInt(GREETINGS.length)];
        }
        
        // Elmas
        if (userInput.contains("elmas")) {
            return "Elmas bulmak için Y seviyesi 11-12'ye in! Şaft açıp branch mining yap. " +
                   "Demir kazma ile kazıyabilirsin. Fortune enchantment ile daha fazla elmas çıkar!";
        }
        
        // Nether
        if (userInput.contains("nether") || userInput.contains("cehennem")) {
            return "Nether'a gitmek için obsidian ile portal yap, 4x5 boyutunda. " +
                   "Çakmak taşı ile ateşle. Dikkatli ol, çok tehlikeli! " +
                   "Ateş direnci iksiri al yanına.";
        }
        
        // Ender Dragon
        if (userInput.contains("dragon") || userInput.contains("ejder")) {
            return "Ender Dragon'u yenmek için: Önce End Portal'ı bul ve aktifleştir. " +
                   "Kristalleri yok et, sonra dragon'a ok at. İksirler ve elma al yanına!";
        }
        
        // Creeper
        if (userInput.contains("creeper")) {
            return "Creeper'dan korunmak için: Işık kullan, onlar sadece karanlıkta spawn olur. " +
                   "Ssssss sesi duyarsan hemen uzaklaş! Kedi besle, creeper'lar kedilerden korkar.";
        }
        
        // Villager
        if (userInput.contains("villager") || userInput.contains("köylü")) {
            return "Köylülerle ticaret yap! İş blokları koy, mesleklerini değiştir. " +
                   "Zombie köylüyü iyileştir, daha ucuz fiyat verirler. Z