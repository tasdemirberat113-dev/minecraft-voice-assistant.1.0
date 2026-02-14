package com.minecraftai.assistant;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MinecraftContextProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger("Context");
    
    public String getContext() {
        MinecraftClient client = MinecraftClient.getInstance();
        
        if (client.player == null || client.world == null) {
            return "Oyuncu şu anda oyunda değil.";
        }
        
        PlayerEntity player = client.player;
        World world = client.world;
        
        StringBuilder context = new StringBuilder();
        
        // Temel bilgiler
        context.append("🎮 OYUN DURUMU:\n");
        
        // Sağlık ve açlık
        float health = player.getHealth();
        float maxHealth = player.getMaxHealth();
        int food = player.getHungerManager().getFoodLevel();
        
        context.append(String.format("- Sağlık: %.1f/%.1f ❤️\n", health, maxHealth));
        context.append(String.format("- Açlık: %d/20 🍖\n", food));
        
        // Konum
        BlockPos pos = player.getBlockPos();
        context.append(String.format("- Konum: X:%d Y:%d Z:%d\n", pos.getX(), pos.getY(), pos.getZ()));
        
        // Biome
        Biome biome = world.getBiome(pos).value();
        String biomeName = biome.toString();
        context.append("- Bölge: " + biomeName + "\n");
        
        // Hava durumu
        boolean raining = world.isRaining();
        boolean thundering = world.isThundering();
        if (thundering) {
            context.append("- Hava: ⚡ Fırtınalı\n");
        } else if (raining) {
            context.append("- Hava: 🌧️ Yağmurlu\n");
        } else {
            context.append("- Hava: ☀️ Güneşli\n");
        }
        
        // Zaman
        long time = world.getTimeOfDay() % 24000;
        String timeOfDay = getTimeOfDay(time);
        context.append("- Zaman: " + timeOfDay + "\n");
        
        // Envanter
        context.append("\n🎒 ENVANTER:\n");
        context.append("- Ana el: " + getItemName(player.getMainHandStack()) + "\n");
        
        // XP
        int xpLevel = player.experienceLevel;
        context.append("- Seviye: " + xpLevel + " ⭐\n");
        
        // Game mode
        String gameMode = client.interactionManager.getCurrentGameMode().getName();
        context.append("- Oyun modu: " + gameMode + "\n");
        
        // Yakındaki moblar (gelişmiş)
        context.append(getNearbyEntities(world, pos));
        
        return context.toString();
    }
    
    private String getItemName(ItemStack stack) {
        if (stack.isEmpty()) {
            return "Boş";
        }
        return stack.getName().getString();
    }
    
    private String getTimeOfDay(long time) {
        if (time < 6000) {
            return "Sabah ☀️";
        } else if (time < 12000) {
            return "Öğlen 🌞";
        } else if (time < 13000) {
            return "Akşam 🌅";
        } else if (time < 18000) {
            return "Gece 🌙";
        } else {
            return "Gece yarısı 🌚";
        }
    }
    
    private String getNearbyEntities(World world, BlockPos playerPos) {
        // Yakındaki mobları tespit et
        var entities = world.getEntitiesByClass(
            net.minecraft.entity.mob.MobEntity.class,
            new net.minecraft.util.math.Box(
                playerPos.add(-20, -10, -20),
                playerPos.add(20, 10, 20)
            ),
            entity -> true
        );
        
        if (entities.isEmpty()) {
            return "- Yakında düşman yok ✅\n";
        }
        
        StringBuilder nearby = new StringBuilder("\n⚠️ YAKINLARDA:\n");
        entities.stream()
            .limit(5) // En fazla 5 mob
            .forEach(mob -> {
                String name = mob.getName().getString();
                double distance = Math.sqrt(mob.squaredDistanceTo(playerPos.getX(), playerPos.getY(), playerPos.getZ()));
                nearby.append(String.format("- %s (%.1fm uzakta)\n", name, distance));
            });
        
        return nearby.toString();
    }
    
    // Craft tarifi bilgisi
    public String getCraftingRecipe(String itemName) {
        // Gelişmiş: Craft tariflerini veritabanından çek
        // Şimdilik basit bir örnek
        return switch (itemName.toLowerCase()) {
            case "crafting table", "işleme masası" -> 
                "4 tahta bloğu 2x2 şeklinde yerleştir";
            case "furnace", "fırın" -> 
                "8 cobblestone ile çevreyi doldur, ortayı boş bırak";
            case "sword", "kılıç" -> 
                "2 materyal (tahta/taş/demir/elmas) dikey + 1 sopa";
            default -> 
                "Bu eşyanın tarifini bilmiyorum, ama araştırabilirim!";
        };
    }
}
