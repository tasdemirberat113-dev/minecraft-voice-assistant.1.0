package com.minecraftai.assistant;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Gelişmiş özellik: Sesli komutları direkt eyleme çevir
 * Örnek: "Elmas ara" -> /locate ore diamond_ore
 */
public class VoiceCommandExecutor {
    private static final Logger LOGGER = LoggerFactory.getLogger("Commands");
    
    private final Map<Pattern, Consumer<Matcher>> commandPatterns;
    
    public VoiceCommandExecutor() {
        commandPatterns = new HashMap<>();
        registerCommands();
    }
    
    private void registerCommands() {
        // "X ara" komutları
        commandPatterns.put(
            Pattern.compile("(\\w+)\\s+(ara|bul)", Pattern.CASE_INSENSITIVE),
            this::executeLocate
        );
        
        // "Teleport X Y Z" komutları
        commandPatterns.put(
            Pattern.compile("teleport\\s+(-?\\d+)\\s+(-?\\d+)\\s+(-?\\d+)", Pattern.CASE_INSENSITIVE),
            this::executeTeleport
        );
        
        // "Zaman değiştir" komutları
        commandPatterns.put(
            Pattern.compile("(gündüz|gece|sabah|akşam)\\s+yap", Pattern.CASE_INSENSITIVE),
            this::executeTimeSet
        );
        
        // "Hava değiştir" komutları
        commandPatterns.put(
            Pattern.compile("hava\\s+(güneşli|yağmurlu|fırtınalı)\\s+yap", Pattern.CASE_INSENSITIVE),
            this::executeWeather
        );
        
        // "Mod ver" komutları
        commandPatterns.put(
            Pattern.compile("(creative|survival|adventure|spectator)\\s+mod", Pattern.CASE_INSENSITIVE),
            this::executeGameMode
        );
    }
    
    public boolean tryExecuteCommand(String userInput) {
        for (Map.Entry<Pattern, Consumer<Matcher>> entry : commandPatterns.entrySet()) {
            Matcher matcher = entry.getKey().matcher(userInput);
            if (matcher.find()) {
                LOGGER.info("🎯 Komut algılandı: " + userInput);
                entry.getValue().accept(matcher);
                return true;
            }
        }
        return false;
    }
    
    private void executeLocate(Matcher matcher) {
        String item = matcher.group(1);
        String translation = translateToMinecraft(item);
        
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player != null) {
            // /locate komutu
            String command = "/locate structure " + translation;
            executeMinecraftCommand(command);
            
            sendMessage("§a✅ " + item + " aranıyor...");
        }
    }
    
    private void executeTeleport(Matcher matcher) {
        int x = Integer.parseInt(matcher.group(1));
        int y = Integer.parseInt(matcher.group(2));
        int z = Integer.parseInt(matcher.group(3));
        
        String command = String.format("/tp @s %d %d %d", x, y, z);
        executeMinecraftCommand(command);
        
        sendMessage(String.format("§a✅ %d %d %d koordinatlarına ışınlanıyorsun!", x, y, z));
    }
    
    private void executeTimeSet(Matcher matcher) {
        String time = matcher.group(1).toLowerCase();
        
        String mcTime = switch (time) {
            case "gündüz", "sabah" -> "day";
            case "gece" -> "night";
            case "akşam" -> "sunset";
            default -> "day";
        };
        
        executeMinecraftCommand("/time set " + mcTime);
        sendMessage("§a✅ Zaman " + time + " yapıldı!");
    }
    
    private void executeWeather(Matcher matcher) {
        String weather = matcher.group(1).toLowerCase();
        
        String mcWeather = switch (weather) {
            case "güneşli" -> "clear";
            case "yağmurlu" -> "rain";
            case "fırtınalı" -> "thunder";
            default -> "clear";
        };
        
        executeMinecraftCommand("/weather " + mcWeather);
        sendMessage("§a✅ Hava " + weather + " yapıldı!");
    }
    
    private void executeGameMode(Matcher matcher) {
        String mode = matcher.group(1).toLowerCase();
        
        executeMinecraftCommand("/gamemode " + mode);
        sendMessage("§a✅ Oyun modu " + mode + " yapıldı!");
    }
    
    private void executeMinecraftCommand(String command) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player != null) {
            client.player.networkHandler.sendCommand(command.substring(1)); // '/' olmadan
            LOGGER.info("⚡ Komut çalıştırıldı: " + command);
        }
    }
    
    private String translateToMinecraft(String turkish) {
        // Türkçe -> Minecraft ID çevirisi
        return switch (turkish.toLowerCase()) {
            case "köy", "village" -> "village";
            case "kale", "mansion" -> "mansion";
            case "tapınak", "temple" -> "temple";
            case "elmas" -> "diamond_ore";
            case "altın" -> "gold_ore";
            case "demir" -> "iron_ore";
            default -> turkish.toLowerCase();
        };
    }
    
    private void sendMessage(String message) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player != null) {
            client.player.sendMessage(Text.literal(message), false);
        }
    }
}
